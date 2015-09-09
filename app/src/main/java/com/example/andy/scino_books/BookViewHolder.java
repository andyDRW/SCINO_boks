package com.example.andy.scino_books;

import android.widget.CheckBox;
import android.widget.TextView;

/**
 * Created by andy on 07.09.15.
 */
public class BookViewHolder  {
    private CheckBox checkBox ;
    private TextView textViewBookName;
    private TextView textViewAuthor;

    public BookViewHolder() {}
    public BookViewHolder( TextView inTextViewName, CheckBox inCheckBox, TextView inTextViewAuthor ) {
        checkBox = inCheckBox ;
        textViewAuthor=inTextViewAuthor;
        textViewBookName = inTextViewName ;
    }
    public CheckBox getCheckBox() {
        return checkBox;
    }

    public TextView getTextViewAuthor() {
        return textViewAuthor;
    }

    public void setTextViewAuthor(TextView textViewAuthor) {
        this.textViewAuthor = textViewAuthor;
    }

    public void setCheckBox(CheckBox inCheckBox) {
       checkBox = inCheckBox;
    }
    public TextView getTextViewBookName() {
        return textViewBookName;
    }
    public void setTextViewBookName(TextView inTextView) {
       textViewBookName = inTextView;
    }
}
