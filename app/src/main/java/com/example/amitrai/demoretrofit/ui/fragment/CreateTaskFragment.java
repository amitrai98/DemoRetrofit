package com.example.amitrai.demoretrofit.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.amitrai.demoretrofit.R;
import com.example.amitrai.demoretrofit.listeners.ResponseListener;
import com.example.amitrai.demoretrofit.models.Task;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class CreateTaskFragment extends BaseFragment {


    @Bind(R.id.edt_create_task)
    EditText edt_create_task;

    @Bind(R.id.spinner_status)
    Spinner spinner_status;


    public CreateTaskFragment() {
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
        View view =inflater.inflate(R.layout.fragment_create_task, container, false);
        ButterKnife.bind(this, view);
        initView(view);
        return view;
    }

    @Override
    public void initView(View view) {

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @OnClick(R.id.btn_cancel)
    void cancel(){
        getActivity().onBackPressed();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }


    /**
     * creates a new task.
     */
    @OnClick(R.id.btn_create_task)
    void createTask(){

        int id = spinner_status.getSelectedItemPosition();
        if (edt_create_task.getText().toString().isEmpty()){
            edt_create_task.setError("can not left blank");
        }else if (id == 0){
            Toast.makeText(getContext(), "please select a valid status", Toast.LENGTH_SHORT).show();
        }else {
            try {
                if (!preference.getAPI_KEY().isEmpty()){
                    Call<ResponseBody> call = service.createTask(preference.getAPI_KEY(),
                            new Task(edt_create_task.getText().toString(),""+id));
                    connection.request(call, new ResponseListener() {
                        @Override
                        public void onSuccess(String response) {
                            Log.e(TAG, "request was success"+response);
                            edt_create_task.setText("");
                            spinner_status.setSelection(0);
                            Toast.makeText(getActivity(), "task created successfully",
                                    Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(String error) {
                            Log.e(TAG, "request failed "+error);
                        }
                    });
                }else{
                    Toast.makeText(getActivity(), "you are not logged in",
                            Toast.LENGTH_SHORT).show();
                }

            }catch (Exception exp){
                exp.printStackTrace();
            }
        }

    }
}
