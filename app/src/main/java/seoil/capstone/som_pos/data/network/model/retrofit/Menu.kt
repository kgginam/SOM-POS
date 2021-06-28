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

    @PUT("menu/{shopId}/{menuName}")
    fun updateMenu(
        @Path("shopId") shopId: String,
        @Path("menuName") menuName: String,
        @Query("menuNewName") menuNewName: String,
        @Query("menuPrice") menuPrice: Int,
        @Query("menuIngredients") menuIngredients: String
    ): Call<Status>

    @PUT("menu/{shopId}/{menuName}/name")
    fun updateMenuName(
        @Path("shopId") shopId: String,
        @Path("menuName") menuName: String,
        @Query("menuNewName") menuNewName: String
    ): Call<Status>

    @PUT("menu/{shopId}/{menuName}/price")
    fun updateMenuPrice(
        @Path("shopId") shopId: String,
        @Path("menuName") menuName: String,
        @Query("menuPrice") menuPrice: Int
    ): Call<Status>

    @PUT("menu/{shopId}/{menuName}/ingredients")
    fun updateMenuIngredients(
        @Path("shopId") shopId: String,
        @Path("menuName") menuName: String,
        @Query("menuIngredients") menuIngredients: String
    ): Call<Status>

    @DELETE("menu/{shopId}")
    fun deleteAllMenu(
        @Path("shopId") shopId: String
    ): Call<Status>

    @DELETE("menu/{shopId}/{menuName}")
    fun deleteMenu(
        @Path("shopId") shopId: String,
        @Path("menuName") menuName: String
    ): Call<Status>
}