package com.strontech.imgautam.patanjalistorenew.merfragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.Toolbar.OnMenuItemClickListener;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.strontech.imgautam.patanjalistorenew.R;
import com.strontech.imgautam.patanjalistorenew.fragments.LoginFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class MerMainFragment extends Fragment implements OnClickListener {

  private CardView cardViewAddProduct;
  private CardView cardViewUpdateProduct;
  private CardView cardViewShowProduct;
  private CardView cardViewDeleteProduct;

  private View v;

  Toolbar toolbar;
  FragmentManager fm;

  public MerMainFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    v = inflater.inflate(R.layout.fragment_mer_main, container, false);
    initViews();
    initListeners();
    initObjects();

    return v;
  }

  /**
   * this method is to initialize Views
   */
  private void initViews() {
    toolbar = v.findViewById(R.id.toolbar);

    cardViewAddProduct = v.findViewById(R.id.cardViewAddProduct);
    cardViewUpdateProduct = v.findViewById(R.id.cardViewUpdateProduct);
    cardViewShowProduct = v.findViewById(R.id.cardViewShowProduct);
    cardViewDeleteProduct = v.findViewById(R.id.cardViewDeleteProduct);
  }

  /**
   * this method is to initialize Listeners
   */
  private void initListeners() {
    cardViewAddProduct.setOnClickListener(this);
    cardViewUpdateProduct.setOnClickListener(this);
    cardViewShowProduct.setOnClickListener(this);
    cardViewDeleteProduct.setOnClickListener(this);
  }


  /**
   * This method is to initialize objects to be used
   */
  private void initObjects() {
    getToolbar();
  }


  /**
   * This method for setting toolbar
   */
  private void getToolbar() {
    toolbar.setTitle("Patanjali Store");
    //  toolbar.setTitleTextColor(getResources().getColor(R.color.textWhiteColor));

    toolbar.inflateMenu(R.menu.user_menu);

    toolbar.setOnMenuItemClickListener(new OnMenuItemClickListener() {
      @Override
      public boolean onMenuItemClick(MenuItem item) {
        fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        int id = item.getItemId();
        if (id == R.id.item1) {
          ft.replace(R.id.first_layout, new LoginFragment());
          ft.commit();
        }
        return true;
      }
    });
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.cardViewAddProduct:
        FragmentTransaction ft1 = getFragmentManager().beginTransaction();
        ft1.replace(R.id.first_layout, new AddProductFragment());
        ft1.addToBackStack("MerMainFragment");
        ft1.commit();
        break;
      case R.id.cardViewUpdateProduct:
        FragmentTransaction ft2 = getFragmentManager().beginTransaction();
        ft2.replace(R.id.first_layout, new UpdateProductFragment());
        ft2.addToBackStack("MerMainFragment");
        ft2.commit();
        break;
      case R.id.cardViewShowProduct:
        FragmentTransaction ft3 = getFragmentManager().beginTransaction();
        ft3.replace(R.id.first_layout, new ShowProductFragment());
        ft3.addToBackStack("MerMainFragment");
        ft3.commit();
        break;
      case R.id.cardViewDeleteProduct:
        FragmentTransaction ft4 = getFragmentManager().beginTransaction();
        ft4.replace(R.id.first_layout, new DeleteProductFragment());
        ft4.addToBackStack("MerMainFragment");
        ft4.commit();
        break;
    }
  }
}
