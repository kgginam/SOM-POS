package seoil.capstone.som_pos.data.network.model.retrofit

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import seoil.capstone.som_pos.data.network.model.LoginDTO

// 사용자 api 라우팅 인터페이스
interface User {
    // 로그인 된 사용자 정보 요청
    @POST("login")
    fun getLoginData(@Body req: LoginDTO.LoginReq?): Call<LoginDTO.LoginRes?>?
}