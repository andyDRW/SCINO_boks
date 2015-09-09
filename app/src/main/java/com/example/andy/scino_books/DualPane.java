package com.example.andy.scino_books;

/**
 * Created by andy on 09.09.15.
 */
public class DualPane {
    private static boolean mDualPane;
    private DualPane(){

    }
    public static void setDualPane(boolean dualPane){
        mDualPane=dualPane;
    }
    public static boolean getDualPane(){
        return mDualPane;
    }
}
