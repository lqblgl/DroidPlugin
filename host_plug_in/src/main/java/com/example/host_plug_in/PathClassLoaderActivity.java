package com.example.host_plug_in;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dalvik.system.PathClassLoader;

public class PathClassLoaderActivity extends Activity {

    private Button btn;
    private RelativeLayout bg;
    private List<Map<String, String>> pluginList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (Button) findViewById(R.id.button1);
        bg = (RelativeLayout) findViewById(R.id.bg);
        btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                View inflate = getLayoutInflater().inflate(R.layout.poplayout, null);
                PopupWindow window = new PopupWindow(inflate, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                window.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_launcher));
                window.setFocusable(true);
                window.setOutsideTouchable(true);
                pluginList = findPluginList();
                if (pluginList == null || pluginList.size() == 0) {
                    Toast.makeText(PathClassLoaderActivity.this, "鏆傛棤鎻掍欢,璇蜂笅杞芥彃浠�", Toast.LENGTH_LONG).show();
                    return;
                }
                ListView listView = (ListView) inflate.findViewById(R.id.listView1);
                SimpleAdapter adapter = new SimpleAdapter(PathClassLoaderActivity.this, pluginList, android.R.layout.simple_list_item_1, new String[]{"label"}, new int[]{android.R.id.text1});
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Context context = findContext(position);
                        int resId = findResourceId(context, position);
                        Drawable drawable = context.getResources().getDrawable(resId);
                        bg.setBackgroundDrawable(drawable);
                    }

                });
                window.setWidth(150);
                window.setHeight(100 * pluginList.size());
                window.showAsDropDown(v);
            }
        });
    }

    private Context findContext(int position) {
        String packageName = pluginList.get(position).get("packageName");
        Context context = null;
        try {
            context = createPackageContext(packageName, Context.CONTEXT_IGNORE_SECURITY);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return context;
    }

    private int findResourceId(Context context, int position) {
        String packageName = pluginList.get(position).get("packageName");
        ClassLoader classLoader = new PathClassLoader(context.getPackageResourcePath(), PathClassLoader.getSystemClassLoader());
        try {
            Class<?> class1 = Class.forName(packageName + ".R$drawable", true, classLoader);
            Field[] fields = class1.getFields();
            for (Field f : fields) {
                String name = f.getName();
                if (name.equals("bg")) {
                    return f.getInt(R.drawable.class);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    protected List<Map<String, String>> findPluginList() {
        List<Map<String, String>> pluginList = new ArrayList();
        PackageManager packageManager = getPackageManager();
        List<PackageInfo> installedPackages = packageManager.getInstalledPackages(PackageManager.GET_ACTIVITIES);
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            for (PackageInfo info : installedPackages) {
                String packageName = info.packageName;
                String sharedUserId = info.sharedUserId;
                if (packageInfo.packageName.equals(packageName)
                        || TextUtils.isEmpty(sharedUserId)
                        || !sharedUserId.equals(packageInfo.sharedUserId)) {
                    continue;
                }
                Map<String, String> map = new HashMap();
                String label = info.applicationInfo.loadLabel(packageManager).toString();
                map.put("packageName", packageName);
                map.put("sharedUserId", sharedUserId);
                map.put("label", label);
                pluginList.add(map);
            }
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return pluginList;
    }


}
