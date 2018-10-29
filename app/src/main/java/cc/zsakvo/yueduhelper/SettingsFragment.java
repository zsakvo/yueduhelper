package cc.zsakvo.yueduhelper;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.print.PrinterId;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Objects;

import cc.zsakvo.yueduhelper.classes.BookSourceText;
import moe.shizuku.preference.CheckBoxPreference;
import moe.shizuku.preference.Preference;
import moe.shizuku.preference.PreferenceFragment;
import moe.shizuku.preference.SwitchPreference;


public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener,Preference.OnPreferenceChangeListener,Preference.OnPreferenceClickListener {

    private static final String TAG = SettingsFragment.class.getSimpleName();
    private SwitchPreference sp_run;
    private SwitchPreference sw_run_auto;
    private Intent bindIntent;
    private SharedPreferences preferences;
    private MyBroadCast myBroadCast;
    private int port;



    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

    }

    private void copy(String sourceName,String sourceParam){
        SharedPreferences preferences = Objects.requireNonNull(getContext()).getSharedPreferences("settings", Context.MODE_MULTI_PROCESS);
        BookSourceText bst = new BookSourceText();
        bst.setBookSourceName(sourceName);
        String bsu = "http://127.0.0.1:"+port+"/"+sourceParam+"/";
        bst.setBookSourceUrl(bsu+"/");
        bst.setRuleSearchUrl(bsu+"search?bookname=searchKey&page=searchPage");
        ClipData clip = ClipData.newPlainText(null, JSON.toJSONString(bst));
        ClipboardManager cmb = (ClipboardManager)Objects.requireNonNull(getContext()).getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setPrimaryClip(clip);
    }

    private void showSnackBar(){
        Snackbar snackbar = Snackbar.make(Objects.requireNonNull(getView()),"代码复制成功，请去「阅读」内新建并粘贴书源",Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        return false;
    }

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        getPreferenceManager().setDefaultPackages(new String[]{BuildConfig.APPLICATION_ID + "."});
        getPreferenceManager().setSharedPreferencesName("settings");
        getPreferenceManager().setSharedPreferencesMode(Context.MODE_PRIVATE);
        setPreferencesFromResource(R.xml.settings, null);
        preferences = Objects.requireNonNull(getContext()).getSharedPreferences("settings",Context.MODE_MULTI_PROCESS);
        port = preferences.getInt("number_picker",2333);
        sp_run = (SwitchPreference)findPreference("sp_run");
        sp_run.setOnPreferenceClickListener(this);

        sw_run_auto = (SwitchPreference)findPreference("sw_run_auto");
        sw_run_auto.setOnPreferenceClickListener(this);

        Preference bs_zssq = (Preference) findPreference("bs_zssq");
        bs_zssq.setOnPreferenceClickListener(this);

        Preference bs_jjcs = (Preference) findPreference("bs_jjcs");
        bs_jjcs.setOnPreferenceClickListener(this);

        myBroadCast = new MyBroadCast();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("cc.zsakvo.yueduhelper");
        getContext().registerReceiver(myBroadCast, intentFilter);
    }

    @Override
    public void onStart() {
        super.onStart();
        sp_run.setChecked(isServiceRunning(getContext(),"cc.zsakvo.yueduhelper.HelperService"));
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        switch (preference.getKey()){
            case "sp_run":
                if (sp_run.isChecked()){
                    bindIntent = new Intent(getContext(), HelperService.class);
                    Objects.requireNonNull(getContext()).startService(bindIntent);
                }else {
                    Objects.requireNonNull(getContext()).stopService(bindIntent);
                }
                break;
            case "sw_run_auto":
                SharedPreferences.Editor editor=preferences.edit();
                editor.putBoolean("sw_run_auto", sw_run_auto.isChecked());
                editor.apply();
                break;
            case "bs_zssq":
                copy("My716_By_zsakvo","zhuishushenqi");
                showSnackBar();
                break;
            case "bs_jjcs":
                copy("九九藏书_By_zsakvo","jiujiucangshu");
                showSnackBar();
                break;
        }
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (getContext()!=null){
            getContext().unregisterReceiver(myBroadCast);
        }
    }

    private static boolean isServiceRunning(Context context, String ServiceName) {
        if (("").equals(ServiceName) || ServiceName == null)
            return false;
        ActivityManager myManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        ArrayList<ActivityManager.RunningServiceInfo> runningService = (ArrayList<ActivityManager.RunningServiceInfo>) myManager
                .getRunningServices(100);
        for (int i = 0; i < runningService.size(); i++) {
            if (runningService.get(i).service.getClassName().toString()
                    .equals(ServiceName)) {
                return true;
            }
        }
        return false;
    }

    class MyBroadCast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Boolean isRunning = intent.getBooleanExtra("isRunning",false);
            sp_run.setChecked(isRunning);
        }
    }

}
