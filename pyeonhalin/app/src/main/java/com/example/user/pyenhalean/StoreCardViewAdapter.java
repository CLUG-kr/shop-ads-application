package com.example.user.pyenhalean;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.pyenhalean.model.StoreItem;

import java.util.ArrayList;

public class StoreCardViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private ArrayList<StoreItem> storeInfoArrayList;
    public StoreCardViewAdapter(ArrayList<StoreItem> storeInfoArrayList){
        this.storeInfoArrayList = storeInfoArrayList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_storeview, parent, false);

        return new RowCell(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        RowCell rowCell = (RowCell) holder;

        rowCell.imageIV.setImageResource(storeInfoArrayList.get(position).imageView);
        rowCell.titleTV.setText(storeInfoArrayList.get(position).title);
        rowCell.subtitleTV.setText(storeInfoArrayList.get(position).subtitle);
        rowCell.placeTV.setText(storeInfoArrayList.get(position).place);
    }

    @Override
    public int getItemCount() {
        return storeInfoArrayList.size();
    }

    public static class RowCell extends RecyclerView.ViewHolder {
        ImageView imageIV;
        TextView titleTV;
        TextView subtitleTV;
        TextView placeTV;

        RowCell(View view){
            super(view);
            imageIV = view.findViewById(R.id.storeCardView_image);
            titleTV = view.findViewById(R.id.storeCardView_title);
            subtitleTV = view.findViewById(R.id.storeCardView_subtitle);
            placeTV = view.findViewById(R.id.storeCardView_place);
        }
    }
}
