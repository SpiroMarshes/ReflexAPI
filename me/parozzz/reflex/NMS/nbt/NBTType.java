package me.parozzz.reflex.NMS.nbt;

public enum NBTType
{
    END((byte) 0),
    BYTE((byte) 1),
    SHORT((byte) 2),
    INT((byte) 3),
    LONG((byte) 4),
    FLOAT((byte) 5),
    DOUBLE((byte) 6),
    BYTEARRAY((byte) 7),
    STRING((byte) 8),
    LIST((byte) 9),
    COMPOUND((byte) 10),
    INTARRAY((byte) 11),
    LONGARRAY((byte) 12);

    private final byte id;

    NBTType(final byte id)
    {
        this.id = id;
    }

    public byte getId()
    {
        return id;
    }

    public static NBTType getType(final int id)
    {
        switch(id)
        {
            case 0:
                return END;
            case 1:
                return BYTE;
            case 2:
                return SHORT;
            case 3:
                return INT;
            case 4:
                return LONG;
            case 5:
                return FLOAT;
            case 6:
                return DOUBLE;
            case 7:
                return BYTEARRAY;
            case 8:
                return STRING;
            case 9:
                return LIST;
            case 10:
                return COMPOUND;
            case 11:
                return INTARRAY;
            case 12:
                return LONGARRAY;
            default:
                return null;
        }
    }
}
