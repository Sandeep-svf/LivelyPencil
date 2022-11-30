package com.webnmobapps.livelyPencil.ModelPython;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookListData {



    @SerializedName("total_page")
    @Expose
    public Integer total_page;

@SerializedName("id")
@Expose
public Integer id;
@SerializedName("book_name")
@Expose
public String bookName;
@SerializedName("book_descriptions")
@Expose
public String bookDescriptions;
@SerializedName("book_image")
@Expose
public String bookImage;

    public Integer getTotal_page() {
        return total_page;
    }

    public Integer getId() {
        return id;
    }

    public String getBookName() {
        return bookName;
    }

    public String getBookDescriptions() {
        return bookDescriptions;
    }

    public String getBookImage() {
        return bookImage;
    }
}