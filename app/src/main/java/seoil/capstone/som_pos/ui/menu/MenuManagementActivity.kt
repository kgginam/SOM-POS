package seoil.capstone.som_pos.ui.menu

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import seoil.capstone.som_pos.GlobalApplication
import seoil.capstone.som_pos.R

class MenuManagementActivity : AppCompatActivity(), MenuManagementContract.View {

    private var mPresenter : MenuManagementPresenter?= null
    private var mRecyclerView : RecyclerView?= null
    private var mTabLayout : TabLayout? = null
    private var mShopId : String?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_management)

        mPresenter = MenuManagementPresenter()
        mPresenter!!.setView(this)
        mPresenter!!.createInteractor()

        initView()

        val mApp : GlobalApplication = applicationContext as GlobalApplication

        mShopId = mApp.getUserId()

        if (mApp.getCategory() == "음식") {

        }

        mRecyclerView!!.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

    }


    override fun onDestroy() {
        mPresenter!!.releaseView()
        mPresenter!!.releaseInteractor()
        mPresenter = null
        super.onDestroy()
    }

    private fun initView() {

        mRecyclerView = findViewById(R.id.recyclerViewMenu)
        mTabLayout = findViewById(R.id.tabLayoutMenu)
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

    private suspend fun initData() {


    }
}