package com.example.amitrai.demoretrofit.ui.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.amitrai.demoretrofit.R;
import com.example.amitrai.demoretrofit.listeners.ActivityResultListener;
import com.example.amitrai.demoretrofit.listeners.PermissionListener;
import com.example.amitrai.demoretrofit.listeners.ResponseListener;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class ImageUploadFragment extends BaseFragment {

    @Bind(R.id.img_selected)
    ImageView img_selected;

    @Bind(R.id.btn_upload)
    Button btn_upload;

    @Bind(R.id.btn_cancel)
    Button btn_cancel;

    private String path = null;


    public ImageUploadFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_image_upload, container, false);
        ButterKnife.bind(this, view);
        initView(view);
        return view;
    }

    @Override
    public void initView(View view) {

    }

    @OnClick(R.id.btn_cancel)
    void cancelUload(){
        try {
            img_selected.setImageDrawable(null);
            path = null;
            btn_upload.setText(R.string.select_image);
        }catch (Exception exp){
            exp.printStackTrace();
        }

    }

    @OnClick(R.id.btn_upload)
    void selectImage(){
        if(btn_upload.getText().toString().equalsIgnoreCase("upload")){
            Log.e(TAG, ""+path);
            uploadImage(path);

        }else {
            utility.checkPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE,
                    new PermissionListener() {
                        @Override
                        public void onPermissionGranted(int permission_code) {
                            activity.selectImage(new ActivityResultListener() {
                                @Override
                                public void onActivityResult(Intent data) {
                                    try {
                                        Uri selectedImage = data.getData();
                                        path = utility.getRealPathFromURI_API19(activity, selectedImage);
                                        if(path != null && !path.isEmpty()){
                                            Drawable myDrawable = new BitmapDrawable(getResources(), path);
                                            img_selected.setImageDrawable(myDrawable);
                                            btn_upload.setText(R.string.upload);
                                        }

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


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * uploads image on server.
     */
    private void uploadImage(String filePath){
        connection.uploadImage(filePath, service, new ResponseListener() {
            @Override
            public void onSuccess(String response) {
                Log.e(TAG, ""+response);
                btn_upload.setText(R.string.select_image);
                Toast.makeText(activity, response, Toast.LENGTH_SHORT).show();
                img_selected.setImageDrawable(null);
                path = null;
            }

            @Override
            public void onError(String error) {
                Log.e(TAG, ""+error);
                Toast.makeText(activity, "unable to upload your image cuz "+error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
