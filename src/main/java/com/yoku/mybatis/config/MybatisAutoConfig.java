package com.yoku.mybatis.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.yoku.mybatis.properties.MybatisProperties;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Desc:
 * Author: <a href="xiahj@terminus.io">xiahj</a>
 * Date: 2016/12/7
 */

@SuppressWarnings("SpringJavaAutowiringInspection")
@Configuration
@EnableAutoConfiguration
@EnableConfigurationProperties(MybatisProperties.class)
@ConditionalOnProperty(prefix = "mybatis", value = "enabled", matchIfMissing = true)
public class MybatisAutoConfig implements TransactionManagementConfigurer {

    @Autowired
    MybatisProperties mybatiesProperties;

    @Autowired
    DataSource dataSource;

    @Bean(name = "dataSource")
    @ConditionalOnMissingBean
    public DataSource druidDataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUsername(mybatiesProperties.getUsername());
        dataSource.setPassword(mybatiesProperties.getPassword());
        dataSource.setUrl(mybatiesProperties.getUrl());
        dataSource.setMaxActive(mybatiesProperties.getMaxActive());
        dataSource.setMinIdle(mybatiesProperties.getMinIdle());
        dataSource.setInitialSize(mybatiesProperties.getInitialSize());
        dataSource.setMaxWait(30000);
        dataSource.setTimeBetweenEvictionRunsMillis(60000);
        //一个连接最小生存时间
        dataSource.setMinEvictableIdleTimeMillis(300000);
        //用来检测是否有效的sql
        dataSource.setValidationQuery("select 1");
        dataSource.setTestWhileIdle(true);
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);
        //打开PSCache,并指定每个连接的PSCache大小
        dataSource.setPoolPreparedStatements(true);
        dataSource.setMaxOpenPreparedStatements(20);
        try {
            dataSource.init();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataSource;
    }

    @Bean(name = "sqlSessionFactory")
    @ConditionalOnBean(name = "dataSource")
    public SqlSessionFactory sqlSessionFactoryBean(){
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            //设置xml扫描路径
            sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:mapper/*.xml"));
            return sqlSessionFactoryBean.getObject();
        } catch (Exception e) {
            throw new RuntimeException("sqlSessionFactory init fail",e);
        }
    }

    @Bean(name = "sqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean(name = "transactionManager")
    @ConditionalOnMissingBean
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }
}
