package com.example.andy.scino_books;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by andy on 30.08.15.
 * list of categories
 */
public class FragmentCategoryList extends Fragment {
    private FragmentTransaction mFTrans;
    private  ArrayList<String> list;
    private FragmentBookList mFragmentBookList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View v=inflater.inflate(R.layout.fragment_category_list, null);
        list = new ArrayList<String>();
        try {
            ArrayList<Category> mCategoriesList = (ArrayList<Category>) HelperFactory.getHelper().getCathegoryDAO().getAllCategories();
            list.add(getString(R.string.no_category));
            list.add(getString(R.string.all));
            for(Category category: mCategoriesList){
                list.add(category.getName());
            }
            ArrayAdapter<String> adapter= new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_list_item_1, list);
            ListView mCategoriesListView = (ListView) v.findViewById(R.id.catigoriesListView);
            mCategoriesListView.setAdapter(adapter);
            mCategoriesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    mFTrans = getActivity().getFragmentManager().beginTransaction();
                    String categoryName = list.get(position);
                    //create list of books without category
                    if (categoryName.equals(getString(R.string.no_category))) {
                        mFragmentBookList = new FragmentBookListNoCategory();
                        mFTrans = getActivity().getFragmentManager().beginTransaction();
                        mFTrans.replace(R.id.fragment, mFragmentBookList)
                                .commit();
                    } else {
                        //create list of all books
                        if (categoryName.equals(getString(R.string.all))) {
                            mFragmentBookList = new FragmentBookListAll();

                            mFTrans = getActivity().getFragmentManager().beginTransaction();
                            mFTrans.replace(R.id.fragment, mFragmentBookList)
                                    .commit();
                        } else {
                            //create list of books in chosen category
                            Category category = null;
                            Bundle bundle = new Bundle();
                            try {
                                category = (Category) HelperFactory.getHelper().getCathegoryDAO().getCategoryByName(categoryName);
                                mFragmentBookList = new FragmentBookList();
                                mFTrans = getActivity().getFragmentManager().beginTransaction();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            bundle.putInt("category", category.getId());
                            mFragmentBookList.setArguments(bundle);
                                mFTrans.replace(R.id.fragment, mFragmentBookList)
                                        .commit();
                        }
                    }
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return v;
    }
    //get list of books
    public FragmentBookList getFragmentBookList(){
        return mFragmentBookList;
    }

}
