import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import seoil.capstone.som_pos.R
import seoil.capstone.som_pos.data.model.DataModel
import seoil.capstone.som_pos.ui.menu.MenuManagementPresenter
import java.lang.StringBuilder

class MenuManagementUpdateIngredientsAdapter(
        stockData: ArrayList<DataModel.StockData>?,
        private val mPresenter: MenuManagementPresenter,
        private val mPosition: Int?
) : RecyclerView.Adapter<MenuManagementUpdateIngredientsAdapter.ViewHolder>() {

    private var stockData: ArrayList<DataModel.StockData>? = null
    private var ingredientsData: ArrayList<DataModel.CountData>? = null

    init {

        ingredientsData = ArrayList()
        setData(stockData)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_menu_insert_stock, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.textName.text = Editable.Factory.getInstance().newEditable(stockData!![holder.adapterPosition].stockName)
        holder.textCount.text = Editable.Factory.getInstance().newEditable(ingredientsData!![holder.adapterPosition].stockAmount!!.toString())

    }

    override fun getItemCount(): Int {
        if (stockData == null) {

            return 0
        }

        return stockData!!.size
    }

    fun getStockIngredients(): String {

        val temp: StringBuilder = StringBuilder()

        var isFirst: Boolean = true

        for (i in stockData!!.indices) {

            if (ingredientsData!![i].stockAmount!! > 0) {

                if (isFirst) {

                    isFirst = false
                } else {

                    temp.append(",")
                }

                temp.append(stockData!![i].stockCode.toString() + ":" + ingredientsData!![i].stockAmount.toString())
            }
        }

        return temp.toString()
    }

    fun setData(stockData: ArrayList<DataModel.StockData>?) {

        if (this.stockData != null) {

            stockData!!.clear()
        }

        this.stockData = stockData

        if (ingredientsData != null) {

            ingredientsData!!.clear()
        }

        val temp: String = mPresenter.getIngredients(mPosition!!).toString()

        if (temp != "") {

            val firstSplit = temp.split(",")
            val secondSplit: ArrayList<DataModel.CountData> = ArrayList()
            for (i in firstSplit.indices) {

                val tempList = firstSplit[i].split(":")
                secondSplit.add(DataModel.CountData(tempList[0].toInt(), tempList[1].toInt()))
            }

            for (i in stockData!!.indices) {

                if (secondSplit.find { it.stockCode == stockData[i].stockCode } == null) {

                    secondSplit.add(DataModel.CountData(stockData[i].stockCode, 0))
                }
            }

            secondSplit.sortBy { it.stockCode }

            ingredientsData!!.addAll(secondSplit)
        } else {

            for (i in stockData!!.indices) {

                ingredientsData!!.add(DataModel.CountData(stockData[i].stockCode, 0))
            }
        }

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val textName: EditText = itemView.findViewById(R.id.editTextMenuInsertStockName)
        val textCount: EditText = itemView.findViewById(R.id.editTextMenuInsertStockCount)
        private val countMinus: ImageView = itemView.findViewById(R.id.imageViewMenuInsertStockMinus)
        private val countPlus: ImageView = itemView.findViewById(R.id.imageViewMenuInsertStockAdd)

        init {

            countMinus.setOnClickListener {

                if (ingredientsData!![adapterPosition].stockAmount!! > 0) {

                    ingredientsData!![adapterPosition].stockAmount = ingredientsData!![adapterPosition].stockAmount!! - 1
                    textCount.text = Editable.Factory.getInstance()
                            .newEditable(ingredientsData!![adapterPosition].stockAmount.toString())
                }
            }

            countPlus.setOnClickListener {

                if (ingredientsData!![adapterPosition].stockAmount!! < stockData!![adapterPosition].stockAmount!!) {

                    ingredientsData!![adapterPosition].stockAmount = ingredientsData!![adapterPosition].stockAmount!! + 1
                    textCount.text = Editable.Factory.getInstance()
                            .newEditable(ingredientsData!![adapterPosition].stockAmount.toString())
                }
            }
        }
    }

}