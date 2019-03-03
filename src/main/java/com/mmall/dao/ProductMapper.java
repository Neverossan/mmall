package com.mmall.dao;

import com.mmall.pojo.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    List<Product> selectGetList();

    List<Product> searchByIdOrName(@Param("productId") int productId, @Param("productName") String productName);

    List<Product> getProductList(@Param("categoryId") Integer categoryId, @Param("keyword") String keyword);

    List<Product> selectByNameAndCategoryIds(@Param("keyword") String keyword,@Param("categoryIdList") List<Integer> categoryIdList);

}