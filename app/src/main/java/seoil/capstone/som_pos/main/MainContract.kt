package seoil.capstone.som_pos.main

import seoil.capstone.som_pos.base.BaseContract
import seoil.capstone.som_pos.data.network.OnFinishApiListener
import seoil.capstone.som_pos.data.network.model.ShopRes

interface MainContract {

    interface View : BaseContract.View {

        fun setGlobalData(category : String)
    }

    interface Presenter : BaseContract.Presenter<View> {

    }

    interface Interactor : BaseContract.Interactor {

        fun getShopInformation(shopId : String, onFinishApiListener: OnFinishApiListener<ShopRes>)
    }
}