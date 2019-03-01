package com.mmall.controller.portal;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.concurrent.Callable;

/**
 * @Auther: HP
 * @Date: 2019/2/19 10:59
 * @Description: 门户-用户接口
 */

@RestController
@RequestMapping("/user/")
public class UserController {

    @Autowired
    private IUserService iUserService;

    /**
    * @Description
    * @Author  RUI
    * @Date   2019/2/19 11:05
    * @Param  username,password,session
    * @Return      
    * @Exception
    */
    @RequestMapping(value = "login.do", method = RequestMethod.POST)
    public ServerResponse<User> login(String username, String password, HttpSession session){
        ServerResponse<User> response = iUserService.login(username, password);
        //设置session
        if (response.isSuccess()){
            session.setAttribute(Const.CURRENT_USER,response.getData());
        }
        return response;
    }

    @RequestMapping(value = "logout.do", method = RequestMethod.POST)
    public ServerResponse<String> logout(HttpSession session){
        session.removeAttribute(Const.CURRENT_USER);
        return ServerResponse.createBySuccess();
    }

    @RequestMapping(value = "register.do",method = RequestMethod.POST)
    public ServerResponse<String> register(User user){
        return iUserService.register(user);
    }

    @RequestMapping(value = "check_valid.do",method = RequestMethod.POST)
    public ServerResponse<String> checkValid(String str, String type){
        return iUserService.checkValid(str, type);
    }

    @RequestMapping(value = "get_user_info.do", method = RequestMethod.POST)
    public ServerResponse<User> getUserInfo(HttpSession session){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户信息");
        }
        return ServerResponse.createBySuccess(user);
    }

    @RequestMapping(value = "forget_get_question.do")
    public ServerResponse<String> forgetGetQuestion(String username){
        return iUserService.forgetGetQuestion(username);
    }

    @RequestMapping(value = "forget_check_answer.do",method = RequestMethod.POST)
    public ServerResponse<String> checkAnswer(String username,
                                              String question, String answer){
        return iUserService.checkAnswer(username,question,answer);
    }

    @RequestMapping(value = "forget_reset_password.do",method = RequestMethod.POST)
    public ServerResponse<String> forgetResetPassword(String username,
                                                String passwordNew, String forgetToken){
        return iUserService.forgetResetPassword(username,passwordNew,forgetToken);
    }

    @RequestMapping(value = "reset_password.do", method = RequestMethod.POST)
    public ServerResponse<String> resetPassword(String passwordOld, String passwordNew, HttpSession session){
        return iUserService.resetPassword(passwordOld,passwordNew,session);
    }

    @RequestMapping(value = "update_information.do",method = RequestMethod.POST)
    public ServerResponse<User> updateInformation(User user, HttpSession session){
        User currentuser = (User) session.getAttribute(Const.CURRENT_USER);
        if (currentuser == null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }

        //从session中获取id和username
        user.setId(currentuser.getId());
        user.setUsername(currentuser.getUsername());

        ServerResponse<User> response = iUserService.updateInformation(user);
        if (response.isSuccess()){
            //将user放入session
            response.getData().setUsername(currentuser.getUsername());
            session.setAttribute(Const.CURRENT_USER,response.getData());
        }
        return response;

    }

    @RequestMapping(value = "get_information.do",method = RequestMethod.POST)
    public ServerResponse<User> getInformation(HttpSession session){
        return iUserService.getInformation(session);
    }



}
