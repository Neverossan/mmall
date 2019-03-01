package com.mmall.controller.backend;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Category;
import com.mmall.pojo.User;
import com.mmall.service.ICategoryService;
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
 * @Date: 2019/2/22 09:52
 * @Description:
 */
@RestController
@RequestMapping("/manage/category/")
public class CategoryManageController {

    @Autowired
    private ICategoryService iCategoryService;

    @Autowired
    private IUserService iUserService;

    @RequestMapping(value = "get_category.do",method = RequestMethod.POST)
    public ServerResponse<List<Category>> category(@RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId, HttpSession session){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorMessage(ResponseCode.NEED_LOGIN.getCode()
                    ,"用户未登录,请登录");
        }
        //验证是否是管理员账号登录
        if (!iUserService.checkAdminRole(user).isSuccess()){
            return  ServerResponse.createByErrorMessage("无权限");
        }
        return iCategoryService.category(categoryId);
    }

    @RequestMapping(value = "add_category.do", method = RequestMethod.POST)
    public ServerResponse<String> addCategory(@RequestParam(value = "parentId", defaultValue = "0") Integer parentId, HttpSession session, String categoryName){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorMessage(ResponseCode.NEED_LOGIN.getCode()
                    ,"用户未登录,请登录");
        }
        //验证是否是管理员账号登录
        if (!iUserService.checkAdminRole(user).isSuccess()){
            return  ServerResponse.createByErrorMessage("无权限");
        }
        return iCategoryService.addCategory(parentId, categoryName);
    }

    @RequestMapping(value = "set_category_name.do", method = RequestMethod.POST)
    public ServerResponse<String> setCategoryName(Integer categoryId,
                                                  HttpSession session, String categoryName){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorMessage(ResponseCode.NEED_LOGIN.getCode()
                    ,"用户未登录,请登录");
        }
        //验证是否是管理员账号登录
        if (!iUserService.checkAdminRole(user).isSuccess()){
            return  ServerResponse.createByErrorMessage("无权限");
        }
        return iCategoryService.setCategoryName(categoryId,categoryName);
    }

    @RequestMapping(value = "get_deep_category.do", method = RequestMethod.POST)
    public ServerResponse<List<Integer>> getDeepCategory(Integer categoryId, HttpSession session){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorMessage(ResponseCode.NEED_LOGIN.getCode()
                    ,"用户未登录,请登录");
        }
        //验证是否是管理员账号登录
        if (!iUserService.checkAdminRole(user).isSuccess()){
            return  ServerResponse.createByErrorMessage("无权限");
        }
        return iCategoryService.getDeepCategory(categoryId);
    }

}
