package seoil.capstone.som_pos.ui.login

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import seoil.capstone.som_pos.main.MainActivity
import seoil.capstone.som_pos.data.network.OnFinishApiListener
import seoil.capstone.som_pos.data.network.api.LoginApi
import seoil.capstone.som_pos.data.network.model.LoginDTO

class LoginPresenter : LoginContract.Presenter {

    private val TAG = "LoginPresenter"

    private var view : LoginContract.View?= null
    private var mPresenter : LoginPresenter? = null
    private var mInteractor: LoginInteractor? = null

    override fun setView(view: LoginContract.View?) {
        this.view = view
    }

    override fun releaseView() {
        view = null
    }

    override fun createInteractor() {

        if(mInteractor == null) {

            mInteractor = LoginInteractor()
        }
    }

    override fun releaseInteractor() {
        mInteractor = null
    }

    // 일반 로그인
    override fun serverLogin(
        id: String?,
        pwd: String?,
        context: Context?,
        onFinishApiListener: OnFinishApiListener<LoginDTO.LoginRes>?
    ) {
        val callback: OnFinishApiListener<LoginDTO.LoginRes>
        if (onFinishApiListener == null) {
            callback = object : OnFinishApiListener<LoginDTO.LoginRes> {
                override fun onSuccess(t: LoginDTO.LoginRes) {
                    val statusCode: Int = t.status

                    // 초기엔 공용 데이터인 아이디와 구분 코드 담기 위한 Bundle 생성
                    val bundle = Bundle()
                    bundle.putString("id", id)
                    bundle.putString("code", t.code)
                    if (pwd == "naver" || pwd == "kakao") {
                        bundle.putString("platform", pwd)
                    } else {
                        bundle.putString("platform", "")
                    }
                    val intent = Intent()
                    if (statusCode == LoginApi.SUCCESS) {

                        // 로그인 성공했으므로 MainActivity로 이동하도록 설정
                        intent.component = ComponentName(context!!, MainActivity::class.java)
                        intent.putExtra("data", bundle)
                        view!!.setUserData(id, t.code)
                        view!!.toMain(intent)
                    } else if (statusCode == LoginApi.NEW_USER) {

                    } else {

                        // 로그인 실패 에러 코드 전송
                        view!!.loginFail(statusCode)
                    }
                }

                override fun onFailure(t: Throwable?) {

                    // som rest api연결 실패 예외처리
                    view!!.showToast("Login Fail : $t")
                }
            }
        } else {

            // 네이버의 경우 Response를 따로 넘겨줘야하기 때문에 인자로 받은 listener를 넘김
            callback = onFinishApiListener
        }
        mInteractor!!.serverLogin(LoginDTO.LoginReq(id!!, pwd!!), callback)
    }

    // 카카오 간편 로그인
    override fun kakaoLogin(context: Context?) {
        val callback: OnFinishApiListener<LoginDTO.KakaoLoginRes?> =
            object : OnFinishApiListener<LoginDTO.KakaoLoginRes?> {
                override fun onSuccess(loginResponse: LoginDTO.KakaoLoginRes?) {

                    // 카카오 아이디가 null이 아니면 성공한 것이므로 som api로 서버에 보내 사용자인지 검사
                    val kakaoId: String? = loginResponse!!.kakaoId
                    if (kakaoId != null) {
                        serverLogin(kakaoId, "kakao", context, null)
                    }
                }

                override fun onFailure(t: Throwable?) {

                    // 로그인 실패 예외처리
                    Log.d("presenter", "Kakao Login Error : $t")
                }
            }
        mInteractor!!.kakaoLogin(context, callback)
    }

    // 네이버 간편 로그인
    override fun naverLogin(
        context: Context?,
        resources: Resources?
    ) {
        val callback: OnFinishApiListener<LoginDTO.NaverLoginRes?> =
            object : OnFinishApiListener<LoginDTO.NaverLoginRes?> {
                override fun onSuccess(naverLoginResponse: LoginDTO.NaverLoginRes?) {

                    // 네이버 아이디가 있으면 네이버 로그인 성공이므로 som rest api로 서버에 보내 사용자인지 검사
                    val naverId: String = naverLoginResponse!!.id
                    Log.d("Presenter", "naverSuccess")
                    serverLogin(
                        naverId,
                        "naver",
                        context,
                        object : OnFinishApiListener<LoginDTO.LoginRes?> {
                            override fun onSuccess(serverLoginResponse: LoginDTO.LoginRes?) {
                                Log.d("presenter", "serverSuccess")
                                val statusCode: Int = serverLoginResponse!!.status
                                val bundle = Bundle()
                                bundle.putString("id", naverId)
                                val intent = Intent()
                                if (statusCode == LoginApi.SUCCESS) {
                                    intent.component = ComponentName(
                                        context!!,
                                        MainActivity::class.java
                                    )
                                    bundle.putString("code", serverLoginResponse!!.code)
                                    bundle.putString("platform", "naver")
                                    intent.putExtra("data", bundle)
                                    view!!.setUserData(naverId, serverLoginResponse.code)
                                    view!!.toMain(intent)
                                } else if (statusCode == LoginApi.NEW_USER) {

                                } else {

                                    // 로그인 실패 에러 코드 전송
                                    view!!.loginFail(statusCode)
                                }
                            }

                            override fun onFailure(t: Throwable?) {

                                // som rest api연결 실패 예외처리
                                view!!.showToast("Fail : $t")
                            }
                        })
                }

                override fun onFailure(t: Throwable?) {

                    // 로그인 실패 예외처리
                    Log.d("presenter", "Naver Login Error : $t")
                }
            }
        mInteractor!!.naverLogin(context, resources, callback)
    }
}