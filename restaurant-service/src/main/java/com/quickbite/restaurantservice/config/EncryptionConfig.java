package com.quickbite.restaurantservice.config;
import java.util.Base64;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

@Configuration
@ConfigurationProperties(prefix = "")
public class EncryptionConfig {
    private String key;

    public String getKey(){
        return key;
    }

    public void setKey(String key){
        this.key = key;
    }

    private byte[] keyDecoder(String key){
        if(key == null || key.isBlank()){
            throw new IllegalArgumentException("Encryption key cannot be null or blank");
        }

        byte[] decodedBytes = Base64.getDecoder().decode(key);
        this.validateKey(decodedBytes);
        return decodedBytes;
    }

    private  void validateKey(byte[] decodedBytes){
        if(decodedBytes.length != 32){
            throw new IllegalArgumentException("AES-256 key must be exactly 32 bytes");
        }
    }

    @Bean
    public SecretKey generateSecretKey(){
        byte[] bytes = keyDecoder(key);
        return new SecretKeySpec(bytes, "AES");
    }
}
