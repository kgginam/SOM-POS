package seoil.capstone.som_pos.data.network.api

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import seoil.capstone.som_pos.data.network.AppApiHelper
import seoil.capstone.som_pos.data.network.OnFinishApiListener
import seoil.capstone.som_pos.data.network.model.LoginDTO
import seoil.capstone.som_pos.data.network.model.retrofit.User

class LoginApi(retrofit: Retrofit) {

    private val mUserData = retrofit.create(User::class.java)

    companion object {

        // 사용자 응답 코드
        const val SUCCESS: Int = 0
        const val ERROR: Int = 1
        const val ERROR_UNDEFINED_VALUE: Int = 2
        const val ERROR_NONE_DATA: Int = 3

        const val LOGIN_FAIL_ID: Int = 3
        const val LOGIN_FAIL_PWD: Int = 4
        const val LOGIN_FAIL_NOT_MANAGER: Int = 5
        const val NEW_USER: Int = 6 // 카카오나 네이버로 로그인 시 새로운 회원이면 이에 맞는 처리 수행

        const val ID_DUPLICATE: Int = 3

        const val CLOSED_BUSINESS: Int = 3
        const val ERROR_CRAWLING: Int = 4

        // 유효하지 않은 인증번호 입력
        const val ERROR_INVALID_AUTH: Int = 4
    }

    // 로그인 요청
    // TODO: 대부분 Nullable로 되어있는데 정말 필요한 경우가 아니면 뺄 것
    fun login(req: LoginDTO.LoginReq, onFinishApiListener: OnFinishApiListener<LoginDTO.LoginRes>) {
        val call: Call<LoginDTO.LoginRes> = mUserData.login(req)
        call.enqueue(object: Callback<LoginDTO.LoginRes> {
            override fun onResponse(call: Call<LoginDTO.LoginRes>, response: Response<LoginDTO.LoginRes>) {

                if (AppApiHelper.getInstance().check404Error(response, onFinishApiListener)) {
                    return
                }

                response.body()?.let { onFinishApiListener.onSuccess(it) }
            }

            override fun onFailure(call: Call<LoginDTO.LoginRes?>, t: Throwable) {
                onFinishApiListener.onFailure(t)
            }
        })
    }

    // POS기 로그인
    fun posLogin(req: LoginDTO.LoginReq, onFinishApiListener: OnFinishApiListener<LoginDTO.LoginRes>) {
        val call: Call<LoginDTO.LoginRes> = mUserData.posLogin(req)
        call.enqueue(object: Callback<LoginDTO.LoginRes> {
            override fun onResponse(call: Call<LoginDTO.LoginRes>, response: Response<LoginDTO.LoginRes>) {

                if (AppApiHelper.getInstance().check404Error(response, onFinishApiListener)) {
                    return
                }

                response.body()!!.let { onFinishApiListener.onSuccess(it) }
            }

            override fun onFailure(call: Call<LoginDTO.LoginRes>, t: Throwable) {
                onFinishApiListener.onFailure(t)
            }
        })
    }
}