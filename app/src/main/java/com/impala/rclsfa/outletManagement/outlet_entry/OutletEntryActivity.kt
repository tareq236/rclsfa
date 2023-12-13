package com.impala.rclsfa.outletManagement.outlet_entry

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.button.MaterialButton
import com.impala.rclsfa.BuildConfig
import com.impala.rclsfa.R
import com.impala.rclsfa.databinding.ActivityOutletEntryBinding
import com.impala.rclsfa.utils.ImageUtils
import com.impala.rclsfa.utils.SessionManager
import com.impala.rclsfa.utils.URIPathHelper
import com.theartofdev.edmodo.cropper.CropImage
import java.io.File
import android.provider.Settings
import androidx.core.app.ActivityCompat
import android.Manifest
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Location
import android.util.Base64
import com.google.android.material.textfield.TextInputLayout
import com.impala.rclsfa.outletManagement.outlet_entry.model.SaveRetailerModel
import com.impala.rclsfa.utils.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.util.Calendar

class OutletEntryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOutletEntryBinding
    lateinit var sessionManager: SessionManager
    var imageFile: File? = null
    private var mUri: Uri? = null
    private lateinit var loadingDialog: Dialog
    private var fusedLocationClient: FusedLocationProviderClient? = null
    private var lastLocation: Location? = null
    var userId = ""
    var latitude = ""
    var longitude = ""
    var routeIId = -1
    var divisionId = -1
    var districtId = -1
    var upazilaId = -1
    var imageBase = ""
    var divisionName =""
    var districtName =""
    var upazilaName =""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOutletEntryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { finish() }

        initView()
    }

    private fun initView() {
        sessionManager = SessionManager(this)
        loadingDialog = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
            .setTitleText("Loading")

        userId = sessionManager.userId!!

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        binding.actvRoute.setOnClickListener {
            startActivity(Intent(this, SearchRouteActivity::class.java))
        }

        binding.actvCategory.setOnClickListener {
            startActivity(Intent(this, SearchCategoryActivity::class.java))
        }

        binding.actvDivision.setOnClickListener {
            startActivity(Intent(this, SearchDivisionActivity::class.java))
        }


        binding.actvDistrict.setOnClickListener {
            startActivity(Intent(this, SearchDistrictActivity::class.java))
        }

        binding.actvUpazila.setOnClickListener {
            startActivity(Intent(this, SearchUpazilaActivity::class.java))
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

        binding.submitId.setOnClickListener {
            val route = binding.actvRoute.text.toString()
            val enName = binding.edtEnName.editText!!.text.toString()
            val bnName = binding.edtBnName.editText!!.text.toString()
            val proprietorName = binding.edtProprietorName.editText!!.text.toString()
            val categoryName = binding.actvCategory.text.toString()
            val mobileNumber = binding.edtMobileNumber.editText!!.text.toString()
            val nid = binding.edtNid.editText!!.text.toString()
            val address = binding.edtPresentAddress.editText!!.text.toString()
            val birthday = binding.edtBirthDay.editText!!.text.toString()

            val marriageDate = binding.edtMarriageDate.editText!!.text.toString()
            val child1stName = binding.edt1stChildName.editText!!.text.toString()
            val child1stBirthDay = binding.edt1stChildBirthDay.editText!!.text.toString()

            val child2ndName = binding.edt2ndChildName.editText!!.text.toString()
            val child2ndBirthDay = binding.edt2ndChildBirthDay.editText!!.text.toString()
              divisionName = binding.actvDivision.text.toString()
              districtName = binding.actvDistrict.text.toString()
              upazilaName = binding.actvUpazila.text.toString()

            if (validateInput(route, enName, mobileNumber,divisionName,districtName,upazilaName, address, latitude, longitude)) {
                showLoadingDialog()
                saveNewRetailer(
                    userId,
                    routeIId.toString(),
                    enName,
                    bnName,
                    proprietorName,
                    categoryName,
                    mobileNumber,
                    address,
                    nid,
                    birthday,
                    marriageDate,
                    child1stName,
                    child1stBirthDay,
                    child2ndName,
                    child2ndBirthDay,
                    latitude,
                    longitude,
                    divisionId.toString(),
                    districtId.toString(),
                    upazilaId.toString(),
                    imageBase
                )
            }
        }

        binding.tieBirthDay.setOnClickListener {
            setDate(binding.edtBirthDay)
        }

        binding.tieChild1stBDay.setOnClickListener {
            setDate(binding.edt1stChildBirthDay)
        }
        binding.tieChild2ndBDay.setOnClickListener {
            setDate(binding.edt2ndChildBirthDay)
        }

        binding.tieMarriageDate.setOnClickListener {
            setDate(binding.edtMarriageDate)
        }


    }

    private fun validateInput(
        route: String,
        nameEn: String,
        phoneNumber: String,
        divisionName:String,
        districtName:String,
        upazilaName:String,
        presentAddress: String,
        latitude: String,
        longitude: String
    ): Boolean {
        if (route.isEmpty()) {
//            usernameEditText.error = "Username is required"
            showDialogBoxForValidation(
                SweetAlertDialog.WARNING_TYPE,
                "Validation",
                "Route Name is required"
            )
            return false
        }

        if (nameEn.isEmpty()) {
//            passwordEditText.error = "Password is required"
            showDialogBoxForValidation(
                SweetAlertDialog.WARNING_TYPE,
                "Validation",
                "Name En is required"
            )
            return false
        }

        if (phoneNumber.isEmpty()) {
//            passwordEditText.error = "Password is required"
            showDialogBoxForValidation(
                SweetAlertDialog.WARNING_TYPE,
                "Validation",
                "Phone Number is required"
            )
            return false
        }
        if (divisionName.isEmpty()) {
//            passwordEditText.error = "Password is required"
            showDialogBoxForValidation(
                SweetAlertDialog.WARNING_TYPE,
                "Validation",
                "Division is required"
            )
            return false
        }
        if (districtName.isEmpty()) {
//            passwordEditText.error = "Password is required"
            showDialogBoxForValidation(
                SweetAlertDialog.WARNING_TYPE,
                "Validation",
                "District is required"
            )
            return false
        }
        if (upazilaName.isEmpty()) {
//            passwordEditText.error = "Password is required"
            showDialogBoxForValidation(
                SweetAlertDialog.WARNING_TYPE,
                "Validation",
                "Upazila is required"
            )
            return false
        }
        if (presentAddress.isEmpty()) {
//            passwordEditText.error = "Password is required"
            showDialogBoxForValidation(
                SweetAlertDialog.WARNING_TYPE,
                "Validation",
                "Present Address is required"
            )
            return false
        }

        if (latitude.isEmpty()) {
//            passwordEditText.error = "Password is required"
            showDialogBoxForValidation(
                SweetAlertDialog.WARNING_TYPE,
                "Validation",
                "Latitude is required"
            )
            return false
        }
        if (longitude.isEmpty()) {
//            passwordEditText.error = "Password is required"
            showDialogBoxForValidation(
                SweetAlertDialog.WARNING_TYPE,
                "Validation",
                "Longitude is required"
            )
            return false
        }
        return true
    }

    override fun onResume() {
        super.onResume()

        routeIId = sessionManager.routeId!!
        val routeName = sessionManager.routeName
        binding.actvRoute.setText(routeName)

        val cateName = sessionManager.categoryName
        binding.actvCategory.setText(cateName)

        val divName = sessionManager.divName
        binding.actvDivision.setText(divName)

        val disName = sessionManager.districtName
        binding.actvDistrict.setText(disName)

        if (divName!!.isNotEmpty()) {
            binding.tILDistrict.visibility = View.VISIBLE
        }

        if (disName!!.isNotEmpty()) {
            binding.tILUpazila.visibility = View.VISIBLE
        }

        val upzName = sessionManager.upzName
        binding.actvUpazila.setText(upzName)

        divisionId = sessionManager.divId!!
        districtId = sessionManager.districtId!!
        upazilaId = sessionManager.upzId!!
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
                this.let { it1 ->
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
                imageBase = getBase64ForUriAndPossiblyCrash(resultUri)
                val pathFromUri = URIPathHelper().getPath(this, resultUri)
                imageFile = File(pathFromUri)
                binding.profileImgId.setImageURI(resultUri)
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = result.error

                showDialogBox(SweetAlertDialog.ERROR_TYPE, "Sorry", error.message!!)
            }
        }
    }

    private fun getBase64ForUriAndPossiblyCrash(uri: Uri): String {

        try {
            val bytes = contentResolver.openInputStream(uri)!!.readBytes()

            return Base64.encodeToString(bytes, Base64.DEFAULT)
        } catch (error: IOException) {
            error.printStackTrace() // This exception always occurs
        }

        return ""
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

                //finish()
            }
        sweetAlertDialog.show()
    }


    private fun showFDialogBox(
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

    public override fun onStart() {
        super.onStart()
        if (!checkPermissions()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions()
            }
        } else {
            getLastLocation()
        }
    }

    private fun getLastLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            return
        }
        fusedLocationClient?.lastLocation!!.addOnCompleteListener(this) { task ->
            if (task.isSuccessful && task.result != null) {
                lastLocation = task.result
                latitude = (lastLocation)!!.latitude.toString()
                longitude = (lastLocation)!!.longitude.toString()

            } else {
                Log.w(TAG, "getLastLocation:exception", task.exception)
                showMessage("No location detected. Make sure location is enabled on the device.")
            }
        }
    }

    private fun showMessage(string: String) {
        val container = findViewById<View>(R.id.linearlayout)
        if (container != null) {
            Toast.makeText(this, string, Toast.LENGTH_LONG).show()
        }
    }

    private fun checkPermissions(): Boolean {
        val permissionState = ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        return permissionState == PackageManager.PERMISSION_GRANTED
    }

    private fun startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
            REQUEST_PERMISSIONS_REQUEST_CODE
        )
    }

    private fun requestPermissions() {
        val shouldProvideRationale = ActivityCompat.shouldShowRequestPermissionRationale(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        if (shouldProvideRationale) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.")
            showSnackbar("Location permission is needed for core functionality", "Okay",
                View.OnClickListener {
                    startLocationPermissionRequest()
                })
        } else {
            Log.i(TAG, "Requesting permission")
            startLocationPermissionRequest()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.i(TAG, "onRequestPermissionResult")
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            when {
                grantResults.isEmpty() -> {
                    // If user interaction was interrupted, the permission request is cancelled and you
                    // receive empty arrays.
                    Log.i(TAG, "User interaction was cancelled.")
                }

                grantResults[0] == PackageManager.PERMISSION_GRANTED -> {
                    // Permission granted.
                    getLastLocation()
                }

                else -> {
                    showSnackbar("Permission was denied", "Settings",
                        View.OnClickListener {
                            // Build intent that displays the App settings screen.
                            val intent = Intent()
                            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                            val uri = Uri.fromParts(
                                "package",
                                Build.DISPLAY, null
                            )
                            intent.data = uri
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                        }
                    )
                }
            }
        }
    }

    private fun showSnackbar(
        mainTextStringId: String, actionStringId: String,
        listener: View.OnClickListener
    ) {
        Toast.makeText(this, mainTextStringId, Toast.LENGTH_LONG).show()
    }

    companion object {
        private val TAG = "LocationProvider"
        private val REQUEST_PERMISSIONS_REQUEST_CODE = 34
    }

    private fun saveNewRetailer(
        srId: String,
        routeId: String,
        retailer_name: String,
        nameBn: String,
        proprietorName: String,
        outletCategory: String,
        mobileNumber: String,
        address: String,
        nid: String,
        birthday: String,
        marriageDate: String,
        firstChildrenName: String,
        firstChildrenBirthDay: String,
        secondChildrenName: String,
        secondChildrenBirthDay: String,
        latitude: String,
        longitude: String,
        divisionId: String,
        districtId: String,
        upazila_id: String,
        image_base: String
    ) {
        val apiService = ApiService.CreateApi2()
        apiService.saveNewRetailer(
            srId,
            routeId,
            retailer_name,
            nameBn,
            proprietorName,
            outletCategory,
            mobileNumber,
            address,
            nid,
            birthday,
            marriageDate,
            firstChildrenName,
            firstChildrenBirthDay,
            secondChildrenName,
            secondChildrenBirthDay,
            latitude,
            longitude,
            divisionId,
            districtId,
            upazila_id,
            image_base
        ).enqueue(object :
            Callback<SaveRetailerModel> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<SaveRetailerModel>,
                response: Response<SaveRetailerModel>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        if (data.getSuccess()!!) {
                            val dataList = data.getResult()
                            showFDialogBox(
                                SweetAlertDialog.SUCCESS_TYPE,
                                "SUCCESS-S5803",
                                "Added new Retailer Successfully "
                            )
                            dismissLoadingDialog()
                        } else {
                            dismissLoadingDialog()
                            showDialogBox(
                                SweetAlertDialog.WARNING_TYPE, "Problem-SF5801",
                                " Failed"
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

            override fun onFailure(call: Call<SaveRetailerModel>, t: Throwable) {
                dismissLoadingDialog()
                showDialogBox(SweetAlertDialog.ERROR_TYPE, "Error-NF5801", "Network error")
            }
        })
    }


    private fun showLoadingDialog() {
        loadingDialog.setCancelable(false)
        loadingDialog.show()
    }

    private fun dismissLoadingDialog() {
        loadingDialog.dismiss()
    }

    private fun showDialogBoxForValidation(
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

            }
        sweetAlertDialog.show()
    }

    private fun setDate(editText: TextInputLayout) {
        val c = Calendar.getInstance()
        val mYear = c[Calendar.YEAR]
        val mMonth = c[Calendar.MONTH]
        val mDay = c[Calendar.DAY_OF_MONTH]


        val datePickerDialog = DatePickerDialog(
            this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth -> //
                val month = monthOfYear + 1
                var fm = "" + month
                var fd = "" + dayOfMonth
                if (month < 10) {
                    fm = "0$month"
                }
                if (dayOfMonth < 10) {
                    fd = "0$dayOfMonth"
                }
                //val date = "$year-$fm-$fd"
                val date = "$fd-$fm-$year"
                editText.editText!!.setText(date)

            }, mYear, mMonth, mDay
        )
        datePickerDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        //  datePickerDialog.datePicker.maxDate = System.currentTimeMillis();
        datePickerDialog.show()
    }
}