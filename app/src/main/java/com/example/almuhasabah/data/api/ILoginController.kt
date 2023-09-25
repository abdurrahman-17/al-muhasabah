package com.example.almuhasabah.data.api

import com.example.almuhasabah.data.repository.BaseResponse
import com.example.almuhasabah.model.emailvalidation.categorylist.CategoryListDataItem
import com.example.almuhasabah.model.emailvalidation.editprofile.EditProfileDataItem
import com.example.almuhasabah.model.emailvalidation.emailvalidation.SignUpEmailDataItem
import com.example.almuhasabah.model.emailvalidation.forgotpassword.ForgotPasswordDataItem
import com.example.almuhasabah.model.emailvalidation.getsecondaryprofile.GetSecondaryProfile
import com.example.almuhasabah.model.emailvalidation.hadeesimageslider.HadeesImageSliderDataItem
import com.example.almuhasabah.model.emailvalidation.hadesslist.HadeesListItem
import com.example.almuhasabah.model.emailvalidation.listquestion.ListQuestionDataItemItem
import com.example.almuhasabah.model.emailvalidation.login.LoginDataItem
import com.example.almuhasabah.model.emailvalidation.otpvalidation.SignupOtpDataItem
import com.example.almuhasabah.model.emailvalidation.profilepicdataitem.ProfilePicDataItem
import com.example.almuhasabah.model.emailvalidation.questionanswerverification.QuestionAnswerVerification
import com.example.almuhasabah.model.emailvalidation.questionanswerverification.Userresponse
import com.example.almuhasabah.model.emailvalidation.registersignup.SignUpDataItem
import com.example.almuhasabah.model.emailvalidation.secondaryprofile.SecondaryProfileDataItem
import com.example.almuhasabah.model.emailvalidation.secprofileupdatedataitem.SecondaryProfileUpdateDataItem
import com.example.almuhasabah.model.emailvalidation.updatepassword.UpdatePasswordDataItem
import retrofit2.Call
import retrofit2.http.*

interface ILoginController {

    @FormUrlEncoded
    @POST("api/emailverify")
    fun emailVerify(
        @Field("email") email:String
    ):Call<BaseResponse<SignUpEmailDataItem>>

    @FormUrlEncoded
    @POST("api/otpverify")
    fun otpVerify(
        @Field("email")email: String,
        @Field("otp")otp:String
    ):Call<BaseResponse<SignupOtpDataItem>>

    @FormUrlEncoded
    @POST("api/register")
    fun register(
        @Field("name")name:String,
        @Field("email")email:String,
        @Field("password")password:String,
        @Field("role_id")role_id:String,
        @Field("age")age:String,
        @Field("gender")gender:String,
        @Field("marital_status")marital_status:String,
        @Field("username")username:String,
        @Field("phone_no")phone_no:String
    ):Call<BaseResponse<SignUpDataItem>>

    @FormUrlEncoded
    @POST("api/login")
    fun Login(
        @Field("email_name") email_name:String,
        @Field("password") password: String,
    ):Call<BaseResponse<LoginDataItem>>

    @FormUrlEncoded
    @POST("api/forget_pwd_otp")
    fun forgetPassword(
        @Field("email")email: String,
    ):Call<BaseResponse<ForgotPasswordDataItem>>

    @FormUrlEncoded
    @POST("api/forget_pwd_otp_verify")
    fun updatePassword(
        @Field("email")email: String,
        @Field("otp")otp: String,
        @Field("password")password: String
    ):Call<BaseResponse<UpdatePasswordDataItem>>

    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("api/update_profile")
    fun editProfile(
        @Field("name")name: String,
        @Field("age")age: String,
        @Field("gender")gender: String,
        @Field("marital_status")marital_status: String,
        @Field("phone_no")phone_no: String,
    ):Call<BaseResponse<EditProfileDataItem>>


    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("api/register")
    fun secondaryProfile(
        @Field("name")name:String,
        @Field("email")email:String,
        @Field("password")password:String,
        @Field("role_id")role_id:String,
        @Field("age")age:String,
        @Field("gender")gender:String,
        @Field("marital_status")marital_status:String,
        @Field("username")username:String,
        @Field("phone_no")phone_no:String,
        @Field("parent_id")parent_id:String,
    ):Call<BaseResponse<SecondaryProfileDataItem>>

    @Headers("Accept: application/json")
    @GET("api/get_sec_user")
    fun getSecondaryProfile(): Call<BaseResponse<List<GetSecondaryProfile>>>


    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("api/update_profile_pic")
    fun profilePicture(
        @Field("profile_user_id") profile_user_id:String,
        @Field("profile") profile: String?,
    ): Call<BaseResponse<ProfilePicDataItem>>

    @Headers("Accept: application/json")
    @GET("api/get_category")
    fun getCategoryList(): Call<BaseResponse<List<CategoryListDataItem>>>

    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("api/get_question")
    fun listQuestionAnswer(
        @Field("category_id") category_id:String,
    ): Call<BaseResponse<ArrayList<ListQuestionDataItemItem>>>


    @GET("api/getLogo")
    fun hadeesHeaderList(): Call<BaseResponse<List<HadeesListItem>>>

    @GET("api/getSliderimg")
    fun hadeesImageSliderList(): Call<BaseResponse<List<HadeesImageSliderDataItem>>>

    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("api/update_sec_profile")
    fun editSecProfile(
        @Field("sec_user_id")sec_user_id: String,
        @Field("age")age: String,
        @Field("gender")gender: String,
        @Field("marital_status")marital_status: String,
        @Field("profile")profile: String,
    ):Call<BaseResponse<SecondaryProfileUpdateDataItem>>

    @Headers("Accept: application/json")
    @POST("api/qaverification")
    fun questionAnswerVerification(@Body questionAnswerVerification: QuestionAnswerVerification):Call<BaseResponse<List<Userresponse>>>

//    @Headers("Accept: application/json")
//    @POST("api/qaverification")
//    fun questionAnswerVerification(@Body userid:String,questionId:String):Call<BaseResponse<List<Userresponse>>>

}