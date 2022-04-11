package com.example.recipeproject.services;

import com.example.recipeproject.domain.Recipe;
import com.example.recipeproject.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    private final RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void saveImageFile(long recipeId, MultipartFile file) {

        try {
            Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

            if (recipeOptional.isPresent()) {
                Recipe recipe = recipeOptional.get();

                byte[] bytes = file.getBytes();
                Byte[] byteObjects = new Byte[bytes.length];

                int i = 0;
                // Associating Byte array values with bytes. (byte[] to Byte[])
                for (byte b : bytes)
                    byteObjects[i++] = b;  // Autoboxing.

                recipe.setImage(byteObjects);
                recipeRepository.save(recipe);
            } else {
                log.error("Recipe not found for id: " + recipeId);
            }

            log.debug("Received a file.");

        } catch (IOException e) {
            //todo handle better
            log.error("Error occured: " + e);

            e.printStackTrace();
        }
    }
}
