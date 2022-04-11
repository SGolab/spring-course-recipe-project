package com.example.recipeproject.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    void saveImageFile(long recipeId, MultipartFile file);
}
