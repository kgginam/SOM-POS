package seoil.capstone.som_pos.ui.payment

import android.util.Log
import seoil.capstone.som_pos.data.network.OnFinishApiListener
import seoil.capstone.som_pos.data.network.api.PaymentApi
import seoil.capstone.som_pos.data.network.model.PaymentModel
import seoil.capstone.som_pos.data.network.model.PaymentRes
import seoil.capstone.som_pos.data.network.model.Status

class PaymentPresenter: PaymentContract.Presenter {

    var mView: PaymentContract.View? = null
    var mInteractor: PaymentInteractor? = null

    override fun setView(view: PaymentContract.View) {
        mView = view
    }

    override fun releaseView() {
        mView = null
    }

    override fun createInteractor() {
        mInteractor = PaymentInteractor()
    }

    override fun releaseInteractor() {
        mInteractor = null
    }

    fun createAlert(deleteData: PaymentModel) {
        mView!!.createAlert(deleteData)
    }

    fun getPayment(memberId: String) {

        val onFinishApiListener: OnFinishApiListener<PaymentRes> =
            object: OnFinishApiListener<PaymentRes> {
                override fun onSuccess(t: PaymentRes) {

                    when (t.status) {

                        PaymentApi.SUCCESS -> {

                            val temp: List<PaymentModel> = t.results
                            val result: ArrayList<PaymentModel> = ArrayList()

                            for (i in temp.indices) {

                                result.add(temp[i])
                            }

                            mView!!.setPaymentData(result)
                        }

                        PaymentApi.ERROR_NONE_DATA -> {
                            mView!!.finishActivity("데이터가 없습니다.")
                        }

                        else -> {
                            mView!!.finishActivity("서버 오류입니다.")
                        }
                    }
                }

                override fun onFailure(t: Throwable?) {
                    Log.d("getPayment", t.toString())
                }
            }

        mInteractor!!.getPayment(memberId, onFinishApiListener)
    }

    fun cancel(req: PaymentModel) {

        val onFinishApiListener: OnFinishApiListener<Status> =
            object: OnFinishApiListener<Status> {
                override fun onSuccess(t: Status) {

                    when (t.status) {

                        PaymentApi.SUCCESS -> {

                            mView!!.showDialog("취소되었습니다.")
                            mView!!.initData()
                        }

                        else -> {

                            mView!!.finishActivity("서버 오류입니다. 관리자에게 문의해주세요")
                        }
                    }
                }

                override fun onFailure(t: Throwable?) {
                    Log.d("cancel", t.toString())
                }

            }

        mInteractor!!.cancel(req, onFinishApiListener)
    }
}