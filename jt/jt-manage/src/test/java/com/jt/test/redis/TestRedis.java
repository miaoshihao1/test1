package com.jt.test.redis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.Transaction;

public class TestRedis {
	/*
	 * 1.防火墙关闭 2.ip绑定 3.保护模式关闭
	 */
	@Test
	public void test01() {
		Jedis jedis =new Jedis("192.168.88.129",6379);
		//jedis.set("1904", "1904版本redis学习");
		jedis.setex("1904", 100, "1904版本redis学习1");
		String result=jedis.get("1904");
		System.out.println(result);
		//为KEy设置超时时间
		//jedis.expire("1904", 100);
		//需求：当前key已经存在，则不能修改
		jedis.setnx("1904","1年薪百万1");
		System.out.println("获取修改后的值"+jedis.get("1904"));
		jedis.set("1904A", "今天中午吃啥","NX", "EX", 5000);
	}
	@Test
	public void testHash() {
		Jedis jedis =new Jedis("192.168.88.129",6379);
		jedis.hset("person", "id", "100");
		jedis.hset("person", "name", "人");
		jedis.hset("person", "age", "18");
		System.out.println(jedis.hgetAll("person"));
	}
	@Test
	public void testList() {
		Jedis jedis =new Jedis("192.168.88.129",6379);
		jedis.lpush("list", "1,2,3,4,5","haha");
		System.out.println(jedis.rpop("list"));
	}
	@Test
	public void testTx() {
		Jedis jedis =new Jedis("192.168.88.129",6379);
		Transaction transaction=jedis.multi();
		try {		
		transaction.set("aa", "aaa");
		transaction.set("bb", "bbb");
		transaction.set("cc", "ccc");
		transaction.exec();
		} catch (Exception e) {
			// TODO: handle exception
			transaction.discard();
		}
	}
	@Test
	public void testshards() {
		String host="192.168.88.129";
		List<JedisShardInfo> shards=new ArrayList<JedisShardInfo>();
		shards.add(new JedisShardInfo(host,6379));
		shards.add(new JedisShardInfo(host,6380));
		shards.add(new JedisShardInfo(host,6381));
		ShardedJedis jedis=new ShardedJedis(shards);
		jedis.set("1904", "分片操作");
		System.out.println(jedis.get("1904")); 
	}
	@Test
	public void testSentinel() {
		Set<String> sentinels=new HashSet<>();
		sentinels.add("192.168.88.129:26379");
		JedisSentinelPool pool=new JedisSentinelPool("mymaster", sentinels);
		Jedis jedis = pool.getResource();
		jedis.set("1904", "测试哨兵");
		System.out.println(jedis.get("1904"));
	}
	@Test
	public void testCluster() {
		
		Set<HostAndPort> nodes =new HashSet<>();
		nodes.add(new HostAndPort("192.168.88.129",7000));
		nodes.add(new HostAndPort("192.168.88.129",7001));
		nodes.add(new HostAndPort("192.168.88.129",7002));
		nodes.add(new HostAndPort("192.168.88.129",7003));
		nodes.add(new HostAndPort("192.168.88.129",7004));
		nodes.add(new HostAndPort("192.168.88.129",7005));
		JedisCluster cluster=new JedisCluster(nodes);
		cluster.set("1904", "集群完成");
		System.out.println(cluster.get("1904"));
	}
}
