package com.example.almuhasabah.data.repository

import androidx.lifecycle.MutableLiveData
import com.example.almuhasabah.data.api.AlMuhasabahClientBuilder
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
import com.example.almuhasabah.utils.Resource

class LoginControllerRepository : BaseRepository() {
    private var apiService = AlMuhasabahClientBuilder.Companion.ServicesApiInterface

    companion object {
        @Volatile
        private var instance: LoginControllerRepository? = null

        fun getInstance() = instance ?: synchronized(this) {
            instance ?: LoginControllerRepository().also { instance = it }
        }

    }
    fun emailVerify(
        responseData: MutableLiveData<Resource<BaseResponse<SignUpEmailDataItem>>>,
        email:String){
        apiService.loginUser()?.emailVerify(email)?.enqueue(getCallback(responseData))
    }

    fun otpVerify(responseData: MutableLiveData<Resource<BaseResponse<SignupOtpDataItem>>>, email:String ,otp:String){
        apiService.loginUser()?.otpVerify(email,otp)?.enqueue(getCallback(responseData))
    }

    fun register(responseData: MutableLiveData<Resource<BaseResponse<SignUpDataItem>>>,
                 name:String,email: String,password:String,
                 role_id:String,age:String,gender:String,marital_status:String,username:String,phone_no:String
                 ){
        apiService.loginUser()?.register(name,email,password,role_id,age,gender,marital_status,username,phone_no)?.enqueue(getCallback(responseData))
    }

    fun login(responseData: MutableLiveData<Resource<BaseResponse<LoginDataItem>>>,email_name:String,password: String,email: String){
        apiService.loginUser()?.Login(email_name, password)?.enqueue(getCallback(responseData))
    }

    fun forgotPassword(responseData: MutableLiveData<Resource<BaseResponse<ForgotPasswordDataItem>>>,email: String){
        apiService.loginUser()?.forgetPassword(email)?.enqueue(getCallback(responseData))
    }

    fun updatePassword(responseData: MutableLiveData<Resource<BaseResponse<UpdatePasswordDataItem>>>,email: String,otp: String,password: String){
        apiService.loginUser()?.updatePassword(email, otp, password)?.enqueue(getCallback(responseData))
    }

    fun editProfile(responseData: MutableLiveData<Resource<BaseResponse<EditProfileDataItem>>>,
                 name:String,age: String,gender:String,
                    marital_status:String,phone_no:String
    ){
        apiService.loginUser()?.editProfile(name,age,gender,marital_status,phone_no)?.enqueue(getCallback(responseData))
    }

    fun secondaryProfile(responseData: MutableLiveData<Resource<BaseResponse<SecondaryProfileDataItem>>>,
                         name:String,email: String,password:String,
                         role_id:String,age:String,gender:String,marital_status:String,username:String,phone_no:String,parent_id:String
    ){
        apiService.loginUser()?.secondaryProfile(name, email, password, role_id, age, gender, marital_status, username, phone_no,parent_id)?.enqueue(getCallback(responseData))
    }

    fun getSecondaryProfileListApi(responseData : MutableLiveData<Resource<BaseResponse<List<GetSecondaryProfile>>>>) {
        apiService.loginUser()?.getSecondaryProfile()?.enqueue(getCallbackList(responseData))
    }

    fun profilePicApi(
        responseData: MutableLiveData<Resource<BaseResponse<ProfilePicDataItem>>>, profile_user_id: String,
        profile: String?
    ){
        apiService.loginUser()?.profilePicture(profile_user_id, profile)?.enqueue(getCallback(responseData))
    }

    fun getCategoryListApi(responseData : MutableLiveData<Resource<BaseResponse<List<CategoryListDataItem>>>>) {
        apiService.loginUser()?.getCategoryList()?.enqueue(getCallbackList(responseData))
    }

    fun listQuestionAnswerApi(
        responseData: MutableLiveData<Resource<BaseResponse<ArrayList<ListQuestionDataItemItem>>>>, category_id: String,
    ){
        apiService.loginUser()?.listQuestionAnswer(category_id)?.enqueue(getCallback(responseData))
    }

    fun hadeesHeaderListApi(responseData : MutableLiveData<Resource<BaseResponse<List<HadeesListItem>>>>) {
        apiService.loginUser()?.hadeesHeaderList()?.enqueue(getCallbackList(responseData))
    }

    fun hadeesImageSliderListApi(responseData : MutableLiveData<Resource<BaseResponse<List<HadeesImageSliderDataItem>>>>) {
        apiService.loginUser()?.hadeesImageSliderList()?.enqueue(getCallbackList(responseData))
    }

    fun editSecProfile(responseData: MutableLiveData<Resource<BaseResponse<SecondaryProfileUpdateDataItem>>>,
                       sec_user_id:String,age: String,gender:String,
                    marital_status:String,profile:String
    ){
        apiService.loginUser()?.editSecProfile(sec_user_id, age, gender, marital_status, profile)?.enqueue(getCallback(responseData))
    }

    fun questionAnswerVerificationApi(
        responseData: MutableLiveData<Resource<BaseResponse<List<Userresponse>>>>,
        questionAnswerVerification: QuestionAnswerVerification
    ){
        apiService.loginUser()?.questionAnswerVerification(questionAnswerVerification)?.enqueue(getCallbackList(responseData))

    }

//    fun questionAnswerVerificationApi(
//        responseData: MutableLiveData<Resource<BaseResponse<List<Userresponse>>>>,
//        questionAnswerVerification: QuestionAnswerVerification
//    ){
//        apiService.loginUser()?.questionAnswerVerification(questionAnswerVerification)?.enqueue(getCallbackList(responseData))
//
//    }

//    fun questionAnswerVerificationApi(
//        responseData: MutableLiveData<Resource<BaseResponse<List<Userresponse>>>>,
//        userid:String, questionId:String){
//        apiService.loginUser()?.questionAnswerVerification(userid, questionId)?.enqueue(getCallbackList(responseData))


}