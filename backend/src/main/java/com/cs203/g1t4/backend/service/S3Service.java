package com.cs203.g1t4.backend.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.cs203.g1t4.backend.models.exceptions.InvalidImageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

@Service
public class S3Service {
    @Autowired
    private AmazonS3 s3;

    public void putObject(String bucketName, String key, MultipartFile file) {

        try {
            // Create the ObjectMetadata to store information regarding the file to be input
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(file.getContentType());
            objectMetadata.setContentLength(file.getSize());

            // Put the file and its information into s3
            s3.putObject(bucketName, key, file.getInputStream(), objectMetadata);


        // Catch IOException in the event that the File could not be saved properly
        } catch (IOException e) {
            throw new InvalidImageException();
        }
    }

    public String getObjectURL(String bucketName, String key) {
        Calendar calendar = Calendar.getInstance();

        // set the expiry
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR, 1); // Generated URL will be valid for 1 hour

        // get the signed URL
        return s3.generatePresignedUrl(bucketName, key, calendar.getTime()).toString();
    }
}
