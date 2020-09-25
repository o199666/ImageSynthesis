@file:Suppress("DEPRECATION")

package com.yun.utils.image

import android.content.Context
import android.view.WindowManager
import android.util.TypedValue



/**
 * Created by xiaor on 2018/4/4.
 */

object SizeUtils {

    /**
     * 获取屏幕宽度
     *
     * @param context
     * @return
     */
    fun getWidth(context: Context): Int {
        val wm = context
                .getSystemService(Context.WINDOW_SERVICE) as WindowManager
        return wm.defaultDisplay.width

    }

    /**
     * 获取屏幕高度
     *
     * @param context
     * @return
     */
    fun getHeight(context: Context): Int {
        val wn = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        return wn.defaultDisplay.height
    }

    /**
     * 根据手机分辨率从DP转成PX
     *
     * @param context
     * @param dpValue
     * @return
     */
    fun dip2px(context: Context, dpValue: Float): Float {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toFloat()
    }

    fun px2db(context: Context?, pxValue: Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, pxValue, context?.resources?.displayMetrics).toInt()

    }

}
