package org.three.minutes.word

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.three.minutes.R
import org.three.minutes.databinding.ActivityWordBinding
import org.three.minutes.word.ui.WordFragment
import org.three.minutes.word.viewmodel.WordViewModel
import kotlin.coroutines.CoroutineContext

class WordActivity : AppCompatActivity(), TextView.OnEditorActionListener, CoroutineScope {
    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private val mBinding: ActivityWordBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_word)
    }
    private lateinit var mImm: InputMethodManager

    private val mViewModel: WordViewModel by viewModels()
    private val wordFragment : WordFragment by lazy {
        WordFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding.apply {
            lifecycleOwner = this@WordActivity
            viewModel = mViewModel
        }

        mImm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        settingToolbar()
        settingFragment()
    }

    private fun settingFragment() {
       supportFragmentManager.beginTransaction().add(mBinding.containerLayout.id,wordFragment).commit()
    }

    private fun settingToolbar() {

        mBinding.wordToolbar.setNavigationOnClickListener {
            if (mBinding.searchBoxEdt.visibility == View.VISIBLE) {
                downKeyBoard()
            } else {
                finish()
            }
        }

        mBinding.searchBoxEdt.setOnEditorActionListener(this)

        mBinding.searchImg.setOnClickListener {
            mBinding.searchBoxEdt.apply {
                visibility = View.VISIBLE
                isFocusableInTouchMode = true
                requestFocus()
                mImm.showSoftInput(this, 0)
            }
        }
    }

    private fun downKeyBoard() {
        if (mImm.isAcceptingText) {
            mImm.hideSoftInputFromWindow(this.currentFocus?.windowToken, 0)
            mBinding.searchBoxEdt.apply {
                visibility = View.GONE
                clearFocus()
            }
        }
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if (EditorInfo.IME_ACTION_SEARCH == actionId) {
            val searchResult = mBinding.searchBoxEdt.text.toString()
            downKeyBoard()
            Toast.makeText(this, searchResult, Toast.LENGTH_SHORT).show()
        } else {
            return false
        }
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        downKeyBoard()
    }

}