package com.example.amitrai.demoretrofit.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.amitrai.demoretrofit.R;
import com.example.amitrai.demoretrofit.ui.AppInitials;
import com.example.amitrai.demoretrofit.ui.fragment.BaseFragment;

import javax.inject.Inject;

public abstract class BaseActivity extends AppCompatActivity{


    @Inject
    String component;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ((AppInitials) getApplication()).getNetComponent().inject(this);
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

}
