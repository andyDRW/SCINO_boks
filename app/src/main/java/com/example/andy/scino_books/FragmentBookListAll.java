package com.example.andy.scino_books;

import android.view.View;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by andy on 07.09.15.
 */
public class FragmentBookListAll extends FragmentBookList {
    @Override
    public void categoryFromDB( View v){
            try {
                mBooksList = (ArrayList<Book>) HelperFactory.getHelper().getBookDAO().getAllBooks();
                mCategoryName.setText(R.string.all);
                mCategoryDescription.setText(R.string.all_description);
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }
    @Override
    public void showAllBooks(){
        try {
            mBooksList = (ArrayList<Book>) HelperFactory.getHelper().getBookDAO().getAllBooks();
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
            mBooksList = (ArrayList<Book>) HelperFactory.getHelper().getBookDAO().getAllReadBooks();
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
            mBooksList = (ArrayList<Book>) HelperFactory.getHelper().getBookDAO().getAllNotReadBooks();
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
