package com.example.documentation.service;

import com.example.documentation.model.Dimension;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class DimensionService {
    private static final String FILE_PATH = "src/main/resources/dimensions.json";
    private ObjectMapper objectMapper = new ObjectMapper();

    public List<Dimension> getAllDimensions() {
        try {
            return objectMapper.readValue(new File(FILE_PATH), new TypeReference<List<Dimension>>() {});
        } catch (IOException e) {
            throw new RuntimeException("Failed to read dimensions from file", e);
        }
    }

    public void addDimension(Dimension dimension) {
        try {
            List<Dimension> dimensions = getAllDimensions();
            dimensions.add(dimension);
            objectMapper.writeValue(new File(FILE_PATH), dimensions);
        } catch (IOException e) {
            throw new RuntimeException("Failed to add dimension to file", e);
        }
    }

    public void updateDimension(Dimension updatedDimension) {
        try {
            List<Dimension> dimensions = getAllDimensions();
            for (Dimension dimension : dimensions) {
                if (dimension.getId().equals(updatedDimension.getId())) {
                    dimension.setName(updatedDimension.getName());
                    dimension.setDescription(updatedDimension.getDescription());
                    break;
                }
            }
            objectMapper.writeValue(new File(FILE_PATH), dimensions);
        } catch (IOException e) {
            throw new RuntimeException("Failed to update dimension in file", e);
        }
    }

    public void deleteDimension(long id) {
        try {
            List<Dimension> dimensions = getAllDimensions();
            dimensions.removeIf(dimension -> dimension.getId() == id);
            objectMapper.writeValue(new File(FILE_PATH), dimensions);
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete dimension from file", e);
        }
    }
}