package com.example.marcosmith.recyclewheeldemo;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{


    private List<ListItems> listItems;
    private Context context;

    public MyAdapter(List<ListItems> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        com.example.marcosmith.recyclewheeldemo.ListItems listItems = this.listItems.get(i);

        viewHolder.textViewHead.setText(listItems.getHead());
        viewHolder.textViewDesc.setText(listItems.getDesc());


        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity) viewHolder.textViewDesc.getContext()).getWindowManager().getDefaultDisplay().getMetrics(metrics);

        float yInches= metrics.heightPixels/metrics.ydpi;
        float xInches= metrics.widthPixels/metrics.xdpi;
        double diagonalInches = Math.sqrt(xInches*xInches + yInches*yInches);
        if (diagonalInches>=6.5){
            viewHolder.textViewDesc.setTextSize(30);
        }else{
            viewHolder.textViewDesc.setTextSize(15);
        }

        viewHolder.textViewDesc.setMovementMethod(new ScrollingMovementMethod());

        Picasso.with(context)
        .load(listItems.getImageUrl())
        .into(viewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewHead;
        public TextView textViewDesc;
        public ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewHead = itemView.findViewById(R.id.textViewHead);
            textViewDesc = itemView.findViewById(R.id.textViewDesc);
            imageView = itemView.findViewById(R.id.imageView);

        }
    }
}
