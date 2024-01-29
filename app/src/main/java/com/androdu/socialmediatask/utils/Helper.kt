package com.androdu.socialmediatask.utils

import android.content.Context
import android.graphics.Color
import com.androdu.socialmediatask.R
import com.labters.lottiealertdialoglibrary.ClickListener
import com.labters.lottiealertdialoglibrary.DialogTypes
import com.labters.lottiealertdialoglibrary.LottieAlertDialog

object Helper {
    fun showErrorDialog(
        context: Context,
        title: String = context.getString(R.string.error),
        desc: String,
        onClick: () -> Unit = {}
    ) {
        val dialog = LottieAlertDialog.Builder(context, DialogTypes.TYPE_ERROR)
            .setTitle(title)
            .setDescription(desc)
            .setPositiveText(context.getString(R.string.ok))
            .setPositiveButtonColor(Color.parseColor("#f44242"))
            .setPositiveTextColor(Color.parseColor("#ffffff"))
            .setPositiveListener(object : ClickListener {
                override fun onClick(dialog: LottieAlertDialog) {
                    dialog.dismiss()
                    onClick()
                }
            })
            .build()
        dialog.setCancelable(false)
        dialog.show()
    }
}