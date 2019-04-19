package kr.ac.jejunu.userdao;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class UserDaoTests {
    UserDao userDao;
    DaoFactory daoFactory;

    @Before
    public void setup() {
        daoFactory = new DaoFactory();
        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
        userDao = context.getBean("userDao", UserDao.class);
    }


    @Test
    public void testGet() throws SQLException, ClassNotFoundException {
        Long id = 1l;
        String name = "허윤호";
        String password = "1234";
//        daoFactory = new DaoFactory();
//        userDao = daoFactory.getUserDao();

        User user = userDao.get(id);
        assertThat(user.getId(), is(id));
        assertThat(user.getName(), is(name));
        assertThat(user.getPassword(), is(password));
    }
//
//    @Test
//    public void HallatestGet() throws SQLException, ClassNotFoundException {
//        Long id = 1l;
//        String name = "허윤호";
//        String password = "1234";
//        ConnectionMaker connectionMaker = new HallaConnectionMaker();
//        UserDao userDao = new UserDao(connectionMaker);
//        User user = userDao.get(id);
//        assertThat(user.getId(), is(id));
//        assertThat(user.getName(), is(name));
//        assertThat(user.getPassword(), is(password));
//    }


    @Test
    public void testAdd() throws SQLException, ClassNotFoundException {
        String name = "허윤호";
        String password="1234";

        User user = new User();
        user.setName(name);
        user.setPassword(password);
//        daoFactory = new DaoFactory();
//        UserDao userDao = daoFactory.getUserDao();
        Long id = userDao.add(user);
        User resultUser = userDao.get(id);


        assertThat(resultUser.getId(), is(id));
        assertThat(resultUser.getName(), is(name));
        assertThat(resultUser.getPassword(), is(password));
    }
}


