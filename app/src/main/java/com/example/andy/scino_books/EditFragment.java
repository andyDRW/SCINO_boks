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
       Stack<Fragment> fragmentStack=FragmentStackSingleton.getFragmentsStack();
        FragmentTransaction mFTrans = getFragmentManager().beginTransaction();
        if(fragmentStack.size()>1) {
                mFTrans.detach(fragmentStack.pop());
        }
        mFTrans.replace(R.id.fragment, fragmentStack.peek());
        mFTrans.commit();
        FragmentStackSingleton.setFragmentsStack(fragmentStack);
    }
}
