package com.webnmobapps.livelyPencil.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.webnmobapps.livelyPencil.Activity.Book.WebviewEditorActivity;
import com.webnmobapps.livelyPencil.ModelPython.BookListData;
import com.webnmobapps.livelyPencil.R;
import com.webnmobapps.livelyPencil.RetrofitApi.API_Client;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class BookListAdapter extends RecyclerView.Adapter<BookListViewHolder> {

    private Context context;
    private List<BookListData> bookListDataList =  new ArrayList<>();


    public BookListAdapter(Context context, List<BookListData> bookListDataList) {
        this.context = context;
        this.bookListDataList = bookListDataList;
    }

    @NonNull
    @Override
    public BookListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.book_list, parent, false);
        return  new BookListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookListViewHolder holder, int position) {

        holder.book_name.setText(bookListDataList.get(position).getBookName());
        holder.book_description.setText(bookListDataList.get(position).getBookDescriptions());
        Glide.with(context).load(API_Client.BASE_IMAGE+bookListDataList.get(position).getBookImage())
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.book_cover_image);


        holder.book_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bookId = String.valueOf(bookListDataList.get(position).getId());

                Intent intent = new Intent(context, WebviewEditorActivity.class);
                intent.putExtra("bookid",bookId);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return bookListDataList.size();
    }
}

class BookListViewHolder extends RecyclerView.ViewHolder {
    AppCompatTextView book_description,book_name;
    CircleImageView book_cover_image;
    ConstraintLayout book_layout;

    public BookListViewHolder(@NonNull View itemView) {
        super(itemView);
        book_name = itemView.findViewById(R.id.book_name);
        book_description = itemView.findViewById(R.id.book_description);
        book_cover_image = itemView.findViewById(R.id.book_cover_image);
        book_layout = itemView.findViewById(R.id.book_layout);
    }
}