package org.three.minutes.architect.presentation.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import org.three.minutes.singleton.StatusObject

abstract class BaseActivity<T : ViewDataBinding>(@LayoutRes private val resId: Int)
    : AppCompatActivity() {

    protected lateinit var binding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, resId)

        StatusObject.setStatusBar(this)

        setObserve()
        setClickEvent()

    }

    open fun setObserve() {}
    open fun setClickEvent() {}

}
