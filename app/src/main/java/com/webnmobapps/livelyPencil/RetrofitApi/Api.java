package com.webnmobapps.livelyPencil.RetrofitApi;
import com.webnmobapps.livelyPencil.Model.BusinessModel;
import com.webnmobapps.livelyPencil.Model.CheckUserModel;
import com.webnmobapps.livelyPencil.Model.CityListModel;
import com.webnmobapps.livelyPencil.Model.ContactInformationModel;
import com.webnmobapps.livelyPencil.Model.CountryListModel;
import com.webnmobapps.livelyPencil.Model.EditContactSettingModel;
import com.webnmobapps.livelyPencil.Model.EditRadioSettingsModel;
import com.webnmobapps.livelyPencil.Model.EditShareSettingsModel;
import com.webnmobapps.livelyPencil.Model.EditStreamSettingModel;
import com.webnmobapps.livelyPencil.Model.EditTVSettingModel;
import com.webnmobapps.livelyPencil.Model.Edit_Personal_Information_Model;
import com.webnmobapps.livelyPencil.Model.EducationModel;
import com.webnmobapps.livelyPencil.Model.FollowerProfileModel;
import com.webnmobapps.livelyPencil.Model.FollowersModel;
import com.webnmobapps.livelyPencil.Model.GroupAgeModel;
import com.webnmobapps.livelyPencil.Model.IntrestListModel;
import com.webnmobapps.livelyPencil.Model.NotificationCountModel;
import com.webnmobapps.livelyPencil.Model.NotificationListModel;
import com.webnmobapps.livelyPencil.Model.PPSettingsModel;
import com.webnmobapps.livelyPencil.Model.PersonalInformationSettingWizardModel;
import com.webnmobapps.livelyPencil.Model.Personal_Information_Settings_Model;
import com.webnmobapps.livelyPencil.Model.PersonalizationPrivacyModel;
import com.webnmobapps.livelyPencil.Model.PopularListModel;
import com.webnmobapps.livelyPencil.Model.RadioListModel;
import com.webnmobapps.livelyPencil.Model.RadioSettingsModel;
import com.webnmobapps.livelyPencil.Model.RegisterModel;
import com.webnmobapps.livelyPencil.Model.ShareSettingDetailsModel;
import com.webnmobapps.livelyPencil.Model.SmFlaxibleModel;
import com.webnmobapps.livelyPencil.Model.StreamPageModel;
import com.webnmobapps.livelyPencil.Model.StreamPageSettingsModel;
import com.webnmobapps.livelyPencil.Model.StreamSettingModel;
import com.webnmobapps.livelyPencil.Model.TvListModel;
import com.webnmobapps.livelyPencil.Model.TvSettingsModel;
import com.webnmobapps.livelyPencil.Model.UserWallListModel;
import com.webnmobapps.livelyPencil.ModelPython.BookListModel;
import com.webnmobapps.livelyPencil.ModelPython.CommonStatusMessageModelPython;
import com.webnmobapps.livelyPencil.ModelPython.FFModel;
import com.webnmobapps.livelyPencil.ModelPython.FollowersListModelPython;
import com.webnmobapps.livelyPencil.ModelPython.InterestingModelPython;
import com.webnmobapps.livelyPencil.ModelPython.LiveUserListModelPython;
import com.webnmobapps.livelyPencil.ModelPython.LoginModelPython;
import com.webnmobapps.livelyPencil.ModelPython.MyFollowersData;
import com.webnmobapps.livelyPencil.ModelPython.MyFollowersModel;
import com.webnmobapps.livelyPencil.ModelPython.NotificationSettingModel;
import com.webnmobapps.livelyPencil.ModelPython.NotificationModelPython;
import com.webnmobapps.livelyPencil.ModelPython.PostListModelPython;
import com.webnmobapps.livelyPencil.ModelPython.RoleSettingModel;
import com.webnmobapps.livelyPencil.ModelPython.SettingModel;
import com.webnmobapps.livelyPencil.ModelPython.UserProfileModel;
import com.webnmobapps.livelyPencil.ModelPython.UserProfileModelPython;
import com.webnmobapps.livelyPencil.newmodel.StreamModel;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Url;

public interface Api {


    @POST("Interesting")
    Call<IntrestListModel> IntrestList();


    @GET("interestings/")
    Call<InterestingModelPython> IntrestList2();

    @POST("getAge")
    Call<GroupAgeModel> groupAge();


    @FormUrlEncoded
    @POST("login/")
    Call<LoginModelPython> login(
            @Field("email") String email,
            @Field("password") String password,
            @Field("device_token") String device_toke);

    @FormUrlEncoded
    @POST("check-user")
    Call<CheckUserModel> checkUser(@Field("username") String username);


    @Multipart
    @POST("registation-api")
    Call<RegisterModel> register(
            @Part("device_token") RequestBody device_token,
            @Part("firstname") RequestBody firstname,
            @Part("lastname") RequestBody lastname,
            @Part("password") RequestBody password,
            @Part("username") RequestBody username,
            @Part("streamtitle") RequestBody streamtitle,
            @Part("role") RequestBody role,
            @Part("interesting") RequestBody interesting,
            @Part("country_code") RequestBody country_code,
            @Part MultipartBody.Part streamcoverimage,
            @Part MultipartBody.Part image);


    @Multipart
    @POST("register/")
    Call<LoginModelPython> register2(@Part("interesting") List<Integer> interesting,
                                     @Part("first_name") RequestBody first_name,
                                         @Part("last_name") RequestBody last_name,
                                     @Part("email") RequestBody email,
                                     @Part("stream_title") RequestBody stream_title,
                                     @Part("role") RequestBody role,
                                     @Part("password") RequestBody password,
                                     @Part MultipartBody.Part stream_cover_image,
                                     @Part MultipartBody.Part image);


    @FormUrlEncoded
    @POST("showpersonalInformation")
    Call<Personal_Information_Settings_Model> PersonalInformationDetails(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("showpersonalInformation")
    Call<PersonalInformationSettingWizardModel> personalInformationSettingWizardModelCall(@Field("user_id") String user_id);


    @FormUrlEncoded
    @POST("addPersonalInformation")
    Call<Edit_Personal_Information_Model> EditPersonalInformation(@Field("user_id") String user_id,
                                                                  @Field("birth_date") String birth_date,
                                                                  @Field("gender") String gender,
                                                                  @Field("Location") String Location,
                                                                  @Field("relationship") String relationship,
                                                                  @Field("aboutme") String aboutme,
                                                                  @Field("firstname") String firstname,
                                                                  @Field("lastname") String lastname);

    @POST("show-country")
    Call<CountryListModel> countryList();

    @FormUrlEncoded
    @POST("show-city")
    Call<CityListModel> cityList(@Field("cid") String cid);

    @FormUrlEncoded
    @POST("personalizationPrivacy")
    Call<PersonalizationPrivacyModel> personalizationPrivacy(@Field("user_id") String user_id,
                                                             @Field("key") String key,
                                                             @Field("status") String status);

    @FormUrlEncoded
    @POST("showpersonalizationPrivacy")
    Call<PPSettingsModel> ppSettingsDetails(@Field("user_id") String user_id);


    @FormUrlEncoded
    @POST("showstreamSetting")
    Call<StreamSettingModel> streamSettingDetails(@Field("user_id") String user_id);

    @Multipart
    @POST("addstreamSetting")
    Call<EditStreamSettingModel> editStreamSetting(@Part("user_id") RequestBody user_id,
                                                   @Part("username") RequestBody username,
                                                   @Part("streamtitle") RequestBody streamtitle,
                                                   @Part("streamaboutme") RequestBody streamaboutme,
                                                   @Part MultipartBody.Part streamcoverimage);

    @FormUrlEncoded
    @POST("show-tv")
    Call<TvSettingsModel> tvSettingDetails(@Field("user_id") String user_id);


    @Multipart
    @POST("show-tv-update")
    Call<EditTVSettingModel> editTvSetting(@Part("id") RequestBody id,
                                           @Part("topic") RequestBody topic,
                                           @Part("agegroup") RequestBody agegroup,
                                           @Part("otherTags") RequestBody otherTags,
                                           @Part MultipartBody.Part tvlogo,
                                           @Part MultipartBody.Part tvcover);

    @FormUrlEncoded
    @POST("show-radio")
    Call<RadioSettingsModel> radioSettingDetails(@Field("user_id") String user_id);


    @Multipart
    @POST("show-radio-Update")
    Call<EditRadioSettingsModel> editRadioSetting(@Part("id") RequestBody id,
                                                  @Part("topic") RequestBody topic,
                                                  @Part("agegroup") RequestBody agegroup,
                                                  @Part("otherTags") RequestBody otherTags,
                                                  @Part MultipartBody.Part radiologo,
                                                  @Part MultipartBody.Part radiocover);


    @FormUrlEncoded
    @POST("contactInformation")
    Call<ContactInformationModel> contactInformation(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("updatecontactInformation")
    Call<EditContactSettingModel> editContactInformation(@Field("user_id") String user_id,
                                                         @Field("email") String email,
                                                         @Field("phone") String phone,
                                                         @Field("url") String url);


    @FormUrlEncoded
    @POST("streampagesetting")
    Call<StreamPageSettingsModel> sreamPageSettingsDetails(@Field("user_id") String user_id);


    @FormUrlEncoded
    @POST("sharepagesetting")
    Call<ShareSettingDetailsModel> shareSettingsDetails(@Field("user_id") String user_id);

    // done
    @FormUrlEncoded
    @POST("addsharepagesetting")
    Call<EditShareSettingsModel> editShareSetting(@Field("user_id") String user_id,
                                                  @Field("key") String key,
                                                  @Field("status") String status
    );

    @FormUrlEncoded
    @POST("notificationList")
    Call<NotificationListModel> notificationList(@Field("user_id") String user_id);


    @GET("notifications/")
    Call<NotificationModelPython> NOTIFICATION_MODEL_PYTHON_CALL(@Header("Authorization") String Authorization);


    @POST("notifications/")
    Call<CommonStatusMessageModelPython> CLEAR_ALL_NOTIFICATION_PYTHON_CALL(@Header("Authorization") String Authorization);


    @POST
    Call<CommonStatusMessageModelPython> notificaitonSingleDelete(@Url String url,
                                                                  @Header("Authorization") String Authorization);

    @FormUrlEncoded
    @POST("notificationRemoveAll")
    Call<SmFlaxibleModel> notificationClearAll(@Field("user_id") String user_id);


    @FormUrlEncoded
    @POST("showpoststeam")
    Call<StreamPageModel> stramPageList(@Field("user_id") String user_id);


    @GET("posts/")
    Call<PostListModelPython> POST_LIST_MODEL_PYTHON_CALL(@Header("Authorization") String Authorization);


    @GET
    Call<UserProfileModelPython> USER_PROFILE_MODEL_PYTHON_CALL(@Url String url,
                                                                @Header("Authorization") String Authorization);






    @GET

    Call<FollowersListModelPython> FOLLOWERS_LIST_MODEL_PYTHON_CALL(@Url String url);

    Call<FollowersListModelPython> FOLLOWERS_LIST_MODEL_PYTHON_CALL (@Url String url,
                                                                   @Header("Authorization") String Authorization);


    @GET
    Call<LiveUserListModelPython> LIVE_USER_LIST_MODEL_PYTHON_CALL (@Url String url,
                                                                    @Header("Authorization") String Authorization);


    @FormUrlEncoded
    @POST("popular-tv")
    Call<TvListModel> popularTvList(@Field("user_id") String user_id);


    @FormUrlEncoded
    @POST("popular-radio")
    Call<RadioListModel> popularRadioList(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("notificationCount")
    Call<NotificationCountModel> notificationCount(@Field("user_id") String user_id);


    @FormUrlEncoded
    @POST("notificationRead")
    Call<SmFlaxibleModel> notificationRead(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("Show-wall")
    Call<UserWallListModel> userWallLlist();

    // Done

    @FormUrlEncoded
    @POST("email-verification")
    Call<SmFlaxibleModel> emailVerification(@Field("email") String email,
                                            @Field("token") String token);


    @FormUrlEncoded
    @POST("otp-verification")
    Call<SmFlaxibleModel> emailVerificationOtp(@Field("email") String email,
                                               @Field("otp") String otp);


    @FormUrlEncoded
    @POST("forget-password")
    Call<SmFlaxibleModel> chnage_password(@Field("username") String username,
                                          @Field("password") String password);


    @Multipart
    @POST("supportData")
    Call<SmFlaxibleModel> support_form(@Part("name") RequestBody name,
                                       @Part("email") RequestBody email,
                                       @Part("about") RequestBody about,
                                       @Part MultipartBody.Part[] image);

    @FormUrlEncoded
    @POST("papulardataList")
    Call<PopularListModel> POPULAR_LIST_MODEL_CALL(@Field("loginId") String loginId

    );

    @FormUrlEncoded
    @POST("FollowUNfollow")
    Call<SmFlaxibleModel> follow_unfollow(@Field("follows_id") String follows_id,
                                          @Field("user_id") String user_id,
                                          @Field("status") String status
    );

    @POST("Business")
    Call<BusinessModel> business_list();

    @POST("Education")
    Call<EducationModel> education_list();

    @Multipart
    @POST("addbook/")
    Call<CommonStatusMessageModelPython> ADD_BOOK_MODEL_CALL(@Header("Authorization") String Authorization,
                                                             @Part("book_name") RequestBody book_name,
                                                             @Part("book_descriptions") RequestBody book_descriptions,
                                                             @Part("book_status") RequestBody book_status,
                                                             @Part("book_interests") List<Integer> book_interests,
                                                             @Part MultipartBody.Part book_image);

    @GET("my-books-list/")
    Call<BookListModel> BOOK_LIST_MODEL_CALL(@Header("Authorization") String Authorization);

    @GET("notificationsetting/")
    Call<NotificationSettingModel> NOTIFICATION_MODEL_SETTINGS_CALL(@Header("Authorization") String Authorization);

    @GET("rolesetting/")
    Call<RoleSettingModel> ROLE_SETTING_MODEL_CALL(@Header("Authorization") String Authorization);

    @FormUrlEncoded
    @PUT("notificationsetting/")
    Call<CommonStatusMessageModelPython> NOTIFICATION_MODEL_CHANGE_SETTINGS_CALL(@Header("Authorization") String Authorization,
                                                                                 @Field("notification") String notification);


    @FormUrlEncoded
    @PUT("rolesetting/")
    Call<CommonStatusMessageModelPython> ROLE_CHANGE_SETTINGS_CALL(@Header("Authorization") String Authorization,
                                                                   @Field("role") String role);


    @GET("profile/")
    Call<UserProfileModel> USER_PROFILE_MODEL_CALL(@Header("Authorization") String Authorization);


    @GET("setting/")
    Call<SettingModel> SETTING_MODEL_CALL(@Header("Authorization") String Authorization);


    @Multipart
    @PUT("setting/")
    Call<SettingModel> SETTING_MODEL_CALL_UPDATE(@Header("Authorization") String Authorization,
                                                 @Part("id") RequestBody id,
                                                 @Part("username") RequestBody username,
                                                 @Part("first_name") RequestBody first_name,
                                                 @Part("last_name") RequestBody last_name,
                                                 @Part("stream_title") RequestBody stream_title,
                                                 @Part("birth_date") RequestBody birth_date,
                                                 @Part("country") RequestBody country,
                                                 @Part MultipartBody.Part image);

    @FormUrlEncoded
    @POST("resetrequest/")
    Call<CommonStatusMessageModelPython> COMMON_STATUS_MESSAGE_MODEL_PYTHON_CALL_REQUEST_OTP
            (@Header("Authorization") String Authorization,
             @Field("email") String email);


    @FormUrlEncoded
    @POST("resetpassword/")
    Call<CommonStatusMessageModelPython> COMMON_STATUS_MESSAGE_MODEL_PYTHON_CALL_OTP_VERIFICATION
            (@Header("Authorization") String Authorization,
             @Field("email") String email,
             @Field("reset_password_otp") String reset_password_otp);


    @FormUrlEncoded
    @PUT("resetpassworddone/")
    Call<CommonStatusMessageModelPython> COMMON_STATUS_MESSAGE_MODEL_PYTHON_CALL_RESET_PASSWORD
            (@Header("Authorization") String Authorization,
             @Field("email") String email,
             @Field("reset_password_otp") String reset_password_otp,
             @Field("password") String password);


    @Multipart
    @POST("support/")
    Call<CommonStatusMessageModelPython> COMMON_STATUS_MESSAGE_MODEL_PYTHON_CALL_SUPPORT(
            @Part("email") RequestBody email,
            @Part("name") RequestBody name,
            @Part("about") RequestBody about,
            @Part MultipartBody.Part[] image);


    @GET("myfollowers/")
    Call<FollowersModel> FOLLOWERS_MODEL_CALL(@Header("Authorization") String Authorization);

    @GET
    Call<FollowerProfileModel> FOLLOWER_PROFILE_MODEL_CALL(@Url String url,@Header("Authorization") String Authorization);



    @FormUrlEncoded
    @POST("profile-report/")
    Call<CommonStatusMessageModelPython> REPORT_PROFILE_COMMON_STATUS_MESSAGE_MODEL_PYTHON_CALL(@Header("Authorization") String Authorization,
                                                                                                @Field("report_to") String report_to);





    @FormUrlEncoded
    @POST("checkuser/")
    Call<CommonStatusMessageModelPython> CHECK_USER_COMMON_STATUS_MESSAGE_MODEL_PYTHON_CALL(@Field("email") String email);



    @DELETE
    Call<CommonStatusMessageModelPython> DELETE_BOOK_COMMON_STATUS_MESSAGE_MODEL_PYTHON_CALL(@Url String url,
            @Header("Authorization") String Authorization);


    @GET("my-stream/")
    Call<StreamModel> STREAM_MODEL_CALL(@Header("Authorization") String Authorization);

    @GET
    Call<FFModel> FF_MODEL_CALL(@Url String url, @Header("Authorization") String Authorization);
}



