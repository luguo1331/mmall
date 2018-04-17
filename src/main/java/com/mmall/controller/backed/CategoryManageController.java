package com.mmall.controller.backed;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.ICategoryService;
import com.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @Author junyi
 * @Date 2018-04-17 10-57
 */
@Controller
@RequestMapping("/manage/category/")
public class CategoryManageController {
    @Autowired
    private IUserService iUserService;
    @Autowired
    private ICategoryService iCategoryService;

    /**
     * 添加分类
     *
     * @param session
     * @param catgoryName
     * @param parentId
     * @return
     */
    @RequestMapping(value = "add_category.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse addCategory(HttpSession session, String catgoryName, @RequestParam(value = "parentId", defaultValue = "0") Integer parentId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeByMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登陆");
        }

        if (iUserService.checkAmdinRole(user).isSuccess()) {
            return iCategoryService.addCategory(catgoryName, parentId);
        } else {
            return ServerResponse.createByErrorByMessage("没有权限操作");
        }
    }

    @RequestMapping(value = "set_category_name.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse setCategoryName(HttpSession session, Integer categoryId, String categoryName) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeByMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录");
        }

        if (iUserService.checkAmdinRole(user).isSuccess()) {
            return iCategoryService.updateCategoryName(categoryId, categoryName);
        } else {
            return ServerResponse.createByErrorByMessage("没有权限操作");
        }

    }

    /**
     * 查找该分类的下一级分类
     *
     * @param session
     * @param categoryId
     * @return
     */
    @RequestMapping(value = "get_category.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse getChildrenParallelCategory(HttpSession session, @RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeByMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录");
        }

        if (iUserService.checkAmdinRole(user).isSuccess()) {
            //查询该节点的子节点
            return iCategoryService.getChildrenParallelCategory(categoryId);
        } else {
            return ServerResponse.createByErrorByMessage("没有权限操作");
        }

    }

    /**
     * 查找该分类的所有子分类
     *
     * @param session
     * @param categoryId
     * @return
     */
    @RequestMapping(value = "get_deep_category.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse getCategoryAndDeepChildrenCategory(HttpSession session, @RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeByMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录");
        }

        if (iUserService.checkAmdinRole(user).isSuccess()) {
            //查询该节点的子节点
            return iCategoryService.getChildrenParallelCategory(categoryId);
        } else {
            return ServerResponse.createByErrorByMessage("没有权限操作");
        }

    }

}

