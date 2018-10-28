package cc.zsakvo.yueduhelper;


import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState==null){
            SettingsFragment fragment = new SettingsFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment,fragment).commit();
        }
        if (!isNoSwitch()){
            openUsageSettings();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    public void openUsageSettings(){
        try {
            Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
            startActivity(intent);
        }catch (Throwable e){
            Toast.makeText(this," 无法打开系统设置，请手动设置 ",Toast.LENGTH_SHORT).show();
        }
    }



    private boolean isNoSwitch() {
        long ts = System.currentTimeMillis();
        UsageStatsManager usageStatsManager = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP_MR1) {
            usageStatsManager = (UsageStatsManager) getApplicationContext()
                    .getSystemService(Context.USAGE_STATS_SERVICE);
        }
        assert usageStatsManager != null;
        List<UsageStats> queryUsageStats = usageStatsManager.queryUsageStats(
                UsageStatsManager.INTERVAL_BEST, 0, ts);
        if (queryUsageStats == null || queryUsageStats.isEmpty()) {
            return false;
        }
        return true;
    }
}
