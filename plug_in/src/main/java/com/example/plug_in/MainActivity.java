package com.example.plug_in;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.lib.BasePluginActivity;


public class MainActivity extends BasePluginActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.main);
        TextView textView = (TextView) findViewById(R.id.text);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(AnotherActivity.class.getName());
            }
        });
    }


}
