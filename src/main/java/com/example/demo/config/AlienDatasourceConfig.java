package com.example.demo.config;

import com.example.demo.alien.Alien;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "alienEntityManagerFactoryBuilder",
        transactionManagerRef = "alienPlatformTransactionManager",
        basePackages = "com.example.demo.alien"
)
public class AlienDatasourceConfig {

    @Primary
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.one")
    public DataSourceProperties alienDatasourceProperties() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean("alienDatasource")
    public DataSource alienDatasource(@Qualifier("alienDatasourceProperties") DataSourceProperties alienDatasourceProperties) {
        return alienDatasourceProperties.initializeDataSourceBuilder().build();
    }

    @Primary
    @Bean("alienEntityManagerFactoryBuilder")
    public LocalContainerEntityManagerFactoryBean alienLocalContainerEntityManager(EntityManagerFactoryBuilder alienEntityManagerFactoryBuilder, @Qualifier("alienDatasource") DataSource alienDatasource) {
        return alienEntityManagerFactoryBuilder.dataSource(alienDatasource).packages(Alien.class).build();
    }

    @Primary
    @Bean("alienPlatformTransactionManager")
    public PlatformTransactionManager alienPlatformTransactionManager(@Qualifier("alienEntityManagerFactoryBuilder") EntityManagerFactory alienEntityManagerFactory) {
        return new JpaTransactionManager(alienEntityManagerFactory);
    }
}
