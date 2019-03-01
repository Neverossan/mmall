package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.Category;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Auther: HP
 * @Date: 2019/2/22 10:23
 * @Description:
 */
public interface ICategoryService {

    ServerResponse<List<Category>> category(Integer categoryId);

    ServerResponse<String> addCategory(Integer categoryId, String categoryName);

    ServerResponse<String> setCategoryName(Integer parentId, String categoryName);

    ServerResponse<List<Integer>> getDeepCategory(Integer categoryId);
}
