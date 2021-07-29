package seoil.capstone.som_pos.ui.payment

import seoil.capstone.som_pos.base.BaseContract
import seoil.capstone.som_pos.data.network.OnFinishApiListener
import seoil.capstone.som_pos.data.network.model.PaymentModel
import seoil.capstone.som_pos.data.network.model.PaymentRes
import seoil.capstone.som_pos.data.network.model.Status

interface PaymentContract {

    interface View: BaseContract.View {

        fun setPaymentData(paymentData: ArrayList<PaymentModel>?)
        fun createAlert(deleteData: PaymentModel)
        fun initData()
        fun finishActivity(msg: String)
    }

    interface Presenter: BaseContract.Presenter<View> {

    }

    interface Interactor: BaseContract.Interactor {

        fun getPayment(memberId: String, onFinishApiListener: OnFinishApiListener<PaymentRes>)
        fun cancel(req: PaymentModel, onFinishApiListener: OnFinishApiListener<Status>)
    }
}