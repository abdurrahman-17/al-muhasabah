package com.example.almuhasabah.ui.editprofile

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.MediaStore.Images
import android.util.Base64
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isEmpty
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.almuhasabah.BR
import com.example.almuhasabah.R
import com.example.almuhasabah.base.BaseActivity
import com.example.almuhasabah.data.local.SharedPreferenceImp
import com.example.almuhasabah.databinding.ActivityEditProfileBinding
import com.example.almuhasabah.ui.homescreen.HomeActivity
import com.example.almuhasabah.utils.Constants
import com.github.dhaval2404.imagepicker.ImagePicker
import kotlinx.android.synthetic.main.activity_edit_profile.*
import java.io.ByteArrayOutputStream
import java.util.*


class EditProfileActivity : BaseActivity<ActivityEditProfileBinding,EditProfileViewModel>() {

    private val editprofileVM : EditProfileViewModel by viewModels()

    override val bindingVariable: Int
        get() = BR.editprofileVM
    override val layoutId: Int
        get() = R.layout.activity_edit_profile
    override val viewModel: EditProfileViewModel
        get() {
            return editprofileVM
        }

    private var selectedPicture = ""
    private val PICK_IMAGE_REQUEST = 71

    private val IMAGE = 100
    var IMG_REQUEST = 21
    var mybitmap: Bitmap? = null
    var sharedPreferences : SharedPreferenceImp? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences = SharedPreferenceImp()


        viewModel.profile_user_id.set(sharedPreferences?.getString(Constants.ID))
        viewModel.name.set(sharedPreferences?.getString(Constants.NAME))
        viewModel.ageNo.set(sharedPreferences?.getString(Constants.DOB))
        viewModel.gender.set(sharedPreferences?.getString(Constants.GENDER))
        viewModel.marital_status.set(sharedPreferences?.getString(Constants.MARITAL_STATUS))
        viewModel.phone_no.set(sharedPreferences?.getString(Constants.PHONE))

        editprofileVM.mShowProgressBar.observeEvent(this, Observer {
            it.let {
                showProgressBar(edit_profile_layout)
            }
        })

        viewDataBinding?.submitBtn?.setOnClickListener {

            if(editProfileValidation()){
                editprofileVM.editProfileApi()
            }
        }


        editprofileVM.editProfileDataResponse.observe(this, Observer {
            it.let {
                dismissProgressBar()
                when(it.data?.isSuccess){
                    true->
                    {
                        putToast(it.data.message)
                        sharedPreferences?.setString(Constants.EMAIL , it.data.response.email)
                        sharedPreferences?.setString(Constants.NAME , it.data.response.name)
                   //     replaceFragment(R.id.bottom_container, ProfileFragment(),"editprofile","editprofile")
                        val i =Intent(this, HomeActivity::class.java)
                        startActivity(i)

                    }
                    false->
                    {
                        putToast(it.data.message)
                    }
                    else->
                    {
                        putToast("Internet Issue")
                    }
                }

            }

        })


        editprofileVM.profilePicDataResponse.observe(this, Observer {
            it.let {
                dismissProgressBar()
                when(it.data?.isSuccess){
                    true->
                    {
                        putToast(it.data.message)
                    }
                    false->
                    {
                        putToast(it.data.message)
                    }
                    else->
                    {
                        putToast("Internet Issue")
                    }
                }

            }

        })

//        viewDataBinding?.cameraIv?.setOnClickListener {
//
//            viewModel.profile_user_id.set(Constants.ID)
//            viewModel.profilePicApi()
//            val intent = Intent()
//            intent.type = "image/*"
//            intent.action = Intent.ACTION_GET_CONTENT
//            startActivityForResult(
//                Intent.createChooser(intent, "Select Picture"),
//                PICK_IMAGE_REQUEST
//            )
//        }

        viewDataBinding?.calenderPicker?.setOnClickListener {

            val calendar= Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val currentYear = Calendar.getInstance().get(Calendar.YEAR)

            val datePickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener
            { view, year, monthOfYear, dayOfMonth ->

                val age = currentYear - year
                val date = "" + dayOfMonth + "/" + (monthOfYear+1) + "/" + year
                viewDataBinding?.AgeEt?.setText(date)
                viewModel.age.set(age.toString())
            }, year, month, day)

            datePickerDialog.show()
        }

        viewDataBinding?.cameraIv?.setOnClickListener {
            editprofileVM.profilePicApi()
            ImagePicker.with(this)
                .crop()	    			//Crop image(Optional), Check Customization for more option
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .start()


//            val intent = Intent()
//            intent.type = "image/*"
//            intent.action = Intent.ACTION_GET_CONTENT
//            startActivityForResult(
//                Intent.createChooser(intent, "Select Picture"),
//                PICK_IMAGE_REQUEST
//            )
        }

    }


    private fun editProfileValidation(): Boolean {
        when{

            viewDataBinding?.nameEt?.text.toString().isEmpty()->{
                viewDataBinding?.nameEt?.error="Please enter name"
                viewDataBinding?.nameEt?.requestFocus()
            }

//            viewDataBinding?.AgeEt?.text.toString().isEmpty()->{
//                viewDataBinding?.AgeEt?.error="Please enter age"
//                viewDataBinding?.AgeEt?.requestFocus()
//            }

            viewDataBinding?.genderSp?.isEmpty() == true ->{
                viewDataBinding?.genderSp?.requestFocus()
            }

            viewDataBinding?.maritalEt?.isEmpty()==true ->{
                viewDataBinding?.maritalEt?.requestFocus()
            }

            viewDataBinding?.contactEt?.text.toString().isEmpty()->{
                viewDataBinding?.contactEt?.error="Please enter phone number"
                viewDataBinding?.contactEt?.requestFocus()
            }

            else->return true
        }
        return false
    }


        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK ) {
            //Image Uri will not be null for RESULT_OK
                    val uri: Uri = data?.data!!

            viewDataBinding?.profileIv?.setImageURI(uri)
            // Use Uri object instead of File to avoid storage permissions
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
                val baos = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                val imageBytes: ByteArray = baos.toByteArray()
                viewModel.profile = Base64.encodeToString(imageBytes, Base64.DEFAULT)
                val bytesImage: ByteArray = Base64.decode(viewModel.profile, Base64.DEFAULT)
                  Glide.with(applicationContext)
                    .load(bytesImage)
                    .into(viewDataBinding?.profileIv!!)

            viewDataBinding?.profileIv?.setImageBitmap(bitmap)

        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }






//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode == Activity.RESULT_OK ) {
//
//            //Image Uri will not be null for RESULT_OK
//            val uri: Uri = data?.data!!
//
//          // Use Uri object instead of File to avoid storage permissions
//           val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
//            val baos = ByteArrayOutputStream()
//             bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
//            val imageBytes: ByteArray = baos.toByteArray()
////            viewModel.profile = android.util.Base64.encodeToString(imageBytes, android.util.Base64.DEFAULT)
////            val bytesImage: ByteArray = android.util.Base64.decode(viewModel.profile, Base64.DEFAULT)
////            Glide.with(applicationContext)
////                .load(bytesImage)
////                .into(viewDataBinding?.profileIv!!)
//
////          viewDataBinding?.profileIv?.setImageBitmap(bitmap)
//
//
//            val photo = data.getExtras()?.get("data")as Bitmap?
//            viewDataBinding?.profileIv?.setImageBitmap(photo)
//
//
//          viewDataBinding?.profileIv?.setImageURI(uri)
//
//            val selectedImagePath: String = getRealPathFromURI(uri)
//            Log.d( selectedImagePath,"path")
//            val imageFile : File = File(selectedImagePath)
//
//            if( imageFile.exists() && imageFile.canRead() )
//            {
//                Log.d( TAG, "File exists" );
//                Glide.with( applicationContext ).load(selectedImagePath).into(profile_iv); // foodImage = ImageView
//            }
//
////            viewDataBinding?.profileIv?.setImageURI(Uri.fromFile(finalFile))
////                viewDataBinding?.profileIv?.setImageURI(uri)
////            viewModel.file =
////                File(
////                    (Environment.getExternalStorageDirectory()).toString() + File.separator + Constants.DIRECTORY_NAME +
////                            File.separator
////                )
// //           val imageUri:Uri = Uri.fromFile(viewModel.file)
////            Glide.with(this)
////                .load(Uri.fromFile(finalFile))
////                .into(profile_iv)
//
//        } else if (resultCode == ImagePicker.RESULT_ERROR) {
//            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
//        } else {
//            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
//        }
//    }


//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if (resultCode == RESULT_OK) {
//            val uri: Uri = data?.data!!
//            val photo = data.getExtras()?.get("data") as Bitmap?
//            viewDataBinding?.profileIv?.setImageBitmap(photo)
//            viewDataBinding?.profileIv?.setImageURI(uri)
//
//
//            // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
//            val tempUri = photo?.let { getImageUri(applicationContext, it) }
//
//            // CALL THIS METHOD TO GET THE ACTUAL PATH
//        //    val finalFile :File =File(getRealPathFromURI(tempUri))
//          viewModel.file =File(getRealPathFromURI(tempUri))
//            Log.d(uri.toString(),"uri")
//        }
//    }


    fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage?.compress(CompressFormat.JPEG, 100, bytes)
        val path = Images.Media.insertImage(inContext.contentResolver, inImage, "Title", null)
        return Uri.parse(path)
    }

    fun getRealPathFromURI(uri: Uri?): String? {
        var path = ""
        if (contentResolver != null) {
            val cursor = uri?.let { contentResolver.query(it, null, null, null, null) }
            if (cursor != null) {
                cursor.moveToFirst()
                val idx = cursor.getColumnIndex(Images.ImageColumns.DATA)
                path = cursor.getString(idx)
                cursor.close()
            }
        }
        return path
    }


}



