package com.example.permission

import android.Manifest
import android.widget.Toast
import androidx.lifecycle.ViewModel

class MainViewModel(
    private val activity: MainActivity
) : ViewModel() {
    private val permissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.READ_CONTACTS
    )
    private var counter = 0

    init {
        permissions.forEach {
            if (!PermissionHelper.checkPermission(activity, it)) {
                PermissionHelper.askPermission(activity, permissions)
            } else {
                counter++
                if (counter == permissions.size) Toast.makeText(
                    activity,
                    activity.resources.getString(R.string.all_permissions),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}