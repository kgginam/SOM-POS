package seoil.capstone.som_pos.ui.menu

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.*
import seoil.capstone.som_pos.GlobalApplication
import seoil.capstone.som_pos.R
import seoil.capstone.som_pos.data.network.model.MenuModel
import seoil.capstone.som_pos.data.network.model.MenuRes
import seoil.capstone.som_pos.data.network.model.StockRes

class MenuManagementActivity: AppCompatActivity(), MenuManagementContract.View {

    private val mDelayTime = 60L * 5L * 1000L
    private var mPresenter: MenuManagementPresenter?= null
    private var mRecyclerView: RecyclerView?= null
    private var mTabLayout: TabLayout? = null
    private var mShopId: String?= null
    private var mMenuData: ArrayList<MenuData>?= null
    private var mStockData: ArrayList<StockData>?= null
    private var mJob: Job?= null
    private var mApp: GlobalApplication?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_management)

        mPresenter = MenuManagementPresenter()
        mPresenter!!.setView(this)
        mPresenter!!.createInteractor()

        mMenuData = ArrayList()
        mStockData = ArrayList()

        initView()

        mApp = applicationContext as GlobalApplication

        mShopId = mApp!!.getUserId()

        initCoroutine()

        mRecyclerView!!.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

    }


    override fun onDestroy() {
        mPresenter!!.releaseView()
        mPresenter!!.releaseInteractor()
        mPresenter = null

        if (mJob != null) {

            mJob!!.cancel()
        }
        super.onDestroy()
    }

    private fun initView() {

        mRecyclerView = findViewById(R.id.recyclerViewMenu)
        mTabLayout = findViewById(R.id.tabLayoutMenu)
    }

    override fun setMenuInfo(menuInfo: ArrayList<MenuData>) {
        mMenuData = menuInfo
    }

    override fun setStock(stockData: ArrayList<StockData>) {
        mStockData = stockData
    }

    override fun initMenu() {
        mPresenter!!.getMenuInfo(mShopId!!)
    }

    override fun initStock() {
        mPresenter!!.getStock(mShopId!!)
    }

    override fun showProgress() {
        //TODO("Not yet implemented")
    }

    override fun hideProgress() {
        //TODO("Not yet implemented")
    }

    override fun showDialog(msg: String?) {
        //TODO("Not yet implemented")
    }

    private fun initCoroutine() {

        mJob = CoroutineScope(Dispatchers.Default).launch {

            repeat(100_000) { i ->
                yield()
                if (mApp!!.getCategory() == "음식") {

                    mPresenter!!.getMenuInfo(mShopId!!)
                    mPresenter!!.getStock(mShopId!!)
                } else {

                    mPresenter!!.getMenuInfo(mShopId!!)
                    mPresenter!!.getStock(mShopId!!)
                }

                delay(mDelayTime)
            }
        }
    }

    data class MenuData(var menuName: String, var menuIngredients: String, var menuPrice: Int)
    data class StockData(var stockCode: Int?, var stockName: String?, var stockAmount: Int?)
}