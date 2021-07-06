package org.three.minutes.login.ui


import android.content.Intent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialog
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import gun0912.tedkeyboardobserver.TedKeyboardObserver
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_calender.*
import kotlinx.coroutines.*
import org.three.minutes.R
import org.three.minutes.ThreeApplication
import org.three.minutes.databinding.ActivityMainBinding
import org.three.minutes.home.ui.HomeActivity
import org.three.minutes.login.data.RequestGoogleLoginData
import org.three.minutes.login.data.RequestLoginData
import org.three.minutes.login.viewmodel.LogInViewModel
import org.three.minutes.server.SangleServiceImpl
import org.three.minutes.signup.ui.SignupActivity
import org.three.minutes.singleton.GoogleLoginObject
import org.three.minutes.singleton.PopUpObject
import org.three.minutes.singleton.StatusObject
import org.three.minutes.util.customEnqueue
import org.three.minutes.util.showToast
import kotlin.coroutines.CoroutineContext


class MainActivity : AppCompatActivity(), CoroutineScope {
    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var mImm: InputMethodManager
    private val gso: GoogleSignInOptions by lazy {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
    }
    private val mBinding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_main)
    }
    private val mViewModel : LogInViewModel by viewModels()

    private val GOOGLE_CODE = 100

    private lateinit var progress : AppCompatDialog

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        mBinding.apply {
            lifecycleOwner = this@MainActivity
            activity = this@MainActivity
            viewModel = mViewModel
        }

        mImm = ThreeApplication.getInstance().getInputMethodManager()
        job = Job()

        progress = PopUpObject.setLoading(this)

        //상태바 투명으로 만들기
        StatusObject.setStatusBar(this)

        // 파이어베이스 인증객체 초기화 + 클라이언트 가져오기
        GoogleLoginObject.settingGoogle(this, gso)

        // 구글 로그인 버튼 클릭 시 구글 로그인 연동
        mBinding.signinGoogle.setOnClickListener {
            googleSignIn()
        }

        getLogOutIntent()

        launch {
            mBinding.apply {
                mainTitleTxt.visibility = View.INVISIBLE
                subTitleTxt.visibility = View.INVISIBLE
            }
            val mainTitleAnim = AnimationUtils.loadAnimation(
                this@MainActivity, R.anim.maintitle_show_animation
            )

            val subTitleAnim = AnimationUtils.loadAnimation(
                this@MainActivity, R.anim.subtitle_show_animation
            )

            delay(1000)

            mBinding.apply {
                mainTitleTxt.visibility = View.VISIBLE
                mainTitleTxt.animation = mainTitleAnim
                subTitleTxt.visibility = View.VISIBLE
                subTitleTxt.animation = subTitleAnim
            }
        }

        mBinding.mainLayout.setOnClickListener {
            clearFocus()
        }
        TedKeyboardObserver(this)
            .listen { isShow ->
                if (!isShow) {
                    clearFocus()
                }
            }

    }

    private fun getLogOutIntent() {
        val code = intent.getIntExtra("LogOut",GoogleLoginObject.GoogleLogInCode.NOT_GOOGLE_LOGIN_CODE.code)
        if (code == GoogleLoginObject.GoogleLogInCode.LOG_OUT_CODE.code){
            googleSignOut()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    // 구글 로그아웃
    private fun googleSignOut() {
        GoogleLoginObject.auth.signOut()
        GoogleLoginObject.googleClient.revokeAccess()

        launch {
            ThreeApplication.getInstance().getDataStore().setReTokens("","")
        }
    }

    // 구글 로그인 intent 객체 생성 후 전달
    private fun googleSignIn() {
        val signInIntent = GoogleLoginObject.googleClient.signInIntent
        startActivityForResult(signInIntent, GOOGLE_CODE)
    }

    // 레이아웃 클릭 시 키보드 내리기
    private fun clearFocus() {
        //키보드가 활성화 되어있는지 확인 -> isAcceptingText
        if (mImm.isAcceptingText) {
            mImm.hideSoftInputFromWindow(this.currentFocus?.windowToken, 0)
        }

        mBinding.loginEdt.clearFocus()
        mBinding.passwordEdt.clearFocus()
    }

    // 로그인 클릭 시 확인 함수
    fun checkLogin() {

        SangleServiceImpl.service.postLogIn(
            RequestLoginData(
                email = mViewModel.email.value.toString(),
                password = mViewModel.password.value.toString()
            )
        ).customEnqueue(
            onSuccess = {
                launch {
                    //토큰 저장
                    ThreeApplication.getInstance().getDataStore().setToken(it.token)
                    ThreeApplication.getInstance().getDataStore().setRefreshToken(it.refresh)
                }

                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            },
            onError = {
                when {
                    it.code() == 401 -> {
                        showToast("아이디 또는 비밀번호를 확인해주세요.")
                    }
                    it.code() == 400 -> {
                        showToast("존재하지 않는 아이디입니다.")
                    }
                    else -> {
                        showToast("${it.code()}")
                    }
                }
            },
            onFailure = {
                showToast("서버 오류로 인해 잠시 후 다시 시도해주세요.")
            }
        )

//        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_hold)
    }

    fun goToSignUp() {
        val intent = Intent(this, SignupActivity::class.java)
        startActivity(intent)
    }

    // 앱 종료
    override fun onBackPressed() {
        super.onBackPressed()
        ActivityCompat.finishAffinity(this)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // 구글 로그인 인증 확인
        if (requestCode == GOOGLE_CODE) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
//                Log.d("GoogleInfo", "id : ${account.id} , idToken : ${account.idToken}")
//                Log.d(
//                    "GoogleInfo",
//                    "email : ${account.email} , displayName : ${account.displayName}"
//                )

                progress.show()
                firebaseAuthWithGoogle(account.idToken!!, account.email!!)
            } catch (e: ApiException) {

            }
        }
    }

    // 인증에 성공했을 때 실제로 로그인이 되었는지?
    private fun firebaseAuthWithGoogle(idToken: String, email: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        GoogleLoginObject.auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // 구글 로그인 인증 서버 통신

                    SangleServiceImpl.service.postLoginGoogle(
                        RequestGoogleLoginData(idToken = idToken)
                    ).customEnqueue(
                        onSuccess = {
                            if (it.user && it.status) {
                                // token과 refreshToken 저장
                                launch {

                                    ThreeApplication.getInstance().getDataStore()
                                        .setToken(it.token)

                                    ThreeApplication.getInstance().getDataStore()
                                        .setRefreshToken(it.refresh)
                                }

                                val intent = Intent(this, HomeActivity::class.java)
                                startActivity(intent)
                            } else {
                                val intent = Intent(this, SignupActivity::class.java)
                                intent.putExtra("google", true)
                                intent.putExtra("googleId", email)
                                startActivity(intent)
                            }
                            progress.dismiss()
                        },
                        onError = {
                            showToast("${it.code()}")
                            progress.dismiss()
                        },
                        onFailure = {
                            showToast("서버 오류로 인해 잠시 후 다시 시도해주세요.")
                            progress.dismiss()
                        }
                    )
                } else {
                    Toast.makeText(this, "GoogleLoginFail", Toast.LENGTH_SHORT).show()
                }
            }

    }

    // 구글아이디로 로그인했으면 자동 로그인
//    private fun autoLogin(user: FirebaseUser?) {
//        if (user != null) {
////            Toast.makeText(this, "Auto Login : ${user.email}", Toast.LENGTH_SHORT).show()
//        } else {
////            Toast.makeText(this, "Not yet Auto Login", Toast.LENGTH_SHORT).show()
//        }
//    }
}