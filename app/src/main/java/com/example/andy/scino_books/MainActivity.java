package com.example.andy.scino_books;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.Stack;


public class MainActivity extends android.support.v7.app.AppCompatActivity  {

    private FragmentTransaction mFTrans;
    private FragmentCategoryList mFragmentCategoryList;
    private Menu mMenu;
    //Fragments names
    public static String SHOW_BOOK="FragmentShowBook";
    public static String SHOW_CATEGORY="FragmentBookList";
    public static String SHOW_NO_CATEGORY="FragmentBookListNoCategory";
    public static String SHOW_CATEGORY_ALL="FragmentBookListAll";
    public static String DELETE_CATEGORY="FragmentDeleteCategory";
    public static String EDIT_CATEGORY="FragmentEditCategory";
    public static String EDIT_BOOK="FragmentEditBook";
    public static String DELETE_BOOK="FragmentDeleteBook";
    public static String CATEGORY_LIST="FragmentCategoryList";
    public static String ADD_CATEGORY="FragmentAddCategory";
    public static String ADD_BOOK="FragmentAddBook";
    //name of last attached fragment
    private String mFragmentName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mFragmentName= "";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View fragmentView = findViewById(R.id.fragment_2);
        boolean dualPane = fragmentView != null &&
                fragmentView.getVisibility() == View.VISIBLE;
        DualPaneSingleton.setDualPane(dualPane);
        HelperFactory.setHelper(getApplicationContext());
        if(FragmentStackSingleton.size()==0){
            FragmentStackSingleton.setFragmentsStack(new Stack<Fragment>());
            mFragmentCategoryList=new FragmentCategoryList();
            mFTrans = getFragmentManager().beginTransaction();
            mFTrans.replace(R.id.fragment, mFragmentCategoryList)
                    .commit();
        }
        else {
            mFragmentCategoryList=(FragmentCategoryList)FragmentStackSingleton.get(0);
            Fragment fragment=FragmentStackSingleton.peek();
            mFTrans = getFragmentManager().beginTransaction();
            if((fragment.getClass().getName().contains(SHOW_BOOK))&&(DualPaneSingleton.getDualPane())){
                setFrameSize(0.5f, 0.5f);
                mFTrans.remove(FragmentStackSingleton.peek())
                        .commit();
                getFragmentManager().executePendingTransactions();
                mFTrans=getFragmentManager().beginTransaction();
                mFTrans
                        .replace(R.id.fragment_2, FragmentStackSingleton.pop())
                        .replace(R.id.fragment, FragmentStackSingleton.peek())
                        .commit();
            }
            else {
                //mFTrans.detach(fragmentStack.peek());
                //Fragment newInstance = recreateFragment(fragment);

                setFrameSize(1.0f, 0);
                mFTrans.remove(FragmentStackSingleton.peek())
                        .commit();
                getFragmentManager().executePendingTransactions();
                mFTrans=getFragmentManager().beginTransaction();
                mFTrans = getFragmentManager().beginTransaction();
                mFTrans
                        .replace(R.id.fragment, FragmentStackSingleton.peek())
                        .commit();
            }
  //          groupsVisibility( FragmentStackSingleton.peek().getClass().getName());
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        mMenu=menu;
        groupsVisibility( mFragmentName);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_add_book: {
                FragmentAddBook mFragmentAddBook = new FragmentAddBook();
                mFTrans = getFragmentManager().beginTransaction();
                mFTrans.replace(R.id.fragment, mFragmentAddBook)
                        .commit();
            }
            break;
            case R.id.action_add_category: {
                FragmentAddCategory mFragmentAddCategory = new FragmentAddCategory();
                mFTrans = getFragmentManager().beginTransaction();
                mFTrans.replace(R.id.fragment, mFragmentAddCategory)
                        .commit();
            }
            break;
            case R.id.action_edit_book: {
                if (mFragmentCategoryList != null) {
                    if (mFragmentCategoryList.getFragmentBookList().getFragmentShowBook() != null) {
                        FragmentEditBook mFragmentEditBook = new FragmentEditBook();
                        int bookId = mFragmentCategoryList.getFragmentBookList().getFragmentShowBook().getBookId();
                        Bundle bundle = new Bundle();
                        bundle.putInt("book", bookId);
                        mFragmentEditBook.setArguments(bundle);
                        mFTrans = getFragmentManager().beginTransaction();
                        mFTrans.replace(R.id.fragment, mFragmentEditBook)
                                .commit();
                    }
                }
            }
            break;
            case R.id.action_delete_book: {
                if (mFragmentCategoryList != null) {
                    if (mFragmentCategoryList.getFragmentBookList() != null) {
                        if (mFragmentCategoryList.getFragmentBookList().getFragmentShowBook() != null) {
                            FragmentDeleteBook mFragmentDeleteBook = new FragmentDeleteBook();
                            int bookId = mFragmentCategoryList.getFragmentBookList().getFragmentShowBook().getBookId();
                            Bundle bundle = new Bundle();
                            bundle.putInt("book", bookId);
                            mFragmentDeleteBook.setArguments(bundle);
                            mFragmentDeleteBook.show(getFragmentManager(), null);
                        }
                    }
                }
            }
            break;
            case R.id.action_edit_category: {
                if (mFragmentCategoryList != null) {
                    if (mFragmentCategoryList.getFragmentBookList() != null) {
                        FragmentEditCategory mFragmentEditCategory = new FragmentEditCategory();
                        int categoryId = mFragmentCategoryList.getFragmentBookList().getCategoryId();
                        Bundle bundle = new Bundle();
                        bundle.putInt("category", categoryId);
                        mFragmentEditCategory.setArguments(bundle);
                        mFTrans = getFragmentManager().beginTransaction();
                        mFTrans.replace(R.id.fragment, mFragmentEditCategory)
                                .commit();
                    }
                }
            }
            break;
            case R.id.action_delete_category: {
                if (mFragmentCategoryList != null) {
                    if (mFragmentCategoryList.getFragmentBookList() != null) {
                        FragmentDeleteCategory mFragmentDeleteCategory = new FragmentDeleteCategory();
                        int categoryId = mFragmentCategoryList.getFragmentBookList().getCategoryId();
                        Bundle bundle = new Bundle();
                        bundle.putInt("category", categoryId);
                        mFragmentDeleteCategory.setArguments(bundle);
                        mFragmentDeleteCategory.show(getFragmentManager(), null);
                    }
                }
            }
            break;
            case R.id.sort_by_author: {
                if (mFragmentCategoryList.getFragmentBookList() != null) {
                    mFragmentCategoryList.getFragmentBookList().sortByAuthor();
                }
            }
            break;
            case R.id.sort_by_name: {
                if (mFragmentCategoryList.getFragmentBookList() != null) {
                    mFragmentCategoryList.getFragmentBookList().sortByName();
                }
            }
            break;
            case R.id.action_show_read_books:{
                if (mFragmentCategoryList.getFragmentBookList() != null) {
                    mFragmentCategoryList.getFragmentBookList().showRead();
                }
            }
            break;
            case R.id.action_show_not_read_books:{
                if (mFragmentCategoryList.getFragmentBookList() != null) {
                    mFragmentCategoryList.getFragmentBookList().showNotRead();
                }
            }
            break;
            case R.id.action_show_all_books:{
                if (mFragmentCategoryList.getFragmentBookList() != null) {
                    mFragmentCategoryList.getFragmentBookList().showAllBooks();
                }
            }
            break;
        }
        return super.onOptionsItemSelected(item);
    }

   @Override
   public void onAttachFragment(Fragment fragment){
       mFragmentName=fragment.getClass().getName();
       if (mFragmentName.contains(SHOW_BOOK)){
           //shows book in right column
           setFrameSize(0.5f, 0.5f);
       }
       else {
           //in one column
           setFrameSize(1.0f, 0);

       }
       if (!(mFragmentName.contains(DELETE_CATEGORY) || mFragmentName.contains(DELETE_BOOK))) {
           if (FragmentStackSingleton.size() == 0) {
                   FragmentStackSingleton.push(fragment);
           }
           else {
                   if (!fragment.equals(FragmentStackSingleton.peek()) &&(FragmentStackSingleton.peek().getClass()
                           .getName().compareTo(fragment.getClass().getName())!=0))
                       FragmentStackSingleton.push(fragment);
           }
       }
       groupsVisibility(mFragmentName);
       super.onAttachFragment(fragment);
   }
    @Override
    public void onBackPressed() {
        if(mFragmentName.contains(SHOW_BOOK)||(mFragmentName.contains(DELETE_BOOK))){
                setFrameSize(1.0f, 0);
            }
       if(FragmentStackSingleton.size()<=1){
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
           FragmentStackSingleton.pop();
           mFTrans
                .replace(R.id.fragment, FragmentStackSingleton.peek())
                   .commit();
           groupsVisibility(FragmentStackSingleton.peek().getClass().getName());
        }
   }

    public void setFrameSize(float fragmetnWeigth_1,float fragmetnWeigth_2){
        if(DualPaneSingleton.getDualPane()) {
            FrameLayout frameLayout2 = (FrameLayout) findViewById(R.id.fragment_2);
            FrameLayout frameLayout = (FrameLayout) findViewById(R.id.fragment);
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT, fragmetnWeigth_2);
            LinearLayout.LayoutParams param2 = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT, fragmetnWeigth_1);
            if(frameLayout!=null)
                frameLayout.setLayoutParams(param);
            if(frameLayout2!=null)
                frameLayout2.setLayoutParams(param2);
        }
    }
    public void groupsVisibility(String fragmentName){
        if (mMenu != null) {
            if (fragmentName.contains(SHOW_BOOK) || fragmentName.contains(DELETE_BOOK)) {
                mMenu.setGroupVisible(R.id.books_buttons, true);
            } else {
                mMenu.setGroupVisible(R.id.books_buttons, false);
            }
            if ((fragmentName.contains(SHOW_CATEGORY) || fragmentName.contains(DELETE_CATEGORY))) {
                if (!(fragmentName.contains(SHOW_CATEGORY_ALL)) && (!(fragmentName.contains(SHOW_NO_CATEGORY)))) {
                    mMenu.setGroupVisible(R.id.category_buttons, true);
                    mMenu.setGroupVisible(R.id.sort_and_show_books, true);
                } else {
                    mMenu.setGroupVisible(R.id.sort_and_show_books, true);
                }
            } else {
                mMenu.setGroupVisible(R.id.category_buttons, false);
                mMenu.setGroupVisible(R.id.sort_and_show_books, false);
            }
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
      HelperFactory.releaseHelper();

    }

}
