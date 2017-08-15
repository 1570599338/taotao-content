package com.taotao.content.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.zookeeper.Op.Create;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.JsonUtils;
import com.taotao.content.service.ContentService;
import com.taotao.jedis.JedisClient;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.pojo.TbContentExample.Criteria;

@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper contentMapper;
	// 缓存
	@Autowired
	private JedisClient jedisClient;
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
		
		//　同步数据库
		// 删除对应的缓存信息
		jedisClient.hdel("INDEX_CONTENT", content.getCategoryId().toString());
		
		
		return TaotaoResult.ok();
	}
	
	/**
	 * 根据id获取内容列表
	 * @param cid
	 * @return
	 */
	@Override
	public List<TbContent> getContentByCid(long cid) {
		// 先查询缓存
		// 添加缓存不影响正常的业务逻辑
		try {
			// 先查询缓存
			String json = jedisClient.hget("INDEX_CONTENT", String.valueOf(cid));
			// 不为空
			if(StringUtils.isNotBlank(json)){
				List<TbContent> list = JsonUtils.jsonToList(json, TbContent.class);
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
		// 缓存没有命中再查询数据库
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		// 设置查询条件
		criteria.andCategoryIdEqualTo(cid);
		// 执行查询
		List<TbContent> list = this.contentMapper.selectByExample(example);
		
		// 把结果插入缓存中
		try {
			jedisClient.hset("INDEX_CONTENT", String.valueOf(cid), JsonUtils.objectToJson(list));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}

}
