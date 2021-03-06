package seoil.capstone.som_pos.main

import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import seoil.capstone.som_pos.GlobalApplication
import seoil.capstone.som_pos.R
import seoil.capstone.som_pos.ui.menu.MenuManagementActivity
import seoil.capstone.som_pos.ui.payment.PaymentActivity
import seoil.capstone.som_pos.ui.sell.SellManagementActivity
import seoil.capstone.som_pos.util.Utility

class MainActivity: AppCompatActivity(), View.OnClickListener, MainContract.View {

    private var mBtnMenu: Button? = null
    private var mBtnSell: Button? = null
    private var mBtnPayment: Button? = null
    private var mPresenter: MainPresenter? = null
    private var isSet: Boolean = false
    private var mDialog: Dialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()

        initListener()

        mPresenter = MainPresenter()
        mPresenter!!.createInteractor()
        mPresenter!!.setView(this)

    }

    override fun onResume() {
        super.onResume()

        val app: GlobalApplication = applicationContext as GlobalApplication
        isSet = mPresenter!!.isTextSet(app.getCategory())
        if (!isSet) {

            mPresenter!!.getShopInformation(app.getUserId())
        }
    }

    override fun onDestroy() {
        mPresenter!!.releaseView()
        mPresenter!!.releaseInteractor()
        mPresenter = null
        super.onDestroy()
    }

    private fun initView() {

        mBtnMenu = findViewById(R.id.btnMainMenuManagement)
        mBtnSell = findViewById(R.id.btnMainSellManagement)
        mBtnPayment = findViewById(R.id.btnMainPayment)
    }

    private fun initListener() {

        mBtnMenu!!.setOnClickListener(this)
        mBtnSell!!.setOnClickListener(this)
        mBtnPayment!!.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val id = v!!.id
        var intent: Intent? = null

        if(!isSet) {

            onResume()
            Toast.makeText(this, "?????? ??? ?????? ????????? ?????????", Toast.LENGTH_SHORT).show()
            return
        }

        if (id == R.id.btnMainMenuManagement) {

            intent = Intent(this, MenuManagementActivity::class.java)
        }

        if (id == R.id.btnMainSellManagement) {

            intent = Intent(this, SellManagementActivity::class.java)
        }

        if (id == R.id.btnMainPayment) {

            intent = Intent(this, PaymentActivity::class.java)
        }

        if (intent != null) {

            startActivity(intent)
        }
    }

    override fun setGlobalData(category: String) {
        val app: GlobalApplication = applicationContext as GlobalApplication
        app.setCategory(category)
        isSet = true
    }

    override fun showProgress() {

    }

    override fun hideProgress() {

    }

    override fun showDialog(msg: String?) {

        val onClickListener: DialogInterface.OnClickListener = DialogInterface.OnClickListener { dialog, which ->
            if (mDialog != null) {

                mDialog = null
            }
        }

        if (mDialog == null) {

            Utility.instance()!!.showDialog(mDialog, msg, this, onClickListener)
        }
    }
}