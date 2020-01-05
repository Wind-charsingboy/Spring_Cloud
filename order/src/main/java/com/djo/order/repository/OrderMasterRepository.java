package com.djo.order.repository;

import com.djo.order.dataobject.OrderMaster;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by 廖师兄
 * 2017-12-10 16:11
 * 此处只是新增操作 不用自己写方法 直接调用JpaRepository的save方法就可以
 */
public interface OrderMasterRepository extends JpaRepository<OrderMaster, String> {
}
