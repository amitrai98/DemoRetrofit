package com.example.amitrai.demoretrofit.ui.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.amitrai.demoretrofit.R;
import com.example.amitrai.demoretrofit.listeners.ActivityResultListener;
import com.example.amitrai.demoretrofit.listeners.PermissionListener;
import com.example.amitrai.demoretrofit.ui.fragment.BaseFragment;
import com.example.amitrai.demoretrofit.utility.AppConstants;

import javax.inject.Inject;

public abstract class BaseActivity extends AppCompatActivity{
    public static int PICK_IMAGE_REQUEST = 203;

    ActivityResultListener activityResultListener;

    private static PermissionListener permissionListener;

    @Inject
    AppConstants constants;
//    @Inject
//    public String component;
//
//    @Inject
//    public SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

//        ((AppInitials) getApplication()).getNetComponent().inject(this);
    }


    /**
     * initialize all view elements here.
     */
    public abstract void initView();

    /**
     * replaces fragment to the container
     * @param fragment name of fragment to be replaced
     * @param storeInStack should store in stack or not.
     */
    public void replaceFragment(BaseFragment fragment, boolean storeInStack){
        String backStateName = fragment.getClass().getName();

        FragmentManager manager = getSupportFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate (backStateName, 0);

        if (!fragmentPopped){ //fragment not in back stack, create it.
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.container, fragment);

            if (storeInStack)
                ft.addToBackStack(backStateName);

            ft.commit();
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            if (activityResultListener != null)
                activityResultListener.onActivityResult(data);

        }
    }


    public void selectImage(ActivityResultListener activityResultListener){
        this.activityResultListener = activityResultListener;
        Intent intent = new Intent();
// Show only images, no videos or anything else
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
// Always show the chooser (if there are multiple options available)
        BaseActivity.this.startActivityForResult(Intent.createChooser(intent, "Select Picture"), BaseActivity.PICK_IMAGE_REQUEST);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if(permissionListener != null)
                permissionListener.onPermissionGranted(requestCode);

        } else {
            if(permissionListener != null)
                permissionListener.onPermissionDenied(requestCode);
        }


        switch (requestCode) {
            case 101: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(permissionListener != null)
                        permissionListener.onPermissionGranted(requestCode);

                } else {
                    if(permissionListener != null)
                        permissionListener.onPermissionDenied(requestCode);
                }
                return;
            }




            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    /**
     * sets permission listeners for future call backs.
     * @param listener
     */
    public static void setPermissionListener(@NonNull PermissionListener listener){
        permissionListener = listener;
    }
}
