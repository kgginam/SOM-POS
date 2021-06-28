package seoil.capstone.som_pos.ui.menu

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import seoil.capstone.som_pos.R

class MenuManagementActivity : AppCompatActivity(), MenuManagementContract.View {

    private var mPresenter : MenuManagementPresenter?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock_management)

        mPresenter = MenuManagementPresenter()
        mPresenter!!.setView(this)
        mPresenter!!.createInteractor()
    }


    override fun onDestroy() {
        mPresenter!!.releaseView()
        mPresenter!!.releaseInteractor()
        mPresenter = null
        super.onDestroy()
    }

    private fun initView(view : View) {

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
}