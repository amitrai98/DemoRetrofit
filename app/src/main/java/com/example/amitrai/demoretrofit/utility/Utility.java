package com.example.amitrai.demoretrofit.utility;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.CursorLoader;
import android.text.format.DateUtils;
import android.util.Log;

import com.example.amitrai.demoretrofit.listeners.PermissionListener;
import com.example.amitrai.demoretrofit.ui.AppInitials;
import com.example.amitrai.demoretrofit.ui.activity.BaseActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import static android.content.ContentValues.TAG;

/**
 * Created by amitrai on 29/12/16.
 */

public class Utility {

    @Inject
    AppConstants constants;


    public Utility(){
        AppInitials.getContext().getNetComponent().inject(this);
    }


    @SuppressLint("NewApi")
    public String getRealPathFromURI_API19(Context context, Uri uri){
        String filePath = "";
        String wholeID = DocumentsContract.getDocumentId(uri);

        // Split at colon, use second item in the array
        String id = wholeID.split(":")[1];

        String[] column = { MediaStore.Images.Media.DATA };

        // where id is equal to
        String sel = MediaStore.Images.Media._ID + "=?";

        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                column, sel, new String[]{ id }, null);

        int columnIndex = cursor.getColumnIndex(column[0]);

        if (cursor.moveToFirst()) {
            filePath = cursor.getString(columnIndex);
        }
        cursor.close();
        return filePath;
    }


    @SuppressLint("NewApi")
    public  String getRealPathFromURI_API11to18(Context context, Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        String result = null;

        CursorLoader cursorLoader = new CursorLoader(
                context,
                contentUri, proj, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();

        if(cursor != null){
            int column_index =
                    cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            result = cursor.getString(column_index);
        }
        return result;
    }

    public  String getRealPathFromURI_BelowAPI11(Context context, Uri contentUri){
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
        int column_index
                = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }


    public  boolean isStoragePermissionGranted(@NonNull Activity activity,
                                               @NonNull PermissionListener listener) {
        BaseActivity.setPermissionListener(listener);
        Log.e(TAG, "is constant null"+constants);
        if (Build.VERSION.SDK_INT >= 23) {
            if (activity.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted");
                return true;
            } else {

                Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        constants.RUNTIME_PERMISSION_READ_EXTERNAL_STORAGE);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted");
            return true;
        }
    }

    /**
     * checks for a permission
     */
    public void checkPermission(Activity activity, String permission , PermissionListener listener){
        BaseActivity.setPermissionListener(listener);
        if (Build.VERSION.SDK_INT >= 23) {
            if (activity.checkSelfPermission(permission)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted");
                listener.onPermissionGranted(102);
            } else {

                Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(activity, new String[]{permission},
                        constants.RUNTIME_PERMISSION_ANONYMUS);
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted");
            listener.onPermissionGranted(102);
        }
    }

    public String getTimeAgo(String server_time){

//        String givenDateString = "2017-01-04 15:45:39";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");//("EEE MMM dd HH:mm:ss z yyyy");
        try {

            Calendar c = Calendar.getInstance();
            System.out.println("Current time => " + c.getTime());

            String formattedDate = sdf.format(c.getTime());

            Date current_date = sdf.parse(formattedDate);
            Date mDate = sdf.parse(server_time);
            long timeInMilliseconds = mDate.getTime();
            long cttimeInMilliseconds = current_date.getTime();

            System.out.println("Date in milli :: " + timeInMilliseconds);
            return
                    DateUtils.getRelativeTimeSpanString(timeInMilliseconds,
                            cttimeInMilliseconds, DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
//        DateUtils.getRelativeTimeSpanString(your_time_in_milliseconds,
//                ctti, DateUtils.MINUTE_IN_MILLIS);
    }

}
