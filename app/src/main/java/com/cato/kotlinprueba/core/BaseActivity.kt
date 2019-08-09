package com.cato.kotlinprueba.core

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity



/**
 * Base Actividad de la cual se va a exteder las otras actividades de la app
 */

abstract class BaseActivity : AppCompatActivity() {






    protected fun next(context: Activity, bundle: Bundle?, activity: Class<*>, destroy: Boolean) {
        val intent = Intent(context, activity)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivity(intent)
        if (destroy) context.finish()
    }


    protected fun nextActivity(context: Activity, bundle: Bundle?, activity: Class<*>, destroy: Boolean) {
        val intent = Intent(context, activity)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivity(intent)
        if (destroy) context.finish()
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



    override protected fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(newBase)
    }

}
