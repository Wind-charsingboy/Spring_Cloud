package com.djo.product.dataObject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by 廖师兄
 * 2017-12-09 21:23
 */
@Data
//如果该实体类不是和数据库表名保持一致 用@Table注解
//@Table(name = "T_proxxx")
//该注解和@Id是保证和数据库关联的保证
@Entity
public class ProductInfo {

    @Id
    private String productId;

    /** 名字. */
    private String productName;

    /** 单价. */
    private BigDecimal productPrice;

    /** 库存. */
    private Integer productStock;

    /** 描述. */
    private String productDescription;

    /** 小图. */
    private String productIcon;

    /** 状态, 0正常1下架. */
    private Integer productStatus;

    /** 类目编号. */
    private Integer categoryType;

    private Date createTime;

    private Date updateTime;
}
