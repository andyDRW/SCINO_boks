package com.example.andy.scino_books;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by andy on 04.09.15.
 * edit book data
 */
public class FragmentEditBook extends EditFragment {
    private Book mBook;
    private EditText mEditName;
    private EditText mEditAuthor;
    private EditText mEditDescription;
    private CheckBox mCheckBoxIsRead;
    private ArrayList<String> mCategoryNames;
    private Category mSelectedCategory;
    private Category mCurrentCategory;
    private String mCurrentCategoryName;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle=getArguments();
        final int bookId=bundle.getInt("book");
        try {
            mBook =(Book)HelperFactory.getHelper().getBookDAO().queryForId(bookId);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            mCurrentCategory=mBook.getCategory();
            HelperFactory.getHelper().getCathegoryDAO().refresh(mCurrentCategory);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(mCurrentCategory!=null) {
            mCurrentCategoryName = mCurrentCategory.getName();
        }
        View v=inflater.inflate(R.layout.fragment_add_book, null);
        mEditName=(EditText)v.findViewById(R.id.editTextBookName);
        mEditName.setText(mBook.getName());
        mEditDescription=(EditText)v.findViewById(R.id.editTextBookDescription);
        mEditDescription.setText(mBook.getDescription());
        mEditAuthor=(EditText)v.findViewById(R.id.editTextBookAuthor);
        mEditAuthor.setText(mBook.getAuthor());
        mCheckBoxIsRead=(CheckBox)v.findViewById(R.id.checkBoxBookRead);
        mCheckBoxIsRead.setChecked(mBook.getRead());
        Spinner mSpinnerCategory = (Spinner) v.findViewById(R.id.spinnerBookCategory);
        ArrayList<Category> mCategories;
        try {
            mCategories = (ArrayList<Category>) HelperFactory.getHelper().getCathegoryDAO().getAllCategories();
        } catch (SQLException e) {
            e.printStackTrace();
            return v;
        }
        mCategoryNames=new ArrayList<String>();
        if(mCurrentCategoryName!=null) {
            mCategoryNames.add(mCurrentCategoryName);
        }
        mCategoryNames.add(getString(R.string.no_category));
        for(Category category: mCategories){
            String name=category.getName();
            if(!name.equals(mCurrentCategoryName)) {
                mCategoryNames.add(name);
            }
        }
        ArrayAdapter<String> mSpinnerAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.spinner_item, mCategoryNames);
        mSpinnerCategory.setAdapter(mSpinnerAdapter);
        mSpinnerCategory.setPrompt(getString(R.string.category_str));
        mSpinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String categoryName = mCategoryNames.get(position);
                if (categoryName.equals(getString(R.string.no_category))) {
                    mSelectedCategory = null;
                } else {
                    try {
                        mSelectedCategory = HelperFactory.getHelper().getCathegoryDAO().getCategoryByName(categoryName);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mSelectedCategory = null;
            }
        });
        Button mButtonOk = (Button) v.findViewById(R.id.book_ok);
        mButtonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bookName = mEditName.getText().toString();
                String bookDescription = mEditDescription.getText().toString();
                String bookAuthor = mEditAuthor.getText().toString();
                boolean bookIsRead = mCheckBoxIsRead.isChecked();
                if (mSelectedCategory == null) {
                    try {
                        HelperFactory.getHelper().getBookDAO().updateBookNulCategory(bookId, bookName, bookDescription, bookAuthor, bookIsRead);
                        normalDismiss();
                        dismiss();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {

                    try {
                        HelperFactory.getHelper().getBookDAO().updateBook(bookId, bookName, bookDescription, bookAuthor, bookIsRead, mSelectedCategory);
                        normalDismiss();
                        dismiss();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        Button mButtonCancel = (Button) v.findViewById(R.id.book_cancel);
        mButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                normalDismiss();
                dismiss();
            }
        });
        return  v;
    }
}

