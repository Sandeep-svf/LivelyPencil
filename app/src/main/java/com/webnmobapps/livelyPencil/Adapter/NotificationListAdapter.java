package com.webnmobapps.livelyPencil.Adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.webnmobapps.livelyPencil.Model.SmFlaxibleModel;
import com.webnmobapps.livelyPencil.ModelPython.CommonStatusMessageModelPython;
import com.webnmobapps.livelyPencil.ModelPython.NotificationListPython;
import com.webnmobapps.livelyPencil.R;
import com.webnmobapps.livelyPencil.RetrofitApi.API_Client;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationListAdapter extends RecyclerView.Adapter<NotificationListViewHolder> {

    List<NotificationListPython> notificationListPythonList;
    Context context;
    private String notificationId, user_id,finalAccessToken;

    public NotificationListAdapter(String finalAccessToken,List<NotificationListPython> notificationListPythonList, Context context) {
        this.notificationListPythonList = notificationListPythonList;
        this.context = context;
        this.finalAccessToken = finalAccessToken;

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

        notificationId = String.valueOf(notificationListPythonList.get(position).getId());

        holder.n_title.setText( notificationListPythonList.get(position).getTitle());
        holder.n_message.setText( notificationListPythonList.get(position).getMessage());

        String Dtime = notificationListPythonList.get(position).getCreatedAt();


       /* String[] parts = Dtime.split("T");
        String dateData = parts[0];
        String rawData = parts[1];

        String timeData = rawData.substring(0,8);

        Log.e("dt_data","dateData  "+dateData);
        Log.e("dt_data","timeData  "+timeData);*/



        holder.n_time_date.setText(Dtime);

        holder.n_single_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // add pop up...
                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.logout_dialog3);
                LinearLayout noDialogLogout = dialog.findViewById(R.id.noDialogLogout);
                LinearLayout yesDialogLogout = dialog.findViewById(R.id.yesDialogLogout);


                dialog.show();
                Window window = dialog.getWindow();
                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);


                yesDialogLogout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        notification_single_delete_api(notificationId ,position);
                        dialog.dismiss();
                    }
                });


                noDialogLogout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });




            }
        });

    }

    private void notification_single_delete_api(String notificationId, int position) {

        final ProgressDialog pd = new ProgressDialog(context);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();


        Call<CommonStatusMessageModelPython> call = API_Client.getClient().notificaitonSingleDelete("removenotification/"+notificationId+"/",finalAccessToken);

        call.enqueue(new Callback<CommonStatusMessageModelPython>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<CommonStatusMessageModelPython> call, Response<CommonStatusMessageModelPython> response) {
                pd.dismiss();


                try {
                    if (response.isSuccessful()) {
                        String message = response.body().getMessage();
                        String success = response.body().getStatus();


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
            public void onFailure(Call<CommonStatusMessageModelPython> call, Throwable t) {
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
            notificationListPythonList.remove(position);
            notifyDataSetChanged();     // Update data in adapter.... Notify adapter for change data
        } catch (IndexOutOfBoundsException index) {
            index.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public int getItemCount() {
        return notificationListPythonList.size();
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