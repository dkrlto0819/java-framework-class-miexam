package kr.ac.jejunu.userdao;

import javax.sql.DataSource;
import java.sql.*;

public class UserDao {
    //ConnectionMaker connectionMaker;

    Connection connection;
    DataSource dataSource;
    JdbcContext jdbcContext;

    public UserDao(JdbcContext jdbcContext){
        this.jdbcContext = jdbcContext;
    }

    public User get(Long id) throws ClassNotFoundException, SQLException {
        StatementStrategy statementStrategy = connection -> {
            String sql = "select * from userinfo where id = ?";
            Object[] params = new Object[] {id};
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            for(int i=0;i<params.length;i++)
                preparedStatement.setObject(i+1, params[i]);

            return preparedStatement;
        };
        return jdbcContext.jdbcContextForGet(statementStrategy);
    }

    public Long add(User user) throws ClassNotFoundException, SQLException {
        StatementStrategy statementStrategy = connection -> {
            String sql = "insert into userinfo(name, password) values(?, ?)";
            Object[] params = new Object[] {user.getName(), user.getPassword()};
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            for(int i=0;i<params.length;i++)
                preparedStatement.setObject(i+1, params[i]);
            return preparedStatement;
        };
        return jdbcContext.jdbcContextForAdd(statementStrategy);
    }

    public void update(User user) throws SQLException {
        StatementStrategy statementStrategy = connection -> {
            String sql = "update userinfo set name = ?, password = ? where id = ?";
            Object[] params = new Object[] {user.getName(), user.getPassword(), user.getId()};
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            for(int i=0;i<params.length;i++)
                preparedStatement.setObject(i+1, params[i]);
            return preparedStatement;
        };
        jdbcContext.jdbcContextUpdate(statementStrategy);
    }

    public void delete(Long id) throws SQLException {
        StatementStrategy statementStrategy = connection -> {
            String sql = "delete from userinfo where id = ?";
            Object[] params = new Object[] {id};
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            for(int i=0;i<params.length;i++)
                preparedStatement.setObject(i+1, params[i]);
            return preparedStatement;
        };
        jdbcContext.jdbcContextDelete(statementStrategy);
    }
}