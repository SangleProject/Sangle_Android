package org.three.minutes.util

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import okhttp3.ResponseBody
import org.json.JSONObject
import org.three.minutes.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

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

fun ViewPager.customChangeListener(pageSelect: (Int) -> Unit) {
    this.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) {
        }

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
        }

        override fun onPageSelected(position: Int) {
            pageSelect(position)
        }
    })
}

// margin값 동적 할당
fun View.margin(
    left: Float? = null,
    top: Float? = null,
    right: Float? = null,
    bottom: Float? = null
) {

    val params = this.layoutParams as ConstraintLayout.LayoutParams
    left?.run { params.leftMargin = dpToPx(this) }
    top?.run { params.topMargin = dpToPx(this) }
    right?.run { params.rightMargin = dpToPx(this) }
    bottom?.run { params.bottomMargin = dpToPx(this) }

    this.layoutParams = params

}

fun View.dpToPx(dp: Float): Int = context.dpToPx(dp)
fun Context.dpToPx(dp: Float): Int =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics).toInt()

fun TextView.changeBlueColor(){
    this.setTextColor(ContextCompat.getColor(this.context, R.color.main_blue))
}

fun TextView.changeBlackColor(){
    this.setTextColor(ContextCompat.getColor(this.context, R.color.black_60))
}

fun ImageView.showView(){
    this.visibility = View.VISIBLE
}
fun ImageView.hideView(){
    this.visibility = View.INVISIBLE
}

// retrofit 통신 확장 함수
fun<T> Call<T>.customEnqueue(
    onSuccess : (T) -> Unit,
    onError : (Response<T>) -> Unit = {}
){
    this.enqueue(object : Callback<T>{
        override fun onFailure(call: Call<T>, t: Throwable) {
            Log.e("Server Fail", "Server Closed")
        }

        override fun onResponse(call: Call<T>, response: Response<T>) {
            response.takeIf { it.isSuccessful }
                ?.body()
                ?.let{
                    onSuccess(it)
                } ?: onError(response)
        }
    })
}

fun Context.showToast(message : String){
    Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
}

@SuppressLint("SimpleDateFormat")
fun Date.formatDate() : String {
    val fm = SimpleDateFormat("yyyy.MM.dd (E) ")
    val fm2 = SimpleDateFormat("aH:mm",Locale.ENGLISH)
    return fm.format(this) + fm2.format(this)
}

// 돈 단위 변환
fun Int.formatCount() : String{
    val fm = DecimalFormat("###,###")
    return fm.format(this)
}