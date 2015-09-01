package com.example.andy.scino_books;

import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;

/**
 * Created by andy on 01.09.15.
 */
public class FragmentScinoBooks extends DialogFragment {
    private FragmentTransaction mFTrans;
    private FragmentCategoryList mFragmentCategoryList;

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        mFragmentCategoryList=new FragmentCategoryList();
        mFTrans=getActivity().getFragmentManager().beginTransaction();
        mFTrans.replace(R.id.fragment, mFragmentCategoryList);
        mFTrans.commit();
    }
}
