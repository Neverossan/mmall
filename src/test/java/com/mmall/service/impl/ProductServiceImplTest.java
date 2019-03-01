package com.mmall.service.impl;

import com.mmall.common.ServerResponse;
import com.mmall.dao.ProductMapper;
import com.mmall.service.IProductService;
import com.mmall.vo.ProductDetailVo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * @Auther: HP
 * @Date: 2019/3/1 10:35
 * @Description:
 */
public class ProductServiceImplTest {

    @Autowired
    private IProductService iProductService;

    @Test
    public void getProductdetail() {
         ServerResponse<ProductDetailVo> productDetailVo = iProductService.getProductdetail(26);
        System.out.println(productDetailVo.isSuccess());
    }
}