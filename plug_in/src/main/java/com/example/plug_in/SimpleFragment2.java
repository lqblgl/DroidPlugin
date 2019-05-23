package com.example.plug_in;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class SimpleFragment2 extends Fragment {

    private Button btn;
    private LinearLayout LinearLayout1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main, null);
        btn = (Button) view.findViewById(R.id.button1);
        LinearLayout1 = (LinearLayout) view.findViewById(R.id.LinearLayout1);
        btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "SimpleFragment2", Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }
}
