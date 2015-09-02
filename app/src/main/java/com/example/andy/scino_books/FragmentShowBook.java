package com.example.andy.scino_books;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.sql.SQLException;

/**
 * Created by andy on 31.08.15.
 */
public class FragmentShowBook extends Fragment {
    private Book mBook;
    private Category mCategory;
    private TextView mTextViewBookName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_show_book, null);
        Bundle bundle = getArguments();
        int id = bundle.getInt("category", 1);
        try {
            mBook=(Book)HelperFactory.getHelper().getBookDAO().queryForId(id);
            mCategory=mBook.getCategory();
            mTextViewBookName=(TextView)v.findViewById(R.id.textViewBookName);
            mTextViewBookName.setText(mBook.getName());

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return v;
    }

}
