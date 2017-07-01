package liquidcreative.belajarboot;

import android.Manifest;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import net.danlew.android.joda.JodaTimeAndroid;

import org.joda.time.DateTime;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import liquidcreative.belajarboot.pojo.Konfigurasi;

public class MainActivity extends AppCompatActivity {
    private final int REQUEST_BOOT=101;
    private Button btnAlert;
    private App app;
    private boolean serviceStarted;
    private Gson gson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app=App.getInstance();
        JodaTimeAndroid.init(this);
        GsonBuilder gsonBuilder=new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            DateFormat df = new SimpleDateFormat("dd MM yyyy hh:mm:ss");
            public Date deserialize(final JsonElement json, final Type type, final JsonDeserializationContext context) throws JsonParseException{
                try {
                    return df.parse(json.getAsString());
                }catch (ParseException e){
                    return null;
                }
            }
        });
        gson=gsonBuilder.create();
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActivityManager manager= (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for(ActivityManager.RunningServiceInfo service: manager.getRunningServices(Integer.MAX_VALUE)){
            if(BootService.class.getName().equals(service.service.getClassName())){
                serviceStarted=true;
            }
        }
        final TextView tv = (TextView) findViewById(R.id.txtTest);
        tv.setText(gson.toJson(app.getPrefMan().getKonfigurasi()));
        if(!serviceStarted){
            Intent intent=new Intent(MainActivity.this,BootService.class);
            MainActivity.this.startService(intent);
        }
        btnAlert=(Button)findViewById(R.id.btnCreateAlert);
        btnAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Konfigurasi konfigurasi=app.getPrefMan().getKonfigurasi();
                konfigurasi.setCreateDate(new Date());
                DateTime now = new DateTime();
                DateTime alertTime=now.plusMinutes(2);
                konfigurasi.setAlarmDate(alertTime.toDate());
                konfigurasi.setHasShown(false);
                app.getPrefMan().setKonfig(konfigurasi);
                //setDateAlert();
                try {
                    tv.setText(gson.toJson(app.getPrefMan().getKonfigurasi()));
                }catch (Exception ex){

                }
            }
        });
        //Intent intent=new Intent(MainActivity.this,BootService.class);
        //MainActivity.this.startService(intent);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    int result = checkSelfPermission(Manifest.permission.RECEIVE_BOOT_COMPLETED);
                    if(result!= PackageManager.PERMISSION_GRANTED){
                        requestPermissions(new String[]{Manifest.permission.RECEIVE_BOOT_COMPLETED},REQUEST_BOOT);
                    }else{
                        ActivityManager manager= (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
                        for(ActivityManager.RunningServiceInfo service: manager.getRunningServices(Integer.MAX_VALUE)){
                            if(BootService.class.getName().equals(service.service.getClassName())){
                                Toast.makeText(MainActivity.this,"Service telah berjalan",Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }
            }
        });
    }
    public void setDateAlert(){
        Long alertTime = new GregorianCalendar().getTimeInMillis()+5*1000;

        Intent alertIntent = new Intent(this,NotificationAlertReceiver.class);

        AlarmManager alarmManager = (AlarmManager)
                getSystemService(Context.ALARM_SERVICE);

        alarmManager.set(AlarmManager.RTC_WAKEUP, alertTime,
                PendingIntent.getBroadcast(this, 1, alertIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT));
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==REQUEST_BOOT){
            if(permissions.length==1 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                //boleh
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
