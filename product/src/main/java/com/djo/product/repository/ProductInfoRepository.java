package com.djo.product.repository;


import com.djo.product.dataObject.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by 廖师兄
 * 2017-12-09 21:29
 * 第一个参数是实体类 第二个参数是主键的类型
 */
public interface ProductInfoRepository extends JpaRepository<ProductInfo, String>{

    /**
     * 查询在架的类型
     * @param productStatus
     * @return
     */
    List<ProductInfo> findByProductStatus(Integer productStatus);

    List<ProductInfo> findByProductIdIn(List<String> productIdList);
}
