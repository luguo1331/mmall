package com.mmall.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author junyi
 * @Date 2018-04-17 16-56
 */
@Data
public class ProductListVo {
    private Integer id;
    private Integer categoryId;

    private String name;
    private String subtitle;
    private String mainImage;
    private BigDecimal price;

    private Integer status;

    private String imageHost;
}
