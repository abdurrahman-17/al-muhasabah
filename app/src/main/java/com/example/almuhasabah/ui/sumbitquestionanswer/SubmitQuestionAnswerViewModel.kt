package com.example.almuhasabah.ui.sumbitquestionanswer

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.example.almuhasabah.base.BaseViewModel
import com.example.almuhasabah.data.local.SharedPreferenceImp
import com.example.almuhasabah.data.repository.BaseResponse
import com.example.almuhasabah.data.repository.LoginControllerRepository
import com.example.almuhasabah.model.emailvalidation.listquestion.ListQuestionDataItemItem
import com.example.almuhasabah.model.emailvalidation.listquestion.Option
import com.example.almuhasabah.model.emailvalidation.questionanswerverification.QuestionAnswerVerification
import com.example.almuhasabah.model.emailvalidation.questionanswerverification.Userresponse
import com.example.almuhasabah.utils.Constants
import com.example.almuhasabah.utils.Resource
import com.example.almuhasabah.utils.SingleLiveData
import javax.inject.Inject

class SubmitQuestionAnswerViewModel@Inject constructor(application: Application) : BaseViewModel(application)  {


    val mShowProgressBar = SingleLiveData<Boolean>()
    val listQuestionAnswerDataResponse= MutableLiveData<Resource<BaseResponse<ArrayList<ListQuestionDataItemItem>>>>()
    var question = ObservableField<String>("")
    var userId = ObservableField<String>("")
    var id = ObservableField<String>("")
    var useranswer = ObservableField<String>("")
    var questionAnswerId = ObservableField<String>("")
    var questiontype = ObservableField<String>("")
    var isYesOrNoType = ObservableField(true)
    var isMultipleOptionCheckBox = ObservableField<Boolean>(true)
    var isCommentType = ObservableField<Boolean>(true)
    var optionsList = ArrayList<Option>()
    var questionList = ArrayList<ListQuestionDataItemItem>()
    var sharedPreferences : SharedPreferenceImp? = null
    val category_id= ObservableField<String>("")
    //val questionAnswerVerifyData=MutableLiveData<Resource<BaseResponse<List<Userresponse>>>>()
    val questionAnswerVerifyData=MutableLiveData<Resource<BaseResponse<List<Userresponse>>>>()
    var answerlist = ArrayList<Userresponse>()


    fun listQuestionAnswerApi(){
        mShowProgressBar.value=true
        LoginControllerRepository.getInstance().listQuestionAnswerApi(listQuestionAnswerDataResponse,category_id.get().toString())
    }




    fun questionAnswerVerificationApi(){
        mShowProgressBar.value = true
        LoginControllerRepository.getInstance().questionAnswerVerificationApi(questionAnswerVerifyData,buildQuestionAnswerVerification())
    }
//    fun questionAnswerVerificationApi(){
//        mShowProgressBar.value = true
//        LoginControllerRepository.getInstance().questionAnswerVerificationApi(questionAnswerVerifyData,buildQuestionAnswerVerification())
//    }


//    fun questionAnswerVerificationApi(){
//        mShowProgressBar.value = true
//        LoginControllerRepository.getInstance().questionAnswerVerificationApi(questionAnswerVerifyData,userId.get().toString(),id.get().toString())
//    }

    private fun buildQuestionAnswerVerification(): QuestionAnswerVerification {
     //  val list : ArrayList<Userresponse> = ArrayList()


        sharedPreferences = SharedPreferenceImp()

        val userresponse =Userresponse(
//            questionanswer_id = id.get().toString(),
//        useranswer = useranswer.get().toString()

            questionanswer_id = sharedPreferences?.getString(Constants.USER_QUES_ANSWER_ID).toString(),
            useranswer = sharedPreferences?.getString(Constants.USER_ANSWER).toString()

        )
        return QuestionAnswerVerification(
            userresponse = listOf(userresponse),
            questionId = id.get().toString(),
            userid = userId.get().toString()
        )
    }


    init {
        setUpOptionsType()
    }

    fun setUpOptionsType(){

        sharedPreferences= SharedPreferenceImp()

        when{
            sharedPreferences?.getString(Constants.QUESTION_TYPE) == "1" ->{
                isYesOrNoType = ObservableField(true)
                isCommentType = ObservableField<Boolean>(false)
                isMultipleOptionCheckBox = ObservableField<Boolean>(false)
            }
            sharedPreferences?.getString(Constants.QUESTION_TYPE) == "2" ->{
                isYesOrNoType = ObservableField(false)
                isMultipleOptionCheckBox = ObservableField<Boolean>(true)
                isCommentType = ObservableField<Boolean>(false)
            }
            sharedPreferences?.getString(Constants.QUESTION_TYPE) == "3" ->{
                isYesOrNoType = ObservableField(true)
                isMultipleOptionCheckBox = ObservableField<Boolean>(false)
                isCommentType = ObservableField<Boolean>(false)
            }
            sharedPreferences?.getString(Constants.QUESTION_TYPE) == "4" ->{
                isYesOrNoType = ObservableField(false)
                isMultipleOptionCheckBox = ObservableField<Boolean>(false)
                isCommentType = ObservableField<Boolean>(true)
            }
        }
    }
}