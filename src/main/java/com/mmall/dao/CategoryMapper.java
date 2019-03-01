package com.mmall.dao;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.Category;
import org.apache.ibatis.annotations.Param;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface CategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Category record);

    int checkCategoryName(@Param("parentId") Integer parentId, @Param("categoryName") String categoryName);

    int insertSelective(Category record);

    Category selectByPrimaryKey(Integer id);

    List<Category> selectGetCategory(Integer categoryId);

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);

    int updateCategoryNameBycategoryId(@Param("categoryId") Integer categoryId, @Param("categoryName") String categoryName);

    List<Integer> getDeepCategory(Integer categoryId);

    int checkCategoryId(Integer categoryId);
}