package com.jt.config;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
@PropertySource("classpath:/properties/redis.properties")
@Configuration
public class RedisConfig {
	/*
	 * @Value("${redis.host}") private String host;
	 * 
	 * @Value("${redis.port}") private Integer port;
	 * 
	 * @Bean public Jedis jedis() { return new Jedis(host, port); }
	 */
	
	  @Value("${redis.nodes}") 
	  private String nodes;
	 
	/*
	 * @Bean public ShardedJedis shardedJedis() { List<JedisShardInfo>
	 * shards=getShards(); return new ShardedJedis(shards); } private
	 * List<JedisShardInfo> getShards() { // TODO Auto-generated method stub
	 * List<JedisShardInfo> list=new ArrayList<JedisShardInfo>(); String[]
	 * nodeArray=nodes.split(","); for (String string : nodeArray) { String
	 * ip=string.split(":")[0]; int port=Integer.parseInt(string.split(":")[1]);
	 * list.add(new JedisShardInfo(ip,port)); } return list; }
	 */
	/*@Value("${redis.masterName}")
	private String masterName;
	@Bean//("jedisSentinelPool")
	public JedisSentinelPool jedisSentinelPool() {
		Set<String> sentinels=new HashSet<>();
		sentinels.add(nodes);
		return new JedisSentinelPool(masterName, sentinels);
	}
	@Bean
	public Jedis jedis(//@Qualifier("jedisSentinelPool")
			JedisSentinelPool jedisSentinelPool) {
		return jedisSentinelPool.getResource();
		
	  
	}*/
	  @Bean
	  public JedisCluster jedisCluster() {
			Set<HostAndPort> nodes=getNodes();
			return new JedisCluster(nodes);
	  }

	private Set<HostAndPort> getNodes() {
		// TODO Auto-generated method stub
		Set<HostAndPort> set=new HashSet<>();
		String[] nodesArray=nodes.split(",");
		for (String string : nodesArray) {
			String host=string.split(":")[0];
			Integer port=Integer.parseInt(string.split(":")[1]);
			set.add(new HostAndPort(host, port));
		}
		return set;
	}

}
