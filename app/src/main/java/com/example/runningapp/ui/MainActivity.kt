package com.example.runningapp.ui

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.runningapp.R
import com.example.runningapp.other.Constants.REQUEST_CODE_LOCATION_PERMISSIONS_B
import com.example.runningapp.other.Constants.REQUEST_CODE_LOCATION_PERMISSIONS_FCB
import com.example.runningapp.other.Constants.REQUEST_CODE_LOCATION_PERMISSIONS_FC_11
import com.example.runningapp.other.Constants.REQUEST_CODE_LOCATION_PERMISSIONS_FC_9
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        bottomNavigationView.setupWithNavController(navHostFragment.findNavController())

        navHostFragment.findNavController()
            .addOnDestinationChangedListener { _, destination, _ ->
                when(destination.id) {
                    R.id.statisticsFragment,R.id.runFragment,R.id.settingsFragment ->
                        bottomNavigationView.visibility = View.VISIBLE
                    else -> bottomNavigationView.visibility = View.GONE
                }

            }

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
            checkLocationPermissionFC(REQUEST_CODE_LOCATION_PERMISSIONS_FC_11)
        } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.Q) {
            checkLocationPermissionFCB(REQUEST_CODE_LOCATION_PERMISSIONS_FCB)
        } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermissionFC(REQUEST_CODE_LOCATION_PERMISSIONS_FC_9)
        }

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun Context.checkLocationPermissionFC(locationRequestCode : Int) {
        if (checkSinglePermission(Manifest.permission.ACCESS_FINE_LOCATION) &&
            checkSinglePermission(Manifest.permission.ACCESS_COARSE_LOCATION)) return
        val permList = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION)
        requestPermissions(permList, locationRequestCode)

    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun Context.checkLocationPermissionFCB(locationRequestCode : Int) {
        if (checkSinglePermission(Manifest.permission.ACCESS_FINE_LOCATION) &&
            checkSinglePermission(Manifest.permission.ACCESS_COARSE_LOCATION) &&
            checkSinglePermission(Manifest.permission.ACCESS_BACKGROUND_LOCATION)) return
        val permList = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_BACKGROUND_LOCATION)
        requestPermissions(permList, locationRequestCode)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun Context.checkBackgroundLocationPermissionB(backgroundLocationRequestCode: Int) {
        if (checkSinglePermission(Manifest.permission.ACCESS_BACKGROUND_LOCATION)) return
            AlertDialog.Builder(this)
                .setTitle(R.string.background_location_permission_title)
                .setMessage(R.string.background_location_permission_message)
                .setPositiveButton(R.string.yes) { _,_ ->
                    // this request will take user to Application's Setting page
                    requestPermissions(arrayOf(Manifest.permission.ACCESS_BACKGROUND_LOCATION), backgroundLocationRequestCode)
                }
                .setNegativeButton(R.string.no) { dialog,_ ->
                    dialog.dismiss()
                }
                .create()
                .show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_LOCATION_PERMISSIONS_FC_11) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted!", Toast.LENGTH_SHORT).show()
                if(Build.VERSION.SDK_INT > Build.VERSION_CODES.Q){
                    checkBackgroundLocationPermissionB(REQUEST_CODE_LOCATION_PERMISSIONS_B)
                }

            } else {
                Toast.makeText(this, "Permission Denied! Go to settings to activate Location Permission", Toast.LENGTH_SHORT).show()
            }
        }
        if (requestCode == REQUEST_CODE_LOCATION_PERMISSIONS_B) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Permission Denied!", Toast.LENGTH_SHORT).show()
            }
        }
        if (requestCode == REQUEST_CODE_LOCATION_PERMISSIONS_FCB) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED
                && grantResults[1] == PackageManager.PERMISSION_GRANTED
                && grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Background permission is necessary for functionality correctly", Toast.LENGTH_SHORT).show()
            }
        }

        if(requestCode == REQUEST_CODE_LOCATION_PERMISSIONS_FC_9){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Permission Denied!", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun Context.checkSinglePermission(permission: String) : Boolean {
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
    }
}