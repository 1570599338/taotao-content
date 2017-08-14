package com.taotao.jedis;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

/**
 * 测试Jedis
 * @author lquan
 *
 */
public class TestJedis {
	@Test
	public void testJedis() {
		// 创建jedis链接
		Jedis jedis = new Jedis("182.92.231.155", 6379);
		System.out.println("**************");
		// 测试是否链接
		String result = jedis.ping();
		System.out.println(result);
		// 关闭链接
		jedis.close();
	}

	@Test
	public void testJedisPool(){
		// 创建一个数据库连接池对象（单利），需要制定服务的IP和端口号
		JedisPool pool = new JedisPool("182.92.231.155", 6379);
		// 从连接池中获取连接
		Jedis jedis = pool.getResource();
		// 测试连接是否链接
		String result = jedis.ping();
		System.out.println(result);
		// 一定要关闭Jedis连接
		jedis.close();
		// 系统连接池关闭;
		pool.close();
		
	}
	
	@Test
	public void testJedisCluster(){
		// 创建一个JedisCluster对象，构造参数set类型，集合中每一个元素是HostAndPort类型
		Set<HostAndPort> nodes = new HashSet<>();
		// 向集合中添加节点
		nodes.add(new HostAndPort("192.168.207.206",7001));
		nodes.add(new HostAndPort("192.168.207.206",7002));
		nodes.add(new HostAndPort("192.168.207.206",7003));
		nodes.add(new HostAndPort("192.168.207.206",7004));
		nodes.add(new HostAndPort("192.168.207.206",7005));
		nodes.add(new HostAndPort("192.168.207.206",7006));
		JedisCluster jedisCluster = new JedisCluster(nodes);
		// 直接使用JedisCluster操作redis,自带连接池。jedisCluster对象可以是单例的。
		jedisCluster.set("cluster-test", "hello Jedis Cluster");
		// 获取对应的key值
		String string = jedisCluster.get("cluster-test");
		System.out.println("*******"+string);
		// 系统结束关闭链接
		jedisCluster.close();
	}
	
	
}
