package seoil.capstone.som_pos.ui.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputEditText
import seoil.capstone.som_pos.GlobalApplication
import seoil.capstone.som_pos.R
import seoil.capstone.som_pos.data.network.api.LoginApi
import seoil.capstone.som_pos.util.Utility

class LoginActivity: AppCompatActivity(), LoginContract.View, View.OnClickListener, TextView.OnEditorActionListener{


    private var mPresenter: LoginPresenter? = null

    private var mSharedPreferences: SharedPreferences? = null
    private var mEditor: SharedPreferences.Editor? = null

    // resource
    private var mEditTextId: TextInputEditText? = null
    private var mEditTextPw: TextInputEditText? = null
    private var mNaverLogin: ImageView? = null
    private var mKakaoLogin: ImageView? = null
    private var mBtnLogin: Button? = null
    private var mChkBoxKeepLogin: CheckBox? = null
    private var mLastTimeBackPressed: Long = 0

    // 프레젠터 생성, 로그인 유지 여부 검사
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        getSharedPreferences("keepLogin", Context.MODE_PRIVATE)
        if (mSharedPreferences != null) {
            mEditor = mSharedPreferences!!.edit()
        }

        initView()
        mPresenter = LoginPresenter()
        mPresenter!!.setView(this)
        mPresenter!!.createInteractor()
        setImgWithGlide(R.drawable.image_naver_login, mNaverLogin!!)
        setImgWithGlide(R.drawable.image_kakao_login, mKakaoLogin!!)
        mEditTextPw!!.setOnEditorActionListener(this)
        mBtnLogin!!.setOnClickListener(this)
        mNaverLogin!!.setOnClickListener(this)
        mKakaoLogin!!.setOnClickListener(this)
    }

    private fun initView() {

        mEditTextId = findViewById(R.id.editTextLoginId)
        mEditTextPw = findViewById(R.id.editTextLoginPw)
        mNaverLogin = findViewById(R.id.btnLoginNaverLogin)
        mKakaoLogin = findViewById(R.id.btnLoginKakaoLogin)
        mBtnLogin = findViewById(R.id.btnLoginLogin)
        mChkBoxKeepLogin = findViewById(R.id.chBoxLoginKeepLogin)
    }

    // 뒤로가기 버튼 빠르게 두번 클릭하여 어플 종료
    override fun onBackPressed() {
        if (System.currentTimeMillis() - mLastTimeBackPressed < 1000) {
            finish()
            return
        }
        mLastTimeBackPressed = System.currentTimeMillis()
        Toast.makeText(this, "'뒤로' 버튼을 한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
    }

    // 어플이 화면에서 내려갔을 때 키보드가 활성화 되있는 오류 방지
    override fun onPause() {
        Utility.instance()?.deactivateKeyboard(this)
        super.onPause()
    }

    // 각 뷰의 클릭 이벤트
    override fun onClick(v: View) {
        val viewId = v.id

        // 로그인 버튼
        if (viewId == R.id.btnLoginLogin) {
            if (mEditTextId!!.text.toString() == "") {
                mEditTextId!!.error = "아이디를 입력해주세요."
                mEditTextId!!.requestFocus()
                Utility.instance()?.activateKeyboard(this)
            } else if (mEditTextPw!!.text.toString() == "") {
                mEditTextPw!!.error = "비밀번호를 입력해주세요."
                mEditTextPw!!.requestFocus()
                Utility.instance()?.activateKeyboard(this)
            } else {
                mPresenter!!.serverLogin(
                        mEditTextId!!.text.toString(),
                        mEditTextPw!!.text.toString(),
                        this,
                        null
                )
            }
        } else if (viewId == R.id.btnLoginNaverLogin) { // 네이버 로그인 버튼
            mPresenter!!.naverLogin(this, resources)
        } else if (viewId == R.id.btnLoginKakaoLogin) { // 카카오 로그인 버튼
            mPresenter!!.kakaoLogin(this)
        }
    }

    // 로그인 실패 에러 코드에 따라 알림창 출력
    override fun loginFail(errorCode: Int) {
        if (errorCode == LoginApi.LOGIN_FAIL_ID) {
            mEditTextId!!.error = "아이디가 존재하지 않습니다.\n다시 확인해주세요."
            mEditTextId!!.requestFocus()
            Utility.instance()?.activateKeyboard(this)
        } else if (errorCode == LoginApi.LOGIN_FAIL_PWD) {
            mEditTextPw!!.error = "비밀번호가 다릅니다.\n다시 확인해주세요."
            mEditTextPw!!.requestFocus()
            Utility.instance()?.activateKeyboard(this)
        } else {
            showToast("알 수 없는 문제가 발생했습니다.\n개발자에게 문의해 주세요.")
        }
    }

    // 메인 뷰로 이동
    override fun toMain(intent: Intent?) {

        // finish대신 flag를 지정하여 메인 액티비티로 이동 시 스택 내 액티비티 인스턴스 모두 삭제
        intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        val isKeepLoginChecked = mChkBoxKeepLogin!!.isChecked

        // 네이버나 카톡 로그인이 아닐 경우 platform의 값은 ""
        if (intent?.getBundleExtra("data")!!.getString("platform")!!.isEmpty()
                && isKeepLoginChecked) {
            mEditor!!.putBoolean("keepLoginState", true)
            mEditor!!.putString("id", mEditTextId!!.text.toString())
            mEditor!!.putString("pwd", mEditTextPw!!.text.toString())
            mEditor!!.commit()
        }
        startActivity(intent)
    }

    // 회원가입 뷰로 이동
    override fun toRegit(intent: Intent?) {
        startActivity(intent)
    }

    override fun onDestroy() {
        mPresenter!!.releaseInteractor()
        mPresenter!!.releaseView()
        mPresenter = null
        super.onDestroy()
    }

    // 글라이드로 이미지를 처리
    private fun setImgWithGlide(imgResId: Int, targetView: ImageView) {
        Glide.with(this)
                .load(imgResId)
                .circleCrop()
                .into(targetView)
    }

    // 토스트 메시지 출력
    override fun showToast(text: String?) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    // 사용자 정보 어플리케이션 전역에 저장
    override fun setUserData(userID: String?, userCode: String?) {
        val app: GlobalApplication = applicationContext as GlobalApplication
        app.setUserId(userID.toString())
    }

    override fun showProgress() {
        TODO("Not yet implemented")
    }

    override fun hideProgress() {
        TODO("Not yet implemented")
    }

    override fun showDialog(msg: String?) {}

    // 비밀번호 텍스트 에디트에서 엔터(완료) 버튼 클릭 시 바로 로그인 버튼이 클릭 되도록 구현
    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if (event != null
                && event.keyCode == KeyEvent.KEYCODE_ENTER
                || actionId == EditorInfo.IME_ACTION_DONE) {
            mBtnLogin!!.performClick()
            return true
        }
        return false
    }
}