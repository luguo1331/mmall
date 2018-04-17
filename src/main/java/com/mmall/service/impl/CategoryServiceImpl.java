package com.mmall.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mmall.common.ServerResponse;
import com.mmall.dao.CategoryMapper;
import com.mmall.pojo.Category;
import com.mmall.service.ICategoryService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author junyi
 * @Date 2018-04-17 11-14
 */
@Service("iCategoryService")
public class CategoryServiceImpl implements ICategoryService {

    private Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 添加分类
     *
     * @param categoryName
     * @param parentId
     * @return
     */
    @Override
    public ServerResponse addCategory(String categoryName, Integer parentId) {
        if (parentId == null || StringUtils.isBlank(categoryName)) {
            return ServerResponse.createByErrorByMessage("分类参数错误");
        }

        Category category = new Category();
        category.setName(categoryName);
        category.setParentId(parentId);
        category.setStatus(true);

        int insertCount = categoryMapper.insert(category);
        if (insertCount > 0) {
            return ServerResponse.createBySuccessMessage("添加品类成功");
        }
        return ServerResponse.createByErrorByMessage("添加品类失败");
    }

    /**
     * 更新分类名字
     *
     * @param categoryId
     * @param categoryName
     * @return
     */
    @Override
    public ServerResponse updateCategoryName(Integer categoryId, String categoryName) {
        if (categoryId == null && StringUtils.isBlank(categoryName)) {
            return ServerResponse.createByErrorByMessage("更新品类参数错误");
        }
        Category category = new Category();
        category.setId(categoryId);
        category.setName(categoryName);
        int updateCount = categoryMapper.updateByPrimaryKeySelective(category);
        if (updateCount > 0) {
            return ServerResponse.createByErrorByMessage("更新分类名字成功");
        }
        return ServerResponse.createByErrorByMessage("更新分类名字失败");
    }

    /**
     * 查找该分类的子节点
     *
     * @param categoryId
     * @return
     */
    @Override
    public ServerResponse<List<Category>> getChildrenParallelCategory(Integer categoryId) {
        if (categoryId == null) {
            return ServerResponse.createByErrorByMessage("参数错误");
        }
        List<Category> categories = categoryMapper.selectCategoryChildrenByParentId(categoryId);
        if (org.apache.commons.collections.CollectionUtils.isEmpty(categories)) {
            logger.info("没有找到分类");
        }
        return ServerResponse.createBySuccess(categories);
    }

    /**
     * 查找给分类的所有字节点
     *
     * @param categoryId
     * @return
     */
    @Override
    public ServerResponse<List<Integer>> selectCategoryAndChildrenById(Integer categoryId) {
        HashSet<Category> categorySet = Sets.newHashSet();
        findChildCategory(categorySet, categoryId);

        List<Integer> categoryIdList = Lists.newArrayList();
        for (Category categoryItem : categorySet) {
            categoryIdList.add(categoryItem.getId());
        }
        return ServerResponse.createBySuccess(categoryIdList);
    }

    private Set<Category> findChildCategory(Set<Category> categorySet, Integer categoryId) {
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        if (category != null) {
            categorySet.add(category);
        }

        List<Category> categories = categoryMapper.selectCategoryChildrenByParentId(categoryId);
        for (Category categoryTtem : categories) {
            findChildCategory(categorySet, categoryTtem.getId());
        }
        return categorySet;
    }
}
