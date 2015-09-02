package com.example.andy.scino_books;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by andy on 24.08.15.
 */
@DatabaseTable(tableName = "book")

public class Book {
    @DatabaseField(generatedId = true)
    private int Id;

    @DatabaseField(canBeNull = false, dataType = DataType.STRING)
    private String name;

    @DatabaseField( columnDefinition = "TEXT")
    private String author;

    @DatabaseField( columnDefinition = "TEXT")
    private String description;

    @DatabaseField(canBeNull = false, dataType = DataType.BOOLEAN)
    private boolean read;

    public boolean getRead(){
        return read;
    }

    @DatabaseField(foreign = true)
    private Category category;

    public void setCategory(Category bookCategory){
        category=bookCategory;
    }
    public Category getCategory(){
        return category;
    }

    public int getId(){
        return Id;
    }

    public String getName(){
        return name;
    }

    public Book(String bookName, String bookAuthor, String bookDescription, boolean bookReaded){
        name=bookName;
        author=bookAuthor;
        description=bookDescription;
        read=bookReaded;
    }
    public Book(String bookName, String bookAuthor, String bookDescription, boolean bookReaded,Category bookCategory){
        name=bookName;
        author=bookAuthor;
        description=bookDescription;
        read=bookReaded;
        category=bookCategory;
    }
    public Book(){

    }



}
