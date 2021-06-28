package seoil.capstone.som_pos.data.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

// TODO: 실패한 쿼리의 경우 results가 널일 수 있음
// TODO: 응답 데이터는 변해선 안되므로 val
data class MenuRes(
    @SerializedName("status")
    @Expose
    val status: Int,
    @SerializedName("results")
    @Expose
    val results: List<MenuModel>?
)

// 메뉴 데이터 모델
// TODO: 각각 필요한 경우 널일 수 있음
// TODO: recyclerView에서 사용 시 값이 변할 수 있으므로 var
data class MenuModel(
    @SerializedName("shopId")
    @Expose
    var shopId: String?,
    @SerializedName("menuName")
    @Expose
    var menuName: String?,
    @SerializedName("menuPrice")
    @Expose
    var menuPrice: Int?,
    @SerializedName("menuIngredients")
    @Expose
    var menuIngredients: String?
)