package seoil.capstone.som_pos.data.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


// 로그인 DTO
class LoginDTO {

    /////////
    // 요청 //
    /////////
    class LoginReq(
        @field:Expose @field:SerializedName("id") var id: String,
        @field:Expose @field:SerializedName(
            "pwd"
        ) var pwd: String
    )

    /////////
    // 응답 //
    /////////
    // 로그인 응답 DTO
    class LoginRes {
        @SerializedName("status")
        @Expose
        var status = 0

        @SerializedName("code")
        @Expose
        var code: String? = null

    }

    // 카카오 간편 로그인 응답 DTO
    class KakaoLoginRes(id: String?) {

        @field:Expose
        @field:SerializedName("id")
        var kakaoId : String? = null

        init {

            this.kakaoId = id
        }
    }

    // 네이버 간편 로그인 응답 DTO
    class NaverLoginRes(
        var id: String,
        var birthdate: String,
        var gender: String,
        var email: String,
        var phoneNumber: String
    )
}