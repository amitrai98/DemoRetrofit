package com.example.amitrai.demoretrofit.ui.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.amitrai.demoretrofit.R;
import com.example.amitrai.demoretrofit.listeners.ResponseListener;
import com.example.amitrai.demoretrofit.models.CommonResponse;
import com.example.amitrai.demoretrofit.models.RememberMe;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RegisterFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    @Bind(R.id.edt_name)
    EditText edt_name;
    @Bind(R.id.edt_mobile_no)
    EditText edt_mobile_no;
    @Bind(R.id.edt_email)
    EditText edt_email;
    @Bind(R.id.edt_password)
    EditText edt_password;



    public RegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegisterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        ButterKnife.bind(this, view);
        initView(view);
        return view;
    }

    @Override
    public void initView(View view) {

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }



    @OnClick(R.id.btn_register)
    void attemptRegister(){

        final String name, email, mobile_no, password;
        name = edt_name.getText().toString();
        email = edt_email.getText().toString();
        mobile_no = edt_mobile_no.getText().toString();
        password = edt_password.getText().toString();

        boolean isCancel = false;
        View focusView = null;

        if (name.isEmpty()){
            isCancel = true;
            focusView = edt_name;
            edt_name.setError("please enter a valid name");
        }else if (mobile_no.isEmpty() && utility.isValidPhone(mobile_no)){
            isCancel = true;
            focusView = edt_mobile_no;
            edt_mobile_no.setError("please enter a valid mobile no.");
        }else if (email.isEmpty()){
            isCancel = true;
            focusView = edt_email;
            edt_email.setError("please enter a valid email address.");
        }else if (password.isEmpty()){
            isCancel = true;
            focusView = edt_password;
            edt_password.setError("please enter a valid password.");
        }

        if (isCancel){
            focusView.requestFocus();
        }else {
            try {
                Log.e(TAG, "received call");
                Call<ResponseBody> call = service.register(name, email, password, mobile_no);
                connection.request(call, new ResponseListener() {
                    @Override
                    public void onSuccess(String response) {
                        Log.e(TAG, "get respo"+response);
                        String message = "unable to register you right now please try again latter";
                        CommonResponse respo = gson.fromJson(response, CommonResponse.class);

                        if (respo.getError()){
                            if(respo.getMessage() != null)
                                message = respo.getMessage();

                            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getContext(), "you are registerd now", Toast.LENGTH_SHORT).show();
                            preference.setRememberme(new RememberMe(email, password));
                            openLoginFragment();
                        }

                    }

                    @Override
                    public void onError(String error) {
                        Log.e(TAG, "get err"+error);
                    }
                });
            }catch (Exception exp){
                exp.printStackTrace();
            }
        }
    }

    @OnClick(R.id.btn_login)
    void openLoginFragment(){
        if(activity != null){
            activity.replaceFragment(new LoginFragment(), true);
        }
    }

}
