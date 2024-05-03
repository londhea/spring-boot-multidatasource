package com.example.demo.config;

import com.example.demo.employee.Employee;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
        entityManagerFactoryRef = "employeeEntityManagerFactoryBuilder",
        transactionManagerRef = "employeePlatformTransactionManager",
        basePackages = "com.example.demo.employee"
)
public class EmployeeDatasourceConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.two")
    public DataSourceProperties employeeDatasourceProperties() {
        return new DataSourceProperties();
    }

    @Bean("employeeDatasource")
    public DataSource employeeDatasource(@Qualifier("employeeDatasourceProperties") DataSourceProperties employeeDatasourceProperties) {
        return employeeDatasourceProperties.initializeDataSourceBuilder().build();
    }

    @Bean("employeeEntityManagerFactoryBuilder")
    public LocalContainerEntityManagerFactoryBean employeeLocalContainerEntityManager(EntityManagerFactoryBuilder employeeEntityManagerFactoryBuilder, @Qualifier("employeeDatasource") DataSource employeeDatasource) {
        return employeeEntityManagerFactoryBuilder.dataSource(employeeDatasource).packages(Employee.class).build();
    }

    @Bean("employeePlatformTransactionManager")
    public PlatformTransactionManager employeePlatformTransactionManager(@Qualifier("employeeEntityManagerFactoryBuilder") EntityManagerFactory employeeEntityManagerFactory) {
        return new JpaTransactionManager(employeeEntityManagerFactory);
    }
}
