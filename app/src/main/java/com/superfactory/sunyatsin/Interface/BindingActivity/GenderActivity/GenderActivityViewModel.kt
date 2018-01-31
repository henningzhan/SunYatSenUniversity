package com.superfactory.sunyatsin.Interface.BindingActivity.GenderActivity

import com.superfactory.library.Bridge.Anko.observable
import com.superfactory.library.Bridge.Model.ToolbarBindingModel

/**
 * Created by vicky on 2018.01.31.
 *
 * @Author vicky
 * @Date 2018年01月31日  20:10:03
 * @ClassName 这里输入你的类名(或用途)
 */
class GenderActivityViewModel : ToolbarBindingModel() {
    override fun setToolbar(toolbarBindingModel: ToolbarBindingModel) {

    }

    var selected=observable(-1)
    val genderList = arrayListOf(
            GenderActivityItemViewModel("头像"),
            GenderActivityItemViewModel("头像")
    )
}

data class GenderActivityItemViewModel(val gender: String, var checked: Boolean = false)