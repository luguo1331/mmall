package com.mmall.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author junyi
 * @Date 2018-04-17 16-17
 */
@Data
public class ProductDetailVo {
    private Integer id;
    private Integer categoryId;
    private String name;
    private String subtitle;
    private String mainImage;
    private String subImages;
    private String detail;
    private BigDecimal price;
    private Integer stock;
    private Integer status;
    private String createTime;
    private String updateTime;


    private String imageHost;
    private Integer parentCategoryId;
}
