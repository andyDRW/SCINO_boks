package com.example.andy.scino_books;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.sql.SQLException;

/**
 * Created by andy on 24.08.15.
 */
public class FragmentAddCategory extends EditFragment {
    private EditText mEditName;
    private EditText mEditDescription;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_add_category, null);
        mEditName=(EditText)v.findViewById(R.id.editTextCategoryName);
        mEditDescription=(EditText)v.findViewById(R.id.editTextCategoryDescription);
        Button mButtonOk = (Button) v.findViewById(R.id.category_ok);
        mButtonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String categoryName = mEditName.getText().toString();
                String categoryDescription = mEditDescription.getText().toString();
                Category category = new Category(categoryName, categoryDescription);
                try {
                    HelperFactory.getHelper().getCathegoryDAO().create(category);
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
                dismiss();
                 normalDismiss();
            }
        });
        return v;
    }

}