package kr.ac.jejunu.userdao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DaoFactory {
//    @Value("${db.classname}")
//    private String classname;
//    @Value("${db.url}")
//    private String url;
//    @Value("${db.password}")
//    private String password;
//    @Value("${db.username}")
//    private String username;
    @Bean
    public UserDao userDao(){
        return new UserDao(connectionMaker());
    }

    @Bean
    public ConnectionMaker connectionMaker(){
        return new JejuConnectionMaker();
    }
}
