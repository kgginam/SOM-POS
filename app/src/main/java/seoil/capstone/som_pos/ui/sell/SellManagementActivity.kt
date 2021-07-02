package seoil.capstone.som_pos.ui.sell

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import seoil.capstone.som_pos.R

class SellManagementActivity:AppCompatActivity(), SellManagementContract.View{

    private var mPresenter: SellManagementPresenter?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sell_management)

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
        TODO("Not yet implemented")
    }

    override fun hideProgress() {
        TODO("Not yet implemented")
    }

    override fun showDialog(msg: String?) {
        TODO("Not yet implemented")
    }

    private fun initView(view: View) {

    }
}