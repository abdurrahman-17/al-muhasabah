package com.example.almuhasabah.ui.questiontabs

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.example.almuhasabah.base.BaseViewModel
import com.example.almuhasabah.data.local.SharedPreferenceImp
import com.example.almuhasabah.data.repository.BaseResponse
import com.example.almuhasabah.data.repository.LoginControllerRepository
import com.example.almuhasabah.model.emailvalidation.listquestion.ListQuestionDataItemItem
import com.example.almuhasabah.model.emailvalidation.listquestion.Option
import com.example.almuhasabah.utils.Constants
import com.example.almuhasabah.utils.Resource
import com.example.almuhasabah.utils.SingleLiveData
import javax.inject.Inject

class QuestionTabsViewModel@Inject constructor(application: Application) : BaseViewModel(application) {

    val mShowProgressBar = SingleLiveData<Boolean>()
    val listQuestionAnswerDataResponse= MutableLiveData<Resource<BaseResponse<ArrayList<ListQuestionDataItemItem>>>>()
    val category_id= ObservableField<String>("")
    val title= ObservableField<String>("")
    val question= ObservableField("")
    val questionsSize= ObservableField<String>("")
    var questionId = ObservableField("")
    var questionType = ObservableField("")
    var optionsList = ArrayList<Option>()
    var questionList = ArrayList<ListQuestionDataItemItem>()
    var isYesOrNoType = ObservableField(true)
    var isMultipleOptionCheckBox = ObservableField<Boolean>(true)
    var isCommentType = ObservableField<Boolean>(true)
    var sharedPreferences : SharedPreferenceImp? = null


    fun listQuestionAnswerApi(){
        mShowProgressBar.value=true
        LoginControllerRepository.getInstance().listQuestionAnswerApi(listQuestionAnswerDataResponse,category_id.get().toString())
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