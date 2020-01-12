package com.djo.product.DTO;

import lombok.Data;

@Data
public class CartDTO {
    private String productId;

    private Integer productQuantity;
}
