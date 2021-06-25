package seoil.capstone.som_pos.util

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.view.inputmethod.InputMethodManager


// 여러 공용 기능 포함
// 싱글턴 클래스
class Utility {

    companion object {

        private var mUtility: Utility? = null

        fun instance(): Utility? {
            if (mUtility == null) {
                mUtility = seoil.capstone.som_pos.util.Utility()
            }
            return mUtility
        }
    }


    // 키보드 활성화
    fun activateKeyboard(activity: Activity) {
        val manager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        manager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

    // 키보드 비활성화
    fun deactivateKeyboard(activity: Activity) {
        val manager = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        manager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
    }


    // 다이얼로그 창 활성화
    fun showDialog(pramDialog: Dialog?, msg: String?, context: Context?, onClickListener: DialogInterface.OnClickListener?) {
        var pramDialog = pramDialog
        if (pramDialog == null) {
            val builder = AlertDialog.Builder(context)
            builder.setMessage(msg)
                    .setPositiveButton("확인", onClickListener)
            pramDialog = builder.create()
            pramDialog.show()
        }
    }

    // xml이 아닌 자바 코드에서 ui크기를 지정할 땐 px단위로 지정되므로
    // 일반적으로 안드로이드에서 사용하는 단위인 dp로 변경해주는 메서드
    fun changePxToDp(density: Float, `val`: Int): Int {
        return Math.round(`val` * density)
    }
}