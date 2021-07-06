package seoil.capstone.som_pos.ui.sell

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*
import seoil.capstone.som_pos.GlobalApplication
import seoil.capstone.som_pos.R
import seoil.capstone.som_pos.data.model.DataModel
import seoil.capstone.som_pos.util.Utility

class SellManagementActivity:AppCompatActivity(), SellManagementContract.View{

    private val mDelayTime = 60L * 5L * 1000L

    private var mPresenter: SellManagementPresenter? = null
    private var mAlertDialog: AlertDialog? = null
    private var mRecyclerView: RecyclerView? = null
    private var mTextViewTotalPrice: TextView? = null
    private var mImageViewPurchase: ImageView? = null
    private var mSellAdapter: SellManagementSellAdapter? = null
    private var mMenuData: ArrayList<DataModel.MenuData>? = null
    private var mJob: Job? = null
    private var mShopId: String? = null
    private var mApp: GlobalApplication? = null
    private var mTotalPrice: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sell_management)

        initView()
        initListener()

        mPresenter = SellManagementPresenter()
        mPresenter!!.setView(this)
        mPresenter!!.createInteractor()

        mApp = applicationContext as GlobalApplication

        mShopId = mApp!!.getUserId()

        mRecyclerView!!.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mSellAdapter = SellManagementSellAdapter(mMenuData, mPresenter!!)
        mRecyclerView!!.adapter = mSellAdapter

        initCoroutine()

        mMenuData = ArrayList()
    }

    override fun onDestroy() {
        mJob!!.cancel()

        mPresenter!!.releaseInteractor()
        mPresenter!!.releaseView()
        mPresenter = null
        super.onDestroy()
    }

    override fun setMenuInfo(menuList: ArrayList<DataModel.MenuData>) {
        mMenuData = menuList
        mSellAdapter!!.setData(mMenuData)
    }

    override fun initTotalPrice(countList: ArrayList<Int>) {

        mTotalPrice = 0

        for (i in countList.indices) {

            if (countList[i] > 0) {

                mTotalPrice = mTotalPrice!! +  (mMenuData!![i].menuPrice!! * countList[i])
            }
        }

        val temp: String = mTotalPrice!!.toString() + "원"
        mTextViewTotalPrice!!.text = temp
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

        mRecyclerView = findViewById(R.id.recyclerViewSellMenuList)
        mTextViewTotalPrice = findViewById(R.id.textViewSellTotalPrice)
        mImageViewPurchase = findViewById(R.id.imageViewSellMenuPurchase)
    }

    private fun initListener() {

        mImageViewPurchase!!.setOnClickListener {

        }
    }

    private fun initCoroutine() {

        mJob = CoroutineScope(Dispatchers.Default).launch {

            repeat(100_000) { i ->
                yield()
                mPresenter!!.getMenuInfo(mShopId!!)

                delay(mDelayTime)
            }
        }
    }
}