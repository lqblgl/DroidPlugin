package com.example.host_plug_in;


import android.content.res.Resources;
import android.os.Environment;

import com.example.lib.PluginManager;


/**
 * Created by liuguangli on 16/3/20.
 */
public class HostApplication extends android.app.Application {
    private Resources oldResource;

    @Override
    public void onCreate() {
        super.onCreate();
        String plugPath = Environment.getExternalStorageDirectory() + "/cj/aa.apk";
        oldResource = super.getResources();
        PluginManager.getInstance().install(this, plugPath);
    }

    @Override
    public Resources getResources() {

        Resources newRes = PluginManager.getInstance().getResources();
        return newRes == null ? oldResource : newRes;
    }
}
