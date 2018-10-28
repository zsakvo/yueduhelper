package cc.zsakvo.yueduhelper;

import android.app.ActivityManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;
import java.util.NavigableSet;
import java.util.TreeMap;

public class RunningTaskUtil {
    private static final String TAG="RunningTaskUtil";
    public static final int TWENTYSECOND = 1000 * 20;
    public static final int THIRTYSECOND = 1000 * 60 * 60 * 3;
    private ActivityManager activityManager;
    private UsageStatsManager mUsageStatsManager;
    private Field mLastEventField;

    public RunningTaskUtil(Context context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            mUsageStatsManager = (UsageStatsManager)context.getApplicationContext().getSystemService(Context.USAGE_STATS_SERVICE);
        }
        activityManager = (ActivityManager)context.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
    }

    public ComponentName getTopRunningTasks(){
        return getTopRunningTasks(true);
    }

    public ComponentName getTopRunningTasks(boolean isFirst){
        ComponentName runningTopActivity=null;
        String topPackageName =null;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ) {
            long time = System.currentTimeMillis();
            List<UsageStats> stats ;
            if (isFirst){
                stats = mUsageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time - TWENTYSECOND, time);
            }else {
                stats = mUsageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time - THIRTYSECOND, time);
            }
            if(stats != null) {
                TreeMap<Long,UsageStats> mySortedMap = new TreeMap<Long,UsageStats>();
                for (UsageStats usageStats : stats) {
                    mySortedMap.put(usageStats.getLastTimeUsed(),usageStats);
                }
                if(mySortedMap != null && !mySortedMap.isEmpty()) {
                    NavigableSet<Long> keySet=mySortedMap.navigableKeySet();
                    Iterator iterator=keySet.descendingIterator();
                    while(iterator.hasNext()){
                        UsageStats usageStats = mySortedMap.get(iterator.next());
                        if (mLastEventField==null) {
                            try {
                                mLastEventField = UsageStats.class.getField("mLastEvent");
                            } catch (NoSuchFieldException e) {
                                break;
                            }
                        }
                        if (mLastEventField!=null) {
                            int lastEvent = 0;
                            try {
                                lastEvent = mLastEventField.getInt(usageStats);
                            } catch (IllegalAccessException e) {
                                break;
                            }
                            if (lastEvent==1){
                                topPackageName=usageStats.getPackageName();
                                break;
                            }
                        }else {
                            break;
                        }
                    }
                    if (topPackageName==null){
                        topPackageName =  mySortedMap.get(mySortedMap.lastKey()).getPackageName();
                        if ("com.android.systemui".equals(topPackageName)){
                            Long currentKey = null;
                            String tempPackage = topPackageName;
                            currentKey = (Long) ((TreeMap) mySortedMap).floorKey(mySortedMap.lastKey()-1);
                            if (currentKey!=null) {
                                tempPackage = mySortedMap.get(currentKey).getPackageName();
                            }
                            if (tempPackage!=null){
                                if (MyApplication.getInstance().getPackageName().equals(tempPackage)){
                                    currentKey = (Long) ((TreeMap) mySortedMap).floorKey(currentKey-1);
                                    if (currentKey!=null) {
                                        tempPackage = mySortedMap.get(currentKey).getPackageName();
                                    }
                                }
                            }
                            if (tempPackage!=null){
                                topPackageName=tempPackage;
                            }
                        }
                    }
                    runningTopActivity=new ComponentName(topPackageName,"");
                }else {
                    if (isFirst){
                        runningTopActivity = getTopRunningTasks(false);
                    }else {
                        runningTopActivity=getTopActivtyFromLolipopOnwards();
                    }
                }
            }
            if (runningTopActivity.getPackageName().equals(MyApplication.getInstance().getPackageName())){
                runningTopActivity = getTopActivtyFromLolipopOnwards();
            }
        }else {
            runningTopActivity = getTopActivtyFromLolipopOnwards();
        }
        return runningTopActivity;
    }

    public ComponentName getTopActivtyFromLolipopOnwards(){
        ComponentName runningTopActivity = activityManager.getRunningTasks(1).get(0).topActivity;
        return runningTopActivity;
    }
}
