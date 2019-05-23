package com.example.host_plug_in;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

public class DexClassLoaderFragment extends Activity {
    private DexClassLoader dexClassLoader;
    private Resources res;
    private AssetManager asm;
    private Theme newTheme;
    private String fragment_class;
    private String path;
    private File filein;
    private File fileout;

    private TextView textView1;
    private ImageView imageView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        textView1 = (TextView) findViewById(R.id.textView1);
        imageView1 = (ImageView) findViewById(R.id.imageView1);
        findViewById(R.id.button1).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                /**
                 *获取图片资源
                 */
                Drawable drawable = res.getDrawable(res.getIdentifier("b", "drawable",
                        "com.example.aaa3"));
                /**
                 *  获取文本资源
                 */
                String text = res.getString(res.getIdentifier("test", "string",
                        "com.example.aaa3"));

                imageView1.setImageDrawable(drawable);
                textView1.setText(text);
            }
        });
        findViewById(R.id.button2).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    Class<?> loadClass = getClassLoader().loadClass(fragment_class);
                    Object newInstance = loadClass.newInstance();
                    Method declaredMethod = loadClass.getDeclaredMethod("add", int.class, int.class);
                    declaredMethod.setAccessible(true);
                    declaredMethod.invoke(newInstance, 1, 2);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        fragment_class = "zrar.cj.SimpleFragment";
        path = Environment.getExternalStorageDirectory() + File.separator + "cj" + File.separator + "aa.apk";
        try {
            filein = new File(path);
            fileout = new File(getApplication().getFilesDir(), "dexout");
            fileout.mkdir();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        dexClassLoader = new DexClassLoader(filein.getAbsolutePath(),
                fileout.getAbsolutePath(), null, super.getClassLoader());
        try {
            asm = AssetManager.class.newInstance();
            asm.getClass().getMethod("addAssetPath", String.class).invoke(asm, filein.getAbsolutePath());
            Resources resources = super.getResources();
            res = new Resources(asm, resources.getDisplayMetrics(), resources.getConfiguration());
            newTheme = res.newTheme();
            newTheme.setTo(super.getTheme());
//			FrameLayout frameLayout = new FrameLayout(this);
//			frameLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
//			frameLayout.setId(2);
//			setContentView(frameLayout);
            Fragment fragment = (Fragment) getClassLoader().loadClass(fragment_class).newInstance();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction beginTransaction = fm.beginTransaction();
//			beginTransaction.add(2, fragment);
            beginTransaction.add(R.id.LinearLayout1, fragment);
            beginTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public ClassLoader getClassLoader() {
        return dexClassLoader == null ? super.getClassLoader() : dexClassLoader;
    }

    @Override
    public AssetManager getAssets() {
        return asm == null ? super.getAssets() : asm;
    }

    @Override
    public Resources getResources() {
        return res == null ? super.getResources() : res;
    }

    @Override
    public Theme getTheme() {
        return newTheme == null ? super.getTheme() : newTheme;
    }
}
