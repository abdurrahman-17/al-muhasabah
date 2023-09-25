package com.example.almuhasabah.ui.profilefragment

import android.content.Intent
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
import com.example.almuhasabah.data.local.SharedPreferenceImp
import com.example.almuhasabah.databinding.FragmentProfileBinding
import com.example.almuhasabah.databinding.SecondaryUserListBinding
import com.example.almuhasabah.model.emailvalidation.getsecondaryprofile.GetSecondaryProfile
import com.example.almuhasabah.ui.editprofile.EditProfileActivity
import com.example.almuhasabah.ui.editsecprofile.EditSecondaryProfileActivity
import com.example.almuhasabah.ui.secondaryuser.SecondaryUserActivity
import com.example.almuhasabah.utils.Constants
import kotlinx.android.synthetic.main.fragment_profile.*


class ProfileFragment : BaseFragment<FragmentProfileBinding,ProfileViewModel>() {

    private val profileVM : ProfileViewModel by viewModels()
    private var secondaryProfileAdapter : BaseRecyclerViewAdapter<GetSecondaryProfile, SecondaryUserListBinding>? = null
    var sharedPreferences : SharedPreferenceImp? = null

    override val bindingVariable: Int
        get() = BR.profileVM
    override val layoutId: Int
        get() = R.layout.fragment_profile
    override val viewModel: ProfileViewModel
        get() {
            return profileVM
        }





    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       super.onCreateView(inflater, container, savedInstanceState)

        sharedPreferences= SharedPreferenceImp()

        secondaryProfileAdapter= BaseRecyclerViewAdapter(
            R.layout.secondary_user_list,BR.secondaryProfileAdapter,
        viewModel.secondaryProfileList,null,object :OnDataBindCallback<SecondaryUserListBinding>{
                override fun onItemClick(view: View, position: Int, v: SecondaryUserListBinding) {


                    v.editSecProfileTv.setOnClickListener {

                        sharedPreferences?.setString(Constants.SEC_ID,viewModel.secondaryProfileList[position].id.toString())
                        sharedPreferences?.setString(Constants.SEC_NAME,viewModel.secondaryProfileList[position].name)
                        sharedPreferences?.setString(Constants.SEC_PHONE,viewModel.secondaryProfileList[position].phone_no.toString())
                        sharedPreferences?.setString(Constants.SEC_GENDER,viewModel.secondaryProfileList[position].gender)
                        sharedPreferences?.setString(Constants.SEC_STATUS, viewModel.secondaryProfileList[position].marital_status.toString())
                        sharedPreferences?.setString(Constants.SEC_AGE, viewModel.secondaryProfileList[position].age.toString())
                        val i =Intent(requireContext(), EditSecondaryProfileActivity::class.java)
                        startActivity(i)
                    }
                }

                override fun onDataBind(
                    v: SecondaryUserListBinding,
                    onClickListener: View.OnClickListener
                ) {}

            })

        viewDataBinding?.secondaryUserRv?.adapter=secondaryProfileAdapter

        viewModel.mShowProgressBar.observeEvent(viewLifecycleOwner, Observer {
            it.let {
                showProgressBar(profile_layout)
            }
        })


        viewModel.name.set(sharedPreferences?.getString(Constants.NAME))
        viewModel.email.set(sharedPreferences?.getString(Constants.EMAIL))
        viewModel.api_token.set(Constants.API_TOKEN)
        viewModel.profile.set(Constants.PROFILEPIC)


        viewModel.secondaryProfileListData.observe(viewLifecycleOwner, Observer {
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
                            secondaryProfileAdapter?.clearDataSet()
//                          viewModel.domainList.clear()
                   //         viewModel.secondaryProfileList.addAll(it)
                            //Log.d("domainList",viewModel.domainList.let { it.toString() })
                            secondaryProfileAdapter?.addDataSet(it)
                            secondaryProfileAdapter?.notifyDataSetChanged()
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



        viewDataBinding?.addSecondaryUserBtn?.setOnClickListener {

            val intent = Intent(activity, SecondaryUserActivity::class.java)
            startActivity(intent)
        }




        viewDataBinding?.editProfileTv?.setOnClickListener {

            val intent = Intent(activity, EditProfileActivity::class.java)
            startActivity(intent)
        }

        return viewDataBinding?.root
    }


}