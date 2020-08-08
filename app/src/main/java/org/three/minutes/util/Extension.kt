package org.three.minutes.util

import android.app.Activity
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import gun0912.tedkeyboardobserver.TedKeyboardObserver

fun Activity.keyBoardObserve(hide : () -> Unit){
    TedKeyboardObserver(this)
        .listen { isShow ->
            if(!isShow){
                hide()
            }
        }
}

fun EditText.textCheckListener(textCheck: (CharSequence?) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(
            s: CharSequence?,
            start: Int,
            count: Int,
            after: Int
        ) = Unit

        override fun onTextChanged(
            s: CharSequence?,
            start: Int,
            before: Int,
            count: Int
        ) {
            textCheck(s)
        }
    })
}