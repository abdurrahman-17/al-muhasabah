package com.example.almuhasabah.ui.sumbitquestionanswer

import android.annotation.SuppressLint
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
import com.example.almuhasabah.databinding.CommentQuestionItemBinding
import com.example.almuhasabah.databinding.FragmentSubmitQuestionAnswerBinding
import com.example.almuhasabah.databinding.MultipleOptionsListItemsBinding
import com.example.almuhasabah.databinding.OptionsListItemsBinding
import com.example.almuhasabah.model.emailvalidation.listquestion.Option
import com.example.almuhasabah.utils.Constants
import kotlinx.android.synthetic.main.fragment_submit_question_answer.*


class SubmitQuestionAnswer :BaseFragment<FragmentSubmitQuestionAnswerBinding,SubmitQuestionAnswerViewModel>() {

    val submitQuesAnsVM : SubmitQuestionAnswerViewModel by viewModels()

    override val bindingVariable: Int
        get() = BR.submitQuesAnsVM
    override val layoutId: Int
        get() = R.layout.fragment_submit_question_answer
    override val viewModel: SubmitQuestionAnswerViewModel
        get() {
            return submitQuesAnsVM
        }
    var sharedPreferences : SharedPreferenceImp? = null

    private var baseRecyclerViewAdapter: BaseRecyclerViewAdapter<Option, OptionsListItemsBinding>?= null
    private var multiOptionCheckBoxAdapter : BaseRecyclerViewAdapter<Option, MultipleOptionsListItemsBinding>? = null
    private var commentsAdapter : BaseRecyclerViewAdapter<Option, CommentQuestionItemBinding>? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState)

        sharedPreferences = SharedPreferenceImp()
        viewModel.userId.set(sharedPreferences?.getString(Constants.ID))
        viewModel.id.set(sharedPreferences?.getString(Constants.QUESTION_ID))
        viewModel.question.set(sharedPreferences?.getString(Constants.QUESTION))
        viewModel.questiontype.set(sharedPreferences?.getString(Constants.QUESTION_TYPE))
        viewModel.questionAnswerId.set(sharedPreferences?.getString(Constants.QUESTION_ANSWER_ID))

        Log.i("userid",sharedPreferences?.getString(Constants.ID).toString())
        Log.i("questionid",sharedPreferences?.getString(Constants.QUESTION_ID).toString())



        viewModel.category_id.set(Constants.CATEGORY_ID)
        viewModel.listQuestionAnswerApi()
        viewModel.setUpOptionsType()

        viewModel.mShowProgressBar.observeEvent(this, Observer {
            it.let {
                showProgressBar(submit_ques_layout)
            }
        })



        viewModel.listQuestionAnswerDataResponse.observe(viewLifecycleOwner, Observer {
            it.let {
                dismissProgressBar()
                when(it.data?.isSuccess){
                    true -> {

                        it.data.response.get(0).Options.let { it ->
                            viewModel.optionsList.addAll(it)
                            setUpRecyclerView(viewModel.optionsList)
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

//        viewDataBinding?.submitBtn?.setOnClickListener {
//            viewModel.questionAnswerVerificationApi()
//
//            group_radio.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { radioGroup, i ->
//
//                if (radio_option_btn.isChecked) {
//                    viewModel.questionAnswerVerifyData.observe(viewLifecycleOwner, Observer {
//                        it.let {
//                            dismissProgressBar()
//                            when (it.data?.isSuccess) {
//                                true -> {
//                                    viewModel.answerlist[0].useranswer
//                                    sharedPreferences?.setString(Constants.USER_ANSWER,viewModel.answerlist[0].useranswer.toString())
//                                    sharedPreferences?.setString(Constants.USER_QUES_ANSWER_ID,viewModel.answerlist[0].questionanswer_id.toString())
//                                    putToast(it.data.message)
//                                }
//                                false -> {
//                                    putToast(it.data.message)
//                                }
//                                else -> {
//                                    putToast("Internet Issue")
//                                }
//                            }
//                        }
//                    })
//                }
//
//                else if (radio_option_checkBox.isChecked){
//                    viewModel.questionAnswerVerifyData.observe(viewLifecycleOwner, Observer {
//                        it.let {
//                            dismissProgressBar()
//                            when (it.data?.isSuccess) {
//                                true -> {
//                                    viewModel.answerlist[0].useranswer
//                                    sharedPreferences?.setString(Constants.USER_ANSWER,viewModel.answerlist[0].useranswer.toString())
//                                    sharedPreferences?.setString(Constants.USER_QUES_ANSWER_ID,viewModel.answerlist[0].questionanswer_id.toString())
//                                    putToast(it.data.message)
//                                }
//                                false -> {
//                                    putToast(it.data.message)
//                                }
//                                else -> {
//                                    putToast("Internet Issue")
//                                }
//                            }
//                        }
//                    })
//                }
//            })
//        }

//      viewModel.questionAnswerVerifyData.observe(viewLifecycleOwner, Observer {
//            it.let {
//                dismissProgressBar()
//                when(it.data?.isSuccess){
//                    true->
//                    {
//                        putToast(it.data.message)
//                    }
//                    false->
//                    {
//                        putToast(it.data.message)
//                    }
//                    else->
//                    {
//                        putToast("Internet Issue")
//                    }
//                }
//            }
//        })


        return viewDataBinding?.root
    }

        @SuppressLint("NotifyDataSetChanged")
        private fun setUpRecyclerView(optionsList: ArrayList<Option>) {
        baseRecyclerViewAdapter = BaseRecyclerViewAdapter(R.layout.options_list_items, BR.optionsItem, optionsList,
            null,
            object : OnDataBindCallback<OptionsListItemsBinding> {
                override fun onItemClick(view: View, position: Int, v: OptionsListItemsBinding) {
//
//                    sharedPreferences?.setString(Constants.QUESTION_ANSWER_ID,viewModel.optionsList[position].questionanswer_id.toString())
//                    sharedPreferences?.setString(Constants.CORRECT_ANSWER,viewModel.optionsList[position].correctanswer.toString())
//
//                    sharedPreferences?.setString(Constants.USER_ANSWER,viewModel.answerlist[position].useranswer.toString())
//                    sharedPreferences?.setString(Constants.USER_QUES_ANSWER_ID,viewModel.answerlist[position].questionanswer_id.toString())




                }

                override fun onDataBind(
                    v: OptionsListItemsBinding,
                    onClickListener: View.OnClickListener
                ) {
                    v.radioOptionBtn.setOnClickListener(onClickListener)
                }
            })

        viewDataBinding?.optionRv?.adapter=baseRecyclerViewAdapter
        baseRecyclerViewAdapter!!.notifyDataSetChanged()




        multiOptionCheckBoxAdapter= BaseRecyclerViewAdapter(R.layout.multiple_options_list_items,BR.multipleOptionsItem,
            optionsList,null,object :OnDataBindCallback<MultipleOptionsListItemsBinding>{
                override fun onItemClick(
                    view: View,
                    position: Int,
                    v: MultipleOptionsListItemsBinding
                ) {

                    if (v.radioOptionCheckBox.isChecked) {

                        viewModel.questionAnswerVerifyData.observe(viewLifecycleOwner, Observer {
                            it.let {
                                dismissProgressBar()
                                when (it.data?.isSuccess) {
                                    true -> {
                                        viewModel.answerlist[0].useranswer
                                        sharedPreferences?.setString(Constants.USER_ANSWER,viewModel.answerlist[position].useranswer.toString())
                                        sharedPreferences?.setString(Constants.USER_QUES_ANSWER_ID,viewModel.answerlist[position].questionanswer_id.toString())
                                        putToast(it.data.message)
                                    }
                                    false -> {
                                        putToast(it.data.message)
                                    }
                                    else -> {
                                        putToast("Internet Issue")
                                    }
                                }
                            }
                        })

                    }

                }

                override fun onDataBind(
                    v: MultipleOptionsListItemsBinding,
                    onClickListener: View.OnClickListener
                ) {
                    v.radioOptionCheckBox.setOnClickListener(onClickListener)
                }

            })

        viewDataBinding?.multiOptionCheckBoxRv?.adapter=multiOptionCheckBoxAdapter
        multiOptionCheckBoxAdapter!!.notifyDataSetChanged()




        commentsAdapter= BaseRecyclerViewAdapter(R.layout.comment_question_item,BR.commentDataItem,optionsList,
            null,object :OnDataBindCallback<CommentQuestionItemBinding>{
                override fun onItemClick(view: View, position: Int, v: CommentQuestionItemBinding) {

//                    sharedPreferences?.setString(Constants.QUESTION_ANSWER_ID,viewModel.optionsList[position].questionanswer_id.toString())
//                    sharedPreferences?.setString(Constants.CORRECT_ANSWER,viewModel.optionsList[position].correctanswer.toString())
//
//                    if ( v.commentItem.equals("")){
//                      putToast("Enter any Text")
//                    }
//                    else{
//                        optionsList[position].correctanswer
//                    }
                }

                override fun onDataBind(
                    v: CommentQuestionItemBinding,
                    onClickListener: View.OnClickListener
                ) {
                    v.commentItem.setOnClickListener(onClickListener)
                }

            })

        viewDataBinding?.commentRv?.adapter=commentsAdapter
        commentsAdapter!!.notifyDataSetChanged()

    }

}