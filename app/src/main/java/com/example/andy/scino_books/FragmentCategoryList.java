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
 */
public class FragmentCategoryList extends Fragment {
    private FragmentTransaction mFTrans;
    private ListView mCategoriesListView;
    private  ArrayList<String> list;
    private ArrayList<Category> mCategoriesList;
    private boolean mDualPane;
    private FragmentBookList mFragmentBookList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View v=inflater.inflate(R.layout.fragment_category_list, null);
        list = new ArrayList<String>();
        try {
            mCategoriesList=(ArrayList<Category>) HelperFactory.getHelper().getCathegoryDAO().getAllCategories();
            list.add(getString(R.string.no_category));
            list.add(getString(R.string.all));
            for(Category category:mCategoriesList){
                list.add(category.getName());
            }
            ArrayAdapter<String> adapter= new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_list_item_1, list);
            mCategoriesListView =(ListView)v.findViewById(R.id.catigoriesListView);
            mCategoriesListView.setAdapter(adapter);
            mCategoriesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    mFTrans = getActivity().getFragmentManager().beginTransaction();
                    String categoryName=list.get(position);
                    if(categoryName.equals(getString(R.string.no_category))) {
                        mFragmentBookList= new FragmentBookListNoCategory();
                        mFTrans = getActivity().getFragmentManager().beginTransaction();
                        mFTrans.replace(R.id.fragment, mFragmentBookList)
                                .commit();
                    }
                    else {
                        if (categoryName.equals(getString(R.string.all))) {
                            mFragmentBookList = new FragmentBookListAll();

                            mFTrans = getActivity().getFragmentManager().beginTransaction();
                            mFTrans.replace(R.id.fragment, mFragmentBookList)
                                    .commit();
                        } else {
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
                            if(mDualPane){
                                FrameLayout frameLayout2 = (FrameLayout) getActivity().findViewById(R.id.fragment_2);
                                FrameLayout frameLayout = (FrameLayout) getActivity().findViewById(R.id.fragment);
                                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT, 0.5f);
                                LinearLayout.LayoutParams param2 = new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT, 0.5f);
                                frameLayout.setLayoutParams(param);
                                frameLayout2.setLayoutParams(param2);
                                mFTrans.replace(R.id.fragment, mFragmentBookList)
                                        .commit();
                            }
                            else {
                                mFTrans.replace(R.id.fragment, mFragmentBookList)
                                        .commit();
                            }

                        }
                    }
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return v;
    }
    public FragmentBookList getFragmentBookList(){
        return mFragmentBookList;
    }

}
