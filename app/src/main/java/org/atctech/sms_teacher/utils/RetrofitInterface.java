package org.atctech.sms_teacher.utils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Jacky on 4/16/2018.
 */

public class RetrofitInterface {
    private static final String BaseUrl="https://esrattanisha85.000webhostapp.com/sms/api/teacher/";

    private static Retrofit retrofit;

    public static Retrofit getApiClient(){
        if(retrofit==null){
            retrofit=new Retrofit.Builder()
                    .baseUrl(BaseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(new OkHttpClient().newBuilder()
                            .connectTimeout(20, TimeUnit.SECONDS)
                            .readTimeout(10, TimeUnit.SECONDS)
                            .writeTimeout(10, TimeUnit.SECONDS)
                            .build())
                    .build();
        }
        return retrofit;
    }
}
