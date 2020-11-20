package org.three.minutes.word

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
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import gun0912.tedkeyboardobserver.TedKeyboardObserver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.three.minutes.R
import org.three.minutes.ThreeApplication
import org.three.minutes.databinding.ActivityWordBinding
import org.three.minutes.word.ui.SearchEmptyFragment
import org.three.minutes.word.ui.SearchResultFragment
import org.three.minutes.word.ui.WordFragment
import org.three.minutes.word.viewmodel.WordViewModel
import kotlin.coroutines.CoroutineContext

class WordActivity : AppCompatActivity(), TextView.OnEditorActionListener, CoroutineScope {
    private val TAG_WORD = "word"
    private val TAG_EMPTY = "empty"
    private val TAG_SEARCH = "search"
    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private val mBinding: ActivityWordBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_word)
    }
    private lateinit var mImm: InputMethodManager
    private val wordFragment = supportFragmentManager.findFragmentByTag(TAG_WORD)
        ?: WordFragment()
    private val searchEmptyFragment = supportFragmentManager.findFragmentByTag(TAG_EMPTY)
        ?: SearchEmptyFragment()

    private val searchResultFragment = supportFragmentManager.findFragmentByTag(TAG_SEARCH)
        ?: SearchResultFragment()

    private val mViewModel: WordViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job()
        mBinding.apply {
            lifecycleOwner = this@WordActivity
            viewModel = mViewModel
        }
        mImm = ThreeApplication.getInstance().getInputMethodManager()
        settingToolbar()
        settingFragment()

        TedKeyboardObserver(this).listen {isShow ->
            if(!isShow && mBinding.searchBoxEdt.visibility == View.VISIBLE){
                mBinding.searchBoxEdt.clearFocus()
            }
        }

    }

    private fun settingFragment() {
        supportFragmentManager.beginTransaction()
            .add(mBinding.containerLayout.id, wordFragment, TAG_WORD)
            .commit()
    }

    private fun settingToolbar() {

        mBinding.wordToolbar.setNavigationOnClickListener {
            if (mBinding.searchBoxEdt.visibility == View.VISIBLE) {
                downKeyBoard(false)
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
            if (mViewModel.searchWord.value.isNullOrBlank())
                replaceSearchFragment(searchEmptyFragment,TAG_EMPTY)
            else
                replaceSearchFragment(searchResultFragment,TAG_SEARCH)
        }
    }

    private fun downKeyBoard(isSearch : Boolean) {
        if (mImm.isAcceptingText) {
            mImm.hideSoftInputFromWindow(this.currentFocus?.windowToken, 0)
            mBinding.searchBoxEdt.apply {
                clearFocus()
            }
        }
        if(!isSearch){
            replaceWordFragment()
            mBinding.searchBoxEdt.visibility = View.GONE
        }
    }

    private fun replaceWordFragment() {
        supportFragmentManager.popBackStackImmediate(null,FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    private fun replaceSearchFragment(fragment : Fragment, tag : String){
        supportFragmentManager
            .beginTransaction()
            .replace(mBinding.containerLayout.id, fragment, tag)
            .addToBackStack(tag)
            .commit()
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if (EditorInfo.IME_ACTION_SEARCH == actionId) {
            downKeyBoard(true)
            val searchResult = mBinding.searchBoxEdt.text.toString()
            if(!searchResult.isBlank()){
                mViewModel.searchWord.value = searchResult
                if(supportFragmentManager.findFragmentByTag(TAG_SEARCH) != SearchResultFragment())
                    replaceSearchFragment(searchResultFragment,TAG_SEARCH)
            }
            else{
                mViewModel.searchWord.value = ""
                if(supportFragmentManager.findFragmentByTag(TAG_EMPTY) != SearchEmptyFragment())
                    replaceSearchFragment(searchEmptyFragment,TAG_EMPTY)
            }
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
        if (mBinding.searchBoxEdt.visibility == View.VISIBLE) {
            downKeyBoard(false)
        }
    }

}