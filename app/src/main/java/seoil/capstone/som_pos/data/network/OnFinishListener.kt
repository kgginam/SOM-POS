package seoil.capstone.som_pos.data.network

// 서버 api의 수행 결과에 따른 처리를 위한 인터페이스
interface OnFinishApiListener<in T> {

    fun onSuccess(t: T) // 성공 시 수행
    fun onFailure(t: Throwable?) // 에러 발생 시 예외 처리 수행
}
