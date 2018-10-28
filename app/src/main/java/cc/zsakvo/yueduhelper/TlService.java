package cc.zsakvo.yueduhelper;


import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Icon;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;
import android.util.Log;

import java.util.ArrayList;

import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.N)
public class TlService extends TileService {

    private final String LOG_TAG = "QuickSettingService";

    @Override
    public void onTileAdded() {
        Log.d(LOG_TAG, "onTileAdded");
    }

    @Override
    public void onTileRemoved() {
        Log.d(LOG_TAG, "onTileRemoved");
    }

    @Override
    public void onClick() {
        int state = getQsTile().getState();
        Icon icon;
        if (state == Tile.STATE_INACTIVE) {
            Intent serviceIntent = new Intent(this,HelperService.class);
            startService(serviceIntent);
            icon =  Icon.createWithResource(getApplicationContext(), R.drawable.ic_book);
            getQsTile().setState(Tile.STATE_ACTIVE);
        } else {
            Intent serviceIntent = new Intent(this,HelperService.class);
            stopService(serviceIntent);
            icon = Icon.createWithResource(getApplicationContext(), R.drawable.ic_book);
            getQsTile().setState(Tile.STATE_INACTIVE);
        }
        getQsTile().setIcon(icon);
        getQsTile().updateTile();
    }

    @Override
    public void onStartListening () {
        Log.d(LOG_TAG, "onStartListening");
        if (isServiceRunning(this,"cc.zsakvo.yueduhelper.HelperService")){
            Log.e(LOG_TAG, "onStartListening: 1" );
            getQsTile().setState(Tile.STATE_ACTIVE);
        }else {
            Log.e(LOG_TAG, "onStartListening: 2" );
            getQsTile().setState(Tile.STATE_INACTIVE);
        }
        getQsTile().updateTile();
    }

    @Override
    public void onStopListening () {
        Log.d(LOG_TAG, "onStopListening");
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

}
