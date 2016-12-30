package com.example.amitrai.demoretrofit.backend;

import com.example.amitrai.demoretrofit.ui.fragment.BaseFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by amitrai on 29/12/16.
 */
@Singleton
@Component(modules={ConnectionModule.class})
public interface NetComponent {
//    void inject(BaseActivity activity);
     void inject(BaseFragment fragment);
    // void inject(MyService service);
}

