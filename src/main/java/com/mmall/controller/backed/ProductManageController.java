package com.mmall.controller.backed;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Product;
import com.mmall.pojo.User;
import com.mmall.service.IProductService;
import com.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @Author junyi
 * @Date 2018-04-17 14-53
 */
@Controller
@RequestMapping("/manage/Product/")
public class ProductManageController {
    @Autowired
    private IUserService iUserService;

    @Autowired
    private IProductService iProductService;

    /**
     * 新增商品
     *
     * @param session
     * @param product
     * @return
     */
    @RequestMapping(value = "save_product.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse productSave(HttpSession session, Product product) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeByMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录");
        }
        if (iUserService.checkAmdinRole(user).isSuccess()) {
            return iProductService.saveOrUpdateProduct(product);
        } else {
            return ServerResponse.createByErrorByMessage("用户无权限操作");
        }
    }

    @RequestMapping("set_sale_status.do")
    @ResponseBody
    public ServerResponse setSaleStatus(HttpSession session, Integer productId, Integer status) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeByMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录");
        }
        if (iUserService.checkAmdinRole(user).isSuccess()) {
            return iProductService.setSaleStatus(productId, status);
        } else {
            return ServerResponse.createByErrorByMessage("用户无权限操作");
        }
    }

}
