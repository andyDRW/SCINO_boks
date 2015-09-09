package com.example.andy.scino_books;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;

import java.sql.SQLException;
import java.util.List;


/**
 * Created by andy on 03.09.15.
 */
public class FragmentDeleteCategory extends DialogFragment{
    private Category mCategory;
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle bundle=getArguments();
        final int categoryId=bundle.getInt("category",1);
        try {
            mCategory=(Category)HelperFactory.getHelper().getCathegoryDAO().queryForId(categoryId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        AlertDialog.Builder dialog= new AlertDialog.Builder(getActivity())
                .setTitle(R.string.delete_category)
                .setMessage(R.string.delete_category_message)
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        try {
                            HelperFactory.getHelper().getBookDAO().updateBooksCategoryToNull(categoryId);
                            HelperFactory.getHelper().getCathegoryDAO().deleteById(categoryId);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                });
        return dialog.create();
    }

}
