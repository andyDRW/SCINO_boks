package com.example.andy.scino_books;

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

/**
 * Created by andy on 28.08.15.
 */
public class FragmentBookList extends Fragment {
    private FragmentTransaction mFTrans;
    private Category mSelectedCategory;
    private TextView mCategoryName;
    private FragmentShowBook mFragmentShowBook;
    private TextView mCategoryDescription;
    private ArrayList<String> mBooksNamesList;
    private ArrayList<Book> mBooksList;
    private ListView mBookListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_books_list, null);
        Bundle bundle = getArguments();
        int id = bundle.getInt("category", 1);
        if (id==Integer.MAX_VALUE){
            try {
                mBooksList = (ArrayList<Book>) HelperFactory.getHelper().getBookDAO().getAllBooks();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else {
            if (id == Integer.MIN_VALUE) {
                try {
                    mBooksList = (ArrayList<Book>) HelperFactory.getHelper().getBookDAO().getBooksWithNullCategory();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    mSelectedCategory = HelperFactory.getHelper().getCathegoryDAO().queryForId(id);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                mCategoryName = (TextView) v.findViewById(R.id.categoryNameTextView);
                mCategoryDescription = (TextView) v.findViewById(R.id.categoryDescriptionTextView);
                mCategoryName.setText(mSelectedCategory.getName());
                try {
                    mBooksList = (ArrayList<Book>) HelperFactory.getHelper().getBookDAO().getBookByCategory(mSelectedCategory);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (mBooksList != null) {
                mBooksNamesList = new ArrayList<String>();
                for (Book book : mBooksList) {
                    mBooksNamesList.add(book.getName());
                }
                mCategoryDescription.setText(mSelectedCategory.getDescription());
                mBookListView = (ListView) v.findViewById(R.id.booksList);

                if (mBooksNamesList.size() > 0) {
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                            android.R.layout.simple_list_item_1, mBooksNamesList);
                    mBookListView.setAdapter(adapter);
                    mBookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Book book=mBooksList.get(position);
                            Bundle bundle = new Bundle();
                            bundle.putInt("book", book.getId());
                            mFragmentShowBook=new FragmentShowBook();
                            mFragmentShowBook.setArguments(bundle);
                            mFTrans=getActivity().getFragmentManager().beginTransaction();
                            mFTrans.replace(R.id.fragment, mFragmentShowBook);
                            mFTrans.commit();
                        }
                    });
                }
            }
        }
        return v;
    }

}
