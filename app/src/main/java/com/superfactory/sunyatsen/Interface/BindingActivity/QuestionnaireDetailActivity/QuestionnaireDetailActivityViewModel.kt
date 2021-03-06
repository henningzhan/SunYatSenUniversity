package com.superfactory.sunyatsen.Interface.BindingActivity.QuestionnaireDetailActivity

import android.content.Intent
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import com.superfactory.library.Bridge.Anko.observable
import com.superfactory.library.Bridge.Anko.observableNullable
import com.superfactory.library.Bridge.Model.ToolbarBindingModel
import com.superfactory.library.Utils.RxSorter
import com.superfactory.sunyatsen.R
import com.superfactory.sunyatsen.Struct.BaseStructImpl
import com.superfactory.sunyatsen.Struct.QuestionaireStruct.Question
import com.superfactory.sunyatsen.Struct.QuestionaireStruct.QuestionnaireDetailStruct
import com.xiasuhuei321.loadingdialog.view.LoadingDialog
import org.jetbrains.anko.collections.forEachWithIndex

/**
 * Created by vicky on 2018.02.01.
 *
 * @Author vicky
 * @Date 2018年02月01日  16:27:58
 * @ClassName 这里输入你的类名(或用途)
 */
class QuestionnaireDetailActivityViewModel(intent: Intent) : ToolbarBindingModel() {
    override fun setToolbar(toolbarBindingModel: ToolbarBindingModel) {
        toolbarBindingModel.title.value = "问卷"
        toolbarBindingModel.backgroundColor.value = Color.parseColor("#1688ff")
        val ctx = getStaticsContextRef()
        toolbarBindingModel.leftIcon.value = ContextCompat.getDrawable(ctx, R.drawable.back_stack_icon)
        toolbarBindingModel.rightText.value = "提交"
        toolbarBindingModel.rightTextColor.value = Color.parseColor("#b4b3b3")
    }

    override fun requestFailed(ld: LoadingDialog, error: Throwable?, witch: Int?) {
        ld.close()
        if (!TextUtils.isEmpty(error?.message)) {
            tips.value = error?.message!!
        }
    }

    override fun requestSuccess(ld: LoadingDialog, model: Any?, witch: Int?) {
        if (model == null) {
            ld.close()
            tips.value = "无法解析数据"
            return
        }
        if (model is BaseStructImpl) {
            if (model.success) {
                ld.close()
                ownerNotifier?.invoke(0, model)
            } else {
                ld.close()
                tips.value = model.msg ?: "未知错误"
            }
        }
    }

    val tips = observable("")


    var parentId: String? = null
    val questionnaireTitle = observable("")
    val questionnaireAmount = observable("")

    val amount = observable(0)
    val itemList = observableNullable<List<QuestionDetailActItemViewModel>?>(null)

    init {
        val struct = intent.extras.getParcelable<QuestionnaireDetailStruct>("data")
        questionnaireTitle.setStableValue(struct.body.qNaire.title)
        questionnaireAmount.setStableValue("共${struct.body.questionList.size}小题")

        parentId = struct.body.qNaire.id

        val temp = arrayListOf<QuestionDetailActItemViewModel>()
        RxSorter.sort<Question>(struct.body.questionList, { value1, value2 ->
            value1.sort.compareTo(value2.sort)
        }, {
            val that = it
            it.forEachWithIndex { i, it2 ->
                val list = ArrayList<Answer>()
                it2.qOptionsList.forEach {
                    list.add(Answer("${it.optionName}.${it.content}", it.score, it.sort, it.id))
                }
                RxSorter.sort<Answer>(list, { value1, value2 ->
                    value1.sort.compareTo(value2.sort)
                }, {
                    val type = that[i].type.toInt()
                    val qu = QuestionDetailActItemViewModel(temp.size,
                            "${i + 1}.${struct.body.qNaire.title}(${when (type) {
                                1 -> {
                                    "单选"
                                }
                                2 -> {
                                    "双选"
                                }
                                else -> {
                                    "多选"
                                }
                            }
                            })",
                            it2.parentId,
                            it2.id,
                            type,
                            list)
                    temp.add(qu)
                })
            }
            itemList.value = temp
        })
    }


}


data class QuestionDetailActItemViewModel(val index: Int, val question: String, val parentId: String, val questionId: String, val type: Int, val answerList: List<Answer>?) {
    var selected: Array<AnswerCheckBox?>? = null

    init {
        selected = arrayOfNulls<AnswerCheckBox>(answerList?.size ?: 0)
        answerList?.forEachWithIndex { i, answer ->
            selected!![i] = AnswerCheckBox(i)
        }
    }
}

data class AnswerCheckBox(val index: Int, var isChecked: Boolean = false)


data class Answer(val name: String, val scope: String, val sort: Int, val optionId: String, var checked: Boolean = false)