package com.mmall.common;

import com.google.common.collect.Sets;
import lombok.Getter;

import java.util.Set;

/**
 * @Author junyi
 * @Date 2018-04-15 15-09
 */
public class Const {

    public static final String CURRENT_USER = "currentUser";
    public static final String EMAIL = "email";
    public static final String USERNAME = "username";

    public interface Role {
        int ROLE_CUSTOMER = 0;
        int ROLE_ADMIN = 1;
    }

    public interface ProductListOrderBy{
        Set<String> PRICE_ASC_DESC = Sets.newHashSet("price_desc","price_asc");
    }

    @Getter
    public enum ProductStatusEnum {
        ON_SALE(1, "在售");
        private String value;
        private int code;

        ProductStatusEnum(int code, String value) {
            this.code = code;
            this.value = value;
        }
        }
}
