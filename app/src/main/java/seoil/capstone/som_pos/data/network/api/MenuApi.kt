package seoil.capstone.som_pos.data.network.api

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.Path
import retrofit2.http.Query
import seoil.capstone.som_pos.data.network.AppApiHelper
import seoil.capstone.som_pos.data.network.OnFinishApiListener
import seoil.capstone.som_pos.data.network.model.MenuModel
import seoil.capstone.som_pos.data.network.model.MenuRes
import seoil.capstone.som_pos.data.network.model.Status
import seoil.capstone.som_pos.data.network.model.retrofit.Menu

class MenuApi(retrofit: Retrofit) {

    private val mMenuData = retrofit.create(Menu::class.java)

    companion object {
        const val SUCCESS: Int = 0
        const val ERROR: Int = 1
        const val ERROR_UNDEFINED_VALUE: Int = 2
        const val ERROR_NONE_DATA: Int = 3
    }

    fun getMenuInfo(shopId: String, onFinishApiListener: OnFinishApiListener<MenuRes>) {
        val call: Call<MenuRes> = mMenuData.getMenuInfo(shopId)
        call.enqueue(object: Callback<MenuRes> {
            override fun onFailure(call: Call<MenuRes>, t: Throwable) {
                onFinishApiListener.onFailure(t)
            }

            override fun onResponse(call: Call<MenuRes>, response: Response<MenuRes>) {
                if (AppApiHelper.getInstance().check404Error(response, onFinishApiListener)) {
                    return
                }

                onFinishApiListener.onSuccess(response.body()!!)
            }
        })
    }

    fun insertMenu(shopId: String, req: MenuModel, onFinishApiListener: OnFinishApiListener<Status>) {
        val call: Call<Status> = mMenuData.insertMenu(shopId, req)
        call.enqueue(object: Callback<Status> {
            override fun onFailure(call: Call<Status>, t: Throwable) {
                onFinishApiListener.onFailure(t)
            }

            override fun onResponse(call: Call<Status>, response: Response<Status>) {
                if (AppApiHelper.getInstance().check404Error(response, onFinishApiListener)) {
                    return
                }

                onFinishApiListener.onSuccess(response.body()!!)
            }
        })
    }

    fun updateMenu(menuCode: Int, menuName: String, menuPrice: Int, menuIngredients: String, onFinishApiListener: OnFinishApiListener<Status>) {
        val call: Call<Status> = mMenuData.updateMenu(menuCode, menuName, menuPrice, menuIngredients)
        call.enqueue(object: Callback<Status> {
            override fun onFailure(call: Call<Status>, t: Throwable) {
                onFinishApiListener.onFailure(t)
            }

            override fun onResponse(call: Call<Status>, response: Response<Status>) {
                if (AppApiHelper.getInstance().check404Error(response, onFinishApiListener)) {
                    return
                }

                onFinishApiListener.onSuccess(response.body()!!)
            }
        })
    }

    fun updateMenuName(menuCode: Int, menuName: String, onFinishApiListener: OnFinishApiListener<Status>) {
        val call: Call<Status> = mMenuData.updateMenuName(menuCode, menuName)
        call.enqueue(object: Callback<Status> {
            override fun onFailure(call: Call<Status>, t: Throwable) {
                onFinishApiListener.onFailure(t)
            }

            override fun onResponse(call: Call<Status>, response: Response<Status>) {
                if (AppApiHelper.getInstance().check404Error(response, onFinishApiListener)) {
                    return
                }

                onFinishApiListener.onSuccess(response.body()!!)
            }
        })
    }

    fun updateMenuPrice(menuCode: Int, menuPrice: Int, onFinishApiListener: OnFinishApiListener<Status>) {
        val call: Call<Status> = mMenuData.updateMenuPrice(menuCode, menuPrice)
        call.enqueue(object: Callback<Status> {
            override fun onFailure(call: Call<Status>, t: Throwable) {
                onFinishApiListener.onFailure(t)
            }

            override fun onResponse(call: Call<Status>, response: Response<Status>) {
                if (AppApiHelper.getInstance().check404Error(response, onFinishApiListener)) {
                    return
                }

                onFinishApiListener.onSuccess(response.body()!!)
            }
        })
    }

    fun updateMenuIngredients(menuCode: Int, menuIngredients: String, onFinishApiListener: OnFinishApiListener<Status>) {
        val call: Call<Status> = mMenuData.updateMenuIngredients(menuCode, menuIngredients)
        call.enqueue(object: Callback<Status> {
            override fun onFailure(call: Call<Status>, t: Throwable) {
                onFinishApiListener.onFailure(t)
            }

            override fun onResponse(call: Call<Status>, response: Response<Status>) {
                if (AppApiHelper.getInstance().check404Error(response, onFinishApiListener)) {
                    return
                }

                onFinishApiListener.onSuccess(response.body()!!)
            }
        })
    }

    fun deleteAllMenu(shopId: String, onFinishApiListener: OnFinishApiListener<Status>) {
        val call: Call<Status> = mMenuData.deleteAllMenu(shopId)
        call.enqueue(object: Callback<Status> {
            override fun onFailure(call: Call<Status>, t: Throwable) {
                onFinishApiListener.onFailure(t)
            }

            override fun onResponse(call: Call<Status>, response: Response<Status>) {
                if (AppApiHelper.getInstance().check404Error(response, onFinishApiListener)) {
                    return
                }

                onFinishApiListener.onSuccess(response.body()!!)
            }
        })
    }

    fun deleteMenu(menuCode: Int, onFinishApiListener: OnFinishApiListener<Status>) {
        val call: Call<Status> = mMenuData.deleteMenu(menuCode)
        call.enqueue(object: Callback<Status> {
            override fun onFailure(call: Call<Status>, t: Throwable) {
                onFinishApiListener.onFailure(t)
            }

            override fun onResponse(call: Call<Status>, response: Response<Status>) {
                if (AppApiHelper.getInstance().check404Error(response, onFinishApiListener)) {
                    return
                }

                onFinishApiListener.onSuccess(response.body()!!)
            }
        })
    }
}