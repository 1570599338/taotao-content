package com.taotao.content.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.zookeeper.Op.Create;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.content.service.ContentService;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.pojo.TbContentExample.Criteria;

@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper contentMapper;
	/**
	 * 添加内容
	 * @param content
	 * @return
	 */
	@Override
	public TaotaoResult addContent(TbContent content) {
		// 补全pojo属性
		content.setCreated(new Date());
		content.setUpdated(new Date());
		// 插入到数据库
		this.contentMapper.insert(content);
		
		
		return TaotaoResult.ok();
	}
	
	/**
	 * 根据id获取内容列表
	 * @param cid
	 * @return
	 */
	@Override
	public List<TbContent> getContentByCid(long cid) {
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		// 设置查询条件
		criteria.andCategoryIdEqualTo(cid);
		// 执行查询
		List<TbContent> list = this.contentMapper.selectByExample(example);
		return list;
	}

}
