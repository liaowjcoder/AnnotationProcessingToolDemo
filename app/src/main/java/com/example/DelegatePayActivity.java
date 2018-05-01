package com.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.annotation.WXPayAnnotation;

@WXPayAnnotation(superClass = DelegatePayActivity.class, packageName = "com.example")
public class DelegatePayActivity<A> extends AppCompatActivity implements Plugin {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    private Demo demo = new Demo() {
    };
    private boolean isOpen = false;

    private boolean add() {
        return false;
    }

    class Demo {

        private boolean add() {
            return false;
        }


        public class Demo2 {
        }
    }

}
