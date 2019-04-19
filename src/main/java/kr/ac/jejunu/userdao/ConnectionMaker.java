package kr.ac.jejunu.userdao;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionMaker {
    public abstract Connection getConnection() throws ClassNotFoundException, SQLException;
}
