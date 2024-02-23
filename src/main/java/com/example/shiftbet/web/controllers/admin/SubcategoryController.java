package com.example.shiftbet.web.controllers.admin;

import com.example.shiftbet.domain.entity.Category;
import com.example.shiftbet.domain.entity.Subcategory;
import com.example.shiftbet.domain.service.CategoryService;
import com.example.shiftbet.domain.service.ImageFileService;
import com.example.shiftbet.domain.service.SubCategoryService;
import com.example.shiftbet.web.dto.SubcategoryDTO;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.List;
@Controller
public class SubcategoryController {

    private final SubCategoryService subcategoryService;
    private final CategoryService categoryService;

    private final ImageFileService imageFileService;

    public SubcategoryController(SubCategoryService subcategoryService, CategoryService categoryService, ImageFileService imageFileService) {
        this.subcategoryService = subcategoryService;
        this.categoryService = categoryService;
        this.imageFileService = imageFileService;
    }
    @GetMapping("/subcategory/add")
    public String showAdd(Model model) {
        model.addAttribute("subcategory", new Subcategory());
        model.addAttribute("categories", categoryService.getAll());
        return "admin/subcategory/add";
    }

    @GetMapping("/subcategory/view")
    public String showAllSubcategories(Model model) {
        List<Subcategory> subcategories = subcategoryService.getAll();
        model.addAttribute("subcategories", subcategories);
        return "admin/subcategory/view";
    }
    @PostMapping("/subcategory/add")
    public String add(Subcategory  subcategory, MultipartHttpServletRequest request) throws IOException {
       subcategory.setImageUrl(imageFileService.saveImage(request.getFile("image")));
        subcategoryService.add(subcategory);
        return "redirect:/subcategory/view";
    }

    @GetMapping("/subcategory/edit/{id}")
    public String showEditSubcategoryForm(@PathVariable("id") int id, Model model) {
        model.addAttribute("subcategory", subcategoryService.get(id));
        model.addAttribute("categories", categoryService.getAll());
        return "admin/subcategory/edit";
    }

    @PostMapping("/subcategory/edit")
    public String editSubcategory(Subcategory subcategory, MultipartHttpServletRequest request) throws IOException {
        String url = imageFileService.saveImage(request.getFile("image"));
       if(url != null)
       {
           subcategory.setImageUrl(url);
       }
        subcategoryService.update(subcategory.getId(),subcategory);
        return "redirect:/subcategory/view";
    }
    @GetMapping("/subcategory/delete/{id}")
    public String deleteSubcategory(@PathVariable("id") int id) {
        subcategoryService.delete(id);
        return "redirect:/subcategory/view";
    }
}

