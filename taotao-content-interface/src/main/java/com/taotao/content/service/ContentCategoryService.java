package com.taotao.content.service;

import java.util.List;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;

public interface ContentCategoryService {
	
	/**
	 * 内容分类查询
	 * @param parentId
	 * @return
	 */
	List<EasyUITreeNode> getContentCategoryList(long parentId);
	
	
	/**
	 * 添加分类信息
	 * @param parentId 父类主键
	 * @param name     分类名称
	 * @return
	 */
	TaotaoResult addContentCategory(long parentId,String name);
}
