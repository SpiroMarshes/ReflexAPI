package me.parozzz.reflex.database.statement;

import me.parozzz.reflex.database.DatabaseCore;
import me.parozzz.reflex.database.SQLConsumer;
import me.parozzz.reflex.database.SQLDataType;
import me.parozzz.reflex.database.SQLFunction;
import me.parozzz.reflex.tools.task.TaskManager;
import org.apache.commons.lang3.Validate;

import javax.annotation.Nullable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class StatementBuilder
{
    private final DatabaseCore databaseCore;
    private final TaskManager taskManager;

    private final List<StatementData> parseList;

    private String sql;

    public StatementBuilder(DatabaseCore databaseCore, TaskManager taskManager)
    {
        this.databaseCore = databaseCore;
        this.taskManager = taskManager;

        this.parseList = new ArrayList<>();
    }

    public StatementBuilder setSQL(final String sql)
    {
        this.sql = sql;
        return this;
    }

    public StatementBuilder parse(final SQLDataType type, final Object obj)
    {
        parseList.add(new StatementData(type, obj));
        return this;
    }

    public void executeUpdate()
    {
        Validate.notNull(sql, "SQL Statement not set.");

        try(Connection con = databaseCore.getConnection(); PreparedStatement stm = con.prepareStatement(sql))
        {
            parseStatement(stm).executeUpdate();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void executeAsyncUpdate()
    {
        taskManager.async(this::executeUpdate);
    }

    public @Nullable <T> T executeQuery(final SQLFunction<ResultSet, T> function)
    {
        Validate.notNull(sql, "SQL Statement not set.");

        try(Connection con = databaseCore.getConnection(); PreparedStatement stm = con.prepareStatement(sql))
        {
            return function.apply(parseStatement(stm).executeQuery());
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public void executeQuery(SQLConsumer<ResultSet> resultConsumer)
    {
        Validate.notNull(sql, "SQL Statement not set.");

        try(Connection con = databaseCore.getConnection(); PreparedStatement stm = con.prepareStatement(sql))
        {
            resultConsumer.accept(parseStatement(stm).executeQuery());
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void executeAsyncQuery(SQLConsumer<ResultSet> setConsumer)
    {
        taskManager.async(() -> executeQuery(setConsumer));
    }

    private PreparedStatement parseStatement(PreparedStatement stm) throws SQLException
    {
        for(int x = 1; x <= parseList.size(); x++)
        {
            StatementData data = parseList.get(x - 1);
            stm.setObject(x, data.getObject(), data.getType().getSQLType());
        }
        return stm;
    }

}
