package com.example.andy.scino_books;

/**
 * Created by andy on 09.09.15.
 * dual pane mode
 */
public class DualPaneSingleton {
    private static boolean mDualPane;
    private DualPaneSingleton(){

    }
    public static void setDualPane(boolean dualPane){
        mDualPane=dualPane;
    }
    public static boolean getDualPane(){
        return mDualPane;
    }
}
