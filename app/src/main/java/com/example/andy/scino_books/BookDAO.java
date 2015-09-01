package com.example.andy.scino_books;

import android.database.SQLException;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;

import java.util.List;

/**
 * Created by andy on 24.08.15.
 */
public class BookDAO extends BaseDaoImpl<Book, Integer> {

    protected BookDAO(ConnectionSource connectionSource,
                      Class<Book> dataClass) throws SQLException, java.sql.SQLException {
        super(connectionSource, dataClass);
    }

    public List<Book> getAllBooksByAuthor() throws SQLException, java.sql.SQLException {
        QueryBuilder<Book, Integer> queryBuilder = queryBuilder();
        queryBuilder.orderBy("author", true);
        PreparedQuery<Book> preparedQuery = queryBuilder.prepare();
        List<Book> booksList =query(preparedQuery);
        return booksList;
    }

    public List<Book> getAllBooks() throws SQLException, java.sql.SQLException {
        QueryBuilder<Book, Integer> queryBuilder = queryBuilder();
        queryBuilder.orderBy("name", true);
        PreparedQuery<Book> preparedQuery = queryBuilder.prepare();
        List<Book> booksList =query(preparedQuery);
        return booksList;
    }

    public List<Book> getAllReadBooks() throws SQLException, java.sql.SQLException {
        QueryBuilder<Book, Integer> queryBuilder = queryBuilder();
        queryBuilder.where().eq("read", "true");
        PreparedQuery<Book> preparedQuery = queryBuilder.prepare();
        List<Book> booksList =query(preparedQuery);
        return booksList;
    }

    public List<Book> getAllNotReadBooks() throws SQLException, java.sql.SQLException {
        QueryBuilder<Book, Integer> queryBuilder = queryBuilder();
        queryBuilder.where().eq("read", "false");
        PreparedQuery<Book> preparedQuery = queryBuilder.prepare();
        List<Book> booksList =query(preparedQuery);
        return booksList;
    }
    public List<Book> getBookByCategory(Category searchCategory) throws SQLException, java.sql.SQLException {
        QueryBuilder<Book, Integer> queryBuilder = queryBuilder();
        queryBuilder.where().eq("category_id", searchCategory.getId());
        PreparedQuery<Book> preparedQuery = queryBuilder.prepare();
        List<Book> booksList =query(preparedQuery);
        return booksList;
    }
    public List<Book> getBooksWithNullCategory() throws SQLException, java.sql.SQLException {
        QueryBuilder<Book, Integer> queryBuilder = queryBuilder();
        queryBuilder.where().eq("category_id", null);
        PreparedQuery<Book> preparedQuery = queryBuilder.prepare();
        List<Book> booksList =query(preparedQuery);
        return booksList;
    }


}
