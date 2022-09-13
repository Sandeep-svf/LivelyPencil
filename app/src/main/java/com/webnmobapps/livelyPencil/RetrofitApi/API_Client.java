package com.webnmobapps.livelyPencil.RetrofitApi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class API_Client {

       /* public static final String BASE_URL = "https://mobileandwebsitedevelopment.com/LivelyPencil/api/";
        public static final String BASE_IMAGE = "https://mobileandwebsitedevelopment.com/LivelyPencil/public/upload/User/";
        public static final String BASE_COVER_IMAGE = "https://mobileandwebsitedevelopment.com/LivelyPencil/public/upload/User/Cover/";
        public  static final String BASE_LOGO_IMAGE = "https://mobileandwebsitedevelopment.com/LivelyPencil/public/upload/avatars/";
        public static final String BASE_VIDEO_URL = "https://mobileandwebsitedevelopment.com/LivelyPencil/public/video/"; */

       // public static final String BASE_URL = "http://192.168.1.130:8001/api/";
        public static final String BASE_URL = "http://ec2-3-91-178-48.compute-1.amazonaws.com:8001/api/";
        public static final String BASE_IMAGE = "https://livelypencil.com/public/upload/User/";
        public static final String BASE_COVER_IMAGE = "https://livelypencil.com/public/upload/User/Cover/";
        public  static final String BASE_LOGO_IMAGE = "https://livelypencil.com/public/upload/avatars/";
        public static final String BASE_VIDEO_URL = "https://livelypencil.com/public/video/";
        public static final String TEMP_BASE_URL = "https://mobileandwebsitedevelopment.com/LivelyPencil/api/";

        private static Retrofit retrofit = null;
        private static Api api;
        static Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        public static Api getClient() {
            if (api == null) {
                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();
                Api api = retrofit.create(Api.class);
                return api;
            } else
                return api;
        }

    public static Api getClient2() {
        if (api == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(TEMP_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            Api api = retrofit.create(Api.class);
            return api;
        } else
            return api;
    }

//    private static Retrofit retrofit = null;
//    public static Retrofit getClient()
//    {
//        if (retrofit==null)
//        {
//            Gson gson = new GsonBuilder()
//                    .setLenient()
//                    .create();
//            retrofit = new Retrofit.Builder()
//                    .client(okHttpClient)
//                    .baseUrl(ServiceUrlList.RootIpUrl)
//                    .addConverterFactory(GsonConverterFactory.create(gson))
//                    .build();
//        }
//        return retrofit;
//    }
    }


