package kr.ac.jejunu.userdao;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcContext {
    Connection connection;
    DataSource dataSource;

    public JdbcContext(DataSource dataSource){
        this.dataSource = dataSource;
    }

    public User jdbcContextForGet(StatementStrategy statementStrategy) throws ClassNotFoundException, SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        User user = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = statementStrategy.makeStatement(connection);
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

    public Long jdbcContextForAdd(StatementStrategy statementStrategy) throws ClassNotFoundException, SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Long id;
        try {
            connection = dataSource.getConnection();
            preparedStatement = statementStrategy.makeStatement(connection);

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

    public void jdbcContextUpdate(StatementStrategy statementStrategy) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Long id;
        try {
            connection = dataSource.getConnection();
            preparedStatement = statementStrategy.makeStatement(connection);
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

    public void jdbcContextDelete(StatementStrategy statementStrategy) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        User user;
        try {
            connection = dataSource.getConnection();
//            preparedStatement = connection.prepareStatement("delete from userinfo where id = ?" );
//            preparedStatement.setLong(1, id);
            preparedStatement = statementStrategy.makeStatement(connection);
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
