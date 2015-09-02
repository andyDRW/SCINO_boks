package com.example.andy.scino_books;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Stack;


public class MainActivity extends android.support.v7.app.AppCompatActivity {

    private FragmentTransaction mFTrans;
    private FragmentBookList mFragmentBookList;
    private FragmentAddBook mFragmentAddBook;
    private FragmentCategoryList mFragmentCategoryList;
    private FragmentAddCategory mFragmentAddCategory;
    private ListView mCategoriesListView;
    private  ArrayList<String> list;
    private ArrayList<Category> mCategoriesList;
    private String mStackName;
    private Stack<Fragment> mFragmentsStack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mStackName ="myStack";
        mFragmentsStack=new Stack<Fragment>();
        mFragmentAddCategory =new FragmentAddCategory();
        mFragmentCategoryList=new FragmentCategoryList();
        mFragmentAddBook = new FragmentAddBook();
        HelperFactory.setHelper(getApplicationContext());

        setContentView(R.layout.activity_main);
        mFTrans = getFragmentManager().beginTransaction();
        // detouchLastFragment();

        mFTrans.replace(R.id.fragment, mFragmentCategoryList)
            .addToBackStack(mStackName)
                .commit();


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
            mFTrans.replace(R.id.fragment, mFragmentAddBook)
                .addToBackStack(null)
                    .commit();
          //  mFragmentsStack.push(mFragmentCategoryList);
            return true;
        }
        if(id == R.id.action_add_category) {
            mFTrans = getFragmentManager().beginTransaction();
           // detouchLastFragment();
            mFTrans.replace(R.id.fragment, mFragmentAddCategory)
            .addToBackStack(null)
                    .commit();
          //  mFragmentsStack.push(mFragmentCategoryList);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
   @Override
   public void onAttachFragment(Fragment fragment){
       if(mFragmentsStack.size()==0) {
           mFragmentsStack.push(fragment);
       }
       else {
           if (!fragment.equals(mFragmentsStack.peek()))
               mFragmentsStack.push(fragment);
       }
       super.onAttachFragment(fragment);
   }
    @Override
    public void onBackPressed() {
       if(mFragmentsStack.size()<=1){
            new AlertDialog.Builder(this)
                    .setTitle(R.string.exit)
                    .setMessage(R.string.exit_message)
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            MainActivity.super.onBackPressed();
                        }
                    }).create().show();
        }
        else {
           mFTrans = getFragmentManager().beginTransaction();
           mFragmentsStack.pop();
           mFTrans.replace(R.id.fragment,mFragmentsStack.peek()).commit();
        }
   }

    @Override
    public void onDestroy() {
        HelperFactory.releaseHelper();
        super.onDestroy();
    }

}
