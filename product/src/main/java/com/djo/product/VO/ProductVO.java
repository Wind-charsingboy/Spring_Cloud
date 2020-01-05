package com.djo.product.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * Created by 廖师兄
 * 2017-12-09 22:11
 */
@Data
public class ProductVO {

    //返回回去的字段名是name 就不用自己去做set属性的操作了
    @JsonProperty("name")
    private String categoryName;

    @JsonProperty("type")
    private Integer categoryType;

    @JsonProperty("foods")
    List<ProductInfoVO> productInfoVOList;
}
