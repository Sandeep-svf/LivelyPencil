package com.webnmobapps.livelyPencil.Adapter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.webnmobapps.livelyPencil.Activity.Interface.RefreshInterface;
import com.webnmobapps.livelyPencil.Activity.RunWizard.PopularListActivity;
import com.webnmobapps.livelyPencil.Activity.RunWizard.PopularListRunWizardModel;
import com.webnmobapps.livelyPencil.Activity.StaticList.FollowersModel;
import com.webnmobapps.livelyPencil.Model.PopularListModel;
import com.webnmobapps.livelyPencil.Model.Record.PopularListResult;
import com.webnmobapps.livelyPencil.Model.SmFlaxibleModel;
import com.webnmobapps.livelyPencil.ModelPython.PopularListModelDataNew;
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

public class PopularListRunWizardAdapter extends RecyclerView.Adapter<PopularListViewHolder> {


    List<FollowersModel2> followersModelList = new ArrayList<>();
     List<PopularListModelDataNew> liveUserModelList = new ArrayList<>();
     Context context;
    private String user_id;
    private String status="0";
    RefreshInterface refreshInterface;


    public interface  Get_Position_Eye_Function
    {
        void page_details(String id, Integer userId);
    }
    public  Get_Position_Eye_Function get_position_eye_function;

    public void setGet_position_itemDrawings(PopularListRunWizardAdapter.Get_Position_Eye_Function get_position_eye_function) {
        this.get_position_eye_function = get_position_eye_function;
    }


    public PopularListRunWizardAdapter(List<PopularListModelDataNew> liveUserModelList, Context context) {
        this.liveUserModelList = liveUserModelList;
        this.context = context;

    }

    @NonNull
    @Override
    public PopularListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.popular_list_run_wizard_layout,parent,false);
        return new PopularListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularListViewHolder holder, @SuppressLint("RecyclerView") int position) {
   /*     holder.bg_image.setImageResource(Integer.parseInt();
        holder.user_profile.setImageResource(liveUserModelList.get(position).getImage());*/




        followersModelList.add(new FollowersModel2(1,position));

        String followerStatus2 = String.valueOf(followersModelList.get(position).getFollowersStatus());
        if(followerStatus2.equals("1"))
        {
            holder.fu_icon.setImageResource(R.drawable.check_mark);
            holder.f_unf_text.setText(R.string.follow);
        }else
        {
            holder.fu_icon.setImageResource(R.drawable.unfollow_icon);
            holder.f_unf_text.setText(R.string.unfollow);
        }


        Glide.with(context).load(API_Client.BASE_IMAGE+liveUserModelList.get(position).getImage()).placeholder(R.drawable.ic_launcher_background).into(holder.user_profile);
        Glide.with(context).load(API_Client.BASE_IMAGE+liveUserModelList.get(position).getStreamCoverImage()).placeholder(R.drawable.ic_launcher_background).into(holder.bg_image);
        holder.name.setText(liveUserModelList.get(position).getFirstName());
        holder.surname.setText(liveUserModelList.get(position).getLastName());
        holder.t1.setText(liveUserModelList.get(position).getStreamTitle());
        holder.t2.setText("Total page # "+liveUserModelList.get(position).getTotal_book());
       // holder.t2.setText("Total page # "+"19");
        holder.t3.setText("Last Stream: "+liveUserModelList.get(position).getLast_stream());
       // holder.t3.setText("Last Stream: "+"22:02:2022");


        holder.follow_unfollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Integer id = liveUserModelList.get(position).getId();

                get_position_eye_function.page_details(String.valueOf(position),id);

                Log.e("test_model_value","Value before : "+followersModelList.get(position).getFollowersStatus());
               // String listUserId = String.valueOf(liveUserModelList.get(position).getId());
              //  String followerStatus = String.valueOf(liveUserModelList.get(position).getFollowerStatus());
               // follow_unfollow_api(listUserId, holder,followerStatus2,position);
                if(followerStatus.equals("1"))
                {
                    holder.fu_icon.setImageResource(R.drawable.check_mark);
                }else
                {
                    holder.fu_icon.setImageResource(R.drawable.unfollow_icon);
                }


            }
        });
    }

    private void follow_unfollow_api(String listUserId, PopularListViewHolder holder, String followerStatus, int position) {
        // folloe unfollow api code..........



            String status;
            if(followerStatus.equals("1"))
            {
                status="0";
            }else
            {
                status="1";
            }

            Call<SmFlaxibleModel> call = API_Client.getClient2().follow_unfollow(listUserId,"47",status);

            call.enqueue(new Callback<SmFlaxibleModel>() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onResponse(Call<SmFlaxibleModel> call, Response<SmFlaxibleModel> response) {


                    try {
                        if (response.isSuccessful()) {
                            String message = response.body().getMessage();
                            String success = response.body().getSuccess();

                            if (success.equals("true") || success.equals("True")) {



                               // update_list(position, status);
                                //   refreshInterface.refresh();
                               // alert_dialog_message(message);
                               // Toast.makeText(context, message, Toast.LENGTH_LONG).show();

                            } else {
                                alert_dialog_message(message);
                               // Toast.makeText(context, message, Toast.LENGTH_LONG).show();

                            }

                        } else {
                            try {
                                JSONObject jObjError = new JSONObject(response.errorBody().string());
                                Toast.makeText(context, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                                switch (response.code()) {
                                    case 400:
                                        alert_dialog_message("400");
                                        //  Toast.makeText(getApplicationContext(), "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 401:
                                        alert_dialog_message("401");
                                        // Toast.makeText(getApplicationContext(), "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 404:
                                        alert_dialog_message("404");
                                        //Toast.makeText(getApplicationContext(), "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 500:
                                        alert_dialog_message("500");
                                        //Toast.makeText(getApplicationContext(), "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 503:
                                        alert_dialog_message("503");
                                        // Toast.makeText(getApplicationContext(), "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 504:
                                        alert_dialog_message("504");
                                        //  Toast.makeText(getApplicationContext(), "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 511:
                                        alert_dialog_message("511");
                                        // Toast.makeText(getApplicationContext(), "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                        break;
                                    default:
                                        alert_dialog_message("default");
                                        //Toast.makeText(getApplicationContext(), "unknown error", Toast.LENGTH_SHORT).show();
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

                    } else {
                        Log.e("conversion issue", t.getMessage());
                        Toast.makeText(context, "Please Check your Internet Connection...." + t.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }
 /////////////////////////////////////


 /*   private void update_list(int position, String status2) {

            try {
               // liveUserModelList.get(position).setTotalFollowers(Integer.valueOf(5));

                followersModelList.get(position).setFollowersStatus(Integer.parseInt(status2));

                notifyDataSetChanged();      // Update data in adapter.... Notify adapter for change data
                Log.e("test_model_value","Value after : "+followersModelList.get(position).getFollowersStatus());

            } catch (IndexOutOfBoundsException index) {
                index.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
    }*/


    private void alert_dialog_message(String value) {
        AlertDialog dialogs;

        //final LayoutInflater inflater = context.getLayoutInflater();
        LayoutInflater inflater = LayoutInflater.from(context);
        View alertLayout = inflater.inflate(R.layout.test_dialog_xml_otp, null, false);

        //View alertLayout = inflater.inflate(R.layout.test_dialog_xml_otp, null);

        final ImageView close_dialog = alertLayout.findViewById(R.id.close_dialog);
        final TextView error_message = alertLayout.findViewById(R.id.error_message);


        if(value.equals("400"))
        {
            error_message.setText(context.getResources().getString(R.string.case_4_0_0));
        }else if(value.equals("401"))
        {
            error_message.setText(context.getResources().getString(R.string.case_4_0_1));
        }else if(value.equals("404"))
        {
            error_message.setText(context.getResources().getString(R.string.case_4_0_4));
        }else if(value.equals("500"))
        {
            error_message.setText(context.getResources().getString(R.string.case_5_0_0));
        }else if(value.equals("503"))
        {
            error_message.setText(context.getResources().getString(R.string.case_5_0_3));
        }else if(value.equals("504"))
        {
            error_message.setText(context.getResources().getString(R.string.case_5_0_4));
        }else if(value.equals("511"))
        {
            error_message.setText(context.getResources().getString(R.string.case_5_1_1));
        }else if(value.equals("504"))
        {
            error_message.setText(context.getResources().getString(R.string.case_5_0_4));
        }else if(value.equals("default"))
        {
            error_message.setText(context.getResources().getString(R.string.default_api_error));
        }else if(value.equals("400"))
        {
            error_message.setText(context.getResources().getString(R.string.case_4_0_0));
        }else if(value.equals("401"))
        {
            error_message.setText(context.getResources().getString(R.string.case_4_0_1));
        }else if(value.equals("404"))
        {
            error_message.setText(context.getResources().getString(R.string.case_4_0_4));
        }else if(value.equals("500"))
        {
            error_message.setText(context.getResources().getString(R.string.case_5_0_0));
        }else if(value.equals("503"))
        {
            error_message.setText(context.getResources().getString(R.string.case_5_0_3));
        }else if(value.equals("504"))
        {
            error_message.setText(context.getResources().getString(R.string.case_5_0_4));
        }else if(value.equals("511"))
        {
            error_message.setText(context.getResources().getString(R.string.case_5_1_1));
        }else if(value.equals("504"))
        {
            error_message.setText(context.getResources().getString(R.string.case_5_0_4));
        }else if(value.equals("default"))
        {
            error_message.setText(context.getResources().getString(R.string.default_api_error));
        }else
        {
            error_message.setText(value);
        }

        final AlertDialog.Builder alert = new AlertDialog.Builder(context);

        alert.setView(alertLayout);
        alert.setCancelable(false);

        dialogs = alert.create();
        dialogs.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogs.show();
        dialogs.setCanceledOnTouchOutside(true);


        close_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialogs.dismiss();
            }
        });
    }


    @Override
    public int getItemCount() {
        return liveUserModelList.size();
    }
}
class PopularListViewHolder extends RecyclerView.ViewHolder {

    AppCompatImageView bg_image, fu_icon;
    CircleImageView user_profile;
    AppCompatTextView name, surname, t1, t2, t3, f_unf_text;
    ConstraintLayout follow_unfollow;

    public PopularListViewHolder(@NonNull View itemView) {
        super(itemView);

        bg_image = itemView.findViewById(R.id.bg_image);
        user_profile = itemView.findViewById(R.id.user_profile);
        name = itemView.findViewById(R.id.name);
        surname = itemView.findViewById(R.id.surname);
        t1 = itemView.findViewById(R.id.t1);
        t2 = itemView.findViewById(R.id.t2);
        t3 = itemView.findViewById(R.id.t3);
        follow_unfollow = itemView.findViewById(R.id.follow_unfollow);
        fu_icon = itemView.findViewById(R.id.fu_icon);
        f_unf_text = itemView.findViewById(R.id.f_unf_text);
    }
}