package org.atctech.sms_teacher.ApiRequest;

import org.atctech.sms_teacher.model.ClassNameID;
import org.atctech.sms_teacher.model.TeacherDetails;
import org.atctech.sms_teacher.model.TeacherProfile;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by Jacky on 2/28/2018.
 */

public interface ApiRequest {


    @GET("all_teacher.php")
    Call<List<TeacherDetails>> getAllTeacher();


    @Multipart
    @POST("live_results.php")
    Call<ResponseBody> PdfUploadFunction (@Part MultipartBody.Part file,
                                          @Part("subject") RequestBody subject,
                                          @Part("class_id") RequestBody class_id);


    @GET("class_id.php")
    Call<List<ClassNameID>> getAllClassName();


    @FormUrlEncoded
    @POST("login.php")
    Call<TeacherProfile> login(@Field("username") String admin, @Field("password") String password);


}
