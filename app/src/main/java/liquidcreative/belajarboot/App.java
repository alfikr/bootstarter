package liquidcreative.belajarboot;

import android.app.Application;
import android.app.PendingIntent;

import liquidcreative.belajarboot.util.PrefMan;

/**
 * Created by danang on 01/07/17.
 */

public class App extends Application {
    private PrefMan prefMan;
    private static App instance;
    public static App getInstance(){
        return instance;
    }
    private PendingIntent alarmIntent;
    @Override
    public void onCreate() {
        super.onCreate();
        prefMan=new PrefMan(App.this);
        instance=this;
    }

    public PendingIntent getAlarmIntent() {
        return alarmIntent;
    }

    public void setAlarmIntent(PendingIntent alarmIntent) {
        this.alarmIntent = alarmIntent;
    }

    public PrefMan getPrefMan() {
        return prefMan;
    }
}
