package com.taotao.content.service;

import java.util.List;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;

public interface ContentService {
	
	/**
	 * 添加内容
	 * @param content
	 * @return
	 */
	TaotaoResult addContent(TbContent content);
	
	/**
	 * 根据id获取内容列表
	 * @param cid
	 * @return
	 */
	List<TbContent> getContentByCid(long cid);
	

}
