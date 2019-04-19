package kr.ac.jejunu.userdao;

import javax.sql.DataSource;
import java.sql.*;

public class UserDao {
    //ConnectionMaker connectionMaker;

    Connection connection;
    DataSource dataSource;

    public UserDao(DataSource dataSource){
        this.dataSource = dataSource;
    }

    public User get(Long id) throws ClassNotFoundException, SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        User user;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement("select * from userinfo where id = ?");
            preparedStatement.setLong(1, id);

            resultSet = preparedStatement.executeQuery();
            resultSet.next();

            user = new User();
            user.setId(resultSet.getLong("id"));
            user.setName(resultSet.getString("name"));
            user.setPassword(resultSet.getString("password"));
        } finally {
            if(resultSet != null){
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    new RuntimeException();
                }
            }
            if(preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    new RuntimeException();
                }
            }
            if(connection != null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    new RuntimeException();
                }
            }
        }
        //리턴
        return user;
    }

    public Long add(User user) throws ClassNotFoundException, SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Long id;
        try {
            connection = dataSource.getConnection();

            preparedStatement = connection.prepareStatement("insert into userinfo(name, password) values(?, ?)" );

            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getPassword());

            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement("select last_insert_id()");
            resultSet = preparedStatement.executeQuery();

            resultSet.next();

            id = resultSet.getLong(1);
        } finally {
            if(resultSet != null){
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    new RuntimeException();
                }
            }
            if(preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    new RuntimeException();
                }
            }
            if(connection != null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    new RuntimeException();
                }
            }
        }
        return id;

    }
}
