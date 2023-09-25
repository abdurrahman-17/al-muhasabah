package com.example.almuhasabah.ui.questionarylist

import android.app.Application
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.MutableLiveData
import com.example.almuhasabah.base.BaseViewModel
import com.example.almuhasabah.data.local.SharedPreferenceImp
import com.example.almuhasabah.data.repository.BaseResponse
import com.example.almuhasabah.data.repository.LoginControllerRepository
import com.example.almuhasabah.model.emailvalidation.categorylist.CategoryListDataItem
import com.example.almuhasabah.utils.Resource
import com.example.almuhasabah.utils.SingleLiveData
import javax.inject.Inject

class QuestionaryListViewModel@Inject constructor(application: Application) : BaseViewModel(application)  {

    val mShowProgressBar= SingleLiveData<Boolean>()
    var sharedPreferences : SharedPreferenceImp?= null

    val CategoryListData = MutableLiveData<Resource<BaseResponse<List<CategoryListDataItem>>>>()
    var CategoryList = ObservableArrayList<CategoryListDataItem>()


    init {
        sharedPreferences = SharedPreferenceImp()
        CategoryListApi()
    }

    private fun CategoryListApi() {
        mShowProgressBar.value = true
        LoginControllerRepository.getInstance().getCategoryListApi(CategoryListData)
    }
}