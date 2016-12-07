package com.yoku.mybatis.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Desc:
 * Author: <a href="xiahj@terminus.io">xiahj</a>
 * Date: 2016/12/7
 */

@ConfigurationProperties(prefix = "mybatis")
public class MybatisProperties {
    private String username;
    private String password;
    private String url = "jdbc:mysql://127.0.0.1:3306/yoku?useUnicode=true&characterEncoding=UTF8";
    private Integer maxActive = 20;
    private Integer minIdle = 1;
    private Integer initialSize = 5;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(Integer maxActive) {
        this.maxActive = maxActive;
    }

    public Integer getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(Integer minIdle) {
        this.minIdle = minIdle;
    }

    public Integer getInitialSize() {
        return initialSize;
    }

    public void setInitialSize(Integer initialSize) {
        this.initialSize = initialSize;
    }
}
