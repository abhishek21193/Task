package com.example.assignment;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;



import java.util.ArrayList;

/*Abhishek Kumar Yadava*/

public class ProductListAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<Product> recordList;

    public ProductListAdapter(Context context, int layout, ArrayList<Product> recordList) {
        this.context = context;
        this.layout = layout;
        this.recordList = recordList;
    }

    @Override
    public int getCount() {
        return recordList.size();
    }

    @Override
    public Object getItem(int i) {
        return recordList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private class ViewHolder{
        ImageView ProductPhoto;
        TextView txtName, txtDescription, txtRegularPrice, txtSalePrice;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View row = view;
        ViewHolder holder = new ViewHolder();

        if (row==null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);
            holder.txtName = row.findViewById(R.id.txtNames);
            holder.txtDescription = row.findViewById(R.id.txtDescriptions);
            holder.txtRegularPrice = row.findViewById(R.id.txtRegularPrices);
            holder.txtSalePrice = row.findViewById(R.id.txtSalePrices);
            holder.ProductPhoto = row.findViewById(R.id.imgProductPhotos);
            row.setTag(holder);
        }
        else {
            holder = (ViewHolder)row.getTag();
        }

        Product model = recordList.get(i);

        holder.txtName.setText(model.getName());
        holder.txtDescription.setText(model.getDescription());
        holder.txtRegularPrice.setText(model.getRegularPrice());
        holder.txtSalePrice.setText(model.getSalePrice());

        byte[] recordImage = model.getProductPhoto();

        final Bitmap bitmap = BitmapFactory.decodeByteArray(recordImage, 0, recordImage.length);
        RoundedBitmapDrawable mDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), bitmap);
        mDrawable.setCircular(true);
        holder.ProductPhoto.setImageBitmap(bitmap);


        holder.ProductPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
                LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View mView = inflater.inflate(R.layout.dialog_custom_layout, null);
                ImageView photoView = mView.findViewById(R.id.imageView);
                photoView.setImageBitmap(bitmap);
                mBuilder.setView(mView);
                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });

        return row;
    }
}