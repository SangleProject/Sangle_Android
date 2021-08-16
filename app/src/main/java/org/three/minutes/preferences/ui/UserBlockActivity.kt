package org.three.minutes.preferences.ui

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.three.minutes.R
import org.three.minutes.architect.data.ResponseBlockedUser
import org.three.minutes.architect.presentation.base.BaseActivity
import org.three.minutes.databinding.ActivityUserBlockBinding
import org.three.minutes.preferences.adapter.BlockedUserListAdapter
import org.three.minutes.preferences.viewmodel.UserBlockViewModel
import org.three.minutes.util.CustomDialog
import org.three.minutes.util.showToast

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class UserBlockActivity : BaseActivity<ActivityUserBlockBinding>(R.layout.activity_user_block) {

    private val userBlockViewModel: UserBlockViewModel by viewModels()
    private val blockedUserList: BlockedUserListAdapter by lazy { BlockedUserListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.run {
            lifecycleOwner = this@UserBlockActivity
        }
        initBlockedUserRcv()
        userBlockViewModel.getBlockedUserList()
    }

    override fun setObserve() {
        super.setObserve()

        userBlockViewModel.blockedUsersLiveData.observe(this) {
            it?.let { userList ->
                Log.i("유저 차단 목록", userList.toString())
                if (userList.isNotEmpty()) {
                    binding.rcvBlockedUser.visibility = View.VISIBLE
                    blockedUserList.blockedUserDiffUtil.set(userList.toMutableList())
                }
                else {
                    binding.rcvBlockedUser.visibility = View.GONE
                }
            }
        }

        userBlockViewModel.isSuccessDeleteBlockUser.observe(this) {
            it?.let { isSuccess ->
                if (isSuccess) {
                    userBlockViewModel.deletedBlockUser?.let { user ->
                        showToast("${user.nickName}님이 차단 해제되었습니다.")
                    } ?: kotlin.run {
                        showToast("잠시 후 다시 시도해주세요.")
                    }
                }
            }
        }

        userBlockViewModel.deleteBlockUserErrorCode.observe(this) {
            it?.let { errorCode ->
                showToast("오류가 발생했어요. 잠시 후에 다시 시도해 주세요. error : $errorCode")
            }
        }


    }

    override fun setClickEvent() {
        super.setClickEvent()

        blockedUserList.setOnClickItem(object : BlockedUserListAdapter.ClickListener {
            override fun onCancelBlock(blockedUser: ResponseBlockedUser, position: Int) {
                CustomDialog(
                    context = this@UserBlockActivity,
                    title = "${blockedUser.nickName}님을 차단 해제하시겠어요?",
                    content = getString(R.string.are_you_really_delete_block_user_contents),
                    cancelTitle = "그대로 둘게요.",
                    okTitle = "네, 차단 해제할게요",
                    dialogImg = R.drawable.illust_popup02
                ).apply {
                    setDialogClickListener(object : CustomDialog.ClickListener {
                        override fun setOnOk(dialog: Dialog) {
                            userBlockViewModel.deleteBlockUser(blockedUser, position)
                        }

                        override fun setOnCancel(dialog: Dialog) {
                        }
                    })
                }.show()
            }
        })
    }

    private fun initBlockedUserRcv() {
        binding.rcvBlockedUser.run {
            adapter = blockedUserList
        }
    }
}