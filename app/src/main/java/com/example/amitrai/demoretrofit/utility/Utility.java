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
import android.util.Log;

import com.example.amitrai.demoretrofit.listeners.PermissionListener;
import com.example.amitrai.demoretrofit.ui.AppInitials;
import com.example.amitrai.demoretrofit.ui.activity.BaseActivity;

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

}
