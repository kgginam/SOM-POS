package seoil.capstone.som_pos.main

import seoil.capstone.som_pos.base.BaseContract

interface MainContract {

    interface Interactor : BaseContract.Interactor {

    }

    interface View : BaseContract.View {

    }

    interface Presenter : BaseContract.Presenter<View> {

    }
}