package com.taotao.content.service;

import java.util.List;

import com.taotao.common.pojo.EasyUITreeNode;

public interface ContentCategoryService {
	
	/**
	 * 内容分类查询
	 * @param parentId
	 * @return
	 */
	List<EasyUITreeNode> getContentCategoryList(long parentId);
}
