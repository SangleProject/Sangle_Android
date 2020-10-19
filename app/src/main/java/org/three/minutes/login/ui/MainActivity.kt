package org.three.minutes.login.ui


import android.content.Intent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_main.*
import org.three.minutes.R
import org.three.minutes.databinding.ActivityMainBinding
import org.three.minutes.home.ui.HomeActiviy
import org.three.minutes.signup.ui.SignupActivity
import org.three.minutes.singleton.StatusObject


class MainActivity : AppCompatActivity() {

    private lateinit var mImm: InputMethodManager
    private val gso : GoogleSignInOptions by lazy {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
    }
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mGoogleSignInClient : GoogleSignInClient

    private val GOOGLE_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.activity = this
        mImm = getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as InputMethodManager

        //상태바 투명으로 만들기
        StatusObject.setStatusBar(this)

        // 파이어베이스 인증객체 초기화 + 클라이언트 가져오기
        mAuth = FirebaseAuth.getInstance()
        mGoogleSignInClient = GoogleSignIn.getClient(this,gso)


        // 구글 로그인 버튼 클릭 시 구글 로그인 연동
        binding.signinGoogle.setOnClickListener {
            googleSignIn()
        }

        // 구글 로그아웃
        binding.signoutGoogle.setOnClickListener {
            googleSignOut()
        }

    }

    private fun googleSignOut() {
        mAuth.signOut()
        mGoogleSignInClient.revokeAccess().addOnCompleteListener {
            Toast.makeText(this,"SignOutSuccess",Toast.LENGTH_SHORT).show()
        }
    }

    // 구글 로그인 intent 객체 생성 후 전달
    private fun googleSignIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent,GOOGLE_CODE)
    }

    // 레이아웃 클릭 시 키보드 내리기
    fun hideKeyboard() {
        //키보드가 활성화 되어있는지 확인 -> isAcceptingText
        if (mImm.isAcceptingText) {
            login_edt.clearFocus()
            password_edt.clearFocus()
            mImm.hideSoftInputFromWindow(this.currentFocus?.windowToken, 0)
        }

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
        if (requestCode == GOOGLE_CODE){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                Log.d("GoogleInfo","id : ${account.id} , idToken : ${account.idToken}")
                Log.d("GoogleInfo","email : ${account.email} , displayName : ${account.displayName}")
                firebaseAuthWithGoogle(account.idToken!!)
            }
            catch (e : ApiException){

            }
        }
    }

    // 인증에 성공했을 때 실제로 로그인이 되었는지?
    private fun firebaseAuthWithGoogle( idToken : String) {
        val credential = GoogleAuthProvider.getCredential(idToken,null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this){ task ->
                if (task.isSuccessful){
                    Toast.makeText(this,"GoogleLoginOk",Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(this,"GoogleLoginFail",Toast.LENGTH_SHORT).show()
                }
            }

    }
}