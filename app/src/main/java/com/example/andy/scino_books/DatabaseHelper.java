package com.example.andy.scino_books;

import android.content.Context;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by andy on 24.08.15.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String TAG = DatabaseHelper.class.getSimpleName();

    //имя файла базы данных который будет храниться в /data/data/APPNAME/DATABASE_NAME.db
    private static final String DATABASE_NAME ="scinobooks.db";

    //с каждым увеличением версии, при нахождении в устройстве БД с предыдущей версией будет выполнен метод onUpgrade();
    private static final int DATABASE_VERSION = 1;

    //ссылки на DAO соответсвующие сущностям, хранимым в БД
    private BookDAO bookDao = null;
    private CategoryDAO categoryDao = null;

    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Выполняется, когда файл с БД не найден на устройстве
    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource){
        try {
            TableUtils.createTableIfNotExists(connectionSource, Book.class);
            TableUtils.createTableIfNotExists(connectionSource, Category.class);
        }
        catch (SQLException e){
            Log.e(TAG, "error creating DB " + DATABASE_NAME);
            throw new RuntimeException(e);
        }
    }

    //Выполняется, когда БД имеет версию отличную от текущей
    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVer,
                          int newVer){
        try{
            //Так делают ленивые, гораздо предпочтительнее не удаляя БД аккуратно вносить изменения
            TableUtils.dropTable(connectionSource, Book.class, true);
            TableUtils.dropTable(connectionSource, Category.class, true);
            onCreate(db, connectionSource);
        }
        catch (SQLException e){
            Log.e(TAG,"error upgrading db "+DATABASE_NAME+"from ver "+oldVer);
            throw new RuntimeException(e);
        }
    }


    public BookDAO getBookDAO() throws SQLException{
        if(bookDao == null){
            bookDao = new BookDAO(getConnectionSource(), Book.class);
        }
        return bookDao;
    }

    public CategoryDAO getCathegoryDAO() throws SQLException{
        if(categoryDao == null){
            categoryDao = new CategoryDAO(getConnectionSource(), Category.class);
        }
        return categoryDao;
    }



    //выполняется при закрытии приложения
    @Override
    public void close(){
        bookDao = null;
        categoryDao = null;
        super.close();
    }
}