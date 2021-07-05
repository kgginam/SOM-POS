package seoil.capstone.som_pos.main

import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import seoil.capstone.som_pos.GlobalApplication
import seoil.capstone.som_pos.R
import seoil.capstone.som_pos.ui.menu.MenuManagementActivity
import seoil.capstone.som_pos.ui.sell.SellManagementActivity
import seoil.capstone.som_pos.util.Utility

class MainActivity: AppCompatActivity(), View.OnClickListener, MainContract.View {

    private var mBtnMenu: Button? = null
    private var mBtnSell: Button? = null
    private var mPresenter: MainPresenter? = null
    private var isEnd: Boolean? = false
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

    override fun onStart() {
        super.onStart()

        val app: GlobalApplication = applicationContext as GlobalApplication
        mPresenter!!.getShopInformation(app.getUserId())
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
    }

    private fun initListener() {


        mBtnMenu!!.setOnClickListener(this)
        mBtnSell!!.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val id = v!!.id
        var intent: Intent? = null

        if(!isEnd!!) {

            Toast.makeText(this, "잠시 후 다시 시도해 주세요", Toast.LENGTH_SHORT).show()
            return
        }

        if (id == R.id.btnMainMenuManagement) {

            intent = Intent(this, MenuManagementActivity::class.java)
        }

        if (id == R.id.btnMainSellManagement) {

            intent = Intent(this, SellManagementActivity::class.java)
        }

        if (intent != null) {

            startActivity(intent)
        }
    }

    override fun setGlobalData(category: String) {
        val app: GlobalApplication = applicationContext as GlobalApplication
        app.setCategory(category)
        isEnd = true
    }

    override fun showProgress() {
        //TODO("Not yet implemented")
    }

    override fun hideProgress() {
        //TODO("Not yet implemented")
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