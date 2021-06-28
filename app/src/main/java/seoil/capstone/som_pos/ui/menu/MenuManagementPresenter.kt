package seoil.capstone.som_pos.ui.menu

class MenuManagementPresenter : MenuManagementContract.Presenter {

    private var mView : MenuManagementContract.View?= null
    private var mInteractor : MenuManagementInteractor?= null

    override fun setView(view: MenuManagementContract.View) {
        mView = view
    }

    override fun releaseView() {
        mView = null
    }

    override fun createInteractor() {
        mInteractor = MenuManagementInteractor()
    }

    override fun releaseInteractor() {
        mInteractor = null
    }
}