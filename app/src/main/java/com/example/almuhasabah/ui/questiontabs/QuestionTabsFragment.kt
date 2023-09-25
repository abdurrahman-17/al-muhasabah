package com.example.almuhasabah.ui.questiontabs

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.almuhasabah.BR
import com.example.almuhasabah.R
import com.example.almuhasabah.adapter.BaseRecyclerViewAdapter
import com.example.almuhasabah.adapter.OnDataBindCallback
import com.example.almuhasabah.base.BaseFragment
import com.example.almuhasabah.data.local.SharedPreferenceImp
import com.example.almuhasabah.databinding.*
import com.example.almuhasabah.model.emailvalidation.listquestion.ListQuestionDataItemItem
import com.example.almuhasabah.model.emailvalidation.listquestion.Option
import com.example.almuhasabah.ui.sumbitquestionanswer.SubmitQuestionAnswer
import com.example.almuhasabah.utils.Constants
import kotlinx.android.synthetic.main.fragment_question_tabs.*


class QuestionTabsFragment :BaseFragment<FragmentQuestionTabsBinding,QuestionTabsViewModel>(){

    private val questiontabVM:QuestionTabsViewModel by viewModels()

    override val bindingVariable: Int
        get() = BR.questiontabVM
    override val layoutId: Int
        get() = R.layout.fragment_question_tabs
    override val viewModel: QuestionTabsViewModel
        get() {
            return questiontabVM
        }

    var sharedPreferences : SharedPreferenceImp? = null


    private var listQuestionAdapter : BaseRecyclerViewAdapter<ListQuestionDataItemItem, ListQuestionAnswerBinding>? = null


    private var baseRecyclerViewAdapter: BaseRecyclerViewAdapter<Option,OptionsListItemsBinding>?= null
    private var multiOptionCheckBoxAdapter : BaseRecyclerViewAdapter<Option, MultipleOptionsListItemsBinding>? = null
    private var commentsAdapter : BaseRecyclerViewAdapter<Option, CommentQuestionItemBinding>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState)

        sharedPreferences = SharedPreferenceImp()

        questiontabVM.mShowProgressBar.observeEvent(this, Observer {
            it.let {
                showProgressBar(list_questions_layout)
            }
        })

        viewModel.setUpOptionsType()
        viewModel.category_id.set(Constants.CATEGORY_ID)
        viewModel.title.set(Constants.CATEGORY_NAME)
        viewModel.listQuestionAnswerApi()

        listQuestionAdapter=BaseRecyclerViewAdapter(R.layout.list_question_answer,BR.QuestionModelitem,viewModel.questionList,
            null,object :OnDataBindCallback<ListQuestionAnswerBinding>{
                override fun onItemClick(view: View, position: Int, v: ListQuestionAnswerBinding) {

                    when(view.id){
                        R.id.list_question_cv->{
                            sharedPreferences?.setString(Constants.QUESTION_ID,viewModel.questionList[position].id.toString())
                            sharedPreferences?.setString(Constants.QUESTION,viewModel.questionList[position].Question)
                            sharedPreferences?.setString(Constants.QUESTION_TYPE, viewModel.questionList[position].QuestionType.toString())
                            sharedPreferences?.setString(Constants.QUESTION_OPTION, viewModel.questionList[position].Options.toString())
                            replaceFragment(R.id.bottom_container, SubmitQuestionAnswer(),"quesAns","queAns")
                        }
                    }

                }

                override fun onDataBind(
                    v: ListQuestionAnswerBinding,
                    onClickListener: View.OnClickListener
                ) {}

            })

        viewDataBinding?.questionListRv?.adapter=listQuestionAdapter

        viewModel.listQuestionAnswerDataResponse.observe(viewLifecycleOwner, Observer {
            it.let {
                dismissProgressBar()
                when(it.data?.isSuccess){
                    true -> {
                        it.data.response.let {
                            listQuestionAdapter?.clearDataSet()
                            listQuestionAdapter?.addDataSet(it)
                            listQuestionAdapter?.notifyDataSetChanged()
                            viewModel.questionsSize.set(it.size.toString() +"/"+ it.size.toString())
                            sharedPreferences?.setString(Constants.QUESTION_TYPE, it[0].QuestionType.toString())
                            Log.d("Question", sharedPreferences?.getString(Constants.QUESTION_TYPE).toString())
//                            sharedPreferences?.setString(Constants.QUESTION_TYPE, it[1].QuestionType.toString())
//                            sharedPreferences?.setString(Constants.QUESTION_TYPE, it[2].QuestionType.toString())
                        }

                        it.data.response.get(0).Options.let { it ->
                            viewModel.optionsList.addAll(it)
                         //   setUpRecyclerView(viewModel.optionsList)
                        }

                    }
                    false->
                    {
                        putToast(it.data.message)
                    }
                    else->
                    {
                        putToast("Api Issue")
                    }
                }
            }
        })

        return viewDataBinding?.root
    }


//    private fun setUpRecyclerView(optionsList: ArrayList<Option>) {
//        baseRecyclerViewAdapter = BaseRecyclerViewAdapter(R.layout.options_list_items, BR.optionsItem, optionsList,
//            null,
//            object :OnDataBindCallback<OptionsListItemsBinding>{
//                override fun onItemClick(view: View, position: Int, v: OptionsListItemsBinding) {
//                    v.groupRadio
//                    if ( v.radioOption.isChecked){
//                        optionsList[position].correctanswer
//                    }
//
//                }
//
//                override fun onDataBind(
//                    v: OptionsListItemsBinding,
//                    onClickListener: View.OnClickListener
//                ) {
//                    v.groupRadio.setOnClickListener(onClickListener)
//                }
//            })
//
//        viewDataBinding?.optionRv?.adapter=baseRecyclerViewAdapter
//        baseRecyclerViewAdapter!!.notifyDataSetChanged()
//
//
//        multiOptionCheckBoxAdapter= BaseRecyclerViewAdapter(R.layout.multiple_options_list_items,BR.multipleOptionsItem,
//            optionsList,null,object :OnDataBindCallback<MultipleOptionsListItemsBinding>{
//                override fun onItemClick(
//                    view: View,
//                    position: Int,
//                    v: MultipleOptionsListItemsBinding
//                ) {
//                    v.radioOption
//                    if ( v.radioOption.isChecked){
//                        optionsList[position].correctanswer
//                    }
//                }
//
//                override fun onDataBind(
//                    v: MultipleOptionsListItemsBinding,
//                    onClickListener: View.OnClickListener
//                ) {
//                    v.radioOption.setOnClickListener(onClickListener)
//                }
//
//            })
//
//        viewDataBinding?.multiOptionCheckBoxRv?.adapter=multiOptionCheckBoxAdapter
//        multiOptionCheckBoxAdapter!!.notifyDataSetChanged()
//
//        commentsAdapter= BaseRecyclerViewAdapter(R.layout.comment_question_item,BR.commentDataItem,optionsList,
//            null,object :OnDataBindCallback<CommentQuestionItemBinding>{
//                override fun onItemClick(view: View, position: Int, v: CommentQuestionItemBinding) {
//                    v.commentItem
//                    if ( v.commentItem.equals("")){
//                      putToast("Enter any Text")
//                    }
//                    else{
//                        optionsList[position].correctanswer
//                    }
//                }
//
//                override fun onDataBind(
//                    v: CommentQuestionItemBinding,
//                    onClickListener: View.OnClickListener
//                ) {
//                    v.commentItem.setOnClickListener(onClickListener)
//                }
//
//            })
//
//        viewDataBinding?.commentRv?.adapter=commentsAdapter
//        commentsAdapter!!.notifyDataSetChanged()
//
//    }

}