package com.example.host_plug_in;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.example.lib.PluginManager;

public class ListActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        findViewById(R.id.button1).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListActivity.this, PathClassLoaderActivity.class));
            }
        });
        findViewById(R.id.button2).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListActivity.this, DexClassLoaderFragment.class));
            }
        });
        findViewById(R.id.button3).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                PluginManager.getInstance().startPlugin(ListActivity.this);
            }
        });
    }
}
