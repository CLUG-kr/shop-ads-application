package com.example.user.pyenhalean;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.pyenhalean.activity.AddItemActivity;
import com.example.user.pyenhalean.activity.OwnerManageItemActivity;
import com.example.user.pyenhalean.model.CardViewItemDTO;
import com.example.user.pyenhalean.model.StoreItem;

import java.util.ArrayList;

public class CardRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<CardViewItemDTO> cardViewItemDTOs = new ArrayList<>();
    Context context;
    public CardRecyclerViewAdapter(CardViewItemDTO[] cardViewItemDTO) {
        for(int i = 0; i < cardViewItemDTO.length; i++){
            cardViewItemDTOs.add(cardViewItemDTO[i]);
        }
    }

    public CardRecyclerViewAdapter(CardViewItemDTO[] cardViewItemDTO, Context context) {
        for(int i = 0; i < cardViewItemDTO.length; i++){
            cardViewItemDTOs.add(cardViewItemDTO[i]);
        }
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_cardview, viewGroup, false);

        return new RowCell(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        ((RowCell)viewHolder).imageView.setImageResource(cardViewItemDTOs.get(position).imageView);
        ((RowCell)viewHolder).title.setText(cardViewItemDTOs.get(position).title);
        ((RowCell)viewHolder).subtitle.setText(cardViewItemDTOs.get(position).subtitle);
        ((RowCell)viewHolder).price.setText(cardViewItemDTOs.get(position).price);
        ((RowCell)viewHolder).itemManageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(context instanceof OwnerManageItemActivity){
                    Log.d("관리자","관리자");
                }
                else {
                    Log.d("실패","실패");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return cardViewItemDTOs.size();
    }

    public class RowCell extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public ImageView itemManageBtn;
        public TextView title;
        public TextView subtitle;
        public TextView price;
        public RowCell(View view){
            super(view);
            imageView = (ImageView) view.findViewById(R.id.cardView_image);
            title = (TextView) view.findViewById(R.id.cardView_title);
            subtitle = (TextView) view.findViewById(R.id.cardView_subtitle);
            price = (TextView) view.findViewById(R.id.cardView_price);
            itemManageBtn = (ImageView) view.findViewById(R.id.mange_button);
        }
    }


}
