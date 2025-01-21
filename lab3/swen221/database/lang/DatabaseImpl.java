package swen221.database.lang;

import java.util.ArrayList;
import java.util.List;

public class DatabaseImpl implements Database{
    private int keyField;
    private ArrayList<Object[]> rows;
    private ColumnType[] schema;
    public DatabaseImpl(int keyField, ArrayList<Object[]> rows, ColumnType[] schema){
        this.keyField = keyField;
        this.rows = rows;
        this.schema = schema;
    }
    @Override
    public ColumnType[] getSchema() {
        return schema;
    }

    @Override
    public int getKeyField() {
        return keyField;
    }

    @Override
    public int size() {
        return rows.size();
    }

    @Override
    public void addRow(Object... data) throws InvalidRowException, DuplicateKeyException {
        if(data.length != schema.length) throw new InvalidRowException();
        for(int i = 0; i < schema.length; i++){
            if(!schema[i].getType().isInstance(data[i])) throw new InvalidRowException();
        }
        for(Object[] r : rows){
            if(r[keyField].equals(data[keyField])){
                throw new DuplicateKeyException();
            }
        }
        rows.add(data);
    }

    @Override
    public Object[] getRow(Object key) throws InvalidKeyException {
        Object[] row = null;
        for(Object[] r : rows){
            if(r[keyField].equals(key)) row = r;
        }
        if(row == null) throw new InvalidKeyException();
        return row;
    }

    @Override
    public Object[] getRow(int index) throws IndexOutOfBoundsException {
        if(index >= size() || index < 0) throw new IndexOutOfBoundsException();
        return rows.get(index);
    }
}
