package com.example.plug_in;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class SimpleFragment extends Fragment {

    public SimpleFragment() {
    }

    private int add(int a, int b) {
        Log.e("SimpleFragment", (a - b) + "");
        try {

            Toast.makeText(getActivity(), (a + b) + "加法运算", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("SimpleFragment", (a + b) + "");
        return a + b;
    }

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
                FragmentManager fragmentManager = getActivity().getFragmentManager();
                FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
                beginTransaction.replace(R.id.LinearLayout1, new SimpleFragment2());
                beginTransaction.addToBackStack(null);
                beginTransaction.commit();
            }
        });
        return view;
    }
}
