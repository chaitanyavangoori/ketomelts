package com.ketomelts.main;

import com.ketomelts.main.domain.Customer;
import com.ketomelts.main.repository.CustomerRepository;
import com.ketomelts.main.services.StockService;
import com.ketomelts.main.services.StockServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@ComponentScan
public class TestApplication extends WebMvcConfigurerAdapter implements CommandLineRunner{

    public static void main(String[] args) {
		SpringApplication.run(TestApplication.class, args);

	}

    @Autowired
    private CustomerRepository repository;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/index").setViewName("index.html");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        super.configurePathMatch(configurer);
        configurer.setUseSuffixPatternMatch(false);
    }

    @Bean
    public StockService stockService(){
        return new StockServiceImpl();
    }

    @Override
    public void run(String... strings) throws Exception {
        repository.save(new Customer("xyz@test.com", "xyz", "abc"));
        for (Customer customer : repository.findAll()) {
            System.out.println(customer.getId());
        }
    }
}
