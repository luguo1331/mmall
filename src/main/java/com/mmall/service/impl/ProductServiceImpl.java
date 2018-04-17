package com.mmall.service.impl;

import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.dao.ProductMapper;
import com.mmall.pojo.Product;
import com.mmall.service.IProductService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author junyi
 * @Date 2018-04-17 15-00
 */
@Service("iProductService")
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductMapper productMapper;

    /**
     * 更新或新增产品
     *
     * @param product
     * @return
     */
    @Override
    public ServerResponse saveOrUpdateProduct(Product product) {
        if (product != null) {
            if (StringUtils.isNotBlank(product.getSubImages())) {
                String[] subImageArray = product.getSubImages().split(",");
                if (subImageArray.length > 0) {
                    product.setMainImage(subImageArray[0]);
                }
            }

            if (product.getId() != null) {
                int updateCount = productMapper.updateByPrimaryKey(product);
                if (updateCount > 0) {
                    return ServerResponse.createBySuccessMessage("更新产品成功");
                }
                return ServerResponse.createByErrorByMessage("更新产品失败");
            } else {
                int insertCount = productMapper.insert(product);
                if (insertCount > 0) {
                    return ServerResponse.createBySuccessMessage("新增产品成功");
                }
                return ServerResponse.createByErrorByMessage("新增产品失败");
            }
        }
        return ServerResponse.createByErrorByMessage("参数不正确");
    }

    @Override
    public ServerResponse<String> setSaleStatus(Integer productId, Integer status) {
        if (productId == null || status == null) {
            return ServerResponse.createByErrorCodeByMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Product product = new Product();
        product.setId(productId);
        product.setStatus(status);
        int updateCount = productMapper.updateByPrimaryKeySelective(product);
        if (updateCount > 0) {
            return ServerResponse.createBySuccessMessage("更新产品销售状态成功");
        }
        return ServerResponse.createByErrorByMessage("修改产品销售状态失败");
    }
}
