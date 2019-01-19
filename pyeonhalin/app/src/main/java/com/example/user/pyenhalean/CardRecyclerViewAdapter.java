package com.example.user.pyenhalean;

import android.inputmethodservice.Keyboard;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CardRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<CardViewItemDTO> cardViewItemDTOs = new ArrayList<>();
    public CardRecyclerViewAdapter(CardViewItemDTO[] cardViewItemDTO) {
        for(int i = 0; i < cardViewItemDTO.length; i++){
            cardViewItemDTOs.add(cardViewItemDTO[i]);
        }
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
    }

    @Override
    public int getItemCount() {
        return cardViewItemDTOs.size();
    }

    public class RowCell extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView title;
        public TextView subtitle;
        public TextView price;
        public RowCell(View view){
            super(view);
            imageView = (ImageView) view.findViewById(R.id.cardView_image);
            title = (TextView) view.findViewById(R.id.cardView_title);
            subtitle = (TextView) view.findViewById(R.id.cardView_subtitle);
            price = (TextView) view.findViewById(R.id.cardView_price);
        }
    }


}
