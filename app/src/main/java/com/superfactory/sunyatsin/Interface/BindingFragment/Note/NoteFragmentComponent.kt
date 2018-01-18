package com.superfactory.sunyatsin.Interface.BindingFragment.Note

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import com.superfactory.library.Bridge.Anko.BindingComponent
import com.superfactory.library.Bridge.Anko.DslView.refresh
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.verticalLayout

/**
 * Created by vicky on 2018.01.18.
 *
 * @Author vicky
 * @Date 2018年01月18日  11:57:26
 * @ClassName 这里输入你的类名(或用途)
 */
class NoteFragmentComponent(viewModel: NoteFragmentViewModel) : BindingComponent<NoteFragment, NoteFragmentViewModel>(viewModel) {
    override fun createViewWithBindings(ui: AnkoContext<NoteFragment>): View = with(ui) {
        refresh {
            setEnableRefresh(true)
            setEnableLoadmore(false)
            verticalLayout {
                backgroundColor = Color.WHITE
                lparams {
                    width = matchParent
                    height = matchParent
                }
            }
            lparams {
                width = ViewGroup.LayoutParams.MATCH_PARENT
                height = ViewGroup.LayoutParams.MATCH_PARENT
            }
        }
    }

}