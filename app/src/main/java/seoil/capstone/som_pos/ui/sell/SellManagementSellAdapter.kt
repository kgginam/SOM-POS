package seoil.capstone.som_pos.ui.sell

import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import seoil.capstone.som_pos.R
import seoil.capstone.som_pos.data.model.DataModel

class SellManagementSellAdapter(
        menuList: ArrayList<DataModel.MenuData>?,
        mPresenter: SellManagementPresenter
): RecyclerView.Adapter<SellManagementSellAdapter.ViewHolder>() {

    private var mMenuData: ArrayList<DataModel.MenuData>? = null
    private var mMenuMaxCount: ArrayList<DataModel.MenuMaxCount>? = null
    private var mCountData: ArrayList<Int>? = null
    private var mPresenter: SellManagementPresenter? = null

    init {
        mMenuData = menuList
        this.mPresenter = mPresenter

        mCountData = ArrayList()

        if (mMenuData != null) {

            for (i in mMenuData!!.indices) {

                mCountData!!.add(0)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_sell_menu_list, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.editTextMenuName.text = Editable.Factory.getInstance().newEditable(mMenuData!![holder.adapterPosition].menuName)
        holder.editTextMenuPrice.text = Editable.Factory.getInstance().newEditable(mMenuData!![holder.adapterPosition].menuPrice.toString() + "원")
        holder.editTextMenuCount.text = Editable.Factory.getInstance().newEditable(mCountData!![holder.adapterPosition].toString())


        holder.imageViewMinus.setOnClickListener {

            if (mCountData!![holder.adapterPosition] > 0) {

                mCountData!![holder.adapterPosition] = mCountData!![holder.adapterPosition] - 1
                holder.editTextMenuCount.text = Editable.Factory.getInstance()
                        .newEditable(mCountData!![holder.adapterPosition].toString())

                mPresenter!!.initTotalPrice(mCountData!!)
            }
        }

        holder.imageViewAdd.setOnClickListener {

            if (mMenuMaxCount!![holder.adapterPosition].menuAmount == -1) {

                mCountData!![holder.adapterPosition] = mCountData!![holder.adapterPosition] + 1
                holder.editTextMenuCount.text = Editable.Factory.getInstance()
                        .newEditable(mCountData!![holder.adapterPosition].toString())

                mPresenter!!.initTotalPrice(mCountData!!)
            } else if (mCountData!![holder.adapterPosition] < mMenuMaxCount!![holder.adapterPosition].menuAmount!!.toInt()) {

                mCountData!![holder.adapterPosition] = mCountData!![holder.adapterPosition] + 1
                holder.editTextMenuCount.text = Editable.Factory.getInstance()
                        .newEditable(mCountData!![holder.adapterPosition].toString())

                mPresenter!!.initTotalPrice(mCountData!!)
            }
        }
    }

    override fun getItemCount(): Int {

        if (mMenuData == null) {

            return 0
        }
        return mMenuData!!.size
    }

    fun setData(menuList: ArrayList<DataModel.MenuData>?) {

        if (mMenuData != null) {

            mMenuData!!.clear()
        }

        mMenuData = menuList

        if (mMenuData != null) {

            if (mCountData != null) {

                for (i in mMenuData!!.indices) {

                    mCountData!!.add(0)
                }
            }
        }

        notifyDataSetChanged()
    }

    fun setMenuMaxData(menuMaxCount: ArrayList<DataModel.MenuMaxCount>?) {

        mMenuMaxCount = menuMaxCount
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val editTextMenuName: EditText = itemView.findViewById(R.id.editTextSellMenuNameRecycler)
        val editTextMenuPrice: EditText = itemView.findViewById(R.id.editTextSellMenuPriceRecycler)
        val editTextMenuCount: EditText = itemView.findViewById(R.id.editTextMenuCountRecycler)
        val imageViewMinus: ImageView = itemView.findViewById(R.id.imageViewSellMenuMinusRecycler)
        val imageViewAdd: ImageView = itemView.findViewById(R.id.imageViewSellMenuCountAddRecycler)
    }
}