package org.three.minutes.word.ui


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import gun0912.tedkeyboardobserver.TedKeyboardObserver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.three.minutes.R
import org.three.minutes.ThreeApplication
import org.three.minutes.databinding.ActivityWordBinding
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
        mViewModel.getToken.observe(this, {
            mViewModel.token = it
        })

        mBinding.apply {
            lifecycleOwner = this@WordActivity
            viewModel = mViewModel
        }
        mImm = ThreeApplication.getInstance().getInputMethodManager()
        settingToolbar()
        settingFragment()
        setObserve()

        TedKeyboardObserver(this).listen { isShow ->
            if (isShow){
                mViewModel.isKeyboardShow = true
            }
            else{
                mViewModel.isKeyboardShow = false
                if (mBinding.searchBoxEdt.visibility == View.VISIBLE){
                    mBinding.searchBoxEdt.clearFocus()
                }
            }
        }

        mBinding.containerLayout.setOnClickListener {
            downKeyBoard(true)
        }
    }

    private fun setObserve() {
        // 검색 결과가 존재할 경우 결과 프래그먼트 출력
        mViewModel.searchResultList.observe(this, {
            if (it.isNotEmpty()) {
                if (supportFragmentManager.findFragmentByTag(TAG_SEARCH) != SearchResultFragment())
                    replaceSearchFragment(searchResultFragment, TAG_SEARCH)
            }
        })

        // 검색 결과가 존재하지 않을 경유 비어있는 프래그먼트 출력
        // 처음 글감 화면 진입 시 기본 화면이 보여져야 해서 하나의 observe를 사용하는게 힘듦
        mViewModel.isSearchEmpty.observe(this, {
            if (it) {
                if (supportFragmentManager.findFragmentByTag(TAG_EMPTY) != SearchEmptyFragment()){
                    replaceSearchFragment(searchEmptyFragment, TAG_EMPTY)
                    mViewModel.searchResultList.value = listOf()
                }

            }
        })
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
            if (mViewModel.searchWord.value.isNullOrBlank() || mViewModel.searchResultList.value!!.isNullOrEmpty()){
                replaceSearchFragment(searchEmptyFragment, TAG_EMPTY)
            }
            else{
                replaceSearchFragment(searchResultFragment, TAG_SEARCH)
            }

        }
    }

    private fun downKeyBoard(isSearch: Boolean) {
        if (mViewModel.isKeyboardShow){
            mImm.hideSoftInputFromWindow(
                this.currentFocus?.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
            mBinding.searchBoxEdt.apply {
                clearFocus()
            }
        }
        else{
            if (!isSearch){
                replaceWordFragment()
                mBinding.searchBoxEdt.visibility = View.GONE
            }
        }

    }

    private fun replaceWordFragment() {
        supportFragmentManager.popBackStackImmediate(null,FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    private fun replaceSearchFragment(fragment: Fragment, tag: String) {
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
            if (searchResult.isNotBlank()) {
                mViewModel.searchWord.value = searchResult
                mViewModel.callSearchRecent()
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