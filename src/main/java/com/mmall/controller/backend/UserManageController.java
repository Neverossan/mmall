package com.mmall.controller.backend;

import com.github.pagehelper.PageInfo;
import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Auther: HP
 * @Date: 2019/2/20 18:45
 * @Description:
 */
@RestController
@RequestMapping("/manage/user/")
public class UserManageController {

    @Autowired
    private IUserService iUserService;

    @RequestMapping(value = "login.do", method = RequestMethod.POST)
    public ServerResponse<User> login(String username, String password, HttpSession session){
        ServerResponse<User> response = iUserService.login(username,password);
        if (response.isSuccess()){
            if (response.getData().getRole().equals(Const.Role.ROLE_ADMIN)){
                session.setAttribute(Const.CURRENT_USER,response.getData());
                return response;
            }else {
                return ServerResponse.createByErrorMessage("非管理员账号登录");
            }
        }
        return response;
    }

    @RequestMapping(value = "list.do", method = RequestMethod.POST)
    public ServerResponse<PageInfo> getUserlist(@RequestParam(value = "pageNum", defaultValue ="1") int pageNum,
                                                @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                                HttpSession session){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorMessage(ResponseCode.NEED_LOGIN.getCode()
                    ,"用户未登录,请登录");
        }
        //验证是否是管理员账号登录
        if (!iUserService.checkAdminRole(user).isSuccess()){
            return  ServerResponse.createByErrorMessage("无权限");
        }
        return iUserService.getUserlist(pageNum,pageSize);
    }
}
