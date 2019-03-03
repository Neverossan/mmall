package com.mmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mmall.common.ServerResponse;
import com.mmall.dao.ShippingMapper;
import com.mmall.pojo.Shipping;
import com.mmall.service.IShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: HP
 * @Date: 2019/3/3 15:38
 * @Description:
 */

@Service("iShippingService")
public class ShippingServiceImpl implements IShippingService {

    @Autowired
    private ShippingMapper shippingMapper;

    @Override
    public ServerResponse<Integer> add(Shipping shipping) {
        int resultCount = shippingMapper.insert(shipping);
        if (resultCount == 0){
            return ServerResponse.createByErrorMessage("新建地址失败");
        }
        Shipping resultShipping = shippingMapper.selectNewShippingByUserId(shipping.getUserId());
        return ServerResponse.createBySuccess("新建地址成功",resultShipping.getId());
    }

    @Override
    public ServerResponse<String> del(Integer shippingId, Integer userId) {
        int resultCount = shippingMapper.deleteByIdAndUserId(shippingId,userId);
        if (resultCount > 0){
            return ServerResponse.createBySuccessMessage("删除地址成功");
        }
        return ServerResponse.createBySuccessMessage("删除地址失败");
    }

    @Override
    public ServerResponse update(Shipping shipping) {
        int resultCount = shippingMapper.updateByPrimaryKey(shipping);
        if (resultCount > 0){
            return ServerResponse.createBySuccessMessage("更新地址成功");
        }
        return ServerResponse.createBySuccessMessage("更新地址失败");
    }

    @Override
    public ServerResponse<Shipping> select(Integer shippingId, Integer userId) {
        Shipping shipping = shippingMapper.selectShippingByIdAndUserId(shippingId,userId);
        if (shipping == null) {
            return ServerResponse.createByErrorMessage("查询失败");
        }
        return ServerResponse.createBySuccess(shipping);
    }

    @Override
    public ServerResponse<PageInfo> getList(Integer userId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Shipping> shippingList = shippingMapper.selectShippingListByUserId(userId);
        PageInfo pageInfo = new PageInfo(shippingList);
        return ServerResponse.createBySuccess(pageInfo);
    }
}
