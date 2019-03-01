package com.mmall.service;

import com.github.pagehelper.PageInfo;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Product;
import com.mmall.vo.ProductDetailVo;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Auther: HP
 * @Date: 2019/2/23 14:43
 * @Description:
 */
public interface IProductService {

    ServerResponse<PageInfo> getList(int pageNum, int pageSize);

    ServerResponse<PageInfo> search(int pageNum, int pageSize, String productName, Integer productId);

    ServerResponse<ProductDetailVo> selectDetailByProductId(Integer productId);

    ServerResponse<String> setSaleStatus(Integer productId, Integer status);

    ServerResponse saveOrUpdate(Product product);

    ServerResponse<PageInfo> getPortalProductList(int pageNum, int pageSize, String orderBy, Integer categoryId, String keyword);

    ServerResponse<ProductDetailVo> getProductdetail(Integer productId);
}


