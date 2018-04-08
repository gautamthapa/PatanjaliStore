package com.strontech.imgautam.patanjalistorenew.userfragments;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.strontech.imgautam.patanjalistorenew.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserProductDescFragment extends Fragment {


  private TextView textViewProductName;
  private TextView textViewProductPrice;
  private TextView textViewProductDesc;
  private TextView textViewProductQuantity;

  private ImageButton imageButtonMinus;
  private ImageButton imageButtonAdd;

  private ImageView imageViewProductImage;
  private Button buttonProductBuy;

  private Toolbar toolbar;

  View view;
  public UserProductDescFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    view= inflater.inflate(R.layout.fragment_user_product_desc, container, false);


    initViews();
    initObjects();
    return view;
  }


  /**
   * This method is to initialize views
   */
  private void initViews(){

    toolbar=view.findViewById(R.id.toolbar);

    textViewProductName=view.findViewById(R.id.textViewProductName);
    textViewProductPrice=view.findViewById(R.id.textViewProductPrice);
    textViewProductQuantity=view.findViewById(R.id.textViewProductQuantity);
    textViewProductDesc=view.findViewById(R.id.textViewProductDesc);

    imageButtonMinus=view.findViewById(R.id.imageButtonMinus);
    imageButtonAdd=view.findViewById(R.id.imageButtonAdd);

    imageViewProductImage=view.findViewById(R.id.imageViewProductImage);

    buttonProductBuy=view.findViewById(R.id.buttonProductBuy);
  }


  /**
   * This method is to initialize objects to be used
   */
  private void initObjects(){

    toolbar.setTitle("Product Description");
    toolbar.setTitleTextColor(getResources().getColor(R.color.textWhiteColor));

    Bundle b=getArguments();

    setData(b);
  }


  private void setData(Bundle bundle){

    byte[] productImage=bundle.getByteArray("product_image");
    Bitmap bitmap= BitmapFactory.decodeByteArray(productImage,0,
        productImage != null ? productImage.length : 0);

    textViewProductName.setText(bundle.getString("product_name"));
    imageViewProductImage.setImageBitmap(bitmap);

    textViewProductPrice.setText(bundle.getString("product_price"));
    textViewProductDesc.setText(bundle.getString("product_desc"));

  }
}
