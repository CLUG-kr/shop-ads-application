package com.example.user.pyenhalean;

import android.content.SharedPreferences;

public class HeadbarSharePreferences {

    public static void save(SharedPreferences data, String ID, String name, String key) {
        // SharedPreferences 객체만으론 저장 불가능 Editor 사용
        SharedPreferences.Editor editor = data.edit();

        // 에디터객체.put타입( 저장시킬 이름, 저장시킬 값 )
        // 저장시킬 이름이 이미 존재하면 덮어씌움
        editor.putString("id", ID);
        editor.putString("name", name);
        editor.putString("key",key);
        editor.apply();
    }

    // 설정값을 불러오는 함수
    public static void load(SharedPreferences data, String ID, String name, String key) {
        // SharedPreferences 객체.get타입( 저장된 이름, 기본값 )
        // 저장된 이름이 존재하지 않을 시 기본값
        ID = data.getString("id", "null");
        name = data.getString("name", "null");
        key = data.getString("key", "null");
    }
}
