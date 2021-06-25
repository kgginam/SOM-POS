package seoil.capstone.som_pos.base

// MVP 공용 메서드
public class BaseContract {

    // 공용 모델 인터페이스
    interface Interactor {

    }

    // 공용 뷰 인터페이스
    interface View {
        fun showProgress() // 로딩 창 보이기
        fun hideProgress() // 로딩 창 숨기기
        fun showDialog(msg: String?) // 다이얼로그 창 보이기
    }

    // 공용 프레젠터 인터페이스
    interface Presenter<T> {
        fun setView(view: T) // 뷰(액티비티 또는 프레그먼트) 설정
        fun releaseView() // 뷰 해제
        fun createInteractor() // 인터렉터 생성
        fun releaseInteractor() // 인터렉터 해제
    }
}