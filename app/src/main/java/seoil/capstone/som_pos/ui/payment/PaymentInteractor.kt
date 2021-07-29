package seoil.capstone.som_pos.ui.payment

import seoil.capstone.som_pos.data.network.AppApiHelper
import seoil.capstone.som_pos.data.network.OnFinishApiListener
import seoil.capstone.som_pos.data.network.model.PaymentModel
import seoil.capstone.som_pos.data.network.model.PaymentRes
import seoil.capstone.som_pos.data.network.model.Status

class PaymentInteractor: PaymentContract.Interactor {
    override fun getPayment(
        memberId: String,
        onFinishApiListener: OnFinishApiListener<PaymentRes>
    ) {
        AppApiHelper.getInstance().getPayment(memberId, onFinishApiListener)
    }

    override fun cancel(req: PaymentModel, onFinishApiListener: OnFinishApiListener<Status>) {
        AppApiHelper.getInstance().cancel(req, onFinishApiListener)
    }
}