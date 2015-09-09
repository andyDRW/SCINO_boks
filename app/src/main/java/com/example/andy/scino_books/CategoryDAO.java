package com.example.andy.scino_books;

import android.database.SQLException;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.support.ConnectionSource;

import java.util.List;

/**
 * Created by andy on 24.08.15.
 */
public class CategoryDAO extends BaseDaoImpl<Category, Integer> {

    protected CategoryDAO(ConnectionSource connectionSource,
                      Class<Category> dataClass) throws SQLException, java.sql.SQLException {
        super(connectionSource, dataClass);
    }

    public List<Category> getAllCategories() throws SQLException, java.sql.SQLException {
        return this.queryForAll();
    }
    public Category getCategoryByName(String categoryName) throws java.sql.SQLException {
        QueryBuilder<Category, Integer> queryBuilder = queryBuilder();
        queryBuilder.where().eq("name", categoryName);
        PreparedQuery<Category> preparedQuery = queryBuilder.prepare();
        List<Category> categories =query(preparedQuery);
        Category category=categories.get(0);
        return category;
    }
    public void updateCategory(String name,String description,int id){
        UpdateBuilder<Category, Integer> updateBuilder=updateBuilder();

        try {
            updateBuilder.where().eq("Id",id);
            updateBuilder.updateColumnValue("name", name);
            updateBuilder.updateColumnValue("description",description);
            updateBuilder.update();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }

    }

}