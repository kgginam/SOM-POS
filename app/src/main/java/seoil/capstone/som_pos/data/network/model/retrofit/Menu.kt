package seoil.capstone.som_pos.data.network.model.retrofit

import retrofit2.Call
import retrofit2.http.*
import seoil.capstone.som_pos.data.network.model.MenuModel
import seoil.capstone.som_pos.data.network.model.MenuRes
import seoil.capstone.som_pos.data.network.model.Status

interface Menu {

    @GET("menu/{shopId}")
    fun getMenuInfo(
        @Path("shopId") shopId: String
    ): Call<MenuRes>

    @POST("menu/{shopId}")
    fun insertMenu(
        @Path("shopId") shopId: String,
        @Body req: MenuModel
    ): Call<Status>

    @PUT("menu/")
    fun updateMenu(
        @Query("menuCode") menuCode: Int,
        @Query("menuName") menuName: String,
        @Query("menuPrice") menuPrice: Int,
        @Query("menuIngredients") menuIngredients: String
    ): Call<Status>

    @PUT("menu/name")
    fun updateMenuName(
            @Query("menuCode") menuCode: Int,
            @Query("menuName") menuName: String
    ): Call<Status>

    @PUT("menu/price")
    fun updateMenuPrice(
            @Query("menuCode") menuCode: Int,
            @Query("menuPrice") menuPrice: Int
    ): Call<Status>

    @PUT("menu/ingredients")
    fun updateMenuIngredients(
            @Query("menuCode") menuCode: Int,
            @Query("menuIngredients") menuIngredients: String
    ): Call<Status>

    @DELETE("menu/{shopId}")
    fun deleteAllMenu(
        @Path("shopId") shopId: String
    ): Call<Status>

    @DELETE("menu/")
    fun deleteMenu(
            @Query("menuCode") menuCode: Int
    ): Call<Status>
}