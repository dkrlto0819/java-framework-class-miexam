package kr.ac.jejunu.userdao;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DaoFactory {

    @Bean
    public UserDao getUserDao(){
        return new UserDao(getConnectionMaker());
    }

    @Bean
    public ConnectionMaker getConnectionMaker(){
        return new JejuConnectionMaker();
    }
}
