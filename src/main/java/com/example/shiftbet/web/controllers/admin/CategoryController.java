package com.example.shiftbet.web.controllers.admin;

import com.example.shiftbet.domain.entity.Category;
import com.example.shiftbet.domain.entity.Category;
import com.example.shiftbet.domain.service.CategoryService;
import com.example.shiftbet.domain.service.ImageFileService;
import com.example.shiftbet.domain.service.SubCategoryService;
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
public class    CategoryController {

    private final CategoryService categoryService;

    private final ImageFileService imageFileService;

    public CategoryController(CategoryService categoryService, ImageFileService imageFileService) {
        this.categoryService = categoryService;
        this.imageFileService = imageFileService;
    }
    @GetMapping("/admin/category/add")
    public String showAdd(Model model) {
        model.addAttribute("category ", new Category ());
        model.addAttribute("categories", categoryService.getAll());
        return "admin/category/add";
    }

    @GetMapping("/admin/category/view")
    public String showAllSubcategories(Model model) {
        List<Category> categories = categoryService.getAll();
        model.addAttribute("categories", categories);
        return "admin/category/view";
    }
    @PostMapping("/admin/category/add")
    public String add(Category  category, MultipartHttpServletRequest request) throws IOException {
        category.setImageUrl(imageFileService.saveImage(request.getFile("image")));
        categoryService.add(category);
        return "redirect:/admin/category/view";
    }

    @GetMapping("/admin/category/edit/{id}")
    public String showEditCategoryForm(@PathVariable("id") int id, Model model) {
        model.addAttribute("category", categoryService.get(id));
        model.addAttribute("categories", categoryService.getAll());
        return "admin/category/edit";
    }

    @PostMapping("/admin/category/edit")
    public String editCategory(Category category, MultipartHttpServletRequest request) throws IOException {
        String url = imageFileService.saveImage(request.getFile("image"));
        if(url != null)
        {
            category.setImageUrl(url);
        }
        categoryService.update(category.getId(),category);
        return "redirect:/admin/category/view";
    }
    @GetMapping("/admin/category/delete/{id}")
    public String deleteCategory(@PathVariable("id") int id) {
        categoryService.delete(id);
        return "redirect:/admin/category/view";
    }
}

