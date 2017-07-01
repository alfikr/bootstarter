package liquidcreative.belajarboot.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Date;
import java.util.prefs.Preferences;

import liquidcreative.belajarboot.pojo.Konfigurasi;

/**
 * Created by danang on 01/07/17.
 */

public class PrefMan {
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private final static String PREF_NAME="alert";
    private final static String TANGGAL_ALERT="alertDate";
    private final static String TANGGAL_CREATE="createDate";
    private final static String Tampil="tampil";

    private Context context;
    public PrefMan(Context context){
        this.context=context;
        preferences=context.getSharedPreferences(PREF_NAME,0);
        editor=preferences.edit();
    }

    public void setKonfig(Konfigurasi konfigurasi){
        editor.clear();
        editor.putLong(TANGGAL_ALERT,konfigurasi.getAlarmDate()==null?0:konfigurasi.getAlarmDate().getTime());
        editor.putLong(TANGGAL_CREATE,konfigurasi.getCreateDate()==null?0:konfigurasi.getCreateDate().getTime());
        editor.putBoolean(Tampil,konfigurasi.isHasShown());
        editor.commit();
    }
    public Konfigurasi getKonfigurasi(){
        Konfigurasi konfigurasi=new Konfigurasi();
        if(preferences.getLong(TANGGAL_CREATE,0)!=0){
            konfigurasi.setCreateDate(new Date(preferences.getLong(TANGGAL_CREATE,0)));

        }
        if(preferences.getLong(TANGGAL_ALERT,0)!=0){
            konfigurasi.setAlarmDate(new Date(preferences.getLong(TANGGAL_ALERT,0)));
        }
        konfigurasi.setHasShown(preferences.getBoolean(Tampil,false));
        return konfigurasi;
    }
}
