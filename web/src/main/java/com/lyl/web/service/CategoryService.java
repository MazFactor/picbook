package com.lyl.web.service;

import com.lyl.web.entity.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAllCategories();

    Category findCategoryByName(String category);

    void insertNewCategory(Category categoryExisted);
}
