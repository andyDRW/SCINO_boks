package com.example.andy.scino_books;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by andy on 28.08.15.
 */
public class FragmentBookList extends DialogFragment {
    protected FragmentTransaction mFTrans;
    protected Category mSelectedCategory;
    protected TextView mCategoryName;
    protected FragmentShowBook mFragmentShowBook;
    protected TextView mCategoryDescription;
    protected ArrayList<String> mBooksNamesList;
    protected ArrayList<Book> mBooksList;
    protected ListView mBookListView;
    protected FragmentBookList itself;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_books_list, null);
        mBookListView = (ListView) v.findViewById(R.id.booksList);
        mCategoryName = (TextView) v.findViewById(R.id.categoryNameTextView);
        mCategoryDescription = (TextView) v.findViewById(R.id.categoryDescriptionTextView);
        categoryFromDB(v);
        if (mBooksList != null) {
            mBooksNamesList = new ArrayList<String>();
            for (Book book : mBooksList) {
                mBooksNamesList.add(book.getName());
            }
        }
        if (mBooksNamesList.size() > 0) {
            BookArrayAdapter adapter = new BookArrayAdapter(getActivity(), mBooksList);
            mBookListView.setAdapter(adapter);
            itself=this;
            mBookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Book book = mBooksList.get(position);
                    Bundle bundle = new Bundle();
                    bundle.putInt("book", book.getId());
                    mFragmentShowBook = new FragmentShowBook();
                    mFragmentShowBook.setArguments(bundle);
                    mFTrans = getActivity().getFragmentManager().beginTransaction();
                    if(DualPane.getDualPane()){
                        mFTrans
                                .replace(R.id.fragment_2,mFragmentShowBook)
                                .commit();
                    }
                    else {
                        mFTrans.replace(R.id.fragment, mFragmentShowBook)
                                .commit();
                    }
                }
            });
        }
        return v;
    }
    public void categoryFromDB( View v){
        Bundle bundle = getArguments();
        int id = bundle.getInt("category", 1);
        try {
            mSelectedCategory = HelperFactory.getHelper().getCathegoryDAO().queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        mCategoryName = (TextView) v.findViewById(R.id.categoryNameTextView);
        mCategoryDescription = (TextView) v.findViewById(R.id.categoryDescriptionTextView);
        mCategoryName.setText(mSelectedCategory.getName());
        mCategoryDescription.setText(mSelectedCategory.getDescription());
        try {
            mBooksList = (ArrayList<Book>) HelperFactory.getHelper().getBookDAO().getBookByCategory(mSelectedCategory);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public FragmentShowBook getFragmentShowBook(){
        return mFragmentShowBook;
    }
    public int getCategoryId(){
        if(mSelectedCategory!=null) {
            return mSelectedCategory.getId();
        }
        else{
            return Integer.MAX_VALUE;
        }
    }
    public void sortByName() {
        Collections.sort(mBooksList, new Comparator<Book>() {
            @Override
            public int compare(Book lhs, Book rhs) {
                return lhs.getName().compareTo(rhs.getName());
            }
        });
        BookArrayAdapter adapter = new BookArrayAdapter(getActivity(), mBooksList);
        mBookListView.setAdapter(adapter);
        mFTrans = getActivity().getFragmentManager().beginTransaction();
        mFTrans.replace(R.id.fragment,this)
                .commit();
    }
    public void sortByAuthor(){
        Collections.sort(mBooksList, new Comparator<Book>() {
            @Override
            public int compare(Book lhs, Book rhs) {
                return lhs.getAuthor().compareTo(rhs.getAuthor());
            }
        });
        BookArrayAdapter adapter = new BookArrayAdapter(getActivity(), mBooksList);
        mBookListView.setAdapter(adapter);
        mFTrans = getActivity().getFragmentManager().beginTransaction();
        mFTrans.replace(R.id.fragment,this)
        .commit();
    }
    public void showAllBooks(){
        try {
            mBooksList = (ArrayList<Book>) HelperFactory.getHelper().getBookDAO().getBookByCategory(mSelectedCategory);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        BookArrayAdapter adapter = new BookArrayAdapter(getActivity(), mBooksList);
        mBookListView.setAdapter(adapter);
        mFTrans = getActivity().getFragmentManager().beginTransaction();
        mFTrans.replace(R.id.fragment,this)
                .commit();
    }
    public void showRead(){
        try {
            mBooksList = (ArrayList<Book>) HelperFactory.getHelper().getBookDAO().getBookByCategoryRead(mSelectedCategory);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        BookArrayAdapter adapter = new BookArrayAdapter(getActivity(), mBooksList);
        mBookListView.setAdapter(adapter);
        mFTrans = getActivity().getFragmentManager().beginTransaction();
        mFTrans.replace(R.id.fragment,this)
                .commit();
    }
    public void showNotRead(){
        try {
            mBooksList = (ArrayList<Book>) HelperFactory.getHelper().getBookDAO().getBookByCategoryNotRead(mSelectedCategory);
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
