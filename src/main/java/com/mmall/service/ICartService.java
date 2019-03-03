package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.vo.CartProductVo;
import com.mmall.vo.CartVo;



/**
 * @Auther: HP
 * @Date: 2019/3/1 19:21
 * @Description:
 */
public interface ICartService {

    ServerResponse<CartVo> list(Integer userId);

    ServerResponse<CartVo> add(Integer userId, Integer productId, Integer count);

    ServerResponse<CartVo> update(Integer userId, Integer productId, Integer count);

    ServerResponse<CartVo> deleteProduct(Integer userId, String productIds);

    ServerResponse<CartVo> selectOrUnSelect(Integer userId, Integer checked, Integer productId);

    ServerResponse<Integer> getCartProductCount(Integer userId);
}
