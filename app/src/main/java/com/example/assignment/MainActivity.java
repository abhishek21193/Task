package com.example.assignment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;

/*
Abhishek Kumar Yadava
*/

public class MainActivity extends AppCompatActivity {

    EditText mEdtName, mEdtDescription, mEdtRegularPrice, mEdtSalePice;
    Button mBtnAdd;
    ImageView mImageProductPhoto;

    final int REQUEST_CODE_GALLERY = 999;

    public static SQLiteHelper mSQLiteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("New Product");

        mEdtName = findViewById(R.id.edtName);
        mEdtDescription = findViewById(R.id.edtDescription);
        mEdtRegularPrice = findViewById(R.id.edtRegularPrice);
        mEdtSalePice = findViewById(R.id.edtSalePrice);
        mBtnAdd = findViewById(R.id.btnAdd);
        mImageProductPhoto = findViewById(R.id.imgProductPhoto);

        //creating database
        mSQLiteHelper = new SQLiteHelper(this, "RECORDDB.sqlite", null, 1);

        //creating table in database
        mSQLiteHelper.queryData("CREATE TABLE IF NOT EXISTS RECORD(id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, description VARCHAR, RegularPrice VARCHAR, salePrice VARCHAR, productPhoto BLOB)");


        //select image by on imageview click
        mImageProductPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //read external storage permission to select image from gallery
                //runtime permission for devices android 6.0 and above
                ActivityCompat.requestPermissions(
                        MainActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY
                );
            }
        });

        //add record to sqlite
        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (mEdtName.getText().toString().isEmpty() || mEdtDescription.getText().toString().isEmpty() || mEdtRegularPrice.getText().toString().isEmpty() || mEdtSalePice.getText().toString().isEmpty())
                    {
                        Toast.makeText(MainActivity.this, "Please insert all data", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        mSQLiteHelper.insertData(
                                mEdtName.getText().toString().trim(),
                                mEdtDescription.getText().toString().trim(),
                                mEdtRegularPrice.getText().toString().trim(),
                                mEdtSalePice.getText().toString().trim(),
                                imageViewToByte(mImageProductPhoto)
                        );

                        Toast.makeText(MainActivity.this, "Product Added Successfully", Toast.LENGTH_SHORT).show();
                        //reset views
                        mEdtName.setText("");
                        mEdtDescription.setText("");
                        mEdtRegularPrice.setText("");
                        mEdtSalePice.setText("");
                        mImageProductPhoto.setImageResource(R.drawable.ic_launcher_foreground);
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });




    }

    public static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_GALLERY){
            if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //gallery intent
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, REQUEST_CODE_GALLERY);
            }
            else {
                Toast.makeText(this, "Don't have permission to access file location", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK){
            Uri imageUri = data.getData();
            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON) //enable image guidlines
                    .setAspectRatio(1,1)// image will be square
                    .start(this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result =CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK){
                Uri resultUri = result.getUri();
                //set image choosed from gallery to image view
                mImageProductPhoto.setImageURI(resultUri);
            }
            else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                Exception error = result.getError();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}