package seoil.capstone.som_pos.data.model

class DataModel {

    data class MenuData(var menuCode: Int?, var menuName: String?, var menuIngredients: String?, var menuPrice: Int?)
    data class StockData(var stockCode: Int?, var stockName: String?, var stockAmount: Int?)
    data class CountData(var stockCode: Int?, var stockAmount: Int?)
    data class MenuMaxCount(var menuCode: Int?, var menuAmount: Int?)
}