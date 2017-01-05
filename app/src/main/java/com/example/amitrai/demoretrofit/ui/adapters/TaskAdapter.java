package com.example.amitrai.demoretrofit.ui.adapters;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amitrai.demoretrofit.R;
import com.example.amitrai.demoretrofit.backend.ApiInterface;
import com.example.amitrai.demoretrofit.backend.Connection;
import com.example.amitrai.demoretrofit.databases.AppPreference;
import com.example.amitrai.demoretrofit.listeners.ResponseListener;
import com.example.amitrai.demoretrofit.models.Task;
import com.example.amitrai.demoretrofit.ui.AppInitials;
import com.example.amitrai.demoretrofit.utility.AppConstants;
import com.example.amitrai.demoretrofit.utility.Utility;

import java.util.List;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import retrofit2.Call;

import static android.content.ContentValues.TAG;

/**
 * Created by amitrai on 4/1/17.
 * see more at https://github.com/amitrai98
 */

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> list_task;
//    private Utility utility;
    private String REQUEST_TYPE;
//    private ApiInterface service;
//    private Connection connection;
//    private AppConstants constants;
    private Context context;

    @Inject
    Connection connection;
    @Inject
    ApiInterface service;
    @Inject
    AppConstants constants;
    @Inject
    Utility utility;
    @Inject
    AppPreference preference;

    public TaskAdapter(List<Task> list_task,String REQUEST_TYPE, Context context){
        this.list_task = list_task;
//        this.utility = utility;
        this.REQUEST_TYPE = REQUEST_TYPE;
//        this.service =service;
//        this.connection = connection;
        this.context = context;
//        constants = new AppConstants();
        AppInitials.getContext().getNetComponent().inject(this);
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent,
                false);
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

    class TaskViewHolder extends RecyclerView.ViewHolder{
        TextView txt_task, txt_date, txt_status, txt_edit;
        CardView card_view;
        TaskViewHolder(View itemView) {
            super(itemView);
            card_view = (CardView) itemView.findViewById(R.id.card_view);
            txt_task = (TextView) itemView.findViewById(R.id.txt_task);
            txt_date = (TextView) itemView.findViewById(R.id.txt_date);
            txt_status = (TextView) itemView.findViewById(R.id.txt_status);
            txt_edit = (TextView) itemView.findViewById(R.id.txt_edit);

            if (REQUEST_TYPE.equalsIgnoreCase(constants.PUT)){
                txt_edit.setVisibility(View.VISIBLE);
            }

            card_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e(TAG, "item clicked "+getAdapterPosition());
                    try {
                        if (REQUEST_TYPE.equalsIgnoreCase(constants.DELETE)){
                            attemptDelete(list_task.get(getAdapterPosition()).getId(),
                                    getAdapterPosition());
                        }
                    }catch (Exception exp){
                        exp.printStackTrace();
                    }

                }
            });

            txt_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showUpdateDialog(list_task.get(getAdapterPosition()),getAdapterPosition());
                }
            });
        }
    }


    /**
     * attempt delete on from server
     */
    private void attemptDelete(String id, final int position){
        Call<ResponseBody> call = service.deleteTask("be307467723b32663997552fb0e81de7", id);
        connection.request(call, new ResponseListener() {
            @Override
            public void onSuccess(String response) {
                Log.e(TAG, ""+response);
                list_task.remove(position);
                notifyDataSetChanged();
            }

            @Override
            public void onError(String error) {
                Log.e(TAG, ""+error);
            }
        });
    }

    /**
     * shows dialog for updating task
     */
    private void showUpdateDialog(final Task task, final int position){
        // Create custom dialog object
        final Dialog dialog = new Dialog(context);
        // Include dialog.xml file
        dialog.setContentView(R.layout.dialog_update_task);
        // Set dialog title
        dialog.setTitle("Update Task");

        // set values for custom dialog components - text, image and button
        final TextView edt_task = (TextView) dialog.findViewById(R.id.edt_task);
        final TextView edt_status = (TextView) dialog.findViewById(R.id.edt_status);

        Button btn_update_task = (Button) dialog.findViewById(R.id.btn_update_task);
        Button btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);


        dialog.show();

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close dialog
                dialog.dismiss();
            }
        });

        btn_update_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String new_task = edt_task.getText().toString();
                String new_status = edt_status.getText().toString();
                if(new_task.trim().equalsIgnoreCase(task.getTask().trim())
                        && new_status.trim().equalsIgnoreCase(task.getStatus().trim())){
                    Toast.makeText(context, "task and status are same.", Toast.LENGTH_SHORT).show();
                }else {
                    dialog.dismiss();
                    updateTask(task.getId(), new_task, new_status, position);
                }
            }
        });
    }


    /**
     * attempts to update the data on server
     * @param task_id id of the current task
     * @param task new task to be updated
     * @param status new status to beupdated
     */
    private void updateTask(String task_id, final String task, final String status, final int position){
        if(!preference.getAPI_KEY().isEmpty()){
            Call call = service.updateTask(preference.getAPI_KEY(), task_id, task, status);
            connection.request(call, new ResponseListener() {
                @Override
                public void onSuccess(String response) {
                    Log.e(TAG, ""+response);
                    list_task.get(position).setTask(task);
                    list_task.get(position).setStatus(status);
                    notifyDataSetChanged();
                }

                @Override
                public void onError(String error) {
                    Log.e(TAG, ""+error);
                }
            });
        }else {
            Toast.makeText(context, "please login to check data", Toast.LENGTH_SHORT).show();
        }

    }
}
