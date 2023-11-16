package com.impala.rclsfa.auth

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.android.material.button.MaterialButton
import com.impala.rclsfa.R
import com.impala.rclsfa.BuildConfig
import com.impala.rclsfa.auth.model.ImageUploadModel
import com.impala.rclsfa.auth.model.ProfileUpdateModel

import com.impala.rclsfa.databinding.ActivityProfilePhotoUploadBinding
import com.impala.rclsfa.utils.ApiService
import com.impala.rclsfa.utils.ImageUtils
import com.impala.rclsfa.utils.SessionManager
import com.impala.rclsfa.utils.URIPathHelper
import com.theartofdev.edmodo.cropper.CropImage
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Part
import java.io.File

class ProfilePhotoUploadActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfilePhotoUploadBinding
    private lateinit var loadingDialog: Dialog
    private lateinit var sessionManager: SessionManager
    var imageFile: File? = null
    private var mUri: Uri? = null
    lateinit var imageBody: MultipartBody.Part
    var userId = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfilePhotoUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {

        sessionManager = SessionManager(this)
        // Initialize the loading dialog
        loadingDialog = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
            .setTitleText("Loading")
        userId =sessionManager.userId!!

        binding.backImage.setOnClickListener {
            finish()
        }

        binding.takePhoto.setOnClickListener {
            val builder = AlertDialog.Builder(this, R.style.CustomAlertDialog)
                .create()
            val view = layoutInflater.inflate(R.layout.custom_photo_dialog, null)

            builder.setView(view)

            val btnTakePhoto = view.findViewById<MaterialButton>(R.id.takePhoto)
            val btnGallery = view.findViewById<MaterialButton>(R.id.chooseFromGallery)
            val btnClose = view.findViewById<MaterialButton>(R.id.close)
            btnTakePhoto.setOnClickListener {
                takePicture()
                builder.dismiss()
            }

            btnGallery.setOnClickListener {
                galleryIntent()
                builder.dismiss()
            }

            btnClose.setOnClickListener {
                builder.dismiss()
            }
            builder.setCanceledOnTouchOutside(false)
            builder.show()
        }

        binding.upload.setOnClickListener {

            val userIdA: MultipartBody.Part =
                MultipartBody.Part.createFormData("user_id", userId)

            if (imageFile != null) {
                val imagePath: RequestBody =
                    RequestBody.create(MediaType.parse("application/octet-stream"), imageFile);
                imageBody = MultipartBody.Part.createFormData(
                    "image",
                    imageFile!!.name,
                    imagePath
                )
                showLoadingDialog()
                profileImageUpload(userIdA,imageBody)
            } else {

            }
        }

    }
    private fun profileImageUpload(
        userId: MultipartBody.Part?,
         image: MultipartBody.Part?
    ) {
        val apiService = ApiService.CreateApi2()
        apiService.profileImageUpload(
            userId,image

        ).enqueue(object :
            Callback<ImageUploadModel> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<ImageUploadModel>,
                response: Response<ImageUploadModel>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        if (data.getSuccess()!!) {
                            val dataList = data.getResult()
                            showDialogBox(
                                SweetAlertDialog.SUCCESS_TYPE,
                                "SUCCESS-S5803",
                                "Profile Image Updated Successfully "
                            )
                            dismissLoadingDialog()
                        } else {
                            dismissLoadingDialog()
                            showDialogBox(
                                SweetAlertDialog.WARNING_TYPE, "Problem-SF5801",
                                "Profile Updated Successfully Failed"
                            )
                        }
                    } else {
                        dismissLoadingDialog()
                        showDialogBox(
                            SweetAlertDialog.WARNING_TYPE,
                            "Error-RN5801",
                            "Response NULL value. Try later."
                        )
                    }
                } else {
                    dismissLoadingDialog()
                    showDialogBox(
                        SweetAlertDialog.WARNING_TYPE,
                        "Error-RR5801",
                        "Response failed. Try later."
                    )
                }
            }

            override fun onFailure(call: Call<ImageUploadModel>, t: Throwable) {
                dismissLoadingDialog()
                showDialogBox(SweetAlertDialog.ERROR_TYPE, "Error-NF5801", "Network error")
            }
        })
    }


    private fun showDialogBox(
        type: Int,
        title: String,
        message: String,
        callback: (() -> Unit)? = null
    ) {
        val sweetAlertDialog = SweetAlertDialog(this, type)
            .setTitleText(title)
            .setContentText(message)
            .setConfirmClickListener {
                it.dismissWithAnimation()
                callback?.invoke()

                finish()
            }
        sweetAlertDialog.show()
    }

    private fun showLoadingDialog() {
        loadingDialog.setCancelable(false)
        loadingDialog.show()
    }

    private fun dismissLoadingDialog() {
        loadingDialog.dismiss()
    }

    private fun takePicture() {
        imageFile = ImageUtils.createImageFile(applicationContext)?.also {
            mUri = FileProvider.getUriForFile(
                applicationContext,
                BuildConfig.APPLICATION_ID + ".provider",
                it
            )

            registerTakePicture.launch(mUri)
        }
    }

    private val registerTakePicture =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
            if (isSuccess) {
                mUri

                CropImage.activity(mUri)
                    .setAspectRatio(1, 1)
                    .start(this)

            } else {
                // commonMethods.showMatDialog("Warning", "Failed")

                showDialogBox(SweetAlertDialog.WARNING_TYPE, "Failed", "Failed to take picture")
            }
        }

    private fun galleryIntent() {
        pickImages.launch("image/*")
    }


    private val pickImages =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let { it ->
                this?.let { it1 ->
//                    CropImage.activity(uri)
//                        .setAspectRatio(1, 1)
//                        .start(it1, this)
                    CropImage.activity(uri)
                        .setAspectRatio(1, 1)
                        .start(this)
                };

            }
        }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == RESULT_OK) {
                val resultUri = result.uri
                val pathFromUri = URIPathHelper().getPath(this, resultUri)
                imageFile = File(pathFromUri)
                binding.profileImgId.setImageURI(resultUri)
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = result.error

                showDialogBox(SweetAlertDialog.ERROR_TYPE, "Sorry", error.message!!)
            }
        }
    }
}