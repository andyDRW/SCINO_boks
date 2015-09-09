package com.example.andy.scino_books;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;

import java.sql.SQLException;

/**
 * Created by andy on 04.09.15.
 */
public class FragmentDeleteBook extends DialogFragment {
    private Book mBook;
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        final int bookId = bundle.getInt("book", 1);
        try {
            mBook = (Book) HelperFactory.getHelper().getBookDAO().queryForId(bookId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.delete_book)
                .setMessage(R.string.delete_book_message)
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        try {
                            HelperFactory.getHelper().getBookDAO().deleteById(bookId);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                });
        return dialog.create();
    }
}
