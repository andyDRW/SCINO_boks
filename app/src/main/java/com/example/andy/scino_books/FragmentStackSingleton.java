package com.example.andy.scino_books;

import android.app.Fragment;

import java.util.Stack;

/**
 * Created by andy on 10.09.15.
 * custom stack with fragments
 */
public class FragmentStackSingleton {
    private static Stack<Fragment> mFragmentsStack=new Stack<Fragment>();
    private FragmentStackSingleton(){
    }
    public static Stack <Fragment> getFragmentsStack(){
        return mFragmentsStack;
    }
    public static void setFragmentsStack(Stack <Fragment> fragmentsStack){
        mFragmentsStack=fragmentsStack;
    }

}
