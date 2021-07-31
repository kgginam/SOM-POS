package seoil.capstone.som_pos.ui.payment

import android.content.DialogInterface
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import seoil.capstone.som_pos.GlobalApplication
import seoil.capstone.som_pos.R
import seoil.capstone.som_pos.data.network.model.PaymentModel
import seoil.capstone.som_pos.util.Utility

class PaymentActivity : AppCompatActivity(), PaymentContract.View {

    private var mAlertDialog: AlertDialog? = null
    private var mPresenter: PaymentPresenter? = null
    private var mPaymentData: ArrayList<PaymentModel>? = null
    private var mUserId: String? = null
    private var mRecyclerView: RecyclerView? = null
    private var mAdapter: PaymentAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        initView()

        mPresenter = PaymentPresenter()
        mPresenter!!.setView(this)
        mPresenter!!.createInteractor()

        mPaymentData = ArrayList()

        val app: GlobalApplication = applicationContext as GlobalApplication

        mUserId = app.getUserId()
        mPresenter!!.getPayment(mUserId!!)

        mRecyclerView!!.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        mAdapter = PaymentAdapter(mPaymentData, mPresenter!!)
        mRecyclerView!!.adapter = mAdapter
    }

    override fun onDestroy() {

        mPresenter!!.releaseInteractor()
        mPresenter!!.releaseView()
        mPresenter = null
        super.onDestroy()
    }

    override fun setPaymentData(paymentData: ArrayList<PaymentModel>?) {

        mPaymentData = paymentData
        mAdapter!!.setData(mPaymentData)
    }

    override fun showProgress() {

    }

    override fun hideProgress() {

    }

    override fun showDialog(msg: String?) {

        val onClickListener: DialogInterface.OnClickListener = DialogInterface.OnClickListener { dialog, which ->
            if (mAlertDialog != null) {

                mAlertDialog = null
            }
        }

        if (mAlertDialog == null) {

            Utility.instance()!!.showDialog(mAlertDialog, msg, this, onClickListener)
        }
    }

    private fun initView() {

        mRecyclerView = findViewById(R.id.recyclerViewPayment)
    }

    override fun createAlert(deleteData: PaymentModel) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("구매 취소 확인")
            .setMessage("구매를 취소하시겠습니까?")
            .setCancelable(false)
            .setPositiveButton("확인") { dialog, which ->
                mPresenter!!.cancel(deleteData)
                dialog.dismiss()
            }
            .setNegativeButton("취소") { dialog, which ->
                dialog.cancel()
            }
        val dialog = builder.create()
        dialog.show()
    }

    override fun initData() {
        mPresenter!!.getPayment(mUserId!!)
    }

    override fun finishActivity(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        finish()
    }
}