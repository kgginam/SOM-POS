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
import seoil.capstone.som_pos.data.network.model.StockModel


class MenuManagementStockAdapter(
        private val stockList: ArrayList<MenuManagementActivity.StockData>,
        private val mPresenter: MenuManagementPresenter,
        private val mShopId: String,
        private val mContext: Context
) : RecyclerView.Adapter<MenuManagementStockAdapter.ViewHolder>() {

    private var mStockList: ArrayList<MenuManagementActivity.StockData>?= null
    private var mAlertDialog: AlertDialog?= null

    init {
        mStockList = stockList
    }

    companion object {
        private const val ADAPTER_EDIT = 1
        private const val ADAPTER_DELETE = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_stock, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        if (mStockList == null) {

            return 0
        }

        return mStockList!!.size
    }

    fun setData(stockList: ArrayList<MenuManagementActivity.StockData>) {
        if (mStockList != null) {
            mStockList!!.clear()
        }
        mStockList = stockList
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val textViewStockName = itemView.findViewById<TextView>(R.id.textViewStockNameRecycler)!!
        val textViewStockAmount = itemView.findViewById<TextView>(R.id.textViewStockAmountRecycler)!!

        init {

            fun onMenuItemClick(item: MenuItem): Boolean {

                when (item.itemId) {
                    ADAPTER_DELETE -> mPresenter.deleteStock(
                            mShopId,
                            mStockList!![adapterPosition].stockCode!!,
                            mStockList!![adapterPosition].stockName!!
                    )
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
                        editTextName.text = mStockList!![adapterPosition].stockName
                        val string: String = mStockList!![adapterPosition].stockAmount.toString()
                        editTextPrice.text = Editable.Factory.getInstance().newEditable(string)
                        mAlertDialog = builder.create()
                        mAlertDialog!!.show()
                        btnSubmit.setOnClickListener {
                            val amount = editTextPrice.text.toString()

                            if (!mPresenter.isNumeric(amount)) {

                                editTextPrice.error = "숫자만 입력해주세요"
                                editTextPrice.requestFocus()
                            } else {

                                mPresenter.updateStock(
                                        StockModel(
                                                mStockList!![adapterPosition].stockCode,
                                                mShopId,
                                                mStockList!![adapterPosition].stockName,
                                                mStockList!![adapterPosition].stockAmount,
                                                null
                                        )
                                )
                                notifyItemChanged(adapterPosition)
                                mAlertDialog!!.dismiss()
                            }
                        }
                    }
                    else -> {

                    }
                }
                return true
            }

            itemView.setOnCreateContextMenuListener { menu, v, menuInfo ->
                val edit: MenuItem = menu!!.add(Menu.NONE, ADAPTER_EDIT, 1, "편집")
                val delete: MenuItem = menu.add(Menu.NONE, ADAPTER_DELETE, 2, "삭제")
                edit.setOnMenuItemClickListener { item: MenuItem? -> onMenuItemClick(item!!) }
                delete.setOnMenuItemClickListener { item: MenuItem? -> onMenuItemClick(item!!) }
            }
        }
    }
}