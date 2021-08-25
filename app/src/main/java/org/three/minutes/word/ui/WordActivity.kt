package org.three.minutes.word.ui


import android.app.Dialog
import android.content.Intent
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
import org.three.minutes.R
import org.three.minutes.ThreeApplication
import org.three.minutes.databinding.ActivityWordBinding
import org.three.minutes.server.SangleServiceImpl
import org.three.minutes.util.CustomDialog
import org.three.minutes.util.customEnqueue
import org.three.minutes.word.data.RequestWrittenData
import org.three.minutes.word.viewmodel.WordViewModel
import org.three.minutes.writing.ui.WritingReadyActivity
import kotlin.coroutines.CoroutineContext

class WordActivity : AppCompatActivity(), TextView.OnEditorActionListener, CoroutineScope {
    private val TAG_WORD = "word"
    private val TAG_EMPTY = "empty"
    private val TAG_SEARCH = "search"
    private val TAG_LAST_DETAIL = "lastDetail"

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

    private val lastDetailFragment = supportFragmentManager.findFragmentByTag(TAG_LAST_DETAIL)
        ?: PostDetailFragment()

    private val mViewModel: WordViewModel by viewModels()


    private val notWrittenDialog: CustomDialog by lazy {
        CustomDialog(
            context = this,
            title = getString(R.string.is_written_pop_up_title),
            content = getString(R.string.is_written_pop_up_contents),
            cancelTitle = "잠시 구경할래요",
            okTitle = "지금 쓰러 갈게요!",
            dialogImg = R.drawable.illust_popup04,
            isCancelable = false
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job()
        mViewModel.getToken.observe(this, {
            mViewModel.token = it
        })
        mViewModel.callTopic()
        mBinding.apply {
            lifecycleOwner = this@WordActivity
            viewModel = mViewModel
        }
        mImm = ThreeApplication.getInstance().getInputMethodManager()
        settingToolbar()
        settingFragment()


        TedKeyboardObserver(this).listen { isShow ->
            if (isShow) {
                mViewModel.isKeyboardShow = true
            } else {
                mViewModel.isKeyboardShow = false
                if (mBinding.searchBoxEdt.visibility == View.VISIBLE) {
                    mBinding.searchBoxEdt.clearFocus()
                }
            }
        }

        mBinding.containerLayout.setOnClickListener {
            downKeyBoard(true)
        }

        getIntentData()

        mViewModel.lastDetailTopic.observe(this) {
            it?.let { topic ->
                if (topic.isNotBlank())
                    SangleServiceImpl.service.postWritten(
                        token = mViewModel.token,
                        body = RequestWrittenData(topic = topic)
                    ).customEnqueue(
                        onSuccess = { result ->
                            if (!result.written) {
                                notWrittenDialog.setDialogClickListener(object :
                                    CustomDialog.ClickListener {
                                    override fun setOnOk(dialog: Dialog) {
                                        val intent =
                                            Intent(
                                                mBinding.root.context,
                                                WritingReadyActivity::class.java
                                            )
                                        intent.putExtra("topic", topic)
                                        startActivity(intent)
                                    }

                                    override fun setOnCancel(dialog: Dialog) {
                                    }
                                })
                                notWrittenDialog.show()
                            }
                        }
                    )
            }
        }
    }

    private fun getIntentData() {
        val topic = intent.getStringExtra("topic")
        if (topic != null) {
            mViewModel.callPastDetailPopular(topic)
            mViewModel.filter.value = "최신순"
            replaceDetailFragment()
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
            if (mViewModel.searchWord.value.isNullOrBlank()) {
                replaceSearchFragment(searchEmptyFragment, TAG_EMPTY)
            } else {
                replaceSearchFragment(searchResultFragment, TAG_SEARCH)
            }

        }
    }

    private fun downKeyBoard(isSearch: Boolean) {
        if (mViewModel.isKeyboardShow) {
            mImm.hideSoftInputFromWindow(
                this.currentFocus?.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
            mBinding.searchBoxEdt.apply {
                clearFocus()
            }
        } else {
            if (!isSearch) {
                replaceWordFragment()
                mBinding.searchBoxEdt.visibility = View.GONE
            }
        }

    }

    private fun replaceWordFragment() {
        supportFragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    private fun replaceSearchFragment(fragment: Fragment, tag: String) {
        supportFragmentManager
            .beginTransaction()
            .replace(mBinding.containerLayout.id, fragment, tag)
            .addToBackStack(tag)
            .commit()
    }

    fun replaceDetailFragment() {
        supportFragmentManager
            .beginTransaction()
            .replace(mBinding.containerLayout.id, lastDetailFragment, TAG_LAST_DETAIL)
            .addToBackStack(TAG_LAST_DETAIL)
            .commit()
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if (EditorInfo.IME_ACTION_SEARCH == actionId) {
            downKeyBoard(true)
            val searchResult = mBinding.searchBoxEdt.text.toString()
            if (searchResult.isNotBlank()) {
                mViewModel.searchWord.value = searchResult
                mViewModel.isFilterTopic.value = true
                mViewModel.callSearchTopic()
                mViewModel.callSearchContents()
                mViewModel.callSearchUser()
                if (supportFragmentManager.findFragmentByTag(TAG_SEARCH) != SearchResultFragment()) {
                    replaceSearchFragment(searchResultFragment, TAG_SEARCH)
                }
            } else {
                supportFragmentManager.beginTransaction()
                    .replace(mBinding.containerLayout.id, searchEmptyFragment, TAG_EMPTY)
                    .commit()
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