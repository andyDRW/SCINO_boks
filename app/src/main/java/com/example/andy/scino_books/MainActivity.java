package com.example.andy.scino_books;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.sql.SQLException;
import java.util.ArrayList;


public class MainActivity extends android.support.v7.app.AppCompatActivity {

    private FragmentTransaction mFTrans;
    private FragmentBookList mFragmentBookList;
    private FragmentAddBook mFragmentAddBook;
    private FragmentCategoryList mFragmentCategoryList;
    private FragmentAddCategory mFragmentAddCategory;
    private ListView mCategoriesListView;
    private  ArrayList<String> list;
    private ArrayList<Category> mCategoriesList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFragmentAddCategory =new FragmentAddCategory();
        mFragmentCategoryList=new FragmentCategoryList();
        mFragmentAddBook = new FragmentAddBook();
        HelperFactory.setHelper(getApplicationContext());

        setContentView(R.layout.activity_main);
        mFTrans = getFragmentManager().beginTransaction();
        // detouchLastFragment();
        mFTrans.replace(R.id.fragment, mFragmentCategoryList);
        mFTrans.commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add_book) {
            mFTrans = getFragmentManager().beginTransaction();
           // detouchLastFragment();
            mFTrans.replace(R.id.fragment, mFragmentAddBook);
            mFTrans.commit();
            return true;
        }
        if(id == R.id.action_add_category) {
            mFTrans = getFragmentManager().beginTransaction();
           // detouchLastFragment();
            mFTrans.replace(R.id.fragment, mFragmentAddCategory);
            mFTrans.commit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onDestroy() {
        HelperFactory.releaseHelper();
        super.onDestroy();
    }
}
