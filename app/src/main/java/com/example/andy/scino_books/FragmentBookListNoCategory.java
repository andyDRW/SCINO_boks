package com.example.andy.scino_books;

import android.view.View;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by andy on 07.09.15.
 */
public class FragmentBookListNoCategory extends FragmentBookList {

    @Override
    public void categoryFromDB( View v){
        try {
            mBooksList = (ArrayList<Book>) HelperFactory.getHelper().getBookDAO().getBooksWithoutCategory();
            mCategoryName = (TextView) v.findViewById(R.id.categoryNameTextView);
            mCategoryDescription = (TextView) v.findViewById(R.id.categoryDescriptionTextView);
            mCategoryName.setText(R.string.no_category);
            mCategoryDescription.setText(R.string.no_category_description);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void showAllBooks(){
        try {
            mBooksList = (ArrayList<Book>) HelperFactory.getHelper().getBookDAO().getBooksWithoutCategory();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        BookArrayAdapter adapter = new BookArrayAdapter(getActivity(), mBooksList);
        mBookListView.setAdapter(adapter);
        mFTrans = getActivity().getFragmentManager().beginTransaction();
        mFTrans.replace(R.id.fragment,this)
                .commit();
    }
    @Override
    public void showRead(){
        try {
            mBooksList = (ArrayList<Book>) HelperFactory.getHelper().getBookDAO().getReadBooksWithoutCategory();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        BookArrayAdapter adapter = new BookArrayAdapter(getActivity(), mBooksList);
        mBookListView.setAdapter(adapter);
        mFTrans = getActivity().getFragmentManager().beginTransaction();
        mFTrans.replace(R.id.fragment,this)
                .commit();
    }
    @Override
    public void showNotRead(){
        try {
            mBooksList = (ArrayList<Book>) HelperFactory.getHelper().getBookDAO().getNotReadBooksWithoutCategory();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        BookArrayAdapter adapter = new BookArrayAdapter(getActivity(), mBooksList);
        mBookListView.setAdapter(adapter);
        mFTrans = getActivity().getFragmentManager().beginTransaction();
        mFTrans.replace(R.id.fragment,this)
                .commit();
    }
}
