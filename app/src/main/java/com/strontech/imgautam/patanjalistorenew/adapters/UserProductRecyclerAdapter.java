package com.strontech.imgautam.patanjalistorenew.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.strontech.imgautam.patanjalistorenew.R;
import com.strontech.imgautam.patanjalistorenew.adapters.ProductsRecyclerAdapter.ProductViewHolder;
import com.strontech.imgautam.patanjalistorenew.model.Product;
import com.strontech.imgautam.patanjalistorenew.userfragments.UserProductDescFragment;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by imgautam on 20/3/18.
 */

public class UserProductRecyclerAdapter extends
    RecyclerView.Adapter<UserProductRecyclerAdapter.ProductViewHolder> {

  private List<Product> productList;

  Context context;

  //make constructor
  public UserProductRecyclerAdapter(List<Product> productList, Context context) {
    this.context = context;
    this.productList = productList;
  }

  @Override
  public UserProductRecyclerAdapter.ProductViewHolder onCreateViewHolder(ViewGroup parent,
      int viewType) {

    //inflate the recycler item view

    View itemView = LayoutInflater
        .from(parent.getContext()).inflate(R.layout.item_product_recycler, parent, false);

    return new UserProductRecyclerAdapter.ProductViewHolder(itemView, productList, context);
  }

  @Override
  public void onBindViewHolder(UserProductRecyclerAdapter.ProductViewHolder holder, int position) {

    //set the products
    //holder.imageViewProductImage.setImageBitmap(productList.get(position).getProduct_image());

    byte[] product_image = productList.get(position).getProduct_image();
    Bitmap bitmap = BitmapFactory.decodeByteArray(product_image, 0, product_image.length);

    holder.imageViewProductImage.setImageBitmap(bitmap);
    holder.textViewProductName.setText(productList.get(position).getProduct_name());
    holder.textViewProductQuantity.setText(productList.get(position).getProduct_qauntity());
    holder.textViewProductPrice.setText("Rs. " + productList.get(position).getProduct_price());
    holder.textViewProductDesc.setText(productList.get(position).getProduct_desc());

  }

  @Override
  public int getItemCount() {

    Log.v(ProductsRecyclerAdapter.class.getSimpleName(), "" + productList.size());
    return productList.size();
  }


  /**
   * ViewHolder class
   */
  class ProductViewHolder extends RecyclerView.ViewHolder implements OnClickListener {

    public ImageView imageViewProductImage;

    public TextView textViewProductName;
    public TextView textViewProductQuantity;
    public TextView textViewProductPrice;
    public TextView textViewProductDesc;

    List<Product> productList = new ArrayList<Product>();

    Context context;
    UserProductDescFragment userProductDescFragment;

    public ProductViewHolder(View itemView, List<Product> productList, Context context) {

      super(itemView);
      this.context = context;
      this.productList = productList;

      itemView.setOnClickListener(this);

      userProductDescFragment = new UserProductDescFragment();
      imageViewProductImage = itemView.findViewById(R.id.imageViewProductImage);
      textViewProductName = itemView.findViewById(R.id.textViewProductName);
      textViewProductQuantity = itemView.findViewById(R.id.textViewProductQuantity);
      textViewProductPrice = itemView.findViewById(R.id.textViewProductPrice);
      textViewProductDesc = itemView.findViewById(R.id.textViewProductDesc);
    }

    @Override
    public void onClick(View v) {

      int position = getAdapterPosition();
      Product product = this.productList.get(position);

      Bundle b = new Bundle();

      b.putByteArray("product_image", product.getProduct_image());
      b.putString("product_name", product.getProduct_name());
      b.putString("product_quantity", product.getProduct_qauntity());
      b.putString("product_price", product.getProduct_price());
      b.putString("product_desc", product.getProduct_desc());

      userProductDescFragment.setArguments(b);

      AppCompatActivity activity = (AppCompatActivity) v.getContext();

      FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
      ft.replace(R.id.first_layout, userProductDescFragment);
      ft.addToBackStack("UserMain");
      ft.commit();

    }
  }


  public void setFilter(List<Product> newProductList) {

    productList = new ArrayList<>();
    productList.addAll(newProductList);
    notifyDataSetChanged();
  }
}
