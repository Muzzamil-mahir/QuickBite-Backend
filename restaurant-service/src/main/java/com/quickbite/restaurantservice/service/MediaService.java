package com.quickbite.restaurantservice.service;

import com.quickbite.restaurantservice.dto.ConfirmResponseDto;
import com.quickbite.restaurantservice.dto.PresignResponseDto;
import com.quickbite.restaurantservice.entity.MenuItem;
import com.quickbite.restaurantservice.entity.MenuItemImage;
import com.quickbite.restaurantservice.entity.RestaurantImage;
import com.quickbite.restaurantservice.entity.Restaurants;
import com.quickbite.restaurantservice.enums.ImageType;
import com.quickbite.restaurantservice.exception.InvalidMediaException;
import com.quickbite.restaurantservice.exception.MediaStorageException;
import com.quickbite.restaurantservice.exception.ObjectNotFoundException;
import com.quickbite.restaurantservice.exception.UploadNotAuthorizedException;
import com.quickbite.restaurantservice.repository.MenuItemImageRepository;
import com.quickbite.restaurantservice.repository.MenuItemRepository;
import com.quickbite.restaurantservice.repository.RestaurantImageRepository;
import com.quickbite.restaurantservice.repository.RestaurantsRepository;
import io.minio.*;
import io.minio.errors.ErrorResponseException;
import io.minio.http.Method;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class MediaService {

    private static final long MAX_IMAGE_SIZE = 5_242_880L;
    @Value("${minio.bucket.name}")
    private String bucketName;
    @Value("${minio.url}")
    private String minioUrl;
    private static final Map<String, String> MIME_TO_EXTENSION = Map.of(
            "image/jpeg", "jpg",
            "image/png",  "png",
            "image/webp", "webp"
    );

    private final MinioClient minioClient;
    private final StringRedisTemplate redisTemplate;
    private final MenuItemRepository menuItemRepository;
    private final MenuItemImageRepository menuItemImageRepository;
    private final RestaurantsRepository restaurantsRepository;
    private final RestaurantImageRepository restaurantImageRepository;



    public MediaService(
            MinioClient minioClient,
            StringRedisTemplate redisTemplate, MenuItemRepository menuItemRepository, MenuItemImageRepository menuItemImageRepository, RestaurantsRepository restaurantsRepository, RestaurantImageRepository restaurantImageRepository
    ){
        this.minioClient = minioClient;
        this.redisTemplate = redisTemplate;
        this.menuItemRepository = menuItemRepository;
        this.menuItemImageRepository = menuItemImageRepository;
        this.restaurantsRepository = restaurantsRepository;
        this.restaurantImageRepository = restaurantImageRepository;
    }

    public PresignResponseDto getRestaurantImgUploadURL(UUID restaurantId, String mimeType){
        Restaurants restaurant = (Restaurants) restaurantsRepository.findById(restaurantId).orElseThrow(
                () -> new ObjectNotFoundException("Restaurant object not found")
        );

        if(!isAllowedContentType(mimeType)){
            throw new InvalidMediaException("Unsupported image type" + mimeType);
        }
        String objectKey = "restaurants/" + restaurantId + "/photos/" + UUID.randomUUID() + "." + MIME_TO_EXTENSION.get(mimeType);
        String uploadUrl = getPresignedUrl(objectKey);
        storePendingUpload(objectKey);
        return  new PresignResponseDto(
                uploadUrl,
                objectKey
        );
    }
    public PresignResponseDto getItemImgUploadURL(UUID restaurantId, UUID itemId, String mimeType){
        MenuItem item = menuItemRepository.findById(itemId).orElseThrow(() ->
                new ObjectNotFoundException("Menu item not found")
        );

        if(!item.getRestaurant().getRestaurantId().equals(restaurantId)){
            throw new UploadNotAuthorizedException("Item does not belong to this restaurant");
        }

        if(!isAllowedContentType(mimeType)){
            throw new InvalidMediaException("Unsupported image type" + mimeType);
        }
        String objectKey = "restaurants/" + restaurantId + "/items/" + itemId + "/photos/" + UUID.randomUUID() + "." + MIME_TO_EXTENSION.get(mimeType);
        String uploadUrl = getPresignedUrl(objectKey);
        storePendingUpload(objectKey);
        return  new PresignResponseDto(
                uploadUrl,
                objectKey
        );
    }

    public ConfirmResponseDto confirmResturantImage(UUID restaurantId, String objectKey, ImageType imageType){
        Restaurants restaurant = (Restaurants) restaurantsRepository.findById(restaurantId).orElseThrow(
                () -> new ObjectNotFoundException("Restaurant object not found")
        );

        String publicUrl = getPublicImgUrl(objectKey);
        int displayOrder = (int) restaurantImageRepository.countByItemRestaurantId(restaurantId);
        RestaurantImage image = new RestaurantImage(
                restaurant,
                publicUrl,
                objectKey,
                imageType,
                displayOrder,
                OffsetDateTime.now()

        );
        restaurantImageRepository.save(image);
        return new ConfirmResponseDto(publicUrl);
    }

    public ConfirmResponseDto confirmItemImage(UUID restaurantId, UUID itemId, String objectKey){
        MenuItem item = menuItemRepository.findById(itemId).orElseThrow(() ->
                new ObjectNotFoundException("Menu item not found")
        );

        if(!item.getRestaurant().getRestaurantId().equals(restaurantId)){
            throw new UploadNotAuthorizedException("Item does not belong to this restaurant");
        }

        String publicUrl = getPublicImgUrl(objectKey);

        int displayOrder = (int) menuItemImageRepository.countByItemMenuItemId(itemId);

        MenuItemImage image = new MenuItemImage(
                item,
                objectKey,
                publicUrl,
                displayOrder,
                OffsetDateTime.now()
        );

        menuItemImageRepository.save(image);

        removePendingUpload(objectKey);
        return new ConfirmResponseDto(publicUrl);
    }

    public String getPublicImgUrl(String objectKey){
        if(!isPendingUploadPresent(objectKey)){
            throw new UploadNotAuthorizedException("Upload invalid or expired");
        }
        StatObjectResponse metadata = getObjectMetadata(objectKey);

        String contetType = metadata.contentType();
        long size = metadata.size();

        if(!isAllowedContentType(contetType)){
            deleteObject(objectKey);
            throw new InvalidMediaException("Unsupported image type" + contetType);
        }

        if(size > MAX_IMAGE_SIZE){
            deleteObject(objectKey);
            throw new InvalidMediaException("Image size exceeds the maximum allowed size of 5 MB");
        }

        return minioUrl + "/" + bucketName + "/" + objectKey;
    }

//=============--------MinIO--------=================================
    private String getPresignedUrl(String objectKey){
        try{
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.PUT)
                            .bucket(bucketName)
                            .object(objectKey)
                            .expiry(10, TimeUnit.MINUTES)
                            .build()
            );
        } catch (Exception e){
            throw new MediaStorageException("Failed to generate presigned upload URL", e);
        }
    }

    private StatObjectResponse getObjectMetadata(String objKey){
        try{
            return minioClient.statObject(
                    StatObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objKey)
                            .build()
            );
        } catch (ErrorResponseException e){
            throw new ObjectNotFoundException("Uploaded object not found");
        } catch (Exception e){
            throw new MediaStorageException("Failed to read object metadata", e);
        }
    }

    private void deleteObject(String objectKey){
        try{
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectKey)
                            .build()
            );
        } catch (Exception e){
            throw new MediaStorageException("Failed to delete invalid uploaded object",e);
        }
    }


//====================-------Redis--------===========================================
    private void storePendingUpload(String objectKey) {
        String redisKey = "restaurant:upload:pending:" + objectKey;

        redisTemplate.opsForValue().set(
                redisKey,
                objectKey,
                15,
                TimeUnit.MINUTES
        );
    }

    private boolean isPendingUploadPresent(String objectKey){
        String redisKey = "restaurant:upload:pending:" + objectKey;
        String pendingObjKey = redisTemplate.opsForValue().get(redisKey);
        return pendingObjKey != null;
    }

    private void removePendingUpload(String objectKey) {
        String redisKey = "restaurant:upload:pending:" + objectKey;
        redisTemplate.delete(redisKey);
    }




    private  boolean isAllowedContentType(String contentType){
        return  MIME_TO_EXTENSION.containsKey(contentType);
    }

}
