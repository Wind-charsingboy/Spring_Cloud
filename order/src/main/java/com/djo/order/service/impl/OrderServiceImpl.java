package com.djo.order.service.impl;

import com.djo.order.dataobject.OrderDetail;
import com.djo.order.dataobject.OrderMaster;
import com.djo.order.dto.OrderDTO;
import com.djo.order.enums.OrderStatusEnum;
import com.djo.order.enums.PayStatusEnum;
import com.djo.order.repository.OrderDetailRepository;
import com.djo.order.repository.OrderMasterRepository;
import com.djo.order.service.OrderService;
import com.djo.order.utils.KeyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by 廖师兄
 * 2017-12-10 16:44
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Override
    public OrderDTO create(OrderDTO orderDTO) {
        String orderId = KeyUtil.genUniqueKey();

        //查询商品信息(调用商品服务)
        //计算总价
        //扣库存(调用商品服务)

        //订单入库
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderAmount(new BigDecimal(3));
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMasterRepository.save(orderMaster);
        return orderDTO;
    }
}
