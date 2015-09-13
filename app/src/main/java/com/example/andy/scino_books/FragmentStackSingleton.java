package com.example.andy.scino_books;

import android.app.Fragment;
import android.util.SizeF;

import java.util.Objects;
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
    public static int size(){
       return mFragmentsStack.size();
    }
    public static Fragment pop(){
        return mFragmentsStack.pop();
    }
    public static Fragment peek(){
        return mFragmentsStack.peek();
    }
    public static void push(Fragment fragment){
        Object[] fragmentsArray=mFragmentsStack.toArray();
        Stack <Fragment> fragmentStack=new Stack<Fragment>();
        for(Object element:fragmentsArray){
            if(fragment.getClass().getName().compareTo(element.getClass().getName())!=0){
                fragmentStack.add((Fragment)element);
            }
        }
        fragmentStack.push(fragment);
        setFragmentsStack(fragmentStack);
    }

}
