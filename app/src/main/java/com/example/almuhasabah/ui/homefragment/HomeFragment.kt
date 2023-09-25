package com.example.almuhasabah.ui.homefragment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.almuhasabah.BR
import com.example.almuhasabah.R
import com.example.almuhasabah.adapter.BaseRecyclerViewAdapter
import com.example.almuhasabah.adapter.OnDataBindCallback
import com.example.almuhasabah.base.BaseFragment
import com.example.almuhasabah.data.local.SharedPreferenceImp
import com.example.almuhasabah.databinding.FragmentHomeBinding
import com.example.almuhasabah.databinding.HadeesHeaderItemBinding
import com.example.almuhasabah.databinding.HadeesItemBinding
import com.example.almuhasabah.model.emailvalidation.hadeesimageslider.HadeesImageSliderDataItem
import com.example.almuhasabah.model.emailvalidation.hadesslist.HadeesListItem
import com.example.almuhasabah.ui.loginscreen.LoginScreenActivity
import com.example.almuhasabah.utils.Constants
import com.example.colanportfolio.ui.indicator.CirclePagerIndicatorDecoration
import kotlinx.android.synthetic.main.fragment_home.*
import okio.IOException
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream


class HomeFragment : BaseFragment<FragmentHomeBinding,HomeFragmentViewModel>(){

    private val homefragmentVM : HomeFragmentViewModel by viewModels()
    private var hadeesHeaderAdapter : BaseRecyclerViewAdapter<HadeesListItem, HadeesHeaderItemBinding>? = null
    private var hadeesImageSliderAdapter : BaseRecyclerViewAdapter<HadeesImageSliderDataItem, HadeesItemBinding>? = null


    override val bindingVariable: Int
        get() = BR.homefragmentVM
    override val layoutId: Int
        get() = R.layout.fragment_home
    override val viewModel: HomeFragmentViewModel
        get() {
            return homefragmentVM
        }
    private var backToast:Toast?=null
    private var backPressedTime:Long = 0
    val PREVENT_WHEN_EMPTY :String =""
    var sharedPreferences : SharedPreferenceImp? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState)

        sharedPreferences= SharedPreferenceImp()

        viewModel.api.set(sharedPreferences?.getString(Constants.API_TOKEN)).toString()
        Log.d("Api Token",sharedPreferences?.getString(Constants.API_TOKEN).toString())


        hadeesHeaderAdapter= BaseRecyclerViewAdapter(R.layout.hadees_header_item,BR.hadeesHeaderItem,
        viewModel.hadeesHeaderList,null,object :OnDataBindCallback<HadeesHeaderItemBinding>{
                override fun onItemClick(view: View, position: Int, v: HadeesHeaderItemBinding) {}

                override fun onDataBind(
                    v: HadeesHeaderItemBinding,
                    onClickListener: View.OnClickListener
                ) {
                }

            })


        hadeesImageSliderAdapter= BaseRecyclerViewAdapter(R.layout.hadees_item,BR.hadeesImageItem,
        viewModel.hadeesImageSliderList,null,object :OnDataBindCallback<HadeesItemBinding>{
                @RequiresApi(Build.VERSION_CODES.N)
                @SuppressLint("CheckResult")
                override fun onItemClick(view: View, position: Int, v: HadeesItemBinding) {

                    v.btnShare.setOnClickListener {
                        val intent = Intent(Intent.ACTION_SEND)
                        intent.putExtra(
                            Intent.EXTRA_TEXT, ("Check out the Hadees slogan "
                                    +viewModel.hadeesImageSliderList[position].profile +
                                    "\nHere is the link to review the slogan image"+
                                    "\nTap the image and you can download it by long press"))

                        intent.type = "text/plain"
                        context!!.startActivity(Intent.createChooser(intent, "Send To"))
                    }



//                    v.btnShare.setOnClickListener {
//                        val target = object : Target{
//                            override fun onBitmapLoaded(
//                                bitmap: Bitmap?,
//                                from: Picasso.LoadedFrom?
//                            ) {
//                                val intent = Intent("android.intent.action.SEND")
//                                intent.type = "image/*"
//                                intent.putExtra(
//                                    "android.intent.extra.STREAM",
//                                    bitmap?.let { it1 -> getlocalBitmapUri(it1) }
//                                )
//                                context!!.startActivity(Intent.createChooser(intent, "share"))
//                            }
//
//                            override fun onBitmapFailed(
//                                e: java.lang.Exception?,
//                                errorDrawable: Drawable?
//                            ) {}
//
//                            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {}
//
//                        }
//
//                        Picasso.get().load(viewModel.hadeesImageSliderList[position].profile).into(target);
//
//
//                    }


//                    v.btnImageDownload.setOnClickListener {
//
//                        val target = object : Target {
//                            override fun onBitmapLoaded(
//                                bitmap: Bitmap?,
//                                from: Picasso.LoadedFrom?
//                            ) {
//                                try {
//                                    val mydie = File(
//                                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
//                                            .toString() + "/RI_IMG"
//                                    )
//                                    if (!mydie.exists()) {
//                                        mydie.mkdirs()
//                                    }
//                                    val fileOutputStream = FileOutputStream(
//                                        File(
//                                            mydie,
//                                            Date().toString().toString() + ".jpg"
//                                        )
//                                    )
//                                    bitmap?.compress(Bitmap.CompressFormat.JPEG, 90, fileOutputStream);
//                                    fileOutputStream.flush()
//                                    fileOutputStream.close()
//                                    Toast.makeText(context, "Save", Toast.LENGTH_LONG).show()
//                                } catch (e: FileNotFoundException) {
//                                    e.printStackTrace()
//                                } catch (e2: IOException) {
//                                    e2.printStackTrace()
//                                }
//                            }
//
//                            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {}
//
//                            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {}
//
//                        }
//                        Picasso.get().load(viewModel.hadeesImageSliderList[position].profile).into(target);
//
//                    }


                }

                override fun onDataBind(
                    v: HadeesItemBinding,
                    onClickListener: View.OnClickListener
                ) {}

            })




        viewDataBinding?.hadeesHeaderRv?.adapter=hadeesHeaderAdapter
        viewDataBinding?.hadeesRv?.adapter=hadeesImageSliderAdapter
        viewDataBinding?.hadeesRv?.addItemDecoration(CirclePagerIndicatorDecoration())
      //  viewDataBinding?.hadeesRv?.addItemDecoration(Indicator())

        viewModel.mShowProgressBar.observeEvent(viewLifecycleOwner, Observer {
            it.let {
                showProgressBar(home_layout)
            }
        })

        viewModel.hadeesHeaderListData.observe(viewLifecycleOwner, Observer {
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
                            hadeesHeaderAdapter?.clearDataSet()
//                          viewModel.domainList.clear()
                            //         viewModel.secondaryProfileList.addAll(it)
                            //Log.d("domainList",viewModel.domainList.let { it.toString() })
                            hadeesHeaderAdapter?.addDataSet(it)
                            hadeesHeaderAdapter?.notifyDataSetChanged()
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


        viewModel.hadeesImageSliderListData.observe(viewLifecycleOwner, Observer {
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
                            hadeesImageSliderAdapter?.clearDataSet()
//                          viewModel.domainList.clear()
                            //         viewModel.secondaryProfileList.addAll(it)
                            //Log.d("domainList",viewModel.domainList.let { it.toString() })
                            hadeesImageSliderAdapter?.addDataSet(it)
                            hadeesImageSliderAdapter?.notifyDataSetChanged()
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


        viewDataBinding?.logoutBtn?.setOnClickListener {

            showAlertDialogueToLogout()
        }



        return viewDataBinding?.root
    }

    private fun getlocalBitmapUri(bitmap: Bitmap): String? {

        var bmuri: Uri? = null
        try {
            val file = File(
                Environment.getExternalStorageDirectory().toString() + File.separator + "image.jpg"
            )
            val fileOutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fileOutputStream)
            fileOutputStream.close()
            bmuri = Uri.fromFile(file)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e2: IOException) {
            e2.printStackTrace()
        }
        return bmuri?.toString()

    }

    private fun showAlertDialogueToLogout() {
        AlertDialog.Builder(requireContext())
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle("Log out?")
            .setMessage("Are you sure you want to Log out?")
            .setPositiveButton("Yes") { _, _ ->
                val i =Intent(requireContext(), LoginScreenActivity::class.java)
                startActivity(i)
                sharedPreferences?.clear()
//                finish()
                //sharedPreferences.setBooleanValue(Constants.ALREADY_LOGED_IN,false)
                // goTo(LoginActivity::class.java,null)
            }
            .setNegativeButton("No", null)
            .show()
    }




}




