package com.example.mybrowserandroidapp.utility

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface

object Utility {
    fun showAlertDialog(
        context: Context,
        title: String?,
        message: String?,
        positiveListener: DialogInterface.OnClickListener?,
        negativeListener: DialogInterface.OnClickListener?,
        dismissListener: DialogInterface.OnDismissListener?,
        positiveText: String?,
        negativeText: String?,
        cancelOnTouchOutside: Boolean
    ) {
        val builder = AlertDialog.Builder(context)
        builder.setMessage(message)
        builder.setTitle(title)
        builder.setPositiveButton(positiveText, positiveListener)
        if (negativeListener != null) {
            builder.setNegativeButton(negativeText, negativeListener)
        }
        if (null != dismissListener) {
            builder.setOnDismissListener(dismissListener)
        }
        val dialog = builder.create()
        dialog.setCancelable(cancelOnTouchOutside)
        dialog.setCanceledOnTouchOutside(cancelOnTouchOutside)
        dialog.show()
    }
}