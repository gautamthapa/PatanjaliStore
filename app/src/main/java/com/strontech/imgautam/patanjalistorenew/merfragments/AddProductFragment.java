package com.strontech.imgautam.patanjalistorenew.merfragments;


import android.Manifest;
import android.Manifest.permission;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.Toolbar.OnMenuItemClickListener;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.strontech.imgautam.patanjalistorenew.MainActivity;
import com.strontech.imgautam.patanjalistorenew.R;
import com.strontech.imgautam.patanjalistorenew.fragments.LoginFragment;
import com.strontech.imgautam.patanjalistorenew.helpers.InputValidation;
import com.strontech.imgautam.patanjalistorenew.model.Product;
import com.strontech.imgautam.patanjalistorenew.sql.UserDatabaseHelper;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddProductFragment extends Fragment implements OnClickListener{

  private LinearLayout linearLayout;

  private TextInputLayout textInputLayoutProductName;
  private TextInputLayout textInputLayoutProductPrice;
  private TextInputLayout textInputLayoutProductQuantity;
  private TextInputLayout textInputLayoutProductDesc;

  private TextInputEditText textInputTextProductName;
  private TextInputEditText textInputTextProductPrice;
  private TextInputEditText textInputTextProductQuantity;
  private TextInputEditText textInputTextProductDesc;

  private ImageView imageViewProductImage;
  final int REQUEST_CODE_GALLERY= 999;

  private Button buttonBrowseProductImage;
  private Button buttonAddProduct;

  private InputValidation inputValidation;
  private UserDatabaseHelper databaseHelper;
  private Product product;

  private View v;

  Toolbar toolbar;
  public AddProductFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    v= inflater.inflate(R.layout.fragment_add_product, container, false);

    initViews();
    initListeners();
    initObjects();

    return v;
  }


  /**
   * This method is to initialize views
   */
  private void initViews(){

    toolbar = v.findViewById(R.id.toolbar);

    linearLayout=v.findViewById(R.id.linearLayout);

    textInputLayoutProductName=v.findViewById(R.id.textInputLayoutProductName);
    textInputLayoutProductPrice=v.findViewById(R.id.textInputLayoutProductPrice);
    textInputLayoutProductQuantity=v.findViewById(R.id.textInputLayoutProductQuantity);
    textInputLayoutProductDesc=v.findViewById(R.id.textInputLayoutProductDesc);

    textInputTextProductName=v.findViewById(R.id.textInputTextProductName);
    textInputTextProductPrice=v.findViewById(R.id.textInputTextProductPrice);
    textInputTextProductQuantity=v.findViewById(R.id.textInputTextProductQuantity);
    textInputTextProductDesc=v.findViewById(R.id.textInputTextProductDesc);

    imageViewProductImage=v.findViewById(R.id.imageViewProductImage);

    buttonBrowseProductImage=v.findViewById(R.id.buttonBrowseProductImage);
    buttonAddProduct=v.findViewById(R.id.buttonAddProduct);
  }


  /**
  * This method is to initialize listeners
  */
  private void initListeners(){
    buttonAddProduct.setOnClickListener(this);
    buttonBrowseProductImage.setOnClickListener(this);
  }



  /**
   * This method is to initialize objects to be used
   */
  private void initObjects()
  {
    inputValidation=new InputValidation(getActivity());
    databaseHelper=new UserDatabaseHelper(getActivity());
    product=new Product();

    getToolbar();
  }

  /**
   * This method for setting toolbar
   */
  private void getToolbar() {
    toolbar.setTitle("Add Products");
    toolbar.inflateMenu(R.menu.user_menu);
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()){
      case R.id.buttonAddProduct:
        postDataToSQLite();
        break;
      case R.id.buttonBrowseProductImage:

        //For Runtime Permission (In fragment)
        requestPermissions(
            new String[]{permission.READ_EXTERNAL_STORAGE},
            REQUEST_CODE_GALLERY
        );

        break;
    }
  }


  /**
   * For Requested permission result
   * */
  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {


    if (requestCode == REQUEST_CODE_GALLERY){
      if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
       startActivityForResult(intent, REQUEST_CODE_GALLERY);
      }
      else {
        Toast.makeText(getActivity(), "You don't have permission to the file location!", Toast.LENGTH_SHORT).show();
      }
      return;
    }
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
  }


  /**
   * This onActivityResult for set image get from gallery to imageView
   * */
  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    Toast.makeText(getActivity(), "onActivityResult", Toast.LENGTH_SHORT).show();

    if (requestCode == REQUEST_CODE_GALLERY && resultCode == Activity.RESULT_OK && data !=null){
      Uri uri=data.getData();

      try {
        InputStream inputStream=getActivity().getContentResolver().openInputStream(uri);

        Bitmap bitmap= BitmapFactory.decodeStream(inputStream);
        imageViewProductImage.setImageBitmap(bitmap);
      } catch (FileNotFoundException e) {
          e.printStackTrace();
      }
    }

    super.onActivityResult(requestCode, resultCode, data);
  }

  /**
   * This method is to validate the input text fields and post data to SQLite
   */
  private void postDataToSQLite(){
    if (!inputValidation.isInputEditTextFilled(textInputTextProductName, textInputLayoutProductName, "Please enter Product Name")){
      return;
    }
    if (!inputValidation.isInputEditTextFilled(textInputTextProductPrice, textInputLayoutProductPrice, "Please enter Product Price")){
      return;
    }
    if (!inputValidation.isInputEditTextFilled(textInputTextProductQuantity, textInputLayoutProductQuantity, "Please enter Product Quantity")){
      return;
    }
    if (!inputValidation.isInputEditTextFilled(textInputTextProductDesc, textInputLayoutProductDesc, "Please enter Product Description")){
      return;
    }

    product.setProduct_name(textInputTextProductName.getText().toString().trim());
    product.setProduct_price(textInputTextProductPrice.getText().toString().trim());
    product.setProduct_qauntity(textInputTextProductQuantity.getText().toString().trim());
    product.setProduct_desc(textInputTextProductDesc.getText().toString().trim());

    product.setProduct_image(imageViewToByte(imageViewProductImage));
    databaseHelper.addProduct(product);

    // Snack Bar to show success message that record saved successfully
    Snackbar.make(linearLayout, "Product added successfully.", Snackbar.LENGTH_LONG).show();
    emptyInputEditText();
  }


  /**
   * This method for Converting imageView to byte
   *
   * @param image
   * */
  public byte[] imageViewToByte(ImageView image){

    Bitmap bitmap=((BitmapDrawable)image.getDrawable()).getBitmap();
    ByteArrayOutputStream stream=new ByteArrayOutputStream();
    bitmap.compress(CompressFormat.WEBP,100,stream);
    byte[] byteArray=stream.toByteArray();
    return byteArray;

  }


  /**
   * This method is to empty all input edit text
   */
  private void emptyInputEditText(){
    textInputTextProductName.setText(null);
    textInputTextProductPrice.setText(null);
    textInputTextProductQuantity.setText(null);
    textInputTextProductDesc.setText(null);
  }
}
