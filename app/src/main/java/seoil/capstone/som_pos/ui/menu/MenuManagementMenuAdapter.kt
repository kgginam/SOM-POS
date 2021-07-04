package seoil.capstone.som_pos.ui.menu

import android.content.Context
import android.text.Editable
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import seoil.capstone.som_pos.R

class MenuManagementMenuAdapter(
        private val menuList: ArrayList<MenuManagementActivity.MenuData>,
        private val mPresenter: MenuManagementPresenter,
        private val mShopId: String,
        private val mContext: Context
): RecyclerView.Adapter<MenuManagementMenuAdapter.ViewHolder>() {

    private var mMenuList: ArrayList<MenuManagementActivity.MenuData>? = null
    private var mAlertDialog: AlertDialog?= null

    companion object {

        private const val ADAPTER_EDIT = 1
        private const val ADAPTER_DELETE = 2
        private const val ADAPTER_STOCK_EDIT = 3
    }

    init {
        mMenuList = menuList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_menu, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.textViewMenuName.text = mMenuList!![holder.adapterPosition].menuName
        holder.textViewMenuPrice.text = Editable.Factory.getInstance().newEditable(mMenuList!![holder.adapterPosition].menuPrice.toString() + "원")
    }

    override fun getItemCount(): Int {
        if (mMenuList == null) {

            return 0
        }

        return mMenuList!!.size
    }

    fun setData(menuList: ArrayList<MenuManagementActivity.MenuData>) {
        if (mMenuList != null) {
            mMenuList!!.clear()
        }
        mMenuList = menuList
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val textViewMenuName: TextView = itemView.findViewById(R.id.textViewMenuNameRecycler)
        val textViewMenuPrice: TextView = itemView.findViewById(R.id.textViewMenuPriceRecycler)

        init {

            fun onMenuItemClick(item: MenuItem): Boolean {

                when(item.itemId) {
                    ADAPTER_DELETE -> mPresenter.deleteMenu(mShopId, mMenuList!![adapterPosition].menuName!!)
                    ADAPTER_EDIT -> {
                        if (mAlertDialog != null) {
                            mAlertDialog!!.dismiss()
                        }
                        //다이얼로그로 재고 편집창 생성
                        val builder = AlertDialog.Builder(mContext)
                        val view: View = LayoutInflater.from(mContext).inflate(R.layout.dialog_menu_edit, null, false)

                        builder.setView(view)

                        val btnSubmit = view.findViewById<Button>(R.id.btnMenuSubitDialog)
                        val editTextName = view.findViewById<TextView>(R.id.editTextMenuNameDialog)
                        val editTextPrice = view.findViewById<EditText>(R.id.editTextMenuPriceDialog)
                        val string: String = mMenuList!![adapterPosition].menuPrice.toString()

                        editTextName.text = mMenuList!![adapterPosition].menuName
                        editTextPrice.text = Editable.Factory.getInstance().newEditable(string)

                        mAlertDialog = builder.create()
                        mAlertDialog!!.show()

                        btnSubmit.setOnClickListener {
                            val amount = editTextPrice.text.toString()

                            if (!mPresenter.isNumeric(amount)) {

                                editTextPrice.error = "숫자만 입력해주세요"
                                editTextPrice.requestFocus()
                            } else {

                                mPresenter.updateMenu(mShopId,
                                        mMenuList!![adapterPosition].menuName!!,
                                        editTextName.text.toString(),
                                        editTextPrice.text.toString().toInt(),
                                        mMenuList!![adapterPosition].menuIngredients!!
                                )
                                notifyItemChanged(adapterPosition)
                                mAlertDialog!!.dismiss()
                            }
                        }
                    }
                    ADAPTER_STOCK_EDIT -> {

                    }
                    else -> {

                    }
                }
                return true
            }

            itemView.setOnCreateContextMenuListener { menu, v, menuInfo ->
                val edit: MenuItem = menu!!.add(Menu.NONE, ADAPTER_EDIT, 1, "편집")
                val delete: MenuItem = menu.add(Menu.NONE, ADAPTER_DELETE, 2, "삭제")
                val stockEdit: MenuItem = menu.add(Menu.NONE, ADAPTER_STOCK_EDIT, 3, "재료 변경")
                edit.setOnMenuItemClickListener { item: MenuItem? -> onMenuItemClick(item!!) }
                delete.setOnMenuItemClickListener { item: MenuItem? -> onMenuItemClick(item!!) }
                stockEdit.setOnMenuItemClickListener { item: MenuItem? -> onMenuItemClick(item!!) }
            }
        }
    }
}