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
    private int mId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_show_book, null);
        Bundle bundle = getArguments();
        if (bundle==null){
            bundle=savedInstanceState;
        }
        mId = bundle.getInt("book", 1);
        try {
            mBook=HelperFactory.getHelper().getBookDAO().getBookByID(mId);
            Category mCategory = mBook.getCategory();
            HelperFactory.getHelper().getCathegoryDAO().refresh(mCategory);
            TextView mTextViewBookName = (TextView) v.findViewById(R.id.textViewBookName);
            TextView mTextViewBookAuthor = (TextView) v.findViewById(R.id.textViewBookAuthor);
            TextView mTextViewBookCategory = (TextView) v.findViewById(R.id.textViewBookCategory);
            TextView mTextViewBookDescription = (TextView) v.findViewById(R.id.textViewBookDescription);
            CheckBox mCheckBoxBookRead = (CheckBox) v.findViewById(R.id.checkBoxBookRead);
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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("book",mId);
        super.onSaveInstanceState(outState);
    }
}
