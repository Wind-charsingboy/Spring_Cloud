package com.djo.order.service.impl;

import com.djo.order.client.ProductClient;
import com.djo.order.dataobject.OrderDetail;
import com.djo.order.dataobject.OrderMaster;
import com.djo.order.dataobject.ProductInfo;
import com.djo.order.dto.CartDTO;
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
import java.util.Optional;
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

    @Autowired
    private ProductClient productClient;

    @Override
    public OrderDTO create(OrderDTO orderDTO) {
        String orderId = KeyUtil.genUniqueKey();

        //查询商品信息(调用商品服务 Feign)
        List<String> productIdList = orderDTO.getOrderDetailList().stream()
                .map(OrderDetail::getProductId)
                .collect(Collectors.toList());
        List<ProductInfo> productInfoList = productClient.listForOrder(productIdList);

        //计算总价
        BigDecimal orderAmout = new BigDecimal(BigInteger.ZERO);
        for (OrderDetail orderDetail: orderDTO.getOrderDetailList()) {
            for (ProductInfo productInfo: productInfoList) {
                if (productInfo.getProductId().equals(orderDetail.getProductId())) {
                    //单价*数量
                    orderAmout = productInfo.getProductPrice()
                            .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                            .add(orderAmout);
                    BeanUtils.copyProperties(productInfo, orderDetail);
                    orderDetail.setOrderId(orderId);
                    orderDetail.setDetailId(KeyUtil.genUniqueKey());
                    //订单详情入库
                    orderDetailRepository.save(orderDetail);
                }
            }
        }

        //扣库存(调用商品服务)
        List<CartDTO> decreaseStockInputList = orderDTO.getOrderDetailList().stream()
                .map(e -> new CartDTO(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());
        productClient.decreaseStock(decreaseStockInputList);

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

    @Override
    public OrderDTO finish(String orderId) {
        //1.先查询订单
        Optional<OrderMaster> orderMasterOptional = orderMasterRepository.findById(orderId);
        OrderMaster orderMaster = orderMasterOptional.get();

        //2.判断订单状态

        //3.修改状态为完结
        orderMaster.setOrderStatus(11);
        orderMasterRepository.save(orderMaster);

        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        return orderDTO;
    }
}
