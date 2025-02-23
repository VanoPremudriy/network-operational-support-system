package ru.mirea.network.operational.support.system.back.configuration;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ZookeeperConfig {

    @Bean
    public CuratorFramework curatorFramework(@Value("${zookeeper.url}") String zookeeperUrl,
                                             @Value("${zookeeper.sleepMsBetweenRetries:1000}") Integer sleepMsBetweenRetries,
                                             @Value("${zookeeper.maxRetries:1}") Integer maxRetries) {
        CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient(zookeeperUrl, new RetryNTimes(maxRetries, sleepMsBetweenRetries));
        curatorFramework.start();
        return curatorFramework;
    }
}
