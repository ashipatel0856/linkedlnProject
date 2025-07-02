package com.ashish.linkedInProject.uploader_service.config;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Map;

@Configuration
public class UploaderConfig {
    @Value("${cloudinary.cloud-name}")
    private String cloudName;

    @Value("${cloudinary.api-key}")
    private String apiKey;

    @Value("${cloudinary.api-secret}")
    private String apiSecret;

    @Bean
    public Cloudinary cloudinary() {
        Map<String,String> config = Map.of(
                "cloud_name",cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret
        );
        return new Cloudinary(config);
    }


    // configure the google cloud
//    @Value("${gcloud.storage-access-key}")
//    private String gcloudStorageAccessKey;
//
//    @Bean
//    public String storage() throws IOException{
//        return  StorageOptions.newBuilder()
//                .setCredentials(ServiceAccountCredentials.fromStream(
//                        new ByteArrayInputStream(gcloudStorageAccessKey.getBytes())
//                ))
//                .build()
//                .getService();
//
//    }
}
