package com.example.amitrai.demoretrofit.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.amitrai.demoretrofit.R;
import com.example.amitrai.demoretrofit.listeners.ResponseListener;
import com.example.amitrai.demoretrofit.models.Task;
import com.example.amitrai.demoretrofit.models.TaskList;
import com.example.amitrai.demoretrofit.ui.adapters.TaskAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TasksFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TasksFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    @Bind(R.id.recycle_tasks)
    RecyclerView recycle_tasks;

    @Bind(R.id.swipe_refresh)
    SwipeRefreshLayout swipe_refresh;

    @Bind(R.id.txt_no_item)
    TextView txt_no_item;

    private TaskAdapter adapter = null;

    private List<Task> taskList = new ArrayList<>();

    private LinearLayoutManager layoutManager;

    TasksFragment fragment;

    private String REQUEST_TYPE = "GET";
    private boolean isEdit = false;



    public TasksFragment() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TasksFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TasksFragment newInstance(String param1, String param2) {
        TasksFragment fragment = new TasksFragment();
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

        taskList.clear();
    }


    public void setRequestType(String REQUEST_TYPE){
        this.REQUEST_TYPE = REQUEST_TYPE;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_fragment_tasks, container, false);
        ButterKnife.bind(this, view);

        initView(view);
        return view;
    }

    @Override
    public void initView(View view) {

        layoutManager = new LinearLayoutManager(getContext());
        adapter = new TaskAdapter(taskList, REQUEST_TYPE, getContext());
        recycle_tasks.setAdapter(adapter);
        recycle_tasks.setLayoutManager(layoutManager);
        txt_no_item.setVisibility(View.GONE);

        swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    getTasks();
                                }
                            });
                        }catch (Exception exp){
                            exp.printStackTrace();
                        }
                    }
                }).start();
            }
        });

        getTasks();
    }

    void getTasks(){
        try {
            Call<ResponseBody> call = service.getTasks(preference.getAPI_KEY());
            connection.request(call, new ResponseListener() {
                @Override
                public void onSuccess(String response) {
                    Log.e(TAG, "request was success"+response);
                    swipe_refresh.setRefreshing(false);
                    try {
                        taskList.clear();
                        TaskList data = new Gson().fromJson(response, TaskList.class);
                        Log.e(TAG, ""+data.getTasks());
                        taskList.addAll(data.getTasks());
                        adapter.notifyDataSetChanged();
                        if(taskList.size() == 0)
                            txt_no_item.setVisibility(View.VISIBLE);
                    }catch (Exception exp){
                        exp.printStackTrace();
                    }
                }

                @Override
                public void onError(String error) {
                    Log.e(TAG, "request failed "+error);
                    swipe_refresh.setRefreshing(false);
                    if(taskList.size() == 0)
                        txt_no_item.setVisibility(View.VISIBLE);
                }
            });
        }catch (Exception exp){
            exp.printStackTrace();
        }

    }

}
