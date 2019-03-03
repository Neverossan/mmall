package com.mmall.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mmall.common.ServerResponse;
import com.mmall.dao.CategoryMapper;
import com.mmall.pojo.Category;
import com.mmall.service.ICategoryService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @Auther: HP
 * @Date: 2019/2/22 10:26
 * @Description:
 */
@Service("iCategoryService")
public class CategoryServiceImpl implements ICategoryService {

    private Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public ServerResponse<List<Category>> category(Integer categoryId) {

        List<Category> list = categoryMapper.selectGetCategory(categoryId);

        if (CollectionUtils.isEmpty(list)){
            //空集合不返回给前端，打印日志
            logger.info("未找到当前分类的子分类");
            //return ServerResponse.createByErrorMessage("未找到该品类");
        }

        return ServerResponse.createBySuccess(list);
    }

    @Override
    public ServerResponse<String> addCategory(Integer parentId, String categoryName) {
        if (parentId == null || StringUtils.isBlank(categoryName)){
            return ServerResponse.createByErrorMessage("更新品类参数错误");
        }

        int resultCount = categoryMapper.checkCategoryName(parentId,categoryName);
        if (resultCount > 0){
            return ServerResponse.createByErrorMessage("节点已存在");
        }

        Category category = new Category();
        category.setParentId(parentId);
        category.setName(categoryName);
        category.setStatus(true);//这个分类是可用的
        int count = categoryMapper.insert(category);
        if (count == 0){
            return ServerResponse.createByErrorMessage("添加品类失败");
        }
        return ServerResponse.createBySuccessMessage("添加品类成功");
    }

    @Override
    public ServerResponse<String> setCategoryName(Integer categoryId, String categoryName) {
        if (categoryId == null || StringUtils.isBlank(categoryName)){
            return ServerResponse.createByErrorMessage("更新品类参数错误");
        }
//        int Count =categoryMapper.checkCategoryId(categoryId);
//        if (Count == 0){
//            return ServerResponse.createByErrorMessage("CategoryId不存在");
//        }


        int resultCount = categoryMapper.updateCategoryNameBycategoryId(categoryId,categoryName);
        if (resultCount > 0){
            return ServerResponse.createBySuccessMessage("更新品类名字成功");
        }
        return ServerResponse.createByErrorMessage("更新品类名字失败");
    }

    @Override
    public ServerResponse<List<Integer>> getDeepCategory(Integer categoryId) {
        /*if (categoryId == null){
            return ServerResponse.createByErrorMessage("categoryId为空");
        }
        int resultCount =categoryMapper.checkCategoryId(categoryId);
        if (resultCount == 0){
            return ServerResponse.createByErrorMessage("CategoryId不存在");
        }

        List<Integer> list = categoryMapper.getDeepCategory(categoryId);
        if (!CollectionUtils.isEmpty(list)){
            return ServerResponse.createBySuccess(list);
        }
        return ServerResponse.createByErrorMessage("未查询到结果");*/

        Set<Category> categorySet = Sets.newHashSet();
        findChildCategory(categorySet,categoryId);

        List<Integer> categoryIdList = Lists.newArrayList();
        if (categoryId != null) {
            for (Category category : categorySet) {
                categoryIdList.add(category.getId());
            }
        }
        return ServerResponse.createBySuccess(categoryIdList);
    }

    //递归算法,算出子节点
    private Set<Category> findChildCategory(Set<Category> categorySet,Integer categoryId){
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        if (category != null) {
            categorySet.add(category);
        }
        //查找子节点,递归算法一定要有一个退出的条件
        List<Category> categoryList = categoryMapper.selectGetCategory(categoryId);
        for (Category categoryItem : categoryList) {
            findChildCategory(categorySet,categoryItem.getId());
        }
        return categorySet;
    }



}
