package com.webnmobapps.livelyPencil.Fragment.TopMenu;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.webnmobapps.livelyPencil.Adapter.LiveUserAdapter;
import com.webnmobapps.livelyPencil.Adapter.NotificationListAdapter;
import com.webnmobapps.livelyPencil.Adapter.PageAdapter;
import com.webnmobapps.livelyPencil.Model.NotificationListModel;
import com.webnmobapps.livelyPencil.Model.Record.StreamPageResult;
import com.webnmobapps.livelyPencil.Model.StreamPageModel;
import com.webnmobapps.livelyPencil.ModelPython.PostListDataPython;
import com.webnmobapps.livelyPencil.ModelPython.PostListModelPython;
import com.webnmobapps.livelyPencil.R;
import com.webnmobapps.livelyPencil.RetrofitApi.API_Client;
import com.webnmobapps.livelyPencil.utility.StaticKey;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PageFragment extends Fragment {



    // TODO: Rename and change types of parameters

    private RecyclerView rcv_page;
    PageAdapter pageAdapter;
    int numberOfColumns = 2;
    private String user_id;
    List<PostListDataPython> postListDataPythonArrayList = new ArrayList<>();
    AppCompatEditText searchText;
    private String accessToken, finalAccessToken;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page, container, false);

        inits(view);


       /* searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                pageAdapter.getFilter().filter(searchText.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });*/

        SharedPreferences sharedPreferences= getActivity().getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
       // user_id=sharedPreferences.getString("UserID","");
        accessToken=sharedPreferences.getString("accessToken","");
        finalAccessToken = StaticKey.prefixTokem+accessToken;

        stream_page_list_api();




       /* GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity());
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);

        rcv_page.setLayoutManager(gridLayoutManager);
        rcv_page.setAdapter(pageAdapter);*/

        return  view;
    }



    private void stream_page_list_api() {
        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();




        Call<PostListModelPython> call = API_Client.getClient().POST_LIST_MODEL_PYTHON_CALL(finalAccessToken);

        call.enqueue(new Callback<PostListModelPython>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<PostListModelPython> call, Response<PostListModelPython> response) {
                pd.dismiss();


                try {
                    if (response.isSuccessful()) {
                        String message = response.body().getMessage();
                        String success = response.body().getStatus();

                        if (success.equals("true") || success.equals("True")) {

                            postListDataPythonArrayList = response.body().getData();
                            rcv_page.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));
                            pageAdapter = new PageAdapter(getActivity(),postListDataPythonArrayList);
                            rcv_page.setAdapter(pageAdapter);


                        } else {
                            Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                            pd.dismiss();
                        }


                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(getActivity(), jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            switch (response.code()) {
                                case 400:
                                    Toast.makeText(getActivity(), "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(getActivity(), "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(getActivity(), "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(getActivity(), "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(getActivity(), "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    Toast.makeText(getActivity(), "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    Toast.makeText(getActivity(), "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(getActivity(), "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }

                        } catch (Exception e) {
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (
                        Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<PostListModelPython> call, Throwable t) {
                Log.e("bhgyrrrthbh", String.valueOf(t));
                if (t instanceof IOException) {
                    Toast.makeText(getActivity(), "This is an actual network failure :( inform the user and possibly retry)" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(getActivity(), "Please Check your Internet Connection...." + t.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }
        });

    }

    private void inits(View view) {
        searchText = view.findViewById(R.id.searchText);
        rcv_page = view.findViewById(R.id.rcv_page);
    }
}