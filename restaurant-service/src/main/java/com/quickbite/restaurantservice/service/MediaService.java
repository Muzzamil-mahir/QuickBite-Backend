package com.quickbite.restaurantservice.service;

import com.quickbite.restaurantservice.dto.PresignResponse;
import com.quickbite.restaurantservice.exception.InvalidMediaException;
import com.quickbite.restaurantservice.exception.MediaStorageException;
import com.quickbite.restaurantservice.exception.ObjectNotFoundException;
import com.quickbite.restaurantservice.exception.UploadNotAuthorizedException;
import io.minio.*;
import io.minio.errors.ErrorResponseException;
import io.minio.http.Method;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class MediaService {

    private static final long MAX_IMAGE_SIZE = 5_242_880L;
    private final MinioClient minioClient;
    private final StringRedisTemplate redisTemplate;
    @Value("${minio.bucket.name}")
    private String bucketName;
    @Value("${minio.url}")
    private String minioUrl;
    private static final Map<String, String> MIME_TO_EXTENSION = Map.of(
            "image/jpeg", "jpg",
            "image/png",  "png",
            "image/webp", "webp"
    );


    public MediaService(
            MinioClient minioClient,
            StringRedisTemplate redisTemplate
    ){
        this.minioClient = minioClient;
        this.redisTemplate = redisTemplate;
    }

    public PresignResponse getRestaurantImgUploadURL(UUID restaurantId, String mimeType){
        if(!isAllowedContentType(mimeType)){
            throw new InvalidMediaException("Unsupported image type" + mimeType);
        }
        String objectKey = "restaurants/" + restaurantId + "/photos/" + UUID.randomUUID() + "." + MIME_TO_EXTENSION.get(mimeType);
        String uploadUrl = getPresignedUrl(objectKey);
        storePendingUpload(objectKey);
        return  new PresignResponse(
                uploadUrl,
                objectKey
        );
    }
    public PresignResponse getItemImgUploadURL(UUID restaurantId, UUID itemId, String mimeType){
        if(!isAllowedContentType(mimeType)){
            throw new InvalidMediaException("Unsupported image type" + mimeType);
        }
        String objectKey = "restaurants/" + restaurantId + "/items/" + itemId + "/photos/" + UUID.randomUUID() + "." + MIME_TO_EXTENSION.get(mimeType);
        String uploadUrl = getPresignedUrl(objectKey);
        storePendingUpload(objectKey);
        return  new PresignResponse(
                uploadUrl,
                objectKey
        );
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
        removePendingUpload(objectKey);
        return minioUrl + "/" + bucketName + "/" + objectKey;
    }


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
