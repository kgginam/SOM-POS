package seoil.capstone.som_pos.ui.sell

import seoil.capstone.som_pos.data.network.AppApiHelper
import seoil.capstone.som_pos.data.network.OnFinishApiListener
import seoil.capstone.som_pos.data.network.model.*

class SellManagementInteractor: SellManagementContract.Interactor{

    override fun getMenuInfo(shopId: String, onFinishApiListener: OnFinishApiListener<MenuRes>) {
        AppApiHelper.getInstance().getMenuInfo(shopId, onFinishApiListener)
    }

    override fun getStock(shopId: String, onFinishApiListener: OnFinishApiListener<StockRes>) {
        AppApiHelper.getInstance().getStock(shopId, onFinishApiListener)
    }

    override fun pay(req: PaymentModel, onFinishApiListener: OnFinishApiListener<Status>) {
        AppApiHelper.getInstance().pay(req, onFinishApiListener)
    }

    override fun getCurrentPoint(userId: String, onFinishApiListener: OnFinishApiListener<GetCurrentRes>) {
        AppApiHelper.getInstance().getCurrentPoint(userId, onFinishApiListener)
    }
}