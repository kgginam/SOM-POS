package seoil.capstone.som_pos.ui.menu

import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import seoil.capstone.som_pos.R
import java.lang.StringBuilder

class MenuManagementMenuInsertStockAdapter(
        stockData: ArrayList<MenuManagementActivity.StockData>?
) : RecyclerView.Adapter<MenuManagementMenuInsertStockAdapter.ViewHolder>() {

    private var stockData: ArrayList<MenuManagementActivity.StockData>?= null
    private var countData: ArrayList<Int>?= null

    init {
        this.stockData = stockData

        countData = ArrayList()

        for(i in this.stockData!!.indices) {

            countData!!.add(0)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_menu_insert_stock, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.textName.text = Editable.Factory.getInstance().newEditable(stockData!![holder.adapterPosition].stockName)
        holder.textCount.text = Editable.Factory.getInstance().newEditable(countData!![holder.adapterPosition].toString())
    }

    override fun getItemCount(): Int {
        if (stockData == null) {

            return 0
        }

        return stockData!!.size
    }

    fun getStockIngredients(): String {

        val temp: StringBuilder = StringBuilder()

        for(i in stockData!!.indices) {

            if (countData!![i] != 0) {

                if (i != 0) {

                    temp.append(",")
                }

                temp.append(stockData!![i].stockCode.toString() + ":" + countData!![i].toString())
            }
        }

        return temp.toString()
    }

    fun setData(data: ArrayList<MenuManagementActivity.StockData>?) {

        if (stockData != null || stockData!!.size == 0) {

            stockData!!.clear()
        }
        stockData = data

        if (countData != null || countData!!.size == 0) {

            countData!!.clear()
        }

        for (i in stockData!!.indices) {

            countData!!.add(0)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val textName: EditText = itemView.findViewById(R.id.editTextMenuInsertStockName)
        val textCount: EditText = itemView.findViewById(R.id.editTextMenuInsertStockCount)
        private val countMinus: ImageView = itemView.findViewById(R.id.imageViewMenuInsertStockMinus)
        private val countPlus: ImageView = itemView.findViewById(R.id.imageViewMenuInsertStockAdd)

        init {

            countMinus.setOnClickListener {

                if (countData!![adapterPosition] > 0) {

                    countData!![adapterPosition] = countData!![adapterPosition] - 1
                    textCount.text = Editable.Factory.getInstance()
                        .newEditable(countData!![adapterPosition].toString())
                }
            }

            countPlus.setOnClickListener {

                if (countData!![adapterPosition] < stockData!![adapterPosition].stockAmount!!) {

                    countData!![adapterPosition] = countData!![adapterPosition] + 1
                    textCount.text = Editable.Factory.getInstance()
                        .newEditable(countData!![adapterPosition].toString())
                }
            }
        }
    }
}