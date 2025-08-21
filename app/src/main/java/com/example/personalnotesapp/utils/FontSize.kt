package com.example.personalnotesapp.utils

import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.personalnotesapp.R

object FontSize {

    // Default base size that your UI was designed for (use 16 if that's your baseline)
    private const val DEFAULT_BASE_SP = 16f

    /**
     * Walk the view tree and update text sizes for all TextView subclasses.
     * - Stores original text size in sp under view tag R.id.original_text_size_sp
     * - Applies scaled size = originalSp * (targetBaseSp / DEFAULT_BASE_SP)
     */
    fun applyFontSizeToViews(root: View, targetBaseSp: Int) {
        val scale = targetBaseSp / DEFAULT_BASE_SP
        applyRecursively(root, scale)
    }

    private fun applyRecursively(view: View, scale: Float) {
        if (view is TextView) {
            // If we didn't store original size yet, store it now
            val tagVal = view.getTag(R.id.original_text_size_sp)
            val originalSp: Float = when (tagVal) {
                is Float -> tagVal
                else -> {
                    // textSize returns pixels. Convert to sp:
                    val px = view.textSize
                    val metrics = view.resources.displayMetrics
                    val original = px / metrics.scaledDensity
                    // store original sp for later use
                    view.setTag(R.id.original_text_size_sp, original)
                    original
                }
            }

            // compute new size and apply in SP
            val newSizeSp = originalSp * scale
            view.setTextSize(TypedValue.COMPLEX_UNIT_SP, newSizeSp)
        }

        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                applyRecursively(view.getChildAt(i), scale)
            }
        }
    }
}