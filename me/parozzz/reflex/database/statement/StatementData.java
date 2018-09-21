package me.parozzz.reflex.database.statement;

import me.parozzz.reflex.database.SQLDataType;

class StatementData
{
    private final SQLDataType type;
    private final Object obj;

    protected StatementData(SQLDataType type, Object obj)
    {
        this.type = type;
        this.obj = obj;
    }

    public SQLDataType getType()
    {
        return type;
    }

    public Object getObject()
    {
        return obj;
    }
}
