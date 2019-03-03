package com.mmall.dao;

import com.mmall.pojo.Cart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CartMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Cart record);

    int insertSelective(Cart record);

    Cart selectByPrimaryKey(Integer id);

    int deleteByUserIdProductIds(@Param("userId") Integer userId, @Param("productIdList") List<String> productIdList);

    List<Cart> selectCartByUserId(Integer userId);

    int updateByPrimaryKeySelective(Cart record);

    int updateByPrimaryKey(Cart record);

    int selectCartProductCheckedStatusByUserId(Integer userId);

    Cart selectCartByUserIdProductId(@Param("userId") Integer userId, @Param("productId") Integer productId);

    int updateOrUnSelect(@Param("userId") Integer userId, @Param("productId") Integer productId,@Param("checked") Integer checked);

    int getCartProductCount(Integer userId);
}