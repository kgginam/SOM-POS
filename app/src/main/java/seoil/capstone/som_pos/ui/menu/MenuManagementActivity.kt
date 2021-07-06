package seoil.capstone.som_pos.ui.menu

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.*
import seoil.capstone.som_pos.GlobalApplication
import seoil.capstone.som_pos.R
import seoil.capstone.som_pos.data.model.DataModel
import seoil.capstone.som_pos.data.network.model.MenuModel
import seoil.capstone.som_pos.data.network.model.StockModel
import seoil.capstone.som_pos.util.Utility


class MenuManagementActivity: AppCompatActivity(), MenuManagementContract.View {

    private val mDelayTime = 60L * 5L * 1000L

    private var mApp: GlobalApplication? = null
    private var mPresenter: MenuManagementPresenter? = null
    private var mJob: Job? = null

    private var mRecyclerView: RecyclerView? = null
    private var mTabLayout: TabLayout? = null
    private var mImageBtnAdd: ImageView? = null

    private var mShopId: String? = null
    private var mSelectedTab: Int? = null

    private var mMenuData: ArrayList<DataModel.MenuData>? = null
    private var mStockData: ArrayList<DataModel.StockData>? = null

    private var mAlertDialog: AlertDialog? = null

    private var mNameQuery: String? = null
    private var mIntQuery: Int? = null
    private var mIngredientsQuery: String? = null

    private var mMenuAdapter: MenuManagementMenuAdapter? = null
    private var mStockAdapter: MenuManagementStockAdapter? = null
    private var mMenuInsertStockAdapter: MenuManagementMenuInsertStockAdapter? = null

    companion object {

        private const val SELECTED_MENU = 0
        private const val SELECTED_STOCK = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_management)

        mPresenter = MenuManagementPresenter()
        mPresenter!!.setView(this)
        mPresenter!!.createInteractor()

        mMenuData = ArrayList()
        mStockData = ArrayList()

        initView()
        initListener()

        mApp = applicationContext as GlobalApplication

        mShopId = mApp!!.getUserId()

        initCoroutine()

        mMenuAdapter = MenuManagementMenuAdapter(mMenuData!!, mPresenter!!, this)
        mStockAdapter = MenuManagementStockAdapter(mStockData!!, mPresenter!!, mShopId!!, this)
        mMenuInsertStockAdapter = MenuManagementMenuInsertStockAdapter(mStockData)

        mRecyclerView!!.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mRecyclerView!!.adapter = mMenuAdapter

        mSelectedTab = SELECTED_MENU
        mTabLayout!!.addTab(mTabLayout!!.newTab().setText("메뉴"), SELECTED_MENU, true)
        mTabLayout!!.addTab(mTabLayout!!.newTab().setText("재고"), SELECTED_STOCK, false)
    }


    override fun onDestroy() {
        mPresenter!!.releaseView()
        mPresenter!!.releaseInteractor()
        mPresenter = null

        if (mJob != null) {

            mJob!!.cancel()
        }
        super.onDestroy()
    }

    private fun initView() {

        mRecyclerView = findViewById(R.id.recyclerViewMenu)
        mTabLayout = findViewById(R.id.tabLayoutMenu)
        mImageBtnAdd = findViewById(R.id.imageViewMenuAdd)
    }

    private fun initListener() {

        mImageBtnAdd!!.setOnClickListener {
            showAlertDialog()
        }

        mTabLayout!!.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab!!.position) {

                    SELECTED_MENU -> {

                        if (mSelectedTab != SELECTED_MENU) {

                            mSelectedTab = SELECTED_MENU
                            mRecyclerView!!.adapter = mMenuAdapter
                        }
                    }

                    SELECTED_STOCK -> {

                        if (mSelectedTab != SELECTED_STOCK) {

                            mSelectedTab = SELECTED_STOCK
                            mRecyclerView!!.adapter = mStockAdapter
                        }
                    }

                    else -> {

                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
    }

    override fun setMenuInfo(menuInfo: ArrayList<DataModel.MenuData>) {
        mMenuData = menuInfo
        mMenuAdapter!!.setData(mMenuData!!)
    }

    override fun setStock(stockData: ArrayList<DataModel.StockData>) {
        mStockData = stockData
        mStockAdapter!!.setData(mStockData!!)
    }

    override fun initMenu() {
        mPresenter!!.getMenuInfo(mShopId!!)
    }

    override fun initStock() {
        mPresenter!!.getStock(mShopId!!)
    }

    override fun getStock(): ArrayList<DataModel.StockData>? {
        return mStockData
    }

    override fun getIngredients(position: Int): String? {

        return mMenuData!![position].menuIngredients
    }

    override fun showProgress() {
        //TODO("Not yet implemented")
    }

    override fun hideProgress() {
        //TODO("Not yet implemented")
    }

    override fun showDialog(msg: String?) {

        val onClickListener: DialogInterface.OnClickListener = DialogInterface.OnClickListener { dialog, which ->
            if (mAlertDialog != null) {

                mAlertDialog = null
            }
        }

        if (mAlertDialog == null) {

            Utility.instance()!!.showDialog(mAlertDialog, msg, this, onClickListener)
        }
    }

    private fun initCoroutine() {

        mJob = CoroutineScope(Dispatchers.Default).launch {

            repeat(100_000) { i ->
                yield()
                if (mApp!!.getCategory() == "음식") {

                    mPresenter!!.getMenuInfo(mShopId!!)
                    mPresenter!!.getStock(mShopId!!)
                } else {

                    mPresenter!!.getMenuInfo(mShopId!!)
                    mPresenter!!.getStock(mShopId!!)
                }

                delay(mDelayTime)
            }
        }
    }

    private fun createAlert(position: Int) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("삭제 확인")
                .setMessage("삭제하시겠습니까?")
                .setCancelable(false)
                .setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which ->

                    when (mSelectedTab) {
                        SELECTED_MENU -> {

                            mPresenter!!.deleteMenu(mMenuData!![position].menuCode!!)
                        }

                        SELECTED_STOCK -> {

                            mPresenter!!.deleteStock(mShopId!!, mStockData!![position].stockCode!!, mStockData!![position].stockName!!)
                        }

                        else -> {

                        }
                    }

                    dialog.dismiss()
                })
                .setNegativeButton("취소", DialogInterface.OnClickListener { dialog, which ->
                    dialog.cancel()
                })
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    @SuppressLint("InflateParams")
    private fun createMenuInsertStockDialog() {

        if (mAlertDialog != null) {

            mAlertDialog!!.dismiss()
        }

        val builder: AlertDialog.Builder = AlertDialog.Builder(this)

        val view: View = LayoutInflater.from(this).inflate(R.layout.dialog_menu_insert_stock, null, false)

        builder.setView(view)

        val recyclerViewMenuInsertStock: RecyclerView = view.findViewById(R.id.recyclerViewMenuInsertStock)
        val btnSubmit: Button = view.findViewById(R.id.btnMenuInsertStockSubmit)
        mMenuInsertStockAdapter!!.setData(mStockData)

        recyclerViewMenuInsertStock.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerViewMenuInsertStock.adapter = mMenuInsertStockAdapter

        btnSubmit.setOnClickListener{
            mIngredientsQuery = mMenuInsertStockAdapter!!.getStockIngredients()
            mPresenter!!.insertMenu(mShopId!!, MenuModel(-1, mShopId, mNameQuery, mIntQuery, mIngredientsQuery))
            mAlertDialog!!.dismiss()
        }

        mAlertDialog = builder.create()
        mAlertDialog!!.show()
    }

    @SuppressLint("InflateParams")
    private fun showAlertDialog() {

        val builder: AlertDialog.Builder = AlertDialog.Builder(this)

        when(mSelectedTab) {
            SELECTED_MENU -> {

                val view: View = LayoutInflater.from(this).inflate(R.layout.dialog_menu_insert, null, false)

                builder.setView(view)

                val editTextNameMenu: EditText = view.findViewById(R.id.editTextMenuNameInsert)
                val editTextPriceMenu: EditText = view.findViewById(R.id.editTextMenuPriceInsert)
                val btnMenu: Button = view.findViewById(R.id.btnMenuAdd)
                val btnMenuInsertStock: Button = view.findViewById(R.id.btnMenuAddWithStock)

                btnMenu.setOnClickListener {

                    if (!mPresenter!!.isTextSet(editTextNameMenu.text.toString())) {

                        editTextNameMenu.error = "메뉴명을 입력해주세요"
                        editTextNameMenu.requestFocus()
                    } else if (!mPresenter!!.isTextSet(editTextPriceMenu.text.toString())) {

                        editTextPriceMenu.error = "가격을 입력해주세요"
                        editTextPriceMenu.requestFocus()
                    } else if (!mPresenter!!.isNumeric(editTextPriceMenu.text.toString())) {

                        editTextPriceMenu.error = "숫자만 입력해주세요"
                        editTextPriceMenu.requestFocus()
                    } else {

                        mIntQuery = editTextPriceMenu.text.toString().toInt()
                        mNameQuery = editTextNameMenu.text.toString()

                        mPresenter!!.insertMenu(
                            mShopId!!,
                            MenuModel(
                                -1,
                                mShopId!!,
                                editTextNameMenu.text.toString(),
                                editTextPriceMenu.text.toString().toInt(),
                                ""
                            )
                        )

                        mAlertDialog!!.dismiss()
                    }

                }

                btnMenuInsertStock.setOnClickListener {

                    if (!mPresenter!!.isTextSet(editTextNameMenu.text.toString())) {

                        editTextNameMenu.error = "메뉴명을 입력해주세요"
                        editTextNameMenu.requestFocus()
                    } else if (!mPresenter!!.isTextSet(editTextPriceMenu.text.toString())) {

                        editTextPriceMenu.error = "가격을 입력해주세요"
                        editTextPriceMenu.requestFocus()
                    } else if (!mPresenter!!.isNumeric(editTextPriceMenu.text.toString())) {

                        editTextPriceMenu.error = "숫자만 입력해주세요"
                        editTextPriceMenu.requestFocus()
                    } else {

                        mIntQuery = editTextPriceMenu.text.toString().toInt()
                        mNameQuery = editTextNameMenu.text.toString()

                        mAlertDialog!!.dismiss()

                        createMenuInsertStockDialog()
                    }
                }
            }

            SELECTED_STOCK -> {

                val view: View =
                    LayoutInflater.from(this).inflate(R.layout.dialog_stock_insert, null, false)

                builder.setView(view)

                val editTextNameStock: EditText = view.findViewById(R.id.editTextStockNameInsert)
                val editTextAmountStock: EditText =
                    view.findViewById(R.id.editTextStockAmountInsert)
                val btnStock: Button = view.findViewById(R.id.btnStockAdd)

                btnStock.setOnClickListener {

                    if (!mPresenter!!.isTextSet(editTextNameStock.text.toString())) {

                        editTextNameStock.error = "재고명을 입력해주세요"
                        editTextNameStock.requestFocus()
                    } else if (!mPresenter!!.isTextSet(editTextAmountStock.text.toString())) {

                        editTextAmountStock.error = "수량을 입력해주세요"
                        editTextAmountStock.requestFocus()
                    } else if (!mPresenter!!.isNumeric(editTextAmountStock.text.toString())) {

                        editTextAmountStock.error = "숫자만 입력해주세요"
                        editTextAmountStock.requestFocus()
                    } else {

                        mPresenter!!.insertStock(
                            StockModel(
                                -1,
                                mShopId,
                                editTextNameStock.text.toString(),
                                editTextAmountStock.text.toString().toInt()
                            )
                        )

                        mAlertDialog!!.dismiss()
                    }
                }
            }
            else -> {
                return
            }
        }

        mAlertDialog = builder.create()
        mAlertDialog!!.show()
    }
}