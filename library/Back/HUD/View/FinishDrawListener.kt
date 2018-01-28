package com.superfactory.library.Graphics.HUD.View

import android.view.View

/**
 * Created by Luo_xiasuhuei321@163.com on 2016/11/6.
 * desc:
 */
interface FinishDrawListener {
    /**
     * 分发绘制完成事件
     *
     * @param v 绘制完成的View
     */
    fun dispatchFinishEvent(v: View)


}