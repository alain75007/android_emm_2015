package com.example.alain.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;

import java.util.Locale;

/**
 * Created by alain on 22/04/15.
 */
public class MySingleton {
    private static MySingleton ourInstance = new MySingleton();
    private  Bitmap bitmap;

    public static Bitmap getBitmap() {
        return ourInstance.bitmap;
    }

    public static void setBitmap(Bitmap bitmap) {
        ourInstance.bitmap = bitmap;
    }


    public static boolean onOptionsItemSelected(ActionBarActivity activity, MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(id) {
            case R.id.action_settings:
                return true;
            case R.id.action_logout:
                askLogoutConfirmation(activity);
                return true;
            case R.id.action_lang_en :
                changeLang(activity, "en");

                return true;
            case R.id.action_lang_fr:
                changeLang(activity, "fr");
                return true;
        }
        return true;
    }

    private static void changeLang(ActionBarActivity activity, String lang ) {
        saveLang(activity, lang);
        setLocale(activity, lang);
        activity.recreate();
    }

    private static void saveLang(ActionBarActivity activity, String lang) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("lang", lang);
        editor.apply();
    }

/*    private static void restartApp(ActionBarActivity activity ) {
        Intent i = activity.getPackageManager()
                .getLaunchIntentForPackage( activity.getPackageName() );
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.finish();
        activity.startActivity(i);

    }*/

    public static void restoreLang(ActionBarActivity activity) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
        setLocale(activity, preferences.getString("lang", "en"));
    }

    private static void setLocale(ActionBarActivity activity, String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config2 = new Configuration();
        config2.locale = locale;
        // updating locale
        activity.getBaseContext().getResources().updateConfiguration(config2, activity.getBaseContext().getResources().getDisplayMetrics());


    }

    private static Boolean askLogoutConfirmation( final ActionBarActivity activity) {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

// 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage(R.string.act_menu_logout_dialog_message)
                .setTitle(R.string.act_menu_logout_dialog_title);


// Add the buttons
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Log.d("XXXXXXX", "Ok button");
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("is_logged", false);
                editor.apply();
                startMainActivity(activity);
                activity.finish();
            }
        });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("XXXXXXX", "Cancel button");
            }

        });

        // Create the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
        return false;
    }

    private static void startMainActivity(ActionBarActivity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        ((Activity)activity).startActivity(intent);
    }
/*

    private static MySingleton getInstance() {
        return ourInstance;
    }

*/
    private MySingleton() {
    }
}
