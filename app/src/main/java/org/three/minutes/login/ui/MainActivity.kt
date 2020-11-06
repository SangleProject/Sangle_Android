package org.three.minutes.login.ui


import android.content.Intent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import gun0912.tedkeyboardobserver.TedKeyboardObserver
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import org.three.minutes.R
import org.three.minutes.ThreeApplication
import org.three.minutes.databinding.ActivityMainBinding
import org.three.minutes.home.ui.HomeActiviy
import org.three.minutes.signup.ui.SignupActivity
import org.three.minutes.singleton.GoogleLoginObject
import org.three.minutes.singleton.StatusObject
import kotlin.coroutines.CoroutineContext


class MainActivity : AppCompatActivity(), CoroutineScope {
    private lateinit var job: Job

    //    override val coroutineContext: CoroutineContext
//        get() = Dispatchers.Main + job
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

    private val GOOGLE_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        mBinding.activity = this
        mImm = ThreeApplication.getInstance().getInputMethodManager()
        job = Job()

        //상태바 투명으로 만들기
        StatusObject.setStatusBar(this)

        // 파이어베이스 인증객체 초기화 + 클라이언트 가져오기
        GoogleLoginObject.settingGoogle(this, gso)


        // 구글 로그인 버튼 클릭 시 구글 로그인 연동
        mBinding.signinGoogle.setOnClickListener {
            googleSignIn()
        }

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
                if(!isShow){
                    clearFocus()
                }
            }

    }

    override fun onStart() {
        super.onStart()
        autoLogin(GoogleLoginObject.auth.currentUser)
    }


    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

//    구글 로그아웃
//    private fun googleSignOut() {
//        GoogleLoginObject.auth.signOut()
//        GoogleLoginObject.googleClient.revokeAccess().addOnCompleteListener {
//            Toast.makeText(this,"SignOutSuccess",Toast.LENGTH_SHORT).show()
//        }
//    }

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
        val intent = Intent(this, HomeActiviy::class.java)
        startActivity(intent)
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
                Log.d("GoogleInfo", "id : ${account.id} , idToken : ${account.idToken}")
                Log.d(
                    "GoogleInfo",
                    "email : ${account.email} , displayName : ${account.displayName}"
                )
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {

            }
        }
    }

    // 인증에 성공했을 때 실제로 로그인이 되었는지?
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        GoogleLoginObject.auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "GoogleLoginOk", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "GoogleLoginFail", Toast.LENGTH_SHORT).show()
                }
            }

    }

    // 구글아이디로 로그인했으면 자동 로그인
    private fun autoLogin(user: FirebaseUser?) {
        if (user != null) {
            Toast.makeText(this, "Auto Login : ${user.email}", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Not yet Auto Login", Toast.LENGTH_SHORT).show()
        }
    }
}