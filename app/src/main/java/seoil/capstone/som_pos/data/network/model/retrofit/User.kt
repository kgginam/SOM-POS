package seoil.capstone.som_pos.data.network.model.retrofit

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import seoil.capstone.som_pos.data.network.model.LoginDTO

// 사용자 api 라우팅 인터페이스
interface User {
    // 로그인 된 사용자 정보 요청
    @POST("login")
    fun login(@Body req: LoginDTO.LoginReq): Call<LoginDTO.LoginRes>

    // POS기 로그인
    // login과는 다르게 pos기는 점주만 로그인 가능하므로 code가 정해져있어 받아오지 않음
    // TODO: login을 posLogin으로 변경하면 될듯
    @POST("login/pos")
    fun posLogin(@Body req: LoginDTO.LoginReq): Call<LoginDTO.LoginRes>
}