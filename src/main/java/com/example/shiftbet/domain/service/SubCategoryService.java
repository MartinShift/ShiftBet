package com.example.shiftbet.domain.service;

import com.example.shiftbet.domain.entity.Subcategory;
import com.example.shiftbet.domain.repository.*;
import jakarta.persistence.TypedQuery;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class SubCategoryService {

    private final SubcategoryRepository subcategoryRepository;
@Autowired
    public SubCategoryService(SubcategoryRepository subcategoryRepository) {
        this.subcategoryRepository = subcategoryRepository;
    }

    public List<Subcategory> getAll() {
        return subcategoryRepository.findAll();
    }

    public Subcategory get(long id) {
        return subcategoryRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Subcategory not found with id: " + id));
       }

    public void add(Subcategory  subcategory ) {
        subcategoryRepository.save(subcategory);
    }
    public List<Subcategory> getTopSubcategories() {
        List<Subcategory> allSubcategories = subcategoryRepository.findTopSubcategories();
        return allSubcategories.subList(0, Math.min(10, allSubcategories.size()));
    }

    public void update(long id, Subcategory subcategory) {
        subcategoryRepository.save(subcategory);
    }
    public void delete(long id) {
        Subcategory subcategory = subcategoryRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Subcategory not found with id: " + id));
        subcategoryRepository.delete(subcategory);
    }


}

