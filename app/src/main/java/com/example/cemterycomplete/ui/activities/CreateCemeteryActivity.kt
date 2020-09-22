package com.example.cemterycomplete.ui.activities

import android.Manifest
import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.location.LocationManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.example.cemterycomplete.R
import com.example.cemterycomplete.data.entities.Cemetery
import com.example.cemterycomplete.databinding.ActivityCreateCemeteryBinding
import com.example.cemterycomplete.ui.viewmodels.CreateCemeteryViewModel
import com.google.android.gms.location.*
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import java.time.Instant
import java.util.*

class CreateCemeteryActivity : AppCompatActivity() {

    private val viewModel: CreateCemeteryViewModel by viewModels()

    private lateinit var binding: ActivityCreateCemeteryBinding

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var  geocoder: Geocoder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_cemetery)
        binding.lifecycleOwner = this


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this) //Use its methods to get location updates
        locationRequest = LocationRequest()    //can also use this to get finer location
        geocoder = Geocoder(this, Locale.getDefault()) //gets lat and long into usable address objects


        binding.locationBtn.setOnClickListener {
            if (!isLocationEnabled()) {
                Toast.makeText(this, "Your location provider is turned off. Please turn it on.", Toast.LENGTH_SHORT).show()

                // This will redirect you to settings from where you need to turn on the location provider.
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            } else {
                // https://www.androdocs.com/kotlin/getting-current-location-latitude-longitude-in-android-using-kotlin.html
                Dexter.withActivity(this)
                    .withPermissions(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                    .withListener(object : MultiplePermissionsListener {
                        override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                            if (report!!.areAllPermissionsGranted()) {
                                startLocationUpdates()
                            }
                        }

                        override fun onPermissionRationaleShouldBeShown(
                            permissions: MutableList<PermissionRequest>?,
                            token: PermissionToken?
                        ) {
                            showRationalDialogForPermissions() //Tell user why they need permissions
                        }
                    }).onSameThread()
                    .check()
            }
        }

        binding.addCemButton.setOnClickListener {
            if (binding.nameEditText.text.isNullOrEmpty() ||
                binding.locationEditText.text.isNullOrEmpty() ||
                binding.stateEditText.text.isNullOrEmpty() ||
                binding.countyEditText.text.isNullOrEmpty() ||
                binding.townshipEditText.text.isNullOrEmpty() ||
                binding.rangeEditText.text.isNullOrEmpty() ||
                binding.sectionEditText.text.isNullOrEmpty() ||
                binding.firstYearEditText.text.isNullOrEmpty()
            ) {
                Toast.makeText(this, "Please entery all fields", Toast.LENGTH_SHORT).show()
            } else {

                val cemetery =
                    Cemetery(
                        cemeteryRowId =  Instant.now().epochSecond.toInt(),
                        cemeteryName = binding.nameEditText.text.toString(),
                        cemeteryLocation = binding.locationEditText.text.toString(),
                        cemeteryState = binding.stateEditText.text.toString(),
                        cemeteryCounty =  binding.countyEditText.text.toString(),
                        township = binding.townshipEditText.text.toString(),
                        range = binding.rangeEditText.text.toString(),
                        section = binding.sectionEditText.text.toString(),
                        spot = binding.spotEditText.text.toString(),
                        firstYear = binding.firstYearEditText.text.toString()
                    )

                viewModel.insertNewCemetery(cemetery)
                finish()
            }
        }
    }

    private val locationCallback = object : LocationCallback() { //We say what happens when the fusedLocationClient.requestLocationUpdates returns location
        override fun onLocationResult(locationResult: LocationResult?) {
            locationResult ?: return
            for (location in locationResult.locations){
                // Update UI with location data
                // ...
                val addressList: List<Address>? = geocoder.getFromLocation(location.latitude, location.longitude, 1) //converts latitude and longitude to an address
                if (addressList != null && addressList.isNotEmpty()) {
                    val address: Address = addressList?.get(0)
                    val sb = StringBuilder()
                    for (i in 0..address.maxAddressLineIndex) {
                        sb.append(address.getAddressLine(i)).append(",")
                    }
                    sb.deleteCharAt(sb.length - 1) //
                    binding.locationEditText.setText(sb) //set the text to the adress
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        fusedLocationClient?.requestLocationUpdates(locationRequest, //returns a Task object that longitude and lat can be extracted from
            locationCallback,                                           //send a callback that we define ourselves
            Looper.getMainLooper()) //started on a looper thread
    }


    private fun isLocationEnabled(): Boolean { //if false take user to location settings
        val locationManager: LocationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun showRationalDialogForPermissions() { //show to the user if their permission are not set
        AlertDialog.Builder(this)
            .setMessage("It Looks like you have turned off permissions required for this feature. It can be enabled under Application Settings")
            .setPositiveButton(
                "GO TO SETTINGS"
            ) { _, _ ->
                try {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", packageName, null)
                    intent.data = uri
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                }
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }.show()
    }

    override fun onPause() {
        super.onPause()
        stopLocationUpdates()

    }

    private fun stopLocationUpdates(){
        fusedLocationClient.removeLocationUpdates(locationCallback) //stop requesting location
    }


}