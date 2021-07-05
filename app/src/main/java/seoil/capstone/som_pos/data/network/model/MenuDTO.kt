package seoil.capstone.som_pos.data.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MenuRes(
    @SerializedName("status")
    @Expose
    val status: Int,
    @SerializedName("results")
    @Expose
    val results: List<MenuModel>?
)

// 메뉴 데이터 모델
data class MenuModel(
    @SerializedName("menuCode")
    @Expose
    var menuCode: Int?,
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