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
        
        User user = null;
        try {
            connection = dataSource.getConnection();
            StatementStrategy statementStrategy = new GetStatementStrategy();
            preparedStatement = statementStrategy.makeStatement(id, connection);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setPassword(resultSet.getString("password"));
            }
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

            StatementStrategy statementStrategy = new AddStatementStrategy();
            preparedStatement = statementStrategy.makeStatement(user, connection);

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

    public void update(User user) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Long id;
        try {
            connection = dataSource.getConnection();

            StatementStrategy statementStrategy = new UpdateStatementStrategy();
            preparedStatement = statementStrategy.makeStatement(user, connection);
            preparedStatement.executeUpdate();

        } finally {
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
    }

    public void delete(Long id) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        User user;
        try {
            connection = dataSource.getConnection();
            StatementStrategy statementStrategy = new DeleteStatementStrategy();
//            preparedStatement = connection.prepareStatement("delete from userinfo where id = ?" );
//            preparedStatement.setLong(1, id);
            preparedStatement = statementStrategy.makeStatement(id, connection);
            preparedStatement.executeUpdate();

        } finally {
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
    }
}