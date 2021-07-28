package seoil.capstone.som_pos.data.network

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.util.Log
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.nhn.android.naverlogin.OAuthLogin
import com.nhn.android.naverlogin.OAuthLoginHandler
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import seoil.capstone.som_pos.R
import seoil.capstone.som_pos.data.network.api.*
import seoil.capstone.som_pos.data.network.model.*


// api 관리자
// 싱글턴 클래스
// TODO: 코틀린은 object라는 예약어로 싱글턴 클래스를 만들 수 있으므로 참고(생성자 사용 불가, 전역으로 꺼내면 됨)
class AppApiHelper {

    private val baseUrl = "https://leebera.name/api/"

    private var mLoginApi: LoginApi
    private var mMenuApi: MenuApi
    private var mStockApi: StockApi
    private var mShopApi: ShopApi
    private var mPaymentApi: PaymentApi

    init {

        val retrofit: Retrofit = Retrofit
                .Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        mLoginApi = LoginApi(retrofit)
        mMenuApi = MenuApi(retrofit)
        mStockApi = StockApi(retrofit)
        mShopApi = ShopApi(retrofit)
        mPaymentApi = PaymentApi(retrofit)
    }

    companion object {

        @Volatile private var mAppApiHelper: AppApiHelper? = null

        fun getInstance(): AppApiHelper {


            if (mAppApiHelper == null) {

                mAppApiHelper = AppApiHelper()
            }

            return mAppApiHelper as AppApiHelper
        }
    }

    val instance: AppApiHelper?
        get() {
            if (mAppApiHelper == null) {
                mAppApiHelper = AppApiHelper()
            }
            return mAppApiHelper
        }

    // TODO: *는 모든 타입을 가리키는 것, Any는 Object를 의미 - 따라서 *를 넣어야 자바에서 사용하던 것과 동일하게 문제없이 작동
    fun check404Error(response: Response<*>, onFinishApiListener: OnFinishApiListener<*>): Boolean {
        if (response.code() == 404) {
            onFinishApiListener.onFailure(Throwable("404 Error(server offline or not exist page)"))
            return true
        }

        return false
    }

    // 서버 로그인 요청
    fun serverLogin(
        req: LoginDTO.LoginReq,
        onFinishApiListener: OnFinishApiListener<LoginDTO.LoginRes>
    ) {
        mLoginApi!!.login(req, onFinishApiListener)
    }

    fun posLogin(req: LoginDTO.LoginReq, onFinishApiListener: OnFinishApiListener<LoginDTO.LoginRes>) {
        mLoginApi!!.posLogin(req, onFinishApiListener)
    }

    // 카카오 간편 로그인 요청
    fun kakaoLogin(
        context: Context?,
        onFinishApiListener: OnFinishApiListener<LoginDTO.KakaoLoginRes>
    ) {

        // 카카오 로그인 콜백 함수 정의
        val callback: Function2<OAuthToken, Throwable, Unit> =
            { oAuthToken: OAuthToken?, throwable: Throwable? ->

                // 로그인 정상 수행
                if (oAuthToken != null) {
                    Log.d("API", "oAuth is available")

                    // 카카오 로그인 사용자의 uid받아 서버에 보내 회원인지 확인
                    UserApiClient.instance.me( true) { user: com.kakao.sdk.user.model.User?, throwable: Throwable? ->

                        // 카카오 유저가 정상적으로 있을 경우
                        if (user != null) {
                            onFinishApiListener.onSuccess(LoginDTO.KakaoLoginRes(user.id.toString()))
                        }
                    }
                }

                // 로그인 실패
                if (throwable != null) {

                    // 예외 처리
                    Log.d("API", "throwable: $throwable")
                }
            }
        // 카카오 어플 존재하는지 확인
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context!!)) {

            // 존재하므로 어플로 로그인
            UserApiClient.instance.loginWithKakaoTalk(context, 1, null, null, callback as (OAuthToken?, Throwable?) -> Unit)
        } else {

            // 존재하지 않아 웹으로 로그인
            UserApiClient.instance.loginWithKakaoAccount(context,
                    null, null, null, callback as (OAuthToken?, Throwable?) -> Unit)
        }
    }

    // 네이버 간편 로그인 요청
    fun naverLogin(
        context: Context?,
        res: Resources,
        onFinishApiListener: OnFinishApiListener<LoginDTO.NaverLoginRes>
    ) {
        val oAuthLogin = OAuthLogin.getInstance()
        oAuthLogin.init(
            context
            , res.getString(R.string.naver_client_id)
            , res.getString(R.string.naver_client_secret)
            , res.getString(R.string.naver_client_name)
        )
        @SuppressLint("HandlerLeak") val oAuthLoginHandler: OAuthLoginHandler =
            object: OAuthLoginHandler() {
                override fun run(success: Boolean) {
                    if (success) {
                        val getDataThread: Thread = object: Thread() {
                            override fun run() {
                                val accessToken = oAuthLogin.getAccessToken(context)
                                val naverLoginData = oAuthLogin.requestApi(
                                    context,
                                    accessToken,
                                    "https://openapi.naver.com/v1/nid/me"
                                )
                                try {
                                    val jsonResult =
                                        JSONObject(naverLoginData).getJSONObject("response")
                                    var gender = jsonResult.getString("gender")

                                    // 성별이 알 수 없음 일 때
                                    if (gender == "U") {

                                        gender = "M"
                                    }
                                    onFinishApiListener.onSuccess(
                                        LoginDTO.NaverLoginRes(
                                            jsonResult.getString("id"),
                                            jsonResult.getString("birthyear") + jsonResult.getString(
                                                "birthday"
                                            ).replace("-", ""),
                                            gender,
                                            jsonResult.getString("email"),
                                            jsonResult.getString("mobile").replace("-", "")
                                        )
                                    )
                                } catch (e: JSONException) {
                                    e.printStackTrace()
                                }
                            }
                        }
                        getDataThread.start()
                        try {
                            getDataThread.join()
                        } catch (e: InterruptedException) {
                            e.printStackTrace()
                        }
                    } else {
                        val errorCode =
                            oAuthLogin.getLastErrorCode(context).code
                        val errorDesc = oAuthLogin.getLastErrorDesc(context)
                    }
                }
            }

        // OAuth로그인 핸들러 넘겨 수행
        oAuthLogin.startOauthLoginActivity(context as Activity?, oAuthLoginHandler)
    }

    fun getMenuInfo(shopId: String, onFinishApiListener: OnFinishApiListener<MenuRes>) {
        mMenuApi.getMenuInfo(shopId, onFinishApiListener)
    }

    fun insertMenu(shopId: String, req: MenuModel, onFinishApiListener: OnFinishApiListener<Status>) {
        mMenuApi.insertMenu(shopId, req, onFinishApiListener)
    }

    fun updateMenu(menuCode: Int, menuName: String, menuPrice: Int, menuIngredients: String, onFinishApiListener: OnFinishApiListener<Status>) {
        mMenuApi.updateMenu(menuCode, menuName, menuPrice, menuIngredients, onFinishApiListener)
    }

    fun updateMenuName(menuCode: Int, menuName: String, onFinishApiListener: OnFinishApiListener<Status>) {
        mMenuApi.updateMenuName(menuCode , menuName, onFinishApiListener)
    }

    fun updateMenuPrice(menuCode: Int, menuPrice: Int, onFinishApiListener: OnFinishApiListener<Status>) {
        mMenuApi.updateMenuPrice(menuCode, menuPrice, onFinishApiListener)
    }

    fun updateMenuIngredients(menuCode: Int, menuIngredients: String, onFinishApiListener: OnFinishApiListener<Status>) {
        mMenuApi.updateMenuIngredients(menuCode, menuIngredients, onFinishApiListener)
    }

    fun deleteAllMenu(shopId: String, onFinishApiListener: OnFinishApiListener<Status>) {
        mMenuApi.deleteAllMenu(shopId, onFinishApiListener)
    }

    fun deleteMenu(menuCode: Int, onFinishApiListener: OnFinishApiListener<Status>) {
        mMenuApi.deleteMenu(menuCode, onFinishApiListener)
    }

    fun getStock(shopId: String, onFinishApiListener: OnFinishApiListener<StockRes>) {
        mStockApi.getStock(shopId, onFinishApiListener)
    }

    fun insertStock(req: StockModel, onFinishApiListener: OnFinishApiListener<Status>) {
        mStockApi.insertStock(req, onFinishApiListener)
    }

    fun updateStock(req: StockUpdateNameModel, onFinishApiListener: OnFinishApiListener<Status>) {
        mStockApi.updateStock(req, onFinishApiListener)
    }

    fun updateStockAmount(req: StockModel, onFinishApiListener: OnFinishApiListener<Status>) {
        mStockApi.updateStockAmount(req, onFinishApiListener)
    }


    fun deleteStock(shopId: String, stockCode: Int, stockName: String, onFinishApiListener: OnFinishApiListener<Status>) {
        mStockApi.deleteStock(shopId, stockCode, stockName, onFinishApiListener)
    }

    //매장 카테고리 요청
    fun getShopCategory(shopId: String, onFinishApiListener: OnFinishApiListener<ShopRes>) {
        mShopApi.getShopCategory(shopId, onFinishApiListener)
    }

    fun getPayment(memberId: String, onFinishApiListener: OnFinishApiListener<PaymentRes>) {
        mPaymentApi.getPayment(memberId, onFinishApiListener)
    }

    fun pay(req: PaymentModel, onFinishApiListener: OnFinishApiListener<Status>) {
        mPaymentApi.pay(req, onFinishApiListener)
    }

    fun cancel(req: PaymentModel, onFinishApiListener: OnFinishApiListener<Status>) {
        mPaymentApi.cancel(req, onFinishApiListener)
    }
}