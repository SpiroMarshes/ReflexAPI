package me.parozzz.reflex.database;

import java.sql.SQLException;

@FunctionalInterface
public interface SQLFunction<V, R>
{
    R apply(V value) throws SQLException;
}
