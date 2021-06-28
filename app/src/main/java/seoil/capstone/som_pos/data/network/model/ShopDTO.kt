package seoil.capstone.som_pos.data.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ShopRes (

    @SerializedName("status")
    @Expose
    val status : Int?,
    @SerializedName("results")
    @Expose
    val results : List<ShopModel>
)

data class ShopModel (

    @SerializedName("shopName")
    @Expose
    val shopName : String?,

    @SerializedName("shopAddress")
    @Expose
    val shopAddress: String?,

    @SerializedName("shopCategory")
    @Expose
    val shopCategory: String?
)
