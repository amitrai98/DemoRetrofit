package com.example.amitrai.demoretrofit.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.amitrai.demoretrofit.R;
import com.example.amitrai.demoretrofit.backend.ApiInterface;
import com.example.amitrai.demoretrofit.backend.Connection;
import com.example.amitrai.demoretrofit.listeners.ActivityResultListener;
import com.example.amitrai.demoretrofit.listeners.LoginListener;
import com.example.amitrai.demoretrofit.listeners.PermissionListener;
import com.example.amitrai.demoretrofit.listeners.ResponseListener;
import com.example.amitrai.demoretrofit.models.Data;
import com.example.amitrai.demoretrofit.ui.AppInitials;
import com.example.amitrai.demoretrofit.ui.fragment.AboutFragment;
import com.example.amitrai.demoretrofit.ui.fragment.CreateTaskFragment;
import com.example.amitrai.demoretrofit.ui.fragment.LoginFragment;
import com.example.amitrai.demoretrofit.ui.fragment.TasksFragment;
import com.example.amitrai.demoretrofit.utility.Utility;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, LoginListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawer;
    @Bind(R.id.nav_view) NavigationView navigationView;
    @Bind(R.id.content_home)
    RelativeLayout content_home;

    @Inject
    Connection connection;

    @Inject
    ApiInterface service;

    private String TAG = getClass().getSimpleName();

    Utility utility;

//    @Bind(R.id.txt_name)
//    TextView txt_name;
//
//    @Bind(R.id.txt_email)
//    TextView txt_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        AppInitials.getContext().getNetComponent().inject(this);

        ButterKnife.bind(this);


        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        initView();
    }

    @Override
    public void initView() {
//        String url = component;
//        Log.e(TAG, ""+url);

        utility = new Utility();
        utility.changeColor(content_home, this);
        LoginFragment fragment = new LoginFragment();
        fragment.setLoginListener(this);
        replaceFragment(fragment, true);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
                finish();
                return;
            }
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        utility.changeColor(content_home, this);

        if (id == R.id.get_request) {
            // Handle the camera action
            TasksFragment fragment = new TasksFragment();
            fragment.setRequestType(constants.GET);
            replaceFragment(fragment, false);
        } else if (id == R.id.post_request) {
            replaceFragment(new CreateTaskFragment(), true);
        } else if (id == R.id.put_request) {
            TasksFragment fragment = new TasksFragment();
            fragment.setRequestType(constants.PUT);
            replaceFragment(fragment, false);

        } else if (id == R.id.delete_request) {
            TasksFragment fragment = new TasksFragment();
            fragment.setRequestType(constants.DELETE);
            replaceFragment(fragment, false);
        } else if (id == R.id.nav_logout) {
            preference.logout();
            replaceFragment(new LoginFragment(), true);
        } else if (id == R.id.nav_about) {
            replaceFragment(new AboutFragment(), true);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public void onLoginSuccess(Data data) {
//        if(data.getName() != null && txt_name != null)
//            txt_name.setText(data.getName());
//        if (data.getEmail() != null && txt_email != null)
//            txt_email.setText(data.getEmail());
        Log.e(TAG, ""+data.getName());
    }

    void selectImage(){
        utility.checkPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE,
                new PermissionListener() {
                    @Override
                    public void onPermissionGranted(int permission_code) {
                       selectImage(new ActivityResultListener() {
                            @Override
                            public void onActivityResult(Intent data) {
                                try {
                                    Uri selectedImage = data.getData();
                                    String path = utility.getRealPathFromURI_API19(HomeActivity.this, selectedImage);
                                    Log.e(TAG, ""+path);
                                    uploadImage(path);
                                }catch (Exception exp){
                                    exp.printStackTrace();
                                }
                            }
                        });
                    }

                    @Override
                    public void onPermissionDenied(int permission_code) {

                    }
                });

    }

    /**
     * uploads image on server.
     */
    private void uploadImage(String filePath){
        connection.uploadImage(filePath, service, new ResponseListener() {
            @Override
            public void onSuccess(String response) {
                Log.e(TAG, ""+response);
                Toast.makeText(HomeActivity.this, response, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String error) {
                Log.e(TAG, ""+error);
            }
        });
    }

}
