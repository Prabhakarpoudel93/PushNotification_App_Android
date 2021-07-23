package androidpractise.com.prabhakar.garbagecollectionsystem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    static String name = "db_wasteconcern";
    static int version = 1;
    String createtableadmin =" CREATE TABLE if not exists \"admin\" (\n" +
            "\t`admin_id`\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "\t`username`\tTEXT,\n" +
            "\t`password`\tTEXT,\n" +
            "\t`email`\tTEXT UNIQUE,\n" +
            "\t`phone`\tTEXT UNIQUE\n" +
            ")";
    String createtablecustomer=" CREATE TABLE if not exists \"customers\" (\n" +
            "\t`id`\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "\t`name`\tTEXT,\n" +
            "\t`phone`\tTEXT UNIQUE,\n" +
            "\t`email`\tTEXT UNIQUE,\n" +
            "\t`areano`\tTEXT,\n" +
            "\t`username`\tTEXT UNIQUE,\n" +
            "\t`password`\tTEXT\n" +
            ")";


    public DatabaseHelper(Context context) {
        super(context, name, null, version);
        getWritableDatabase().execSQL(createtableadmin);
        getWritableDatabase().execSQL(createtablecustomer);
        insertAdminUser();

    }

    public void insertAdminUser() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", "admin");
        contentValues.put("password", "admin1");
        contentValues.put("email", "admin");
        contentValues.put("phone", "9868125317");
        if (!isLoginValid("admin", "admin1")) {
            getWritableDatabase().insert("admin", "", contentValues);
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean isLoginValid(String username, String password) {
        String sql = " Select count(*) from admin where username='" + username + "' and password='" + password + "'";
        SQLiteStatement statement = getReadableDatabase().compileStatement(sql);
        long l = statement.simpleQueryForLong();
        if (l == 1) {
            return true;
        } else
            return false;
    }

    public void insertCustomer(ContentValues contentValues)
    {
        getWritableDatabase().insert("customers","",contentValues);
    }

    public ArrayList<Customerinfo> getUserList()
    {
        String sql= "select * from customers";
        Cursor cursor=getReadableDatabase().rawQuery(sql,null);
        ArrayList<Customerinfo> list= new ArrayList<>();
        while (cursor.moveToNext())
        {
            Customerinfo info=new Customerinfo();
            info.id = cursor.getString(cursor.getColumnIndex("id"));
            info.name = cursor.getString(cursor.getColumnIndex("name"));
            info.phone = cursor.getString(cursor.getColumnIndex("phone"));
            info.email = cursor.getString(cursor.getColumnIndex("email"));
            info.areano = cursor.getString(cursor.getColumnIndex("areano"));
            info.username = cursor.getString(cursor.getColumnIndex("username"));
            info.password = cursor.getString(cursor.getColumnIndex("password"));
            list.add(info);
        }
        cursor.close();
        return list;

    }
    public ArrayList<String> getUserNameList()
    {
        String sql= "select * from customers";
        Cursor cursor=getReadableDatabase().rawQuery(sql,null);
        ArrayList<String > list= new ArrayList<>();
        while (cursor.moveToNext())
        {
            list.add(cursor.getString(cursor.getColumnIndex("name")));
            list.add(cursor.getString(cursor.getColumnIndex("id")));
        }
        cursor.close();
        return list;

    }
    public boolean isCustomerValid(String username1,String password1)
    {
        String sql = " Select count(*) from customers where username='" + username1 + "' and password='" + password1 + "'";
        SQLiteStatement statement=getReadableDatabase().compileStatement(sql);
        long l= statement.simpleQueryForLong();
        if (l == 1) {
            return true;
        } else
            return false;

    }
    public Customerinfo getUserInfo(String id)
    {
        String sql= "select * from customers where id="+id;
        Cursor cursor=getReadableDatabase().rawQuery(sql,null);
        Customerinfo info=new Customerinfo();
        while (cursor.moveToNext())
        {
            info.id = cursor.getString(cursor.getColumnIndex("id"));
            info.name = cursor.getString(cursor.getColumnIndex("name"));
            info.phone = cursor.getString(cursor.getColumnIndex("phone"));
            info.email = cursor.getString(cursor.getColumnIndex("email"));
            info.areano = cursor.getString(cursor.getColumnIndex("areano"));
            info.username = cursor.getString(cursor.getColumnIndex("username"));
            info.password = cursor.getString(cursor.getColumnIndex("password"));

        }
        cursor.close();
        return info;

    }

    public void updateCustomer(String id, ContentValues contentValues)
    {
        getWritableDatabase().update("customers",contentValues,"id="+id,null);

    }
    public void deleteCustomer(String id)
    {
        getWritableDatabase().delete("customers","id="+id,null);
    }

}
