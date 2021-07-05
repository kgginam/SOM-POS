package seoil.capstone.som_pos.ui.sell

class SellManagementPresenter: SellManagementContract.Presenter{

    private var mView: SellManagementContract.View ? = null
    private var mInteractor: SellManagementInteractor ? = null

    override fun setView(view: SellManagementContract.View) {
        mView = view
    }

    override fun releaseView() {
        mView = null
    }

    override fun createInteractor() {
        mInteractor = SellManagementInteractor()
    }

    override fun releaseInteractor() {
        mInteractor = null
    }
}