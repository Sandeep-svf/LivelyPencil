package com.webnmobapps.livelyPencil.ModelPython;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookListData {

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