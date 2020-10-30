package com.lyl.web.service.impl;

import com.lyl.web.entity.Category;
import com.lyl.web.mapper.CategoryMapper;
import com.lyl.web.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> findAllCategories() {
        return categoryMapper.findAllCategories();
    }
}
