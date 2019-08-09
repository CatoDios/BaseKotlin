package com.cato.kotlinprueba.core

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment


/**
 * Created by manu on 14/06/16.
 */
abstract class BaseFragment : Fragment() {

    var isLoading = false


    val isOpenKeyboard: Boolean
        get() {
            val imm = getActivity()?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

            return if (imm.isAcceptingText) {
                true
            } else {
                false
            }
        }


    protected fun nextActivity(context: Activity, bundle: Bundle?, activity: Class<*>, destroy: Boolean) {
        val intent = Intent(context, activity)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivity(intent)
        if (destroy) context.finish()
    }


    protected fun startLoading(v: View) {
        v.visibility = View.VISIBLE

    }

    protected fun endLoading(v: View) {
        v.visibility = View.GONE
    }

    protected fun newActivityClearPreview(context: Activity, bundle: Bundle?, activity: Class<*>) {
        val intent = Intent(context, activity)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivity(intent)
        context.finish()
    }

    protected fun nextActivityNewTask(context: Activity, bundle: Bundle?, activity: Class<*>, destroy: Boolean) {
        val intent = Intent(context, activity)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivity(intent)
        if (destroy) context.finish()
    }


    fun openWebPage(url: String) {
        val webpage = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, webpage)
        if (intent.resolveActivity(activity?.packageManager) != null) {
            startActivity(intent)
        }
    }




    fun visibleLayoutToogle(active: Boolean, view: View) {
        if (active) {
            view.visibility = View.VISIBLE
        } else {
            view.visibility = View.GONE
        }

    }

    companion object {
        val PLACE_AUTOCOMPLETE_REQUEST_CODE = 100
        private val LOG_TAG = "PlaceSelectionListener"
    }


}
