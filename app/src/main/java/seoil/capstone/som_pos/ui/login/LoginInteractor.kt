package seoil.capstone.som_pos.ui.login

import android.content.Context
import android.content.res.Resources
import seoil.capstone.som_pos.data.network.AppApiHelper
import seoil.capstone.som_pos.data.network.OnFinishApiListener
import seoil.capstone.som_pos.data.network.model.LoginDTO

// 로그인 모델
class LoginInteractor : LoginContract.Interactor {

    override fun serverLogin(
        req: LoginDTO.LoginReq?,
        onFinishApiListener: OnFinishApiListener<LoginDTO.LoginRes>?
    ) {
        AppApiHelper.getInstance().serverLogin(req!!, onFinishApiListener!!)
    }

    override fun kakaoLogin(
        context: Context?,
        onFinishApiListener: OnFinishApiListener<LoginDTO.KakaoLoginRes>?
    ) {
        AppApiHelper.getInstance().kakaoLogin(context, onFinishApiListener!!)
    }

    override fun naverLogin(
        context: Context?,
        res: Resources?,
        onFinishApiListener: OnFinishApiListener<LoginDTO.NaverLoginRes>?
    ) {
        AppApiHelper.getInstance().naverLogin(context, res!!, onFinishApiListener!!)
    }
}