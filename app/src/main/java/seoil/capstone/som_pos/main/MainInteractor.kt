package seoil.capstone.som_pos.main

import seoil.capstone.som_pos.data.network.AppApiHelper
import seoil.capstone.som_pos.data.network.OnFinishApiListener
import seoil.capstone.som_pos.data.network.model.ShopRes

class MainInteractor : MainContract.Interactor {

    override fun getShopInformation(shopId : String, onFinishApiListener: OnFinishApiListener<ShopRes>) {

        AppApiHelper.getInstance().getShopCategory(shopId, onFinishApiListener)
    }
}