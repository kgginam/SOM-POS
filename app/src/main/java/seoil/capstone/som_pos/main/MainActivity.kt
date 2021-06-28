package seoil.capstone.som_pos.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import seoil.capstone.som_pos.R
import seoil.capstone.som_pos.ui.sell.SellManagementFragment
import seoil.capstone.som_pos.ui.stock.StockManagementFragment

class MainActivity : AppCompatActivity(), MainContract.View {

    companion object {

        private const val SELECTED_STOCK : Int = 0
        private const val SELECTED_SELL : Int = 1

        private const val mFrameLayoutId : Int = R.id.frameLayoutMain
    }

    private var mPresenter: MainPresenter? = null
    private var mTabLayout: TabLayout?= null
    private var mSelectedIndex : Int?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mPresenter = MainPresenter()
        mPresenter!!.setView(this)
        mPresenter!!.createInteractor()

        initView()

        initListener()
        mTabLayout!!.addTab(mTabLayout!!.newTab().setText("재고 관리"))
        mTabLayout!!.addTab(mTabLayout!!.newTab().setText("판매"))

        mSelectedIndex = SELECTED_STOCK

        supportFragmentManager
                .beginTransaction()
                .add(mFrameLayoutId, StockManagementFragment())
                .commit()
    }

    override fun onDestroy() {
        mPresenter!!.releaseInteractor()
        mPresenter!!.releaseView()
        mPresenter = null
        super.onDestroy()
    }

    override fun showProgress() {
        TODO("Not yet implemented")
    }

    override fun hideProgress() {
        TODO("Not yet implemented")
    }

    override fun showDialog(msg: String?) {
        TODO("Not yet implemented")
    }

    private fun initView() {

        mTabLayout = findViewById(R.id.tabLayoutMain)
    }

    private fun initListener() {

        mTabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                val position : Int = tab!!.position

                var selectedFragment : Fragment?= null

                if (position == SELECTED_STOCK) {

                    if (mSelectedIndex != SELECTED_STOCK) {

                        mSelectedIndex = SELECTED_STOCK
                        selectedFragment = StockManagementFragment()
                    }
                } else if (position == SELECTED_SELL) {

                    if (mSelectedIndex != SELECTED_SELL) {

                        mSelectedIndex = SELECTED_SELL
                        selectedFragment = SellManagementFragment()
                    }
                }

                if (selectedFragment != null) {

                    supportFragmentManager
                            .beginTransaction()
                            .replace(mFrameLayoutId, selectedFragment)
                            .commit()
                }
            }

        })
    }
}