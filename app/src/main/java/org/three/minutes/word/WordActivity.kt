package org.three.minutes.word

import android.content.Context
import android.hardware.input.InputManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import org.three.minutes.R
import org.three.minutes.databinding.ActivityWordBinding

class WordActivity : AppCompatActivity(),TextView.OnEditorActionListener {
    private val mBinding : ActivityWordBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_word)
    }
    private lateinit var mImm : InputMethodManager

    private lateinit var mSearchView : SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding.apply {
            lifecycleOwner = this@WordActivity
        }

        mImm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        settingToolbar()


    }

    private fun settingToolbar() {

        mBinding.wordToolbar.setNavigationOnClickListener {
            if(mBinding.searchBoxEdt.visibility == View.VISIBLE){
                downKeyBoard()
            }
            else{
                Toast.makeText(this,"Go Back",Toast.LENGTH_SHORT).show()
            }
        }

        mBinding.searchBoxEdt.setOnEditorActionListener(this)

        mBinding.searchImg.setOnClickListener {
            mBinding.searchBoxEdt.apply {
                visibility = View.VISIBLE
                isFocusableInTouchMode = true
                requestFocus()
                mImm.showSoftInput(this,0)
            }
        }
    }

    private fun downKeyBoard(){
        if(mImm.isAcceptingText){
            mImm.hideSoftInputFromWindow(this.currentFocus?.windowToken,0)
            mBinding.searchBoxEdt.apply {
                visibility = View.GONE
                clearFocus()
            }
        }
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if(EditorInfo.IME_ACTION_SEARCH == actionId){
            val searchResult = mBinding.searchBoxEdt.text.toString()
            downKeyBoard()
            Toast.makeText(this,searchResult,Toast.LENGTH_SHORT).show()
        }
        else{
            return false
        }
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        downKeyBoard()
    }

}