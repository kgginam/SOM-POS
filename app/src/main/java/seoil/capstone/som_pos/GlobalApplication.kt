package seoil.capstone.som_pos

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class GlobalApplication: Application() {

    companion object {

        // 사용자 정보 getter setter
        private var loginPlatform // 사용자 로그인 플랫폼(null: 일반, naver, kakao)
               : String? = null
        private var userId // 사용자 아이디
               : String? = null

        private var category
               : String? = null
    }

    override fun onCreate() {
        super.onCreate()

        // 카카오 어플 네이티브 앱 키 초기화
        KakaoSdk.init(this, "807b94e191e23dfd5f19907be7eec2ae")
    }

    // 로그아웃 시 사용자 정보 삭제
    fun logout() {
        userId = null
    }

    fun setLoginPlatform(str: String) {

        loginPlatform = str;
    }

    fun getLoginPlatform(): String {

        return loginPlatform.toString();
    }

    fun setUserId(str: String) {

        userId = str;
    }

    fun getUserId(): String {

        return userId.toString()
    }

    fun setCategory(str: String) {

        category = str
    }

    fun getCategory(): String {

        return category.toString()
    }
}