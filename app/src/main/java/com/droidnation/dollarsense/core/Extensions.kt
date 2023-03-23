package com.droidnation.dollarsense.core

import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

object ViewBindingUtil {
    inline fun <reified T : ViewBinding> AppCompatActivity.bindView() : T {
        return T::class.java.getMethod("inflate", LayoutInflater::class.java)
            .invoke(null, layoutInflater) as T
    }
}
