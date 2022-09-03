package com.webnmobapps.livelyPencil.Fragment.BottonMenu;

import android.content.res.Resources;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import com.webnmobapps.livelyPencil.Adapter.SearchListAdapter;
import com.webnmobapps.livelyPencil.Model.SearchListModel;
import com.webnmobapps.livelyPencil.R;
import com.webnmobapps.livelyPencil.utility.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    List<SearchListModel> searchListModelList = new ArrayList<>();


    AppCompatEditText search_edit_text;
    RecyclerView rcv_search_list;
    SearchListAdapter searchListAdapter;

    private int numberOfColumns = 2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        intis(view);

        add_model_data();






        //rcv_search_list.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));


        RecyclerView.LayoutManager topLayoutManager = new GridLayoutManager(getActivity(), 2);
        rcv_search_list.setLayoutManager(topLayoutManager);
        rcv_search_list.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(8), true));
        rcv_search_list.setItemAnimator(new DefaultItemAnimator());

        SearchListAdapter searchListAdapter = new SearchListAdapter(getActivity(),searchListModelList);
        rcv_search_list.setAdapter(searchListAdapter);

        search_edit_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchListAdapter.getFilter().filter(search_edit_text.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view;
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    private void add_model_data() {
        SearchListModel searchListModel = new SearchListModel();
        searchListModel.setUserName("Lena Wilson");
        searchListModel.setStreamName("Drawing");
        searchListModel.setUserProfile(R.drawable.test_profile);
        searchListModelList.add(searchListModel);

        searchListModel = new SearchListModel();
        searchListModel.setUserName("John Deo");
        searchListModel.setStreamName("Car Lover");
        searchListModel.setUserProfile(R.drawable.demo);
        searchListModelList.add(searchListModel);

        searchListModel = new SearchListModel();
        searchListModel.setUserName("Lovely");
        searchListModel.setStreamName("Food Lover");
        searchListModel.setUserProfile(R.drawable.girl_with_cofee);
        searchListModelList.add(searchListModel);

        searchListModel = new SearchListModel();
        searchListModel.setUserName("Lena Wilson");
        searchListModel.setStreamName("Drawing");
        searchListModel.setUserProfile(R.drawable.test_profile);
        searchListModelList.add(searchListModel);

        searchListModel = new SearchListModel();
        searchListModel.setUserName("John Deo");
        searchListModel.setStreamName("Car Lover");
        searchListModel.setUserProfile(R.drawable.demo);
        searchListModelList.add(searchListModel);

        searchListModel = new SearchListModel();
        searchListModel.setUserName("Lovely");
        searchListModel.setStreamName("Food Lover");
        searchListModel.setUserProfile(R.drawable.girl_with_cofee);
        searchListModelList.add(searchListModel);
        searchListModel = new SearchListModel();
        searchListModel.setUserName("Lena Wilson");
        searchListModel.setStreamName("Drawing");
        searchListModel.setUserProfile(R.drawable.test_profile);
        searchListModelList.add(searchListModel);

        searchListModel = new SearchListModel();
        searchListModel.setUserName("John Deo");
        searchListModel.setStreamName("Car Lover");
        searchListModel.setUserProfile(R.drawable.demo);
        searchListModelList.add(searchListModel);

        searchListModel = new SearchListModel();
        searchListModel.setUserName("Lovely");
        searchListModel.setStreamName("Food Lover");
        searchListModel.setUserProfile(R.drawable.girl_with_cofee);
        searchListModelList.add(searchListModel);
        searchListModel = new SearchListModel();
        searchListModel.setUserName("Lena Wilson");
        searchListModel.setStreamName("Drawing");
        searchListModel.setUserProfile(R.drawable.test_profile);
        searchListModelList.add(searchListModel);

        searchListModel = new SearchListModel();
        searchListModel.setUserName("John Deo");
        searchListModel.setStreamName("Car Lover");
        searchListModel.setUserProfile(R.drawable.demo);
        searchListModelList.add(searchListModel);

        searchListModel = new SearchListModel();
        searchListModel.setUserName("Lovely");
        searchListModel.setStreamName("Food Lover");
        searchListModel.setUserProfile(R.drawable.girl_with_cofee);
        searchListModelList.add(searchListModel);

    }

    private void intis(View view) {
        rcv_search_list = view.findViewById(R.id.rcv_search_list);
        search_edit_text = view.findViewById(R.id.search_edit_text);
    }
}