package com.info.chatbot.config;


import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//@Configuration
//@PropertySources({
//@PropertySource(value = "classpath:application.properties"),
//@PropertySource(value = "file:./config/application.properties", ignoreResourceNotFound = true)
//})
@ComponentScan(basePackages = "com.info.chatbot" )
@EnableJpaRepositories(basePackages = "com.info.chatbot.repository")
public class AppConfig {


//    private SignedRepository signedRepository;
//
//    @Autowired
//    public void setSignedRepository(SignedRepository signedRepository){
//        this.signedRepository = signedRepository;
//    }


//    private final SignedRepository signedRepository;
//
//    public AppConfig(SignedRepository signedRepository) {
//        this.signedRepository = signedRepository;
//    }
//    @Bean
//    public SignedUpService getSignedUpService() {
//        SignedUpServiceImpl signedUpService = new SignedUpServiceImpl();
//        signedUpService.setSignedRepository(signedRepository);
//        return signedUpService;
//
//    }

//    @Bean
//    public DataSource dataSource() {
////        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        BasicDataSource dataSource = new BasicDataSource();
//        dataSource.setDriverClassName("org.postgresql.Driver");
//        dataSource.setUrl("jdbc:postgresql://localhost:5432/infobot");
//        dataSource.setUsername("postgres");
//        dataSource.setPassword("postgres");
//        // Налаштування DataSource
//        return dataSource;
//    }
//
////    @Bean
////    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
////        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
////        em.setDataSource(dataSource());
////        em.setPackagesToScan("com.info.chatbot.entity");
////        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
////        return em;
////    }
//
//
//    @Bean
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
//        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
//        em.setDataSource(dataSource());
//        em.setPackagesToScan("com.info.chatbot.entity");
//
//        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//        em.setJpaVendorAdapter(vendorAdapter);
//
//        Properties properties = new Properties();
//        properties.setProperty("hibernate.hbm2ddl.auto", "update");
//        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
//        properties.setProperty("hibernate.show_sql", "true");
//        properties.setProperty("hibernate.format_sql", "true");
//
//        em.setJpaProperties(properties);
//
//        return em;
//    }
//
//    @Bean
//    public JpaTransactionManager transactionManager() {
//        JpaTransactionManager transactionManager = new JpaTransactionManager();
//        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
//        return transactionManager;
//    }


}
