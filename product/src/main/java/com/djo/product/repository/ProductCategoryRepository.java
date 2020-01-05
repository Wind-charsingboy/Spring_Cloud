package com.djo.product.repository;

import com.djo.product.dataObject.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by 廖师兄
 * 2017-12-09 21:41
 */
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {

    /**
     * 参数是list 获取类目type列表
     * @param categoryTypeList
     * @return
     */
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
}
