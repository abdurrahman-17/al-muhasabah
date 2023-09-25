package com.example.almuhasabah.ui.questionarylist

import android.os.Bundle
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
import com.example.almuhasabah.databinding.FragmentQuestionaryListBinding
import com.example.almuhasabah.databinding.QuestionaryListItemBinding
import com.example.almuhasabah.model.emailvalidation.categorylist.CategoryListDataItem
import com.example.almuhasabah.ui.questiontabs.QuestionTabsFragment
import com.example.almuhasabah.utils.Constants
import kotlinx.android.synthetic.main.fragment_questionary_list.*


class QuestionaryListFragment : BaseFragment<FragmentQuestionaryListBinding,QuestionaryListViewModel>() {

    private val questionlistVM:QuestionaryListViewModel by viewModels()
    private var CategoryListAdapter : BaseRecyclerViewAdapter<CategoryListDataItem, QuestionaryListItemBinding>? = null



    override val bindingVariable: Int
        get() = BR.questionlistVM
    override val layoutId: Int
        get() = R.layout.fragment_questionary_list
    override val viewModel: QuestionaryListViewModel
        get() {
            return questionlistVM
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       super.onCreateView(inflater, container, savedInstanceState)

        CategoryListAdapter= BaseRecyclerViewAdapter(R.layout.questionary_list_item,BR.categoryListAdapter,
        viewModel.CategoryList,null,object :OnDataBindCallback<QuestionaryListItemBinding>{
                override fun onItemClick(view: View, position: Int, v: QuestionaryListItemBinding) {

                    Constants.CATEGORY_ID= viewModel.CategoryList[position].category_id.toString()
                    Constants.CATEGORY_NAME= viewModel.CategoryList[position].category_name.toString()
                    replaceFragment(R.id.bottom_container, QuestionTabsFragment(), "questionList", "questionList")

                }

                override fun onDataBind(
                    v: QuestionaryListItemBinding,
                    onClickListener: View.OnClickListener
                ) {}

            })

        viewDataBinding?.questionRv?.adapter=CategoryListAdapter

        viewModel.mShowProgressBar.observeEvent(viewLifecycleOwner, Observer {
            it.let {
                showProgressBar(Category_list_layout)
            }
        })



        viewModel.CategoryListData.observe(viewLifecycleOwner, Observer {
            it.let {
                dismissProgressBar()
                when(it.data?.isSuccess){
                    true -> {
                        /* it.data.response.forEach{
                             viewModel.domainList.clear()
                             viewModel.domainList.add(it)
                             domainAdapter?.addDataSet(viewModel.domainList)
                             //domainAdapter?.notifyDataSetChanged()
                         }*/

                        it.data.response.let {
                                CategoryListAdapter?.clearDataSet()
//                          viewModel.domainList.clear()
                   //         viewModel.CategoryList.addAll(it)
                            //Log.d("domainList",viewModel.domainList.let { it.toString() })
                            CategoryListAdapter?.addDataSet(it)
                            CategoryListAdapter?.notifyDataSetChanged()
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




}