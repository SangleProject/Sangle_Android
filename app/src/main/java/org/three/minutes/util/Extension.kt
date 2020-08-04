package org.three.minutes.util

import android.app.Activity
import gun0912.tedkeyboardobserver.TedKeyboardObserver

fun Activity.keyBoardObserve(hide : () -> Unit){
    TedKeyboardObserver(this)
        .listen { isShow ->
            if(!isShow){
                hide()
            }
        }
}