package com.mmall.service;

import com.github.pagehelper.PageInfo;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Shipping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Auther: HP
 * @Date: 2019/3/3 15:22
 * @Description:
 */
public interface IShippingService {

    ServerResponse<Integer> add(Shipping shipping);

    ServerResponse<String> del(Integer shippingId, Integer userId);

    ServerResponse update(Shipping shipping);

    ServerResponse<Shipping> select(Integer shippingId, Integer userId);

    ServerResponse<PageInfo> getList(Integer userId, int pageNum, int pageSize);
}
