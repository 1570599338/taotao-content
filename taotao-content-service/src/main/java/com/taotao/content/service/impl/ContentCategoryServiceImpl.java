package com.taotao.content.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.content.service.ContentCategoryService;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.pojo.TbContentCategoryExample.Criteria;

/**
 * 内容管理service
 * @author lquan
 *
 */
@Service("contentCategoryServiceX")
public class ContentCategoryServiceImpl implements ContentCategoryService {

	@Autowired
	private TbContentCategoryMapper tbContentCategoryMapper;
	
	/**
	 * 内容分类查询
	 * @param parentId
	 * @return
	 */
	@Override
	public List<EasyUITreeNode> getContentCategoryList(long parentId) {
		// 根据parentID进行查询
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteia = example.createCriteria();
		criteia.andParentIdEqualTo(parentId);
		// 执行查询
		List<TbContentCategory> list = this.tbContentCategoryMapper.selectByExample(example);
		List<EasyUITreeNode> resultList = new ArrayList<>();
		for(TbContentCategory tbContentCategory:list){
			EasyUITreeNode node = new EasyUITreeNode();
			node.setId(tbContentCategory.getId());
			node.setText(tbContentCategory.getName());
			node.setState(tbContentCategory.getIsParent()?"closed":"open");
			// 添加结果
			resultList.add(node);
		}
		
		return resultList;
	}

	
	/**
	 * 添加分类信息
	 * @param parentId 父类主键
	 * @param name     分类名称
	 * @return
	 */
	@Override
	public TaotaoResult addContentCategory(long parentId, String name) {
		// 床架pojo对象
		TbContentCategory contentCategory = new TbContentCategory();
		//设置属性
		contentCategory.setParentId(parentId);
		contentCategory.setName(name);
		contentCategory.setStatus(1);// 状态：1 正常  2 删除
		contentCategory.setSortOrder(1);//
		contentCategory.setIsParent(false);
		contentCategory.setCreated(new Date());
		contentCategory.setUpdated(new Date());
		// 执行插入
		tbContentCategoryMapper.insert(contentCategory);
		// 判断父节点的状态
		TbContentCategory parent = tbContentCategoryMapper.selectByPrimaryKey(parentId);
		if(!parent.getIsParent()){
			// 如果父节点为叶子节点应该为父节点
			parent.setIsParent(true);
			// 更新父节点
			tbContentCategoryMapper.updateByPrimaryKey(parent);
		}
		
		// 返回结果
		return TaotaoResult.ok(contentCategory);
	}

}
