package com.android;

import android.media.Image;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<ImageView> menu_image_location = new ArrayList<>();
    ArrayList<LinearLayout> menu_button = new ArrayList<>();
    ArrayList<Integer> menu_image = new ArrayList<>();
    ArrayList<Integer> menu_image_pressed = new ArrayList<>();
    final int map_activity = 0;
    final int item_activity = 1;
    final int home_activity = 2;
    final int event_activity = 3;
    final int communication_activity = 4;
    int current_menu = home_activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        menu_button.add((LinearLayout) findViewById(R.id.map_button));
        menu_button.add((LinearLayout) findViewById(R.id.item_button));
        menu_button.add((LinearLayout) findViewById(R.id.home_button));
        menu_button.add((LinearLayout) findViewById(R.id.event_button));
        menu_button.add((LinearLayout) findViewById(R.id.community_button));

        menu_image_location.add((ImageView) findViewById(R.id.map_image));
        menu_image_location.add((ImageView) findViewById(R.id.item_image));
        menu_image_location.add((ImageView) findViewById(R.id.home_image));
        menu_image_location.add((ImageView) findViewById(R.id.event_image));
        menu_image_location.add((ImageView) findViewById(R.id.communication_image));

        menu_image.add(R.drawable.map_image);
        menu_image.add(R.drawable.item_image);
        menu_image.add(R.drawable.home_image);
        menu_image.add(R.drawable.event_image);
        menu_image.add(R.drawable.community_image);

        menu_image_pressed.add(R.drawable.map_image_pressed);
        menu_image_pressed.add(R.drawable.item_image_pressed);
        menu_image_pressed.add(R.drawable.home_image_pressed);
        menu_image_pressed.add(R.drawable.event_image_pressed);
        menu_image_pressed.add(R.drawable.community_image_pressed);

        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new HomeFragment()).commit();

        menu_button.get(map_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeMenuImage(current_menu, map_activity);
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.animator.enter_animation, R.animator.exit_animation).replace(R.id.main_frame, new MapFragment()).commit();
            }
        });
        menu_button.get(item_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeMenuImage(current_menu, item_activity);
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.animator.enter_animation, R.animator.exit_animation).replace(R.id.main_frame, new ItemFragment()).commit();
            }
        });
        menu_button.get(home_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeMenuImage(current_menu, home_activity);
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.animator.enter_animation, R.animator.exit_animation).replace(R.id.main_frame, new HomeFragment()).commit();
            }
        });
        menu_button.get(event_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeMenuImage(current_menu, event_activity);
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.animator.enter_animation, R.animator.exit_animation).replace(R.id.main_frame, new EventFragment()).commit();
            }
        });
        menu_button.get(communication_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeMenuImage(current_menu, communication_activity);
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.animator.enter_animation, R.animator.exit_animation).replace(R.id.main_frame, new CommunityFragment()).commit();
            }
        });
    }

    protected void changeMenuImage(int from, int to){
        menu_image_location.get(from).setImageResource(menu_image.get(from));
        current_menu = to;
        menu_image_location.get(to).setImageResource(menu_image_pressed.get(to));
    }
}
