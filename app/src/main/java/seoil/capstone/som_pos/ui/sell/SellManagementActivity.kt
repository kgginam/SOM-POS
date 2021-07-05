package seoil.capstone.som_pos.ui.sell

import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import seoil.capstone.som_pos.R
import seoil.capstone.som_pos.util.Utility

class SellManagementActivity:AppCompatActivity(), SellManagementContract.View{

    private var mPresenter: SellManagementPresenter? = null
    private var mAlertDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sell_management)

        initView()

        mPresenter = SellManagementPresenter()
        mPresenter!!.setView(this)
        mPresenter!!.createInteractor()
    }

    override fun onDestroy() {
        mPresenter!!.releaseInteractor()
        mPresenter!!.releaseView()
        mPresenter = null
        super.onDestroy()
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

    }
}