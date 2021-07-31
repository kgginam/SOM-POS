package seoil.capstone.som_pos.ui.payment

import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import seoil.capstone.som_pos.R
import seoil.capstone.som_pos.data.network.model.PaymentModel


class PaymentAdapter(
        paymentData: ArrayList<PaymentModel>?,
        val mPresenter: PaymentPresenter
): RecyclerView.Adapter<PaymentAdapter.ViewHolder>() {

    private var mPaymentData: ArrayList<PaymentModel>? = null

    init {

        mPaymentData = paymentData
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_payment_item, null, false)
        val lp = RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        view.layoutParams = lp

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.textViewDate.text = mPaymentData!![holder.adapterPosition].paymentDate
        val temp: String = mPaymentData!![holder.adapterPosition].cost.toString() + "원"
        holder.textViewPrice.text = temp
    }

    override fun getItemCount(): Int {

        if (mPaymentData == null) {

            return 0
        }

        return mPaymentData!!.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val textViewDate: TextView = itemView.findViewById(R.id.textViewPaymentDateRecycler)
        val textViewPrice: TextView = itemView.findViewById(R.id.textViewPaymentPriceRecycler)

        init {

            fun onMenuItemClick(item: MenuItem): Boolean {

                mPresenter.createAlert(mPaymentData!![adapterPosition])

                return true
            }

            itemView.setOnCreateContextMenuListener { menu, v, menuInfo ->
                val edit: MenuItem = menu!!.add(
                        Menu.NONE,
                        0, 1, "구매 취소")
                edit.setOnMenuItemClickListener { item: MenuItem? -> onMenuItemClick(item!!) }
            }
        }
    }

    fun setData(paymentList: ArrayList<PaymentModel>?) {

        if (mPaymentData != null) {

            mPaymentData!!.clear()
        }

        mPaymentData = paymentList
        notifyDataSetChanged()
    }
}