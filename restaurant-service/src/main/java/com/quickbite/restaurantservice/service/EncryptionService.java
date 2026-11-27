package com.quickbite.restaurantservice.service;

import com.quickbite.restaurantservice.exception.EncryptionException;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import java.util.Base64;

@Service
public class EncryptionService {
    private final SecretKey key;
    public EncryptionService(SecretKey key) {
        this.key = key;
    }

    private static final int IV_BYTES = 12;
    private static final int TAG_BITS = 128;
    private static final SecureRandom RANDOM = new SecureRandom();

    public String encrypt(String plainText){
        if(plainText == null || plainText.isBlank()){
            throw new IllegalArgumentException("Cannot encrypt an empty value");
        }
        byte[] plainTxtBytes = plainText.getBytes(StandardCharsets.UTF_8);
        byte[] iv = new byte[IV_BYTES];
        RANDOM.nextBytes(iv);
        byte[] cipherTxt;
        try{
            Cipher c = Cipher.getInstance("AES/GCM/NoPadding");
            c.init(Cipher.ENCRYPT_MODE, key, new GCMParameterSpec(TAG_BITS, iv));
            cipherTxt = c.doFinal(plainTxtBytes);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidAlgorithmParameterException |
                 InvalidKeyException | BadPaddingException | IllegalBlockSizeException e){
            throw new EncryptionException("Failed to encrypt sensitive data: ", e);
        }
        byte[] encryptPayload = new byte[iv.length + cipherTxt.length];
        System.arraycopy(iv, 0 , encryptPayload, 0, iv.length);
        System.arraycopy(cipherTxt,0, encryptPayload, iv.length, cipherTxt.length);
        return Base64.getEncoder().encodeToString(encryptPayload);
    }

    public String decrypt(String payload){
        if(payload == null || payload.isBlank()){
            throw new IllegalArgumentException("Cannot decrypt an empty payload");
        }
        byte[] decodedPayload;
        try {
            decodedPayload = Base64.getDecoder().decode(payload);
        }catch (IllegalArgumentException e){
            throw new EncryptionException("Invalid encrypted payload encoding", e);
        }
        if(decodedPayload.length < 28){
            throw new EncryptionException("Encrypted payload is malformed or incomplete");
        }
        GCMParameterSpec spec = new GCMParameterSpec(TAG_BITS, decodedPayload, 0,IV_BYTES);
        byte[] decryptedTxt;
        try{
            Cipher c = Cipher.getInstance("AES/GCM/NoPadding");
            c.init(Cipher.DECRYPT_MODE, key, spec);
            decryptedTxt = c.doFinal(decodedPayload, IV_BYTES, decodedPayload.length - IV_BYTES);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidAlgorithmParameterException |
                InvalidKeyException | BadPaddingException | IllegalBlockSizeException e){
            throw new EncryptionException("Unable to decrypt payload. Authentication failed or data is invalid: ", e);
        }

        return new String(decryptedTxt, StandardCharsets.UTF_8);

    }
}
