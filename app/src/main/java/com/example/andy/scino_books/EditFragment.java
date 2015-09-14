package com.example.andy.scino_books;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.view.View;

import java.util.Stack;

/**
 * Created by andy on 10.09.15.
 */
public class EditFragment extends DialogFragment {

    public void normalDismiss() {
        FragmentTransaction FTrans = getFragmentManager().beginTransaction();
        FragmentStackSingleton.pop();
        getFragmentManager().executePendingTransactions();
        FTrans.replace(R.id.fragment, FragmentStackSingleton.peek())
        .commit();

    }
}
