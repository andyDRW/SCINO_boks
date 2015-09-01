package com.example.andy.scino_books;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by andy on 24.08.15.
 */
@DatabaseTable(tableName = "category")

public class Category {

    @DatabaseField(generatedId = true)
    private int Id;

    @DatabaseField(canBeNull = false, dataType = DataType.STRING,unique = true)
    private String name;

    @DatabaseField(columnDefinition = "TEXT")
    private String description;

    public int getId(){
        return Id;
    }

    public String getName(){
        return name;
    }

    public void setName(String categoryName){
        name=categoryName;
    }

    public Category(){

    }
    public String getDescription(){
        return description;
    }

    public Category(String categoryName, String categoryDescription){
        name=categoryName;
        description=categoryDescription;
    }

}
