package me.parozzz.reflex.database;

import org.bukkit.craftbukkit.libs.it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import org.bukkit.craftbukkit.libs.it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

import java.sql.JDBCType;
import java.sql.SQLType;

public enum SQLDataType
{
    STRING(JDBCType.VARCHAR),
    INT(JDBCType.INTEGER),
    DOUBLE(JDBCType.DOUBLE),
    FLOAT(JDBCType.FLOAT),
    LONG(JDBCType.BIGINT),
    CHAR(JDBCType.CHAR),
    BOOLEAN(JDBCType.BOOLEAN);
    //COMPLEX_OBJECT(JDBCType.VARCHAR);

    private final static Int2ObjectMap<SQLDataType> typesMap = new Int2ObjectOpenHashMap<>();

    public static SQLDataType getByType(final int type)
    {
        return typesMap.get(type);
    }

    static
    {
        for(SQLDataType type : values())
        {
            typesMap.put(type.getSQLType(), type);
        }
    }

    private final SQLType sqlType;

    SQLDataType(final SQLType sqlType)
    {
        this.sqlType = sqlType;
    }

    public String getSQLName()
    {
        return sqlType.getName();
    }

    public int getSQLType()
    {
        return sqlType.getVendorTypeNumber();
    }

}
