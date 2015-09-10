package com.example.andy.scino_books;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.sql.SQLException;

/**
 * Created by andy on 04.09.15.
 * edits category's data
 */

public class FragmentEditCategory extends EditFragment {
    private EditText mEditName;
    private EditText mEditDescription;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_add_category, null);
        Bundle bundle=getArguments();
        final int categoryId=bundle.getInt("category");
        try {
            Category mCategory = (Category) HelperFactory.getHelper().getCathegoryDAO().queryForId(categoryId);
            mEditName=(EditText)v.findViewById(R.id.editTextCategoryName);
            mEditName.setText(mCategory.getName());
            mEditDescription=(EditText)v.findViewById(R.id.editTextCategoryDescription);
            mEditDescription.setText(mCategory.getDescription());
            Button mButtonOk = (Button) v.findViewById(R.id.category_ok);
            mButtonOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String categoryName = mEditName.getText().toString();
                    String categoryDescription = mEditDescription.getText().toString();
                    try {
                        HelperFactory.getHelper().getCathegoryDAO().updateCategory(categoryName, categoryDescription, categoryId);
                        normalDismiss();
                        dismiss();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }
            });
            Button mButtonCancel = (Button) v.findViewById(R.id.category_cancel);
            mButtonCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    normalDismiss();
                    dismiss();
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return v;
    }

}