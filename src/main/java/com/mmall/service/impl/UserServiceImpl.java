package com.mmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.common.TokenCache;
import com.mmall.dao.UserMapper;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import com.mmall.util.MD5Util;
import net.sf.jsqlparser.schema.Server;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.xml.ws.Response;
import java.util.List;
import java.util.UUID;

/**
 * @Auther: HP
 * @Date: 2019/2/19 12:45
 * @Description:
 */
@Service("iUserService")
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ServerResponse<User> login(String username, String password) {

        int ResultCode = userMapper.checkUsername(username);
        if (ResultCode == 0){
            return ServerResponse.createByErrorMessage("用户名不存在");
        }

        //密码md5加密
        String md5Password = MD5Util.MD5EncodeUtf8(password);
        User user = userMapper.selectLogin(username,md5Password);
        if (user == null){
            return ServerResponse.createByErrorMessage("用户名或密码错误");
        }

        //将密码设置为空,不返回给前端
        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess("登录成功",user);
    }

    @Override
    public ServerResponse<String> register(User user) {
        //判断用户名是否已存在
        ServerResponse response = this.checkValid(user.getUsername(), Const.USERNAME);
        if (!response.isSuccess()){
            return response;
        }
        //判断email是否已使用
        response = this.checkValid(user.getEmail(), Const.EMAIL);
        if (!response.isSuccess()){
            return response;
        }

        //设置角色
        user.setRole(Const.Role.ROLE_CUSTOMER);

        //密码md5加密
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
        int resultCount = userMapper.insert(user);
        if (resultCount == 0){
            return ServerResponse.createByErrorMessage("注册失败");
        }


        return ServerResponse.createBySuccessMessage("注册成功");
    }

    @Override
    public ServerResponse<String> checkValid(String str, String type) {
        if (StringUtils.isNotBlank(type)){
            if (Const.USERNAME.equals(type)){
                int resultCount = userMapper.checkUsername(str);
                if (resultCount != 0){
                    return ServerResponse.createByErrorMessage("用户名已存在");
                }
            }
            if (Const.EMAIL.equals(type)){
                int resultCount = userMapper.checkEmail(str);
                if (resultCount != 0){
                    return ServerResponse.createByErrorMessage("email已存在");
                }
            }
        }else {
            return ServerResponse.createByErrorMessage("参数错误");
        }

        return ServerResponse.createBySuccessMessage("检验成功");
    }

    @Override
    public ServerResponse forgetGetQuestion(String username) {
        //判断用户名是否存在
        ServerResponse validResponse = this.checkValid(username,Const.USERNAME);
        if (validResponse.isSuccess()){
            //检验成功用户不存在
            return ServerResponse.createByErrorMessage("用户不存在");
        }
        String resultDesc = userMapper.forgetGetQuestion(username);
        if (StringUtils.isBlank(resultDesc)){
            return ServerResponse.createByErrorMessage("该用户未设置找回密码问题");
        }
        return ServerResponse.createBySuccess(resultDesc);
    }

    @Override
    public ServerResponse<String> checkAnswer(String username, String question, String answer) {
        int resultCount = userMapper.checkAnswer(username,question,answer);
        if (resultCount == 0){
            return ServerResponse.createByErrorMessage("问题答案错误");
        }
        String forgetToken = UUID.randomUUID().toString();
        TokenCache.setKey(TokenCache.TOKEN_PREFIX+username,forgetToken);
        return ServerResponse.createBySuccess(forgetToken);
    }

    @Override
    public ServerResponse<String> forgetResetPassword(String username, String passwordNew, String forgetToken) {

        if(org.apache.commons.lang3.StringUtils.isBlank(forgetToken)){
            return ServerResponse.createByErrorMessage("参数错误,token需要传递");
        }
        ServerResponse validResponse = this.checkValid(username,Const.USERNAME);
        if(validResponse.isSuccess()){
            //用户不存在
            return ServerResponse.createByErrorMessage("用户不存在");
        }
        String token = TokenCache.getKey(TokenCache.TOKEN_PREFIX+username);
        if(org.apache.commons.lang3.StringUtils.isBlank(token)){
            return ServerResponse.createByErrorMessage("token无效或者过期");
        }

        if(org.apache.commons.lang3.StringUtils.equals(forgetToken,token)){
            String md5Password  = MD5Util.MD5EncodeUtf8(passwordNew);
            int rowCount = userMapper.forgetResetPassword(username,md5Password);

            if(rowCount > 0){
                return ServerResponse.createBySuccessMessage("修改密码成功");
            }
        }else{
            return ServerResponse.createByErrorMessage("token错误,请重新获取重置密码的token");
        }
        return ServerResponse.createByErrorMessage("修改密码失败");
    }

    @Override
    public ServerResponse<String> resetPassword(String passwordOld, String passwordNew, HttpSession session) {

        //判断新密码或旧密码是否为空
        if (StringUtils.isBlank(passwordOld) || StringUtils.isBlank(passwordNew)){
            return ServerResponse.createByErrorMessage("新密码或旧密码为空");
        }

        //判断用户是否已登录
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }

        //判断旧密码是否正确,md5加密
        int resultCount = userMapper.checkPasswordOld(MD5Util.MD5EncodeUtf8(passwordOld),user.getId());
        if (resultCount == 0){
            return ServerResponse.createByErrorMessage("旧密码输入错误");
        }

        //更改用户密码
        int rowCount = userMapper.resetPassword(MD5Util.MD5EncodeUtf8(passwordNew),user.getId());
        if (rowCount == 0){
            return ServerResponse.createByErrorMessage("密码更改失败");
        }
        return ServerResponse.createBySuccessMessage("密码更新成功");
    }

    @Override
    public ServerResponse<User> updateInformation(User user) {

        //检验当前email是否重复
        int resultCount = userMapper.checkEmailByUserId(user.getEmail(), user.getId());
        if (resultCount > 0){
            return ServerResponse.createByErrorMessage("email已存在,请更换email再尝试更新");
        }

        User updateUser = new User();
        updateUser.setId(user.getId());
        updateUser.setEmail(user.getEmail());
        updateUser.setPhone(user.getPhone());
        updateUser.setQuestion(user.getQuestion());
        updateUser.setAnswer(user.getAnswer());

        int Count = userMapper.updateByPrimaryKeySelective(user);
        if (Count == 0){
            return ServerResponse.createByErrorMessage("跟新失败");
        }
        return ServerResponse.createBySuccess("更新个人信息成功",updateUser);
    }

    @Override
    public ServerResponse<User> getInformation(HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorMessage(ResponseCode.NEED_LOGIN.getCode()
                    ,"用户未登录,无法获取当前用户信息,status=10,强制登录");
        }

        user = userMapper.selectByPrimaryKey(user.getId());
        //得到一个值，一定要判空
        if (user == null){
            return ServerResponse.createByErrorMessage("找不到当前用户");
        }

        //老是忘了，密码不可以返回给前端
        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess(user);
    }

    //backend

    /**
     * 校验是否是管理员
     * @param user
     * @return
     */
    @Override
    public ServerResponse checkAdminRole(User user) {
        if (user != null && user.getRole().intValue() == Const.Role.ROLE_ADMIN){
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError();
    }

    @Override
    public ServerResponse<PageInfo> getUserlist(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<User> userList = userMapper.getUserList();
        PageInfo pageInfo = new PageInfo(userList);
        return ServerResponse.createBySuccess(pageInfo);
    }


}
