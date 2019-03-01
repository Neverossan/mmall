package com.mmall.dao;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int checkUsername(String username);

    int checkEmail(String email);

    int checkEmailByUserId(@Param("email") String email, @Param("id") int id);

    User selectLogin(@Param("username") String username, @Param("password") String password);

    String forgetGetQuestion(String username);

    int checkAnswer(@Param("username") String username, @Param("question") String question, @Param("answer") String answer);

    int forgetResetPassword(@Param("username")String username, @Param("passwordNew") String passwordNew);

    int checkPasswordOld(@Param("passwordOld") String passwordOld, @Param("id") int id);

    int resetPassword(@Param("passwordNew") String passwordNew, @Param("id") int id);

    List<User> getUserList();
}