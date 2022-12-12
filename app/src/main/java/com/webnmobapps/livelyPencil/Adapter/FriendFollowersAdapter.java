package com.webnmobapps.livelyPencil.Adapter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.webnmobapps.livelyPencil.Model.friend_followers_model;
import com.webnmobapps.livelyPencil.ModelPython.CommonStatusMessageModelPython;
import com.webnmobapps.livelyPencil.ModelPython.LiveUserListDataPython;
import com.webnmobapps.livelyPencil.R;
import com.webnmobapps.livelyPencil.RetrofitApi.API_Client;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FriendFollowersAdapter extends RecyclerView.Adapter<FriendFollowersViewHolder> implements Filterable {

    private Context context;
     private static List<LiveUserListDataPython>  liveUserListDataPythonArrayList = new ArrayList<>();
    private static  List<LiveUserListDataPython>  backup;

    private String finalAccessToken;






    public FriendFollowersAdapter(Context context, List<LiveUserListDataPython> liveUserListDataPythonArrayList,String finalAccessToken) {
        this.context = context;
        this.liveUserListDataPythonArrayList = liveUserListDataPythonArrayList;
        backup = new ArrayList<>((liveUserListDataPythonArrayList));
        this.finalAccessToken = finalAccessToken;
    }

    @NonNull
    @Override
    public FriendFollowersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.friend_follwers_view_holder2, parent, false);
        return new FriendFollowersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendFollowersViewHolder holder, @SuppressLint("RecyclerView") int position) {

        try {
            if(liveUserListDataPythonArrayList.get(position).getFirstName() != null){
                holder.liveUserName.setText(liveUserListDataPythonArrayList.get(position).getFirstName()+" "+liveUserListDataPythonArrayList.get(position).getLastName());
            }else{
               // Toast.makeText(context, "API response null value at position: "+position, Toast.LENGTH_SHORT).show();
            }

            if(liveUserListDataPythonArrayList.get(position).getStreamTitle() != null){
                holder.liveUserStreamName.setText(String.valueOf(liveUserListDataPythonArrayList.get(position).getStreamTitle()));
            }else{
               // Toast.makeText(context, "API response null value at position: "+position, Toast.LENGTH_SHORT).show();
            }
            Glide.with(context).load(API_Client.BASE_IMAGE+liveUserListDataPythonArrayList.get(position).getImage()).
                    placeholder(R.drawable.ic_launcher_background).
                    into(holder.live_user_profile);

            if(liveUserListDataPythonArrayList.get(position).getFollowed()){
                holder.live_add_follow_button.setText("Follow");
            }else{
                holder.live_add_follow_button.setText("Followed");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        holder.live_add_follow_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userId = String.valueOf(liveUserListDataPythonArrayList.get(position).getId());
                Boolean followFlag = liveUserListDataPythonArrayList.get(position).getFollowed();

                follow_unfollow_api(userId,followFlag,position,holder);
                Log.e("test_id","userId is :"+userId);
            }
        });


    }



    private void follow_unfollow_api(String userId, Boolean followFlag, int position,FriendFollowersViewHolder holder) {


            /*final ProgressDialog pd = new ProgressDialog(context);
            pd.setCancelable(false);
            pd.setMessage("loading...");
            pd.show();*/


            Call<CommonStatusMessageModelPython> call = API_Client.getClient().FOLLOW_UFOLLOW_COMMON_STATUS_MESSAGE_MODEL_PYTHON_CALL
                    ("addfollower/"+userId+"/",finalAccessToken);

            call.enqueue(new Callback<CommonStatusMessageModelPython>() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onResponse(Call<CommonStatusMessageModelPython> call, Response<CommonStatusMessageModelPython> response) {
                  //  pd.dismiss();


                    try {
                        if (response.isSuccessful()) {
                            String message = response.body().getMessage();
                            String success = response.body().getStatus();


                            if (success.equals("true") || success.equals("True")) {

                              //  Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                                if(followFlag){

                                    holder.live_add_follow_button.setText("Followed");

                                    liveUserListDataPythonArrayList.get(position).setFollowed(false);

                                }else{

                                    holder.live_add_follow_button.setText("Follow");
                                    liveUserListDataPythonArrayList.get(position).setFollowed(true);
                                }

                              //  pd.dismiss();


                            } else {
                                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                             //   pd.dismiss();
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
                     //   pd.dismiss();
                    } else {
                        Log.e("conversion issue", t.getMessage());
                        Toast.makeText(context, "Please Check your Internet Connection...." + t.getMessage(), Toast.LENGTH_SHORT).show();
                     //   pd.dismiss();
                    }
                }
            });
        }

    @Override
    public int getItemCount() {
        return liveUserListDataPythonArrayList.size();
    }

    @Override
    public Filter getFilter() {

        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence keyword) {
                ArrayList<LiveUserListDataPython> filtereddata=new ArrayList<>();


                if(keyword.toString().isEmpty())
                    filtereddata.addAll(backup);
                else
                {
                    for(LiveUserListDataPython obj : backup)
                    {
                        if(obj.getStreamTitle().toString().toLowerCase().contains(keyword.toString().toLowerCase()) || obj.getFirstName().toString().toLowerCase().contains(keyword.toString().toLowerCase()))
                            filtereddata.add(obj);
                    }
                }

                FilterResults results=new FilterResults();
                results.values=filtereddata;
                return results;
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                liveUserListDataPythonArrayList.clear();
                liveUserListDataPythonArrayList.addAll((ArrayList<LiveUserListDataPython>)results.values);
                notifyDataSetChanged();
            }
        };

        return filter;
    }
}
class FriendFollowersViewHolder extends RecyclerView.ViewHolder {

    CircleImageView live_user_profile;
    AppCompatTextView liveUserName, liveUserStreamName;
    AppCompatButton live_add_follow_button;

    public FriendFollowersViewHolder(@NonNull View itemView) {
        super(itemView);
        live_user_profile = itemView.findViewById(R.id.live_user_profile);
        liveUserName = itemView.findViewById(R.id.liveUserName);
        liveUserStreamName = itemView.findViewById(R.id.liveUserStreamName);
        live_add_follow_button = itemView.findViewById(R.id.live_add_follow_button);
    }
}