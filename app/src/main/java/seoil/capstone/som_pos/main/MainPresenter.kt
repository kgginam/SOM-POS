package seoil.capstone.som_pos.main

import seoil.capstone.som_pos.base.BaseContract

class MainPresenter : MainContract.Presenter {

    private var mView : MainContract.View? = null
    private var mInteractor : MainInteractor?= null

    override fun setView(view: MainContract.View) {
        mView = view
    }

    override fun releaseView() {
        mView = null
    }

    override fun createInteractor() {
        mInteractor = MainInteractor()
    }

    override fun releaseInteractor() {
        mInteractor = null
    }
}