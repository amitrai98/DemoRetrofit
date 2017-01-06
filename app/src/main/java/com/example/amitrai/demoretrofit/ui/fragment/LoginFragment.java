package com.example.amitrai.demoretrofit.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.amitrai.demoretrofit.R;
import com.example.amitrai.demoretrofit.listeners.LoginListener;
import com.example.amitrai.demoretrofit.listeners.ResponseListener;
import com.example.amitrai.demoretrofit.models.Data;
import com.example.amitrai.demoretrofit.models.LoginModel;
import com.example.amitrai.demoretrofit.models.RememberMe;
import com.google.gson.Gson;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    @Bind(R.id.edt_email)
    EditText edt_email;

    @Bind(R.id.edt_password)
    EditText edt_password;

    @Bind(R.id.remember_me)
    CheckBox remember_me;

    private LoginListener loginListener;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public void setLoginListener(LoginListener loginListener){
        this.loginListener = loginListener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);

        initView(view);
        return view;
    }

    @Override
    public void initView(View view) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.btn_login)
    void attemptLogin(){
        try {

            final String email = edt_email.getText().toString();
            final String password = edt_password.getText().toString();

            if (!email.trim().isEmpty() && utility.isValidEmail(email) && !password.trim().isEmpty()){
                Call<ResponseBody> call = service.login(email, password);
                connection.request(call, new ResponseListener() {
                    @Override
                    public void onSuccess(String response) {
                        Log.e(TAG, "get respo "+response);
                        try {
                            LoginModel model = new Gson().fromJson(response, LoginModel.class);
                            Data login_data = model.getData();
                            Log.e(TAG, ""+login_data);
                            if(model.getError().equals("true")){
                                String message = "Invalid username or password";
                                if (login_data.getMessage()!= null)
                                    message = login_data.getMessage();

                                Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
                            }else {
                                preference.setUserLoginTrue(login_data);
                                if(loginListener != null)
                                    loginListener.onLoginSuccess(login_data);

                                if (remember_me.isChecked()){
                                    preference.setRememberme(new RememberMe(email, password));
                                }else {
                                    preference.setRememberme(new RememberMe("", ""));
                                }

                                replaceFragment(new HomeFragment(), true);
                            }
                        }catch (Exception exp){
                            exp.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(String error) {
                        Log.e(TAG, "get error"+error);
                    }
                });
            }else {
                if(activity != null)
                Toast.makeText(activity, "Invalid username or password", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception exp){
            exp.printStackTrace();
        }
    }

    /**
     * opens registration fragment
     */
    @OnClick(R.id.btn_register)
    void openRegistrationFragment(){
        if(activity != null){
            activity.replaceFragment(new RegisterFragment(), true);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        RememberMe rememberMe = preference.getRemberme();
        if(!rememberMe.getUsername().isEmpty() && !rememberMe.getPassword().isEmpty()){
            edt_email.setText(rememberMe.getUsername());
            edt_password.setText(rememberMe.getPassword());
            remember_me.setChecked(true);
        }
    }
}
