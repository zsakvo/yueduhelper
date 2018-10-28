package cc.zsakvo.yueduhelper.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.Objects;

import cc.zsakvo.yueduhelper.HelperService;

public class AutoStartBroadcastReceiver extends BroadcastReceiver{

    private static final String ACTION = "android.intent.action.BOOT_COMPLETED";

    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences mPreferences = context.getSharedPreferences("settings",
                ContextWrapper.MODE_PRIVATE);
        if (Objects.requireNonNull(intent.getAction()).equals(ACTION)) {

            if (mPreferences.getBoolean("sw_run_auto", false)) {

                Intent service = new Intent(context,HelperService.class);
                context.startService(service);
            }
        }

    }
}