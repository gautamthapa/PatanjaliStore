package com.strontech.imgautam.patanjalistorenew.userfragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.Toolbar.OnMenuItemClickListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;
import com.strontech.imgautam.patanjalistorenew.R;
import com.strontech.imgautam.patanjalistorenew.adapters.ProductsRecyclerAdapter;
import com.strontech.imgautam.patanjalistorenew.adapters.UserProductRecyclerAdapter;
import com.strontech.imgautam.patanjalistorenew.fragments.LoginFragment;
import com.strontech.imgautam.patanjalistorenew.merfragments.ShowProductFragment;
import com.strontech.imgautam.patanjalistorenew.model.Product;
import com.strontech.imgautam.patanjalistorenew.sql.UserDatabaseHelper;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserMainFragment extends Fragment implements SearchView.OnQueryTextListener{

  private RecyclerView recyclerViewProducts;
  private List<Product> productList;
  private UserProductRecyclerAdapter productsRecyclerAdapter;
  private UserDatabaseHelper databaseHelper;
  Toolbar toolbar;
  Menu menu;

  FragmentManager fm;


  View v;
  public UserMainFragment() {
    // Required empty public constructor
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);

    setHasOptionsMenu(true);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    v= inflater.inflate(R.layout.fragment_user_main, container, false);

//    getActivity().getActionBar().setTitle("");
    initViews();
    initObjects();

    return v;
  }

  /**
   * This method is to initialize views
   */
  private void initViews(){
    toolbar=v.findViewById(R.id.toolbar);
    recyclerViewProducts=v.findViewById(R.id.recyclerViewProducts);
  }


  /**
   * This method is to initialize objects to be used
   */
  private void initObjects(){


    getToolbar();

    productList=new ArrayList<>();
    productsRecyclerAdapter=new UserProductRecyclerAdapter(productList,getContext());

    RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity().getApplicationContext());
    recyclerViewProducts.setLayoutManager(layoutManager);
    recyclerViewProducts.setItemAnimator(new DefaultItemAnimator());
    recyclerViewProducts.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
    recyclerViewProducts.setHasFixedSize(true);
    recyclerViewProducts.setAdapter(productsRecyclerAdapter);
    databaseHelper=new UserDatabaseHelper(getActivity());

    getDataFromSQLite();

  }


  /**
   * This method for setting toolbar
   * */
  private void getToolbar() {
    toolbar.setTitle("Patanjali Store");
  //  toolbar.setTitleTextColor(getResources().getColor(R.color.textWhiteColor));

    toolbar.inflateMenu(R.menu.menu_search);


    toolbar.setOnMenuItemClickListener(new OnMenuItemClickListener() {
      @Override
      public boolean onMenuItemClick(MenuItem item) {
        SearchView searchView;
        //int searchId=item.getItemId()
       MenuItem item1=menu.findItem(R.id.action_search);
        searchView=(SearchView) MenuItemCompat.getActionView(item1);
        searchView.setOnQueryTextListener(UserMainFragment.this);
        fm=getFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        int id=item.getItemId();
        if (id==R.id.item1)
        {
          ft.replace(R.id.first_layout, new LoginFragment());
          ft.commit();
        }else if (id==R.id.action_search){
         searchView.setOnQueryTextListener(UserMainFragment.this);
        }
        return true;
      }
    });
  }


  /**
   * This method is to fetch all products records from SQLite
   */
  public void getDataFromSQLite(){
    // AsyncTask is used that SQLite operation not blocks the UI Thread.
    new AsyncTask<Void, Void, Void>(){

      @Override
      protected Void doInBackground(Void... voids) {
        productList.clear();
        productList.addAll(databaseHelper.getAllProducts());

        return null;
      }


      @Override
      protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        productsRecyclerAdapter.notifyDataSetChanged();
      }
    }.execute();
  }


  @Override
  public boolean onQueryTextSubmit(String query) {
    return false;
  }

  @Override
  public boolean onQueryTextChange(String newText) {

    Toast.makeText(getActivity(), "Searchview clicked", Toast.LENGTH_SHORT).show();
    newText=newText.toLowerCase();
    List<Product> newProductList=new ArrayList<>();
    for (Product product:newProductList)
    {
      String productName=product.getProduct_name();
      if (productName.contains(newText))
          newProductList.add(product);
    }

    productsRecyclerAdapter.setFilter(newProductList);
    return true;
  }
}
