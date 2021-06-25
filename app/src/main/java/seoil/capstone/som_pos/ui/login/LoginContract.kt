package seoil.capstone.som_pos.ui.login

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import seoil.capstone.som_pos.base.BaseContract
import seoil.capstone.som_pos.data.network.OnFinishApiListener
import seoil.capstone.som_pos.data.network.model.LoginDTO

interface LoginContract {

    interface Interactor {
        fun serverLogin(
            req: LoginDTO.LoginReq?,
            onFinishApiListener: OnFinishApiListener<LoginDTO.LoginRes>?
        )

        fun kakaoLogin(
            context: Context?,
            onFinishApiListener: OnFinishApiListener<LoginDTO.KakaoLoginRes>?
        )

        fun naverLogin(
            context: Context?,
            resources: Resources?,
            onFinishApiListener: OnFinishApiListener<LoginDTO.NaverLoginRes>?
        )
    }

    interface View : BaseContract.View {
        fun loginFail(errorCode: Int)
        fun toMain(intent: Intent?)
        fun toRegit(intent: Intent?)
        fun showToast(text: String?)
        fun setUserData(userID: String?, userCode: String?)
    }

    interface Presenter : BaseContract.Presenter<View?> {
        fun serverLogin(
            id: String?,
            pwd: String?,
            context: Context?,
            onFinishApiListener: OnFinishApiListener<LoginDTO.LoginRes>?
        )

        fun kakaoLogin(context: Context?)
        fun naverLogin(
            context: Context?,
            resources: Resources?
        )
    }
}