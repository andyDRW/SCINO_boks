package com.example.andy.scino_books;

import android.database.SQLException;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.stmt.Where;
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
        queryBuilder.where().eq("read", true);
        PreparedQuery<Book> preparedQuery = queryBuilder.prepare();
        List<Book> booksList =query(preparedQuery);
        return booksList;
    }
    public Book getBookByID(int id)throws SQLException, java.sql.SQLException {
        QueryBuilder<Book, Integer> queryBuilder = queryBuilder();
        queryBuilder.where().eq("id", id);
        PreparedQuery<Book> preparedQuery = queryBuilder.prepare();
        List<Book> booksList =query(preparedQuery);
        return booksList.get(0);
    }

    public List<Book> getReadBooksWithoutCategory() throws SQLException, java.sql.SQLException {
        QueryBuilder<Book, Integer> queryBuilder = queryBuilder();
        Where where =queryBuilder.where();
        where.and(where.isNull("category_id"), where.eq("read", true));
        PreparedQuery<Book> preparedQuery = queryBuilder.prepare();
        List<Book> booksList =query(preparedQuery);
        return booksList;
    }

    public List<Book> getNotReadBooksWithoutCategory() throws SQLException, java.sql.SQLException {
        QueryBuilder<Book, Integer> queryBuilder = queryBuilder();
        Where where =queryBuilder.where();
        where.and(where.isNull("category_id"), where.eq("read", false));
        PreparedQuery<Book> preparedQuery = queryBuilder.prepare();
        List<Book> booksList =query(preparedQuery);
        return booksList;
    }

    public List<Book> getAllNotReadBooks() throws SQLException, java.sql.SQLException {
        QueryBuilder<Book, Integer> queryBuilder = queryBuilder();
        queryBuilder.where().eq("read", false);
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

    public List<Book> getBookByCategoryRead(Category searchCategory) throws SQLException, java.sql.SQLException {
        QueryBuilder<Book, Integer> queryBuilder = queryBuilder();
        Where where = queryBuilder.where();
        where.and(where.eq("category_id", searchCategory.getId()), where.eq("read", true));
        PreparedQuery<Book> preparedQuery = queryBuilder.prepare();
        List<Book> booksList =query(preparedQuery);
        return booksList;
    }

    public List<Book> getBookByCategoryNotRead(Category searchCategory) throws SQLException, java.sql.SQLException {
        QueryBuilder<Book, Integer> queryBuilder = queryBuilder();
        Where where = queryBuilder.where();
        where.and(where.eq("category_id", searchCategory.getId()), where.eq("read", false));
        PreparedQuery<Book> preparedQuery = queryBuilder.prepare();
        List<Book> booksList =query(preparedQuery);
        return booksList;
    }
    public List<Book> getBooksWithoutCategory() throws SQLException, java.sql.SQLException {
        QueryBuilder<Book, Integer> queryBuilder = queryBuilder();
        queryBuilder.where().isNull("category_id");
        PreparedQuery<Book> preparedQuery = queryBuilder.prepare();
        List<Book> booksList =query(preparedQuery);
        return booksList;
    }
    public void updateBooksCategoryToNull(int categoryId){
        UpdateBuilder<Book,Integer> updateBuilder=updateBuilder();
        SelectArg arg = new SelectArg();
        arg.setValue(null);
        try {
            updateBuilder.where().eq("category_id",categoryId);
            updateBuilder.updateColumnValue("category_id",arg);
            updateBuilder.update();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }
    public void updateBook(int bookId,String bookName, String bookDescription,String bookAuthor, boolean bookRead, Category category){
        UpdateBuilder<Book,Integer> updateBuilder=updateBuilder();
        try {
            updateBuilder.where().eq("id",bookId);
            updateBuilder.updateColumnValue("name", bookName);
            updateBuilder.updateColumnValue("author",bookAuthor);
            updateBuilder.updateColumnValue("description",bookDescription);
            updateBuilder.updateColumnValue("read", bookRead);
            updateBuilder.updateColumnValue("category_id",category.getId());
            updateBuilder.update();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }
    public void updateBookNulCategory(int bookId,String bookName, String bookDescription,String bookAuthor, boolean bookRead){
        UpdateBuilder<Book,Integer> updateBuilder=updateBuilder();
        SelectArg arg = new SelectArg();
        arg.setValue(null);
        try {
            updateBuilder.where().eq("id",bookId);
            updateBuilder.updateColumnValue("name", bookName);
            updateBuilder.updateColumnValue("author",bookAuthor);
            updateBuilder.updateColumnValue("description",bookDescription);
            updateBuilder.updateColumnValue("read",bookRead);
            updateBuilder.updateColumnValue("category_id",arg);
            updateBuilder.update();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

}
