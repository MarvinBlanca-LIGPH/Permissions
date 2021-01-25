package com.example.permission

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

object PermissionHelper {
    private var alertDialog: AlertDialog? = null
    const val requestCode = 100

    fun checkPermission(context: Context, permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun askPermission(activity: Activity, permissions: Array<String>) {
        permissions.forEach {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                activity.shouldShowRequestPermissionRationale(it)
            ) {
                showPermissionRequiredDialog(activity, permissions)
            } else if (!checkPermission(activity, it)) {
                requestPermission(activity, permissions)
            }
        }
    }

    private fun requestPermission(
        activity: Activity,
        permissions: Array<String>
    ) {
        ActivityCompat.requestPermissions(activity, permissions, requestCode)
    }

    private fun showPermissionRequiredDialog(
        activity: Activity,
        permissions: Array<String>
    ) {
        if (alertDialog == null) {
            alertDialog = AlertDialog.Builder(activity)
                .setCancelable(false)
                .setTitle(activity.resources.getString(R.string.important))
                .setMessage(activity.resources.getString(R.string.permissions_required))
                .setPositiveButton(activity.resources.getString(R.string.okay)) { _: DialogInterface, _: Int ->
                    requestPermission(activity, permissions)
                }.create()
        } else if (alertDialog?.isShowing == false) {
            alertDialog?.show()
        }
    }
}