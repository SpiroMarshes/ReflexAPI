package me.parozzz.reflex.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class SQLTable implements Connectionable
{
    private final DatabaseCore core;
    private final String name;

    public SQLTable(DatabaseCore core, String name)
    {
        this.core = core;
        this.name = name;
    }

    @Override
    public final Connection getConnection() throws SQLException
    {
        return core.getConnection();
    }

    public final String getName()
    {
        return name;
    }

    protected final void executeSQL(SQLConsumer<Connection> consumer)
    {
        try(Connection con = core.getConnection())
        {
            consumer.accept(con);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    protected final void executeUpdate(String sql)
    {
        try(Connection con = core.getConnection(); PreparedStatement stm = con.prepareStatement(sql))
        {
            stm.executeUpdate();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    protected final void executeUpdate(String sql, SQLConsumer<PreparedStatement> consumer)
    {
        try(Connection con = core.getConnection(); PreparedStatement stm = con.prepareStatement(sql))
        {
            consumer.accept(stm);
            stm.executeUpdate();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    protected final void executeQuery(String sql, SQLConsumer<PreparedStatement> consumer, SQLConsumer<ResultSet> resultConsumer)
    {
        try(Connection con = core.getConnection(); PreparedStatement stm = con.prepareStatement(sql))
        {
            consumer.accept(stm);
            resultConsumer.accept(stm.executeQuery());
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    protected final void executeQuery(String sql, SQLConsumer<ResultSet> resultConsumer)
    {
        try(Connection con = core.getConnection(); PreparedStatement stm = con.prepareStatement(sql))
        {
            resultConsumer.accept(stm.executeQuery());
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
}
