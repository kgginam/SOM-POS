package seoil.capstone.som_pos.ui.stock

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import seoil.capstone.som_pos.R

class StockManagementFragment : Fragment(), StockManagementContract.View {

    private var mPresenter : StockManagementPresenter?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mPresenter = StockManagementPresenter()
        mPresenter!!.setView(this)
        mPresenter!!.createInteractor()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view : View = inflater.inflate(R.layout.fragment_stock_management, container, false)

        initView(view)
        return view
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