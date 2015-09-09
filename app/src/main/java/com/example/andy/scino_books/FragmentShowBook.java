package com.example.andy.scino_books;


import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.sql.SQLException;

/**
 * Created by andy on 31.08.15.
 */
public class FragmentShowBook extends DialogFragment {
    private Book mBook;
    private Category mCategory;
    private TextView mTextViewBookName;
    private TextView mTextViewBookAuthor;
    private CheckBox mCheckBoxBookRead;
    private TextView mTextViewBookCategory;
    private TextView mTextViewBookDescription;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_show_book, null);
        Bundle bundle = getArguments();
        int id = bundle.getInt("book", 1);
        try {
            mBook=HelperFactory.getHelper().getBookDAO().getBookByID(id);
            mCategory=mBook.getCategory();
            HelperFactory.getHelper().getCathegoryDAO().refresh(mCategory);
            mTextViewBookName=(TextView)v.findViewById(R.id.textViewBookName);
            mTextViewBookAuthor=(TextView)v.findViewById(R.id.textViewBookAuthor);
            mTextViewBookCategory=(TextView)v.findViewById(R.id.textViewBookCategory);
            mTextViewBookDescription=(TextView)v.findViewById(R.id.textViewBookDescription);
            mCheckBoxBookRead=(CheckBox)v.findViewById(R.id.checkBoxBookRead);
            mTextViewBookName.setText(mBook.getName());
            mCheckBoxBookRead.setChecked(mBook.getRead());
            mTextViewBookAuthor.setText(mBook.getAuthor());
            mTextViewBookDescription.setText(mBook.getDescription());
            if(mBook.getCategory()!=null) {
                mTextViewBookCategory.setText(mBook.getCategory().getName());
            }
            else{
                mTextViewBookCategory.setText(R.string.no_category);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return v;
    }
    public int getBookId(){
        return mBook.getId();
    }

}
