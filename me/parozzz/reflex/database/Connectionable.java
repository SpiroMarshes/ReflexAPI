package me.parozzz.reflex.database;

import java.sql.Connection;
import java.sql.SQLException;

public interface Connectionable
{
    Connection getConnection() throws SQLException;
}
