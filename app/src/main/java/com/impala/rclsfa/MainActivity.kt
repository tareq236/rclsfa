package com.impala.rclsfa

import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.android.material.navigation.NavigationView
import com.google.gson.Gson
import com.impala.rclsfa.activities.NotificationActivity
import com.impala.rclsfa.activities.SettingsActivity
import com.impala.rclsfa.adapter.MenuAdapter
import com.impala.rclsfa.auth.LoginActivity
import com.impala.rclsfa.models.MenuResponse
import com.impala.rclsfa.utils.ApiService
import com.impala.rclsfa.utils.MyBackgroundService
import com.impala.rclsfa.utils.MyForegroundService
import com.impala.rclsfa.utils.SessionManager
import com.impala.rclsfa.utils.SocketManager
import com.impala.rclsfa.utils.UserRolesCheck
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MenuAdapter
    private lateinit var loadingDialog: Dialog
    private lateinit var socketManager: SocketManager
    private val MY_PERMISSION_REQUEST_CODE = 1
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private val REQUEST_CHECK_SETTINGS = 1001
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var sessionManager: SessionManager
    private lateinit var userNameId:TextView
    private lateinit var designationId:TextView

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        sessionManager = SessionManager(this)



        drawerLayout = findViewById(R.id.drawer_layout)
        toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val navView: NavigationView = findViewById(R.id.nav_view)

        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_menu_notification -> {
                    // Handle Notification item click
                    // Add your code here
                    startActivity(Intent(this,NotificationActivity::class.java))
                    true
                }
                R.id.nav_menu_setting -> {
                    // Handle Setting item click
                    // Add your code here
                    startActivity(Intent(this,SettingsActivity::class.java))
                    true
                }
                R.id.nav_menu_logout -> {
                    val sharedPreferences = getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.clear()
                    editor.apply()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                    true
                }
                // Add more cases for other menu items if needed
                else -> false
            }
        }

        // Initialize the Socket.io manager when your app starts
        socketManager = SocketManager.getInstance()
        socketManager.connect()
        socketManager.setOnConnectCallback {
            socketManager.sendSocketInfoFromPrefs(applicationContext)
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        val serviceIntent = Intent(this, MyBackgroundService::class.java)
        startService(serviceIntent)

        // Check and request location permission if not granted
        if (checkLocationPermission()) {
            // Permission is granted, get the current location
            startLocationUpdates()
        } else {
            // Request location permission
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                MY_PERMISSION_REQUEST_CODE
            )
        }

        // Initialize locationCallback for handling location updates
        createLocationCallback()

//        startBackgroundService()

        // Initialize the loading dialog
        loadingDialog = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
            .setTitleText("Loading")

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 4) // Two columns

        // Create a Retrofit API service (assuming you have Retrofit set up)
        val apiService = ApiService.CreateApi1()

        userNameId = findViewById(R.id.userNameId)
        designationId = findViewById(R.id.designation1)
        val userId = sessionManager.userId
        val designationName= sessionManager.designationName
        val userName = sessionManager.userName

        userNameId.text = "$userName( $userId )"
        designationId.text=designationName.toString()

        showLoadingDialog()
        // Make the API call to get menu items
        apiService.getMenuItems(userId!!,designationId.toString()).enqueue(object : Callback<MenuResponse> {
            override fun onResponse(call: Call<MenuResponse>, response: Response<MenuResponse>) {
                if (response.isSuccessful) {
                    val menuResponse = response.body()
                    if (menuResponse != null) {
                        if(menuResponse.success){
                            val sharedPreferences: SharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE)
                            val editor: SharedPreferences.Editor = sharedPreferences.edit()
                            val gson = Gson()
                            val jsonStringUserRole = gson.toJson(menuResponse.user_roles)
                            editor.putString("user_roles", jsonStringUserRole)
                            sessionManager.userRoles = jsonStringUserRole
                            if(jsonStringUserRole=="null"){

                            }else{
                                val menuList = UserRolesCheck.checkMenuRole(menuResponse.result, jsonStringUserRole)
                                adapter = MenuAdapter(menuList)
                                recyclerView.adapter = adapter
                            }

                            dismissLoadingDialog()
                        }else{
                            dismissLoadingDialog()
                            showDialogBox(SweetAlertDialog.WARNING_TYPE, "Problem-SF5801", menuResponse.message)
                        }
                    } else {
                        dismissLoadingDialog()
                        showDialogBox(SweetAlertDialog.WARNING_TYPE, "Error-RN5801", "Response NULL value. Try later.")
                    }
                } else {
                    dismissLoadingDialog()
                    showDialogBox(SweetAlertDialog.WARNING_TYPE, "Error-RR5801", "Response failed. Try later.")
                }
            }

            override fun onFailure(call: Call<MenuResponse>, t: Throwable) {
                dismissLoadingDialog()
                showDialogBox(SweetAlertDialog.ERROR_TYPE, "Error-NF5801", "Network error")
            }
        })
    }

    private fun showDialogBox(type: Int, title: String, message: String, callback: (() -> Unit)? = null) {
        val sweetAlertDialog = SweetAlertDialog(this, type)
            .setTitleText(title)
            .setContentText(message)
            .setConfirmClickListener {
                it.dismissWithAnimation()
                callback?.invoke()
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

    override fun onDestroy() {
        super.onDestroy()

        // socketManager.disconnect()
        // You can optionally stop the background service here
        // val serviceIntent = Intent(this, MyBackgroundService::class.java)
        // stopService(serviceIntent)
    }

    private fun checkLocationPermission(): Boolean {
        return (ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED)
    }

    private fun createLocationCallback() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.lastLocation?.let { location ->
                    // Handle the location result
                    // You can send this location to the server via socket
                    val latitude = location.latitude
                    val longitude = location.longitude
                     socketManager.sendLocationViaSocket(latitude, longitude)
                }
            }
        }
    }

    private fun startLocationUpdates() {
        val locationRequest = LocationRequest.create().apply {
            interval = 10000 // Interval in milliseconds (e.g., every 10 seconds)
            fastestInterval = 5000 // Fastest interval for location updates
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)

        val client = LocationServices.getSettingsClient(this)
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())

        task.addOnSuccessListener(
            this,
            OnSuccessListener<LocationSettingsResponse> { locationSettingsResponse ->
                // Location settings are satisfied, start location updates
                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return@OnSuccessListener
                }
                fusedLocationClient.requestLocationUpdates(
                    locationRequest,
                    locationCallback,
                    null
                )
            }
        )

        task.addOnFailureListener(
            this,
            OnFailureListener { exception ->
                if (exception is
                            ResolvableApiException
                ) {
                    // Location settings are not satisfied, show a dialog or prompt to enable location services
                    try {
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult()
                        exception.startResolutionForResult(
                            this,
                            REQUEST_CHECK_SETTINGS
                        )
                    } catch (sendEx: IntentSender.SendIntentException) {
                        // Ignore the error
                    }
                }
            }
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            MY_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission is granted, get the current location
                    startLocationUpdates()
                } else {
                    // Permission is denied, handle it (e.g., inform the user)
                }
            }
        }
    }

    private fun startBackgroundService() {
        val serviceIntent = Intent(this, MyBackgroundService::class.java)
        startService(serviceIntent)
    }

    private fun startForegroundService() {
        val serviceIntent = Intent(this, MyForegroundService::class.java)

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            startForegroundService(serviceIntent)
        } else {
            startService(serviceIntent)
        }
    }

    fun onTaskRemoved(rootIntent: Intent?) {
        //do your task;
        Log.d("app_close","App Closed")
    }

}
