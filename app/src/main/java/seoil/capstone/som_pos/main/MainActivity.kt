package seoil.capstone.som_pos.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import seoil.capstone.som_pos.R

class MainActivity : AppCompatActivity(), MainContract.View {

    private var mPresenter: MainPresenter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mPresenter = MainPresenter()
        mPresenter!!.setView(this)
        mPresenter!!.createInteractor()
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