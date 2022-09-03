package com.webnmobapps.livelyPencil.Adapter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.webnmobapps.livelyPencil.Activity.Setting.PersonalInformationActivity;
import com.webnmobapps.livelyPencil.Model.Personal_Information_Settings_Model;
import com.webnmobapps.livelyPencil.Model.Record.NotificationListResult;
import com.webnmobapps.livelyPencil.Model.SmFlaxibleModel;
import com.webnmobapps.livelyPencil.R;
import com.webnmobapps.livelyPencil.RetrofitApi.API_Client;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationListAdapter extends RecyclerView.Adapter<NotificationListViewHolder> {

    List<NotificationListResult> notificationListResultList;
    Context context;
    private String notificationId, user_id;

    public NotificationListAdapter(List<NotificationListResult> notificationListResultList, Context context, String user_id) {
        this.notificationListResultList = notificationListResultList;
        this.context = context;
        this.user_id = user_id;
    }

    @NonNull
    @Override
    public NotificationListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.notofication_list, parent, false);
        return  new NotificationListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationListViewHolder holder, @SuppressLint("RecyclerView") int position) {

        notificationId = String.valueOf(notificationListResultList.get(position).getId());

        holder.n_title.setText( notificationListResultList.get(position).getTitle());
        holder.n_message.setText( notificationListResultList.get(position).getMessage());

        String Dtime = notificationListResultList.get(position).getNotificationTime();


        String[] parts = Dtime.split("T");
        String dateData = parts[0];
        String rawData = parts[1];

        String timeData = rawData.substring(0,8);

        Log.e("dt_data","dateData  "+dateData);
        Log.e("dt_data","timeData  "+timeData);



        holder.n_time_date.setText(dateData+" "+timeData);

        holder.n_single_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                notification_single_delete_api(notificationId , user_id,position);

            }
        });

    }

    private void notification_single_delete_api(String notificationId, String user_id, int position) {

        final ProgressDialog pd = new ProgressDialog(context);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();


        Call<SmFlaxibleModel> call = API_Client.getClient().notificaitonSingleDelete(user_id,notificationId);

        call.enqueue(new Callback<SmFlaxibleModel>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<SmFlaxibleModel> call, Response<SmFlaxibleModel> response) {
                pd.dismiss();


                try {
                    if (response.isSuccessful()) {
                        String message = response.body().getMessage();
                        String success = response.body().getSuccess();


                        if (success.equals("true") || success.equals("True")) {

                            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                            removeItem(position);

                        } else {
                            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                            pd.dismiss();
                        }


                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(context, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            switch (response.code()) {
                                case 400:
                                    Toast.makeText(context, "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(context, "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(context, "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(context, "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(context, "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    Toast.makeText(context, "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    Toast.makeText(context, "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(context, "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }

                        } catch (Exception e) {
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (
                        Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<SmFlaxibleModel> call, Throwable t) {
                Log.e("bhgyrrrthbh", String.valueOf(t));
                if (t instanceof IOException) {
                    Toast.makeText(context, "This is an actual network failure :( inform the user and possibly retry)" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(context, "Please Check your Internet Connection...." + t.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }
        });
    }

    public void removeItem(int position) {
        try {
            notificationListResultList.remove(position);
            notifyDataSetChanged();     // Update data in adapter.... Notify adapter for change data
        } catch (IndexOutOfBoundsException index) {
            index.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public int getItemCount() {
        return notificationListResultList.size();
    }
}
class NotificationListViewHolder extends RecyclerView.ViewHolder {

    AppCompatImageView n_single_delete;
    AppCompatTextView n_title, n_message, n_time_date;

    public NotificationListViewHolder(@NonNull View itemView) {
        super(itemView);

        n_time_date = itemView.findViewById(R.id.n_time_date);
        n_message = itemView.findViewById(R.id.n_message);
        n_title = itemView.findViewById(R.id.n_title);
        n_single_delete = itemView.findViewById(R.id.n_single_delete);


    }
}