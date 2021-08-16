package org.three.minutes.preferences.ui

import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.three.minutes.R
import org.three.minutes.architect.presentation.base.BaseActivity
import org.three.minutes.databinding.ActivityUserBlockBinding
import org.three.minutes.preferences.adapter.BlockedUserListAdapter
import org.three.minutes.preferences.viewmodel.UserBlockViewModel

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
                blockedUserList.blockedUserDiffUtil.set(userList.toMutableList())
            }
        }

    }

    override fun setClickEvent() {
        super.setClickEvent()

    }

    private fun initBlockedUserRcv() {
        binding.rcvBlockedUser.run {
            adapter = blockedUserList
        }
    }
}