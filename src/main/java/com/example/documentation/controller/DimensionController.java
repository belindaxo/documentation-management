package com.example.documentation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.documentation.model.Dimension;
import com.example.documentation.service.DimensionService;

@RestController
@RequestMapping("/api/dimensions")
public class DimensionController {

    @Autowired
    private DimensionService dimensionService;

    @GetMapping
    public List<Dimension> getAllDimensions() {
        return dimensionService.getAllDimensions();
    }

    @PostMapping
    public void createDimension(@RequestBody Dimension dimension) {
        dimensionService.addDimension(dimension);
    }

    @PutMapping("/{id}")
    public void updateDimension(@PathVariable Long id, @RequestBody Dimension dimensionDetails) {
        Dimension dimension = new Dimension();
        dimension.setId(id);
        dimension.setName(dimensionDetails.getName());
        dimension.setDescription(dimensionDetails.getDescription());
        dimensionService.updateDimension(dimension);
    }

    @DeleteMapping("/{id}")
    public void deleteDimension(@PathVariable Long id) {
        dimensionService.deleteDimension(id);
    }
}