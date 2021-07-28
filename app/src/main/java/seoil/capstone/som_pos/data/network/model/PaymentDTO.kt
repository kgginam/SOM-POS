package seoil.capstone.som_pos.data.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PaymentRes (

    @SerializedName("status")
    @Expose
    val status: Int?,
    @SerializedName("results")
    @Expose
    val results: List<ShopModel>
)

data class PaymentModel (

    @SerializedName("paymentCode")
    @Expose
    val paymentCode: Int?,

    @SerializedName("paymentDate")
    @Expose
    val paymentDate: String?,

    @SerializedName("memberId")
    @Expose
    val memberId: String?,

    @SerializedName("shopId")
    @Expose
    val shopId: String?,

    @SerializedName("stock")
    @Expose
    val stock: String?,

    @SerializedName("point")
    @Expose
    val point: Int?,

    @SerializedName("cost")
    @Expose
    val cost: Int?
)