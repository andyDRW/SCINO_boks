package com.example.andy.scino_books;

import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.internal.widget.AdapterViewCompat;
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
 * Created by andy on 24.08.15.
 */
public class FragmentAddBook extends FragmentScinoBooks {
    private Button mButtonOk;
    private Book mBook;
    private Button mButtonCancel;
    private EditText mEditName;
    private EditText mEditAuthor;
    private EditText mEditDescription;
    private CheckBox mCheckBoxIsRead;
    private Spinner mSpinnerCategory;
    private ArrayList<String> mCategoryNames;
    private ArrayList<Category> mCategories;
    private ArrayAdapter<String> mSpinnerAdapter;
    private Category mSelectedCategory;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_add_book, null);
        mEditName=(EditText)v.findViewById(R.id.editTextBookName);
        mEditDescription=(EditText)v.findViewById(R.id.editTextBookDescription);
        mEditAuthor=(EditText)v.findViewById(R.id.editTextBookAuthor);
        mCheckBoxIsRead=(CheckBox)v.findViewById(R.id.checkBoxBookRead);
        mSpinnerCategory=(Spinner)v.findViewById(R.id.spinnerBookCategory);
        try {
            mCategories= (ArrayList<Category>) HelperFactory.getHelper().getCathegoryDAO().getAllCategories();
        } catch (SQLException e) {
            e.printStackTrace();
            return v;
        }
        mCategoryNames=new ArrayList<String>();
        mCategoryNames.add(getString(R.string.no_category));
        for(Category category:mCategories){
            mCategoryNames.add(category.getName());
        }

        mSpinnerAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),R.layout.spinner_item, mCategoryNames);
        mSpinnerCategory.setAdapter(mSpinnerAdapter);
        mSpinnerCategory.setPrompt(getString(R.string.category_str));
        mSpinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String categoryName = mCategoryNames.get(position);
                if (categoryName.equals(getString(R.string.no_category))) {
                    mSelectedCategory = null;
                }
                else {
                    try {
                        mSelectedCategory = HelperFactory.getHelper().getCathegoryDAO().getCategoryByName(categoryName);
                    }
                    catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mSelectedCategory = null;
            }
        });
        mButtonOk = (Button) v.findViewById(R.id.book_ok);
        mButtonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bookName = mEditName.getText().toString();
                String bookDescription = mEditDescription.getText().toString();
                String bookAuthor = mEditAuthor.getText().toString();
                boolean bookIsRead = mCheckBoxIsRead.isChecked();
                if (mSelectedCategory == null) {
                    mBook = new Book(bookName, bookAuthor, bookDescription, bookIsRead);
                } else {
                    mBook = new Book(bookName, bookAuthor, bookDescription, bookIsRead,mSelectedCategory);
                }
                try {
                    HelperFactory.getHelper().getBookDAO().create(mBook);
                    dismiss();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        mButtonCancel=(Button)v.findViewById(R.id.book_cancel);
        mButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return  v;
    }
}
