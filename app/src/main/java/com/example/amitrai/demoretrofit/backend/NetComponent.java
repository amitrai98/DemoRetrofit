package com.example.amitrai.demoretrofit.backend;

import com.example.amitrai.demoretrofit.ui.activity.BaseActivity;
import com.example.amitrai.demoretrofit.ui.adapters.TaskAdapter;
import com.example.amitrai.demoretrofit.ui.fragment.BaseFragment;
import com.example.amitrai.demoretrofit.utility.Utility;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by amitrai on 29/12/16.
 * see more at www.github.com/amitrai98
 */
@Singleton
@Component(modules={ConnectionModule.class})
public interface NetComponent {
//    void inject(BaseActivity activity);
     void inject(BaseFragment fragment);
     void inject(Utility utility);
     void inject(TaskAdapter adapter);
     void inject(BaseActivity activity);
    // void inject(MyService service);
}

