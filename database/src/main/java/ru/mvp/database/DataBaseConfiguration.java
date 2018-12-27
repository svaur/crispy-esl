package ru.mvp.database;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories("ru.mvp.database.repositories")
public class DataBaseConfiguration {

//        Пример использования
//        ApplicationContext ctx = new AnnotationConfigApplicationContext(DataBaseConfiguration.class);
//        DirectoryParamsRepository directoryParamsRepository = ctx.getBean(DirectoryParamsRepository.class);
//
//        for (DirectoryParams directoryParams: directoryParamsRepository.findAll()) {
//            System.out.println(directoryParams.getId() + " " + directoryParams.getName());
//        }

    @Bean
    public DataSource dataSource() {

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(org.postgresql.Driver.class.getName());
        //TODO: вынести в конфиг
        dataSource.setUrl("jdbc:postgresql://127.0.0.1:5432/eslbase");
        dataSource.setUsername("postgres");
        dataSource.setPassword("Ieatcxz123");

        return dataSource;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter(){
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        //TODO: вынести в конфиг
        vendorAdapter.setGenerateDdl(false);
        vendorAdapter.setShowSql(true);
        vendorAdapter.setDatabase(Database.POSTGRESQL);
        return vendorAdapter;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(jpaVendorAdapter());
        factory.setPackagesToScan("ru.mvp.database.entities");
        factory.setDataSource(dataSource());

        return factory;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {

        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory);
        return txManager;
    }
}
