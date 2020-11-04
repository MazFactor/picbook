package com.lyl.web.mapper;

import com.lyl.web.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface CategoryMapper {
    List<Category> findAllCategories();

    Category findCategoryByName(String category);

    void insertNewCategory(Category categoryExisted);

    Category findCategoryById(Integer category_id);
}
