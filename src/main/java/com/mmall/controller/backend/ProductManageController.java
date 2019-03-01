package com.mmall.controller.backend;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Product;
import com.mmall.pojo.User;
import com.mmall.service.IProductService;
import com.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Auther: HP
 * @Date: 2019/2/23 14:26
 * @Description:
 */
@RestController
@RequestMapping("/manage/product/")
public class ProductManageController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IProductService iProductService;

    @RequestMapping("list.do")
    public ServerResponse getList(HttpSession session, @RequestParam(value = "pageNum", defaultValue ="1") int pageNum,
                                                 @RequestParam(value = "pageSize", defaultValue = "10") int pageSize){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorMessage(ResponseCode.NEED_LOGIN.getCode()
                    ,"用户未登录,请登录");
        }
        //验证是否是管理员账号登录
        if (!iUserService.checkAdminRole(user).isSuccess()){
            return  ServerResponse.createByErrorMessage("无权限");
        }
        return iProductService.getList(pageNum,pageSize);
    }

    @RequestMapping("search.do")
    public ServerResponse search(HttpSession session, @RequestParam(value = "pageNum", defaultValue ="1") int pageNum,
                                  @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                  String productName, Integer productId){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorMessage(ResponseCode.NEED_LOGIN.getCode()
                    ,"用户未登录,请登录");
        }
        //验证是否是管理员账号登录
        if (!iUserService.checkAdminRole(user).isSuccess()){
            return  ServerResponse.createByErrorMessage("无权限");
        }
        return iProductService.search(pageNum,pageSize,productName,productId);
    }

    @RequestMapping("detail.do")
    public ServerResponse detail(HttpSession session, Integer productId){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorMessage(ResponseCode.NEED_LOGIN.getCode()
                    ,"用户未登录,请登录");
        }
        //验证是否是管理员账号登录
        if (!iUserService.checkAdminRole(user).isSuccess()){
            return  ServerResponse.createByErrorMessage("无权限");
        }
        return iProductService.selectDetailByProductId(productId);
    }

    @RequestMapping("set_sale_status.do")
    public ServerResponse setSaleStatus(HttpSession session, Integer productId, Integer status){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorMessage(ResponseCode.NEED_LOGIN.getCode()
                    ,"用户未登录,请登录");
        }
        //验证是否是管理员账号登录
        if (!iUserService.checkAdminRole(user).isSuccess()){
            return  ServerResponse.createByErrorMessage("无权限");
        }
        return iProductService.setSaleStatus(productId, status);
    }

    @RequestMapping("save.do")
    public ServerResponse saveOrUpdate(HttpSession session, Product product){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorMessage(ResponseCode.NEED_LOGIN.getCode()
                    ,"用户未登录,请登录");
        }
        //验证是否是管理员账号登录
        if (!iUserService.checkAdminRole(user).isSuccess()){
            return  ServerResponse.createByErrorMessage("无权限");
        }
        return iProductService.saveOrUpdate(product);
    }










}
