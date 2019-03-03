package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.vo.CartProductVo;
import com.mmall.vo.CartVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @Auther: HP
 * @Date: 2019/3/2 17:26
 * @Description:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager = "transactionManager")
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class ICartServiceTest {

    @Autowired
    private ICartService iCartService;

    @Test
    public void list() {
        ServerResponse<CartVo> cartVoServerResponse = iCartService.list(22);
        List<CartProductVo> cartProductVoList = cartVoServerResponse.getData().getCartProductVoList();
        for (CartProductVo cartProductVo : cartProductVoList) {
            System.out.println("--------------"+cartProductVo.getId());
        }

    }

    @Test
    public void add() {
    }

    @Test
    public void update() {
    }

    @Test
    public void deleteProduct() {
    }

    @Test
    public void selectOrUnSelect() {
    }

    @Test
    public void getCartProductCount() {
    }
}