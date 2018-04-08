package com.strontech.imgautam.patanjalistorenew.merfragments;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.strontech.imgautam.patanjalistorenew.R;
import com.strontech.imgautam.patanjalistorenew.adapters.ProductsRecyclerAdapter;
import com.strontech.imgautam.patanjalistorenew.model.Product;
import com.strontech.imgautam.patanjalistorenew.sql.UserDatabaseHelper;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShowProductFragment extends Fragment {

  private RecyclerView recyclerViewShowProducts;
  private List<Product> productList;
  private ProductsRecyclerAdapter productsRecyclerAdapter;
  private UserDatabaseHelper databaseHelper;
  private Toolbar toolbar;
  ProgressDialog pd;
  View v;
  public ShowProductFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    v= inflater.inflate(R.layout.fragment_show_product, container, false);



    initViews();
    initObjects();

    return v;
  }


  /**
   * This method is to initialize views
   */
  private void initViews(){
    recyclerViewShowProducts=v.findViewById(R.id.recyclerViewShowProducts);
    toolbar=v.findViewById(R.id.toolbar);
  }


  /**
   * This method is to initialize objects to be used
   */
  private void initObjects(){

      toolbar.setTitle("Products");
      toolbar.setTitleTextColor(getResources().getColor(R.color.textWhiteColor));

    productList=new ArrayList<>();
    productsRecyclerAdapter=new ProductsRecyclerAdapter(productList,getContext());

    RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity().getApplicationContext());
    recyclerViewShowProducts.setLayoutManager(layoutManager);
    recyclerViewShowProducts.setItemAnimator(new DefaultItemAnimator());
    recyclerViewShowProducts.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
    recyclerViewShowProducts.setHasFixedSize(true);
    recyclerViewShowProducts.setAdapter(productsRecyclerAdapter);
    databaseHelper=new UserDatabaseHelper(getActivity());

    getDataFromSQLite();

  }


  /**
   * This method is to fetch all products records from SQLite
   */
  public void getDataFromSQLite(){
    // AsyncTask is used that SQLite operation not blocks the UI Thread.
    new AsyncTask<Void, Void, Void>(){

      @Override
      protected void onPreExecute() {
        pd = new ProgressDialog(getActivity());
        pd.setMessage("Please wait..");
        pd.show();
        super.onPreExecute();
      }

      @Override
      protected Void doInBackground(Void... voids) {
        productList.clear();
        productList.addAll(databaseHelper.getAllProducts());
       // productsRecyclerAdapter.notifyDataSetChanged();
        return null;
      }


      @Override
      protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        productsRecyclerAdapter.notifyDataSetChanged();
        pd.dismiss();
      }
    }.execute();
  }


}
