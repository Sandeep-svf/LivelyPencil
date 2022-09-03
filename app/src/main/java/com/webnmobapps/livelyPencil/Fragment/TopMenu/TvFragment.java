package com.webnmobapps.livelyPencil.Fragment.TopMenu;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.webnmobapps.livelyPencil.Adapter.PageAdapter;
import com.webnmobapps.livelyPencil.Adapter.TVAdapter;
import com.webnmobapps.livelyPencil.Model.Record.TvListResult;
import com.webnmobapps.livelyPencil.Model.StreamPageModel;
import com.webnmobapps.livelyPencil.Model.TvListModel;
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


public class TvFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView tv_rcv_page;
    TVAdapter tvAdapter;
    private int numberOfColumns = 2;
    private String user_id;
    List<TvListResult> tvListResultList = new ArrayList<>();
    private String streamNameData, pageData, commentData, watchData, soloVideoData, soloTvLogoData;
    AppCompatTextView soloWatch, soloPage, soloComment, soloStreamName;
    AppCompatImageView solo_image;
    CircleImageView soloLogoImage;
    AppCompatEditText tv_searchText;

    public TvFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static TvFragment newInstance(String param1, String param2) {
        TvFragment fragment = new TvFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_tv, container, false);
        inits(view);

        tv_list_api();


        tv_searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                tvAdapter.getFilter().filter(tv_searchText.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        SharedPreferences sharedPreferences= getActivity().getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
        user_id=sharedPreferences.getString("UserID","");




        return  view;
    }

    private void tv_list_api() {

            final ProgressDialog pd = new ProgressDialog(getActivity());
            pd.setCancelable(false);
            pd.setMessage("loading...");
            pd.show();




            Call<TvListModel> call = API_Client.getClient().popularTvList(user_id);

            call.enqueue(new Callback<TvListModel>() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onResponse(Call<TvListModel> call, Response<TvListModel> response) {
                    pd.dismiss();


                    try {
                        if (response.isSuccessful()) {
                            String message = response.body().getMessage();
                            String success = response.body().getSuccess();

                            if (success.equals("true") || success.equals("True")) {

                                tvListResultList = response.body().getRecord();

                                if(!tvListResultList.isEmpty())
                                {
                                    streamNameData = tvListResultList.get(0).getStreamName();
                                    pageData = String.valueOf(tvListResultList.get(0).getPage());
                                    commentData = String.valueOf(tvListResultList.get(0).getComments());
                                    watchData = String.valueOf(tvListResultList.get(0).getView());
                                    soloVideoData = tvListResultList.get(0).getVideo();
                                    soloTvLogoData = tvListResultList.get(0).getTvlogo();


                                   // Log.e("soloVideoData",API_Client.BASE_VIDEO_URL+soloVideoData);


                                    soloWatch.setText(watchData);
                                    soloPage.setText(pageData);
                                    soloComment.setText(commentData);
                                    soloStreamName.setText(streamNameData);
                                    Glide.with(getActivity()).load(API_Client.BASE_VIDEO_URL+soloVideoData).placeholder(R.drawable.ic_launcher_background).into(solo_image);
                                    Glide.with(getActivity()).load(API_Client.BASE_LOGO_IMAGE+soloTvLogoData).placeholder(R.drawable.ic_launcher_background).into(soloLogoImage);

                                }else
                                {
                                    // Set Visibility Gone here if tv list is empty...
                                }



                                tv_rcv_page.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));
                                tvAdapter = new TVAdapter(getActivity(),tvListResultList);
                                tv_rcv_page.setAdapter(tvAdapter);


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
                public void onFailure(Call<TvListModel> call, Throwable t) {
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

        tv_searchText = view.findViewById(R.id.tv_searchText);
        soloStreamName = view.findViewById(R.id.soloStreamName);
        soloComment = view.findViewById(R.id.soloComment);
        soloPage = view.findViewById(R.id.soloPage);
        soloWatch = view.findViewById(R.id.soloWatch);
        solo_image = view.findViewById(R.id.solo_image);
        tv_rcv_page = view.findViewById(R.id.tv_rcv_page);
        soloLogoImage = view.findViewById(R.id.soloLogoImage);

    }
}