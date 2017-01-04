package com.example.amitrai.demoretrofit.ui.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.amitrai.demoretrofit.R;
import com.example.amitrai.demoretrofit.backend.ApiInterface;
import com.example.amitrai.demoretrofit.backend.Connection;
import com.example.amitrai.demoretrofit.listeners.ResponseListener;
import com.example.amitrai.demoretrofit.models.Task;
import com.example.amitrai.demoretrofit.utility.Utility;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;

import static android.content.ContentValues.TAG;

/**
 * Created by amitrai on 4/1/17.
 */

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> list_task;
    private Utility utility;
    private boolean isDelete;
    private ApiInterface service;
    private Connection connection;

    public TaskAdapter(List<Task> list_task, Utility utility, boolean isDelete,
                       ApiInterface service, Connection connection){
        this.list_task = list_task;
        this.utility = utility;
        this.isDelete = isDelete;
        this.service =service;
        this.connection = connection;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, null);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        if(list_task.get(position) != null){
            Task task = list_task.get(position);
            if(task.getCreatedAt() != null)
                holder.txt_date.setText("created : "+utility.getTimeAgo(task.getCreatedAt()));
            if (task.getTask() != null)
                holder.txt_task.setText(task.getTask());
            if (task.getStatus() != null)
                holder.txt_status.setText(task.getStatus());
        }
    }

    @Override
    public int getItemCount() {
        return list_task.size();
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder{
        TextView txt_task, txt_date, txt_status;
        CardView card_view;
        public TaskViewHolder(View itemView) {
            super(itemView);
            card_view = (CardView) itemView.findViewById(R.id.card_view);
            txt_task = (TextView) itemView.findViewById(R.id.txt_task);
            txt_date = (TextView) itemView.findViewById(R.id.txt_date);
            txt_status = (TextView) itemView.findViewById(R.id.txt_status);

            card_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e(TAG, "item clicked "+getAdapterPosition());
                    try {
                        if (isDelete){

                        }
                            list_task.remove(getAdapterPosition());

                        attemptDelete(list_task.get(getAdapterPosition()).getId());

                        notifyDataSetChanged();
                    }catch (Exception exp){
                        exp.printStackTrace();
                    }

                }
            });
        }
    }


    /**
     * attempt delete on from server
     */
    private void attemptDelete(String id){
        Call<ResponseBody> call = service.deleteTask("be307467723b32663997552fb0e81de7", id);
        connection.request(call, new ResponseListener() {
            @Override
            public void onSuccess(String response) {
                Log.e(TAG, ""+response);
            }

            @Override
            public void onError(String error) {
                Log.e(TAG, ""+error);
            }
        });
    }
}
