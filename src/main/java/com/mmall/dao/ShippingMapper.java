package com.mmall.dao;

import com.mmall.pojo.Shipping;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShippingMapper {
    int deleteByPrimaryKey(Integer id);

    int deleteByIdAndUserId(@Param("id") Integer shippingId, @Param("userId") Integer userId);

    int insert(Shipping record);

    int insertSelective(Shipping record);

    Shipping selectByPrimaryKey(Integer id);

    Shipping selectShippingByIdAndUserId(@Param("id") Integer shippingId, @Param("userId") Integer userId);

    Shipping selectNewShippingByUserId(Integer userId);

    List<Shipping> selectShippingListByUserId(Integer userId);

    int updateByPrimaryKeySelective(Shipping record);

    int updateByPrimaryKey(Shipping record);

    int updateByShipping(Shipping record);
}