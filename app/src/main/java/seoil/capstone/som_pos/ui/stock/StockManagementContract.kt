package seoil.capstone.som_pos.ui.stock

import seoil.capstone.som_pos.base.BaseContract

interface StockManagementContract {

    interface View : BaseContract.View {

    }

    interface Presenter : BaseContract.Presenter<View> {

    }

    interface Interactor : BaseContract.Interactor {

    }
}