package com.example.amitrai.demoretrofit.ui.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.amitrai.demoretrofit.R;
import com.example.amitrai.demoretrofit.listeners.ActivityResultListener;
import com.example.amitrai.demoretrofit.listeners.PermissionListener;
import com.example.amitrai.demoretrofit.listeners.ResponseListener;
import com.example.amitrai.demoretrofit.ui.activity.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int PICK_IMAGE_REQUEST = 201;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    @Bind(R.id.btn_register)
    Button btn_register;


    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);

        return view;
    }

    @Override
    public void initView(View view) {
        ButterKnife.bind(this, view);
        Button btn_register = (Button) view.findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "register called"+utility.getTimeAgo("2017-01-04 15:45:39"));

//                attemptRegister();
//                selectImage();
//                createTask();
            }
        });
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
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

    @OnClick(R.id.btn_login)
    void attemptLogin(){
        try {
            Call<ResponseBody> call = service.login("amit.rai@evontech.com","123456");
            connection.request(call, new ResponseListener() {
                @Override
                public void onSuccess(String response) {
                    Log.e(TAG, "get respo"+response);
                }

                @Override
                public void onError(String error) {
                    Log.e(TAG, "get error"+error);
                }
            });
        }catch (Exception exp){
            exp.printStackTrace();
        }
    }

    @OnClick(R.id.btn_register)
    void attemptRegister(){
        try {
            Log.e(TAG, "received call");
            Call<ResponseBody> call = service.register("android_user","user@evon.com","asdf"
                    ,"123456789");
            connection.request(call, new ResponseListener() {
                @Override
                public void onSuccess(String response) {
                    Log.e(TAG, "get respo"+response);
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

    /**
     * uploads image on server.
     */
    private void uploadImage(String filePath){
        connection.uploadImage(filePath, service, new ResponseListener() {
            @Override
            public void onSuccess(String response) {
                Log.e(TAG, ""+response);
                Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String error) {
                Log.e(TAG, ""+error);
            }
        });
    }


    private void selectImage(){
        utility.checkPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE,
                new PermissionListener() {
                    @Override
                    public void onPermissionGranted(int permission_code) {
                        BaseActivity ctx = (BaseActivity) getContext();
                        ctx.selectImage(new ActivityResultListener() {
                            @Override
                            public void onActivityResult(Intent data) {
                                try {
                                    Uri selectedImage = data.getData();
                                    String path = utility.getRealPathFromURI_API19(getActivity(), selectedImage);
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
}
