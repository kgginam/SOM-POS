package seoil.capstone.som_pos.data.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class StockRes(
    @SerializedName("status")
    @Expose
    val status: Int,
    @SerializedName("results")
    @Expose
    val results: List<StockModel>?
)

data class StockModel(
    @SerializedName("stockCode")
    @Expose
    var stockCode: Int?,
    @SerializedName("shopId")
    @Expose
    var shopId: String?,
    @SerializedName("stockName")
    @Expose
    var stockName: String?,
    @SerializedName("stockAmount")
    @Expose
    var stockAmount: Int?
)

// 이름 수정 모델
data class StockUpdateNameModel(
        @SerializedName("stockCode")
        @Expose
        var stockCode: Int?,
        @SerializedName("shopId")
        @Expose
        var shopId: String?,
        @SerializedName("stockPrevName")
        @Expose
        var stockPrevName: String?,
        @SerializedName("stockNewName")
        @Expose
        var stockNewName: String?,
        @SerializedName("stockAmount")
        @Expose
        var stockAmount: Int?
)