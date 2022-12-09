package com.webnmobapps.livelyPencil.Adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.webnmobapps.livelyPencil.Activity.Book.CreateBookActivity;
import com.webnmobapps.livelyPencil.Activity.Book.EditBookActivity;
import com.webnmobapps.livelyPencil.Activity.Book.WebviewEditorActivity;
import com.webnmobapps.livelyPencil.Activity.Login.LoginJoinusActivity;
import com.webnmobapps.livelyPencil.ModelPython.BookListData;
import com.webnmobapps.livelyPencil.ModelPython.CommonStatusMessageModelPython;
import com.webnmobapps.livelyPencil.R;
import com.webnmobapps.livelyPencil.RetrofitApi.API_Client;
import com.webnmobapps.livelyPencil.newmodel.CustomBookListModel;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookListAdapter extends RecyclerView.Adapter<BookListViewHolder> {

    private Context context;
    private List<BookListData> bookListDataList =  new ArrayList<>();
    private String finalAccessToken;


    public BookListAdapter(Context context, List<BookListData> bookListDataList, String finalAccessToken) {
        this.context = context;
        this.bookListDataList = bookListDataList;
        this.finalAccessToken = finalAccessToken;
    }

    @NonNull
    @Override
    public BookListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.book_list2, parent, false);
        return  new BookListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookListViewHolder holder, @SuppressLint("RecyclerView") int position) {


        if(position==0) holder.delete_book_text.setVisibility(View.GONE);

        holder.book_name.setText(bookListDataList.get(position).getBookName());
        holder.bhu3.setText("P#"+bookListDataList.get(position).getTotal_page());
        holder.book_description.setText(bookListDataList.get(position).getBookDescriptions());
        holder.created_at.setText("created at "+bookListDataList.get(position).getCreated_at()+" / "+"total page "+bookListDataList.get(position).getTotal_page());
        Glide.with(context).load(API_Client.BASE_IMAGE+bookListDataList.get(position).getBookImage())
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.book_cover_image);



        holder.edit_book_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String bookName = bookListDataList.get(position).getBookName();
                String bookDescription = bookListDataList.get(position).getBookDescriptions();
                String image = bookListDataList.get(position).getBookImage();
                String bookId = String.valueOf(bookListDataList.get(position).getId());

                Intent intent = new Intent(context, EditBookActivity.class);
                intent.putExtra("bookName",bookName);
                intent.putExtra("bookDescription",bookDescription);
                intent.putExtra("bookCoverImage",image);
                intent.putExtra("bookId",bookId);
                context.startActivity(intent);
            }
        });

        holder.delete_book_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String bookId = String.valueOf(bookListDataList.get(position).getId());

                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.delete_book_dialog);
                LinearLayout noDialogLogout = dialog.findViewById(R.id.noDialogLogout);
                LinearLayout yesDialogLogout = dialog.findViewById(R.id.yesDialogLogout);


                dialog.show();
                Window window = dialog.getWindow();
                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                yesDialogLogout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        delete_book_api(bookId,position);
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


        holder.book_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bookId = String.valueOf(bookListDataList.get(position).getId());

                Intent intent = new Intent(context, WebviewEditorActivity.class);
                intent.putExtra("bookid",bookId);
                intent.putExtra("key","2");
                context.startActivity(intent);
            }
        });

    }

    private void delete_book_api(String bookId, int position) {

            final ProgressDialog pd = new ProgressDialog(context);
            pd.setCancelable(false);
            pd.setMessage("loading...");
            pd.show();


            Call<CommonStatusMessageModelPython> call = API_Client.getClient().DELETE_BOOK_COMMON_STATUS_MESSAGE_MODEL_PYTHON_CALL
                    ("my-book-detail/"+bookId+"/",finalAccessToken);

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
            bookListDataList.remove(position);
            notifyDataSetChanged();     // Update data in adapter.... Notify adapter for change data
        } catch (IndexOutOfBoundsException index) {
            index.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return bookListDataList.size();
    }
}

class BookListViewHolder extends RecyclerView.ViewHolder {
    AppCompatTextView book_description,book_name,delete_book_text,bhu3;
    CircleImageView book_cover_image;
    AppCompatImageView book_layout;
    AppCompatTextView created_at,edit_book_layout;

    public BookListViewHolder(@NonNull View itemView) {
        super(itemView);
        edit_book_layout = itemView.findViewById(R.id.edit_book_layout);
        created_at = itemView.findViewById(R.id.created_at);
        bhu3 = itemView.findViewById(R.id.bhu3);
        delete_book_text = itemView.findViewById(R.id.delete_book_text);
        book_name = itemView.findViewById(R.id.book_name);
        book_description = itemView.findViewById(R.id.book_description);
        book_cover_image = itemView.findViewById(R.id.book_cover_image);
        book_layout = itemView.findViewById(R.id.bhu1);
    }
}