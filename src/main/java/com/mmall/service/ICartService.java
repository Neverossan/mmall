package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.vo.CartProductVo;
import com.mmall.vo.CartVo;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Auther: HP
 * @Date: 2019/3/1 19:21
 * @Description:
 */
public interface ICartService {

    ServerResponse<CartVo> list(Integer userId);

    ServerResponse<CartVo> add(Integer userId, Integer productId, Integer count);
}
