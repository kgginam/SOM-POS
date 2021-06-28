package seoil.capstone.som_pos.ui.stock

class StockManagementPresenter : StockManagementContract.Presenter {

    private var mView : StockManagementContract.View?= null
    private var mInteractor : StockManagementInteractor?= null

    override fun setView(view: StockManagementContract.View) {
        mView = view
    }

    override fun releaseView() {
        mView = null
    }

    override fun createInteractor() {
        mInteractor = StockManagementInteractor()
    }

    override fun releaseInteractor() {
        mInteractor = null
    }
}