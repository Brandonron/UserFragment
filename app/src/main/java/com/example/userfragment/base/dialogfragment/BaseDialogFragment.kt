package com.example.userfragment.ui.detail

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.DialogFragment
import com.example.userfragment.R

open class BaseDialogFragment : DialogFragment() {

    override fun onDestroyView() {
        if (dialog != null && retainInstance) {
            dialog!!.setDismissMessage(null)
        }
        super.onDestroyView()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true

        setStyle(STYLE_NORMAL, R.style.BaseDialogFragmentStyle)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)
        return dialog
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initWindowParams(dialog!!)
    }

    open fun getWindowWidthPercent(): Double {
        return ViewGroup.LayoutParams.MATCH_PARENT.toDouble()
    }

    open fun getWindowHeightPercent(): Double {
        return ViewGroup.LayoutParams.MATCH_PARENT.toDouble()
    }

    fun initWindowParams(dialog: Dialog) {
        val window = dialog.window
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val layoutParams = window.attributes
        layoutParams.gravity = Gravity.BOTTOM
        layoutParams.verticalMargin = 0f
        layoutParams.horizontalMargin = 0f

        val windowWidth = dialog.context.resources.displayMetrics.widthPixels
        var dialogWidth = 0
        if (getWindowWidthPercent().toInt() == -1) {
            layoutParams.width = windowWidth
        } else if (getWindowWidthPercent().toInt() == -2) {
            layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT
        } else {
            layoutParams.width = (windowWidth * getWindowWidthPercent()).toInt()
        }

        val windowHeight = dialog.context.resources.displayMetrics.heightPixels
        var dialogHeight: Int = 0
        if (getWindowHeightPercent().toInt() == -1) {
            layoutParams.height = windowHeight - getStatusBarHeight(dialog.context)
        } else if (getWindowHeightPercent().toInt() == -2) {
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
        } else {
            layoutParams.height =
                (windowHeight * getWindowHeightPercent()).toInt() - getStatusBarHeight(dialog.context)
        }

//        if (windowHeight * 0.8 < dialogHeight) {
//            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
//        } else {
//            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
//        }

        // TODO: 2019/3/19 設置顯示動畫
        layoutParams.windowAnimations = R.style.BottomSlideStyle
        window.attributes = layoutParams
    }

    fun getStatusBarHeight(context: Context): Int {
        var height = 0
        val resourceId =
            context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            height = context.resources.getDimensionPixelSize(resourceId)
        }
        return height
    }
}