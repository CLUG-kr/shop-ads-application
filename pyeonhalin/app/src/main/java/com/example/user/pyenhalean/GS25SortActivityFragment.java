package com.example.user.pyenhalean;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.concurrent.ExecutionException;

public class GS25SortActivityFragment extends Fragment {
    public static final String TAG = "GS25";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sort_activity_fragment, container, false);
        RecyclerView recyclerView= (RecyclerView)view.findViewById(R.id.item_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new CardRecyclerViewAdapter(initialCardViewItem()));
        return view;
    }

    CardViewItemDTO[] initialCardViewItem(){
        CardViewItemDTO[] returnCardViewItemDTO;
        GetHTMLTask taskTestData = new GetHTMLTask();
        String[] data = new String[3];
        try {
            String[] response = taskTestData.execute("testGS25").get().substring(1).split("!");
            returnCardViewItemDTO = new CardViewItemDTO[response.length];
            for(int i = 0; i < response.length; i++){
                if (response[i].split("#").length != 3){
                    data[0] = data[1] = data[2] = response[i].split("#")[0];
                }
                else
                    data = response[i].split("#");
                returnCardViewItemDTO[i] = new CardViewItemDTO(R.drawable.preparing_image
                        , data[0],data[2] + " 행사", data[1]);
            }
            return returnCardViewItemDTO;
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        };
        returnCardViewItemDTO = new CardViewItemDTO[1];
        returnCardViewItemDTO[0] = new CardViewItemDTO(R.drawable.preparing_image, "첫번째"
                , "설명", "1500원");
        return returnCardViewItemDTO;
    }
}
