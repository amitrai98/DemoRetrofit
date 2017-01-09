package com.example.amitrai.demoretrofit.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.amitrai.demoretrofit.R;
import com.example.amitrai.demoretrofit.backend.ApiInterface;
import com.example.amitrai.demoretrofit.backend.Connection;
import com.example.amitrai.demoretrofit.databases.AppPreference;
import com.example.amitrai.demoretrofit.ui.AppInitials;
import com.example.amitrai.demoretrofit.ui.activity.BaseActivity;
import com.example.amitrai.demoretrofit.utility.Utility;
import com.google.gson.Gson;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BaseFragment} factory method to
 * create an instance of this fragment.
 */
public abstract class BaseFragment extends Fragment {

    @Inject
    ApiInterface service;

    @Inject
    Connection connection;

    @Inject
    Utility utility;

    @Inject
    AppPreference preference;

    @Inject
    Gson gson;


    BaseActivity activity;



    public BaseFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppInitials.getContext().getNetComponent().inject(this);

        if (getActivity() instanceof  BaseActivity)
            activity = (BaseActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_base, container, false);
    }

    /**
     * initialize view elements here
     * @param view parent view of fragment
     */
    public abstract void initView(View view);

    public void replaceFragment(BaseFragment fragment, boolean isAddToStack){
        activity.replaceFragment(fragment, isAddToStack);
    }

}
