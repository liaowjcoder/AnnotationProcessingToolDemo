package com.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.annotation.WXEntryAnnotation;

@WXEntryAnnotation(packageName = "com.example", superClass = DelegateEntryActivity.class)
public class DelegateEntryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
