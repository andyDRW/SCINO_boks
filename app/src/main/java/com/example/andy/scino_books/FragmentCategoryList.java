package com.example.andy.scino_books;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
                    mFragmentBookList= new FragmentBookList();
                    Bundle bundle = new Bundle();
                    String categoryName=list.get(position);
                    if(categoryName.equals(getString(R.string.no_category))) {
                        bundle.putInt("category", Integer.MIN_VALUE);
                    }
                    else {
                        if(categoryName.equals(getString(R.string.all))){
                            bundle.putInt("category", Integer.MAX_VALUE);
                        }
                        else{
                            Category category = null;
                            try {
                                category = (Category) HelperFactory.getHelper().getCathegoryDAO().getCategoryByName(categoryName);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            bundle.putInt("category", category.getId());
                        }
                    }
                    //   detouchLastFragment();
                    mFragmentBookList.setArguments(bundle);
                    mFTrans.replace(R.id.fragment, mFragmentBookList);
                    mFTrans.commit();
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return v;
    }
}
