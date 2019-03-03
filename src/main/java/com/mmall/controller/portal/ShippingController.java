package com.mmall.controller.portal;

import com.github.pagehelper.PageInfo;
import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Shipping;
import com.mmall.pojo.User;
import com.mmall.service.IShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Auther: HP
 * @Date: 2019/3/3 15:13
 * @Description:
 */
@RestController
@RequestMapping("/shipping/")
public class ShippingController {

    @Autowired
    private IShippingService iShippingService;

    @RequestMapping("add.do")
    public ServerResponse<Integer> add(Shipping shipping, HttpSession session){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null || shipping == null) {
            return ServerResponse.createByErrorMessage(ResponseCode.NEED_LOGIN.getCode(),
                    ResponseCode.NEED_LOGIN.getDesc());
        }
        shipping.setUserId(user.getId());
        return iShippingService.add(shipping);
    }

    @RequestMapping("del.do")
    public ServerResponse del(Integer shippingId, HttpSession session){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null || shippingId == null) {
            return ServerResponse.createByErrorMessage(ResponseCode.NEED_LOGIN.getCode(),
                    ResponseCode.NEED_LOGIN.getDesc());
        }
        return iShippingService.del(shippingId,user.getId());
    }

    @RequestMapping("update.do")
    public ServerResponse update(Shipping shipping, HttpSession session){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null || shipping == null) {
            return ServerResponse.createByErrorMessage(ResponseCode.NEED_LOGIN.getCode(),
                    ResponseCode.NEED_LOGIN.getDesc());
        }
        shipping.setUserId(user.getId());
        return iShippingService.update(shipping);
    }

    @RequestMapping("select.do")
    public ServerResponse<Shipping> select(Integer shippingId, HttpSession session){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null || shippingId == null) {
            return ServerResponse.createByErrorMessage(ResponseCode.NEED_LOGIN.getCode(),
                    ResponseCode.NEED_LOGIN.getDesc());
        }
        return iShippingService.select(shippingId,user.getId());
    }

    @RequestMapping("list.do")
    public ServerResponse<PageInfo> getList(HttpSession session, @RequestParam(value = "pageNum", defaultValue ="1") int pageNum,
                                            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage(ResponseCode.NEED_LOGIN.getCode(),
                    ResponseCode.NEED_LOGIN.getDesc());
        }
        return iShippingService.getList(user.getId(),pageNum,pageSize);
    }
}
