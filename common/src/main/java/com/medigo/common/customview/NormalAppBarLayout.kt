package com.medigo.common.customview

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import com.google.android.material.appbar.AppBarLayout
import com.medigo.common.R
import kotlinx.android.synthetic.main._appbarlayout_normal.view.*

class NormalAppBarLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : AppBarLayout(context, attrs) {

    init {
        LayoutInflater.from(context).inflate(R.layout._appbarlayout_normal, this, true)
        this.toolbar.navigationIcon = resources.getDrawable(R.drawable.ic_arrow_back_black, null)
    }

    fun title(func: (() -> Unit)? = null) {

    }

}