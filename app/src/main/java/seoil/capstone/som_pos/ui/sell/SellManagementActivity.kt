package seoil.capstone.som_pos.ui.sell

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import seoil.capstone.som_pos.GlobalApplication
import seoil.capstone.som_pos.R
import seoil.capstone.som_pos.data.model.DataModel
import seoil.capstone.som_pos.data.network.model.PaymentModel
import seoil.capstone.som_pos.util.Utility

class SellManagementActivity:AppCompatActivity(), SellManagementContract.View{

    private var mPresenter: SellManagementPresenter? = null
    private var mAlertDialog: AlertDialog? = null
    private var mRecyclerView: RecyclerView? = null
    private var mTextViewTotalPrice: TextView? = null
    private var mImageViewPurchase: ImageView? = null
    private var mSellAdapter: SellManagementSellAdapter? = null
    private var mMenuData: ArrayList<DataModel.MenuData>? = null
    private var mStockData: ArrayList<DataModel.StockData>? = null
    private var mShopId: String? = null
    private var mApp: GlobalApplication? = null
    private var mTotalPrice: Int = 0
    private var mUserAvailablePoint: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sell_management)

        initView()
        initListener()

        mPresenter = SellManagementPresenter()
        mPresenter!!.setView(this)
        mPresenter!!.createInteractor()

        mApp = applicationContext as GlobalApplication

        mShopId = mApp!!.getUserId()

        mRecyclerView!!.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mSellAdapter = SellManagementSellAdapter(mMenuData, mPresenter!!)
        mRecyclerView!!.adapter = mSellAdapter

        mMenuData = ArrayList()
        mStockData = ArrayList()

        mPresenter!!.getMenuInfo(mShopId!!)
        mPresenter!!.getStock(mShopId!!)
    }

    override fun onDestroy() {

        mPresenter!!.releaseInteractor()
        mPresenter!!.releaseView()
        mPresenter = null
        super.onDestroy()
    }

    override fun setMenuInfo(menuList: ArrayList<DataModel.MenuData>) {
        mMenuData = menuList
        mSellAdapter!!.setData(mMenuData)
    }

    override fun initTotalPrice(countList: ArrayList<Int>) {

        mTotalPrice = 0

        for (i in countList.indices) {

            if (countList[i] > 0) {

                mTotalPrice += (mMenuData!![i].menuPrice!! * countList[i])
            }
        }

        val temp: String = mTotalPrice.toString() + "원"
        mTextViewTotalPrice!!.text = temp
    }

    override fun setStock(stockList: ArrayList<DataModel.StockData>) {
        mStockData = stockList

        if (mMenuData == null) {

            return
        }

        val menuMaxCount: ArrayList<DataModel.MenuMaxCount> = ArrayList()

        for (i in mMenuData!!.indices) {

            menuMaxCount.add(DataModel.MenuMaxCount(mMenuData!![i].menuCode, mPresenter!!.getMenuMaxCount(mMenuData!![i].menuIngredients!!, mStockData)))
        }

        mSellAdapter!!.setMenuMaxData(menuMaxCount)
    }

    override fun finishActivity(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun setCurrentPoint(point: Int) {
        mUserAvailablePoint = point

        if (mAlertDialog != null) {

            mAlertDialog!!.dismiss()
        }

        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        val view = LayoutInflater.from(this).inflate(R.layout.dialog_sell_use_point, null, false)

        val textViewPoint = view.findViewById<TextView>(R.id.textViewCurrentPoint)
        val editTextPoint = view.findViewById<EditText>(R.id.editTextGetPoint)

        textViewPoint.text = mUserAvailablePoint.toString()

        val btnUsePointSubmit = view.findViewById<Button>(R.id.btnUsePointSubmit)
        btnUsePointSubmit.setOnClickListener {

            if (!mPresenter!!.isTextSet(editTextPoint.text.toString())) {

                editTextPoint.error = "포인트를 입력해주세요"
            } else if (!mPresenter!!.isNumeric(editTextPoint.text.toString())) {

                editTextPoint.error = "숫자만 입력해주세요"
            } else if (!mPresenter!!.checkPoint(editTextPoint.text.toString().toInt(), mUserAvailablePoint)) {

                editTextPoint.error = "최대 사용 포인트 값 이하로 입력해주세요"
            } else {

                val inputPoint = editTextPoint.text.toString().toInt()

                mPresenter!!.pay(
                        PaymentModel(
                                -1,
                                "",
                                "",
                                mShopId,
                                mPresenter!!.getTotalIngredients(mMenuData, mSellAdapter!!.getCountData()),
                                inputPoint * -1,
                                mTotalPrice - inputPoint
                        )
                )
                mAlertDialog!!.dismiss()
            }
        }

        builder.setView(view)
        mAlertDialog = builder.create()
        mAlertDialog!!.show()
    }


    override fun showProgress() {

    }

    override fun hideProgress() {

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

    private fun initView() {

        mRecyclerView = findViewById(R.id.recyclerViewSellMenuList)
        mTextViewTotalPrice = findViewById(R.id.textViewSellTotalPrice)
        mImageViewPurchase = findViewById(R.id.imageViewSellMenuPurchase)
    }

    private fun initListener() {

        mImageViewPurchase!!.setOnClickListener {

            if (mTotalPrice == 0) {

                showDialog("메뉴를 선택해주세요")
            } else {

                if (mAlertDialog != null) {

                    mAlertDialog!!.dismiss()
                }

                val builder = AlertDialog.Builder(this)
                val view = LayoutInflater.from(this).inflate(R.layout.dialog_get_user_id, null, false)
                builder.setView(view)
                val editTextId = view.findViewById<EditText>(R.id.editTextGetId)
                val btnCancel: Button = view.findViewById(R.id.btnGetUserIdCancel)

                btnCancel.setOnClickListener {

                    mPresenter!!.pay(
                            PaymentModel(
                                    -1,
                                    "",
                                    "",
                                    mShopId,
                                    mPresenter!!.getTotalIngredients(mMenuData, mSellAdapter!!.getCountData()),
                                    0,
                                    mTotalPrice
                            )
                    )
                    mAlertDialog!!.dismiss()
                }

                val btnUsePoint: Button = view.findViewById(R.id.btnGetUserIdUsePoint)

                btnUsePoint.setOnClickListener {

                    if (!mPresenter!!.isTextSet(editTextId.text.toString())) {

                        editTextId.error = "아이디를 입력해주세요"
                        editTextId.requestFocus()
                    } else {

                        mPresenter!!.getCurrentPoint(editTextId.text.toString())
                    }
                }

                val btnSubmit: Button = view.findViewById(R.id.btnGetUserIdSubmit)

                btnSubmit.setOnClickListener {

                    mPresenter!!.pay(
                            PaymentModel(
                                    -1,
                                    "",
                                    editTextId.text.toString(),
                                    mShopId,
                                    mPresenter!!.getTotalIngredients(mMenuData, mSellAdapter!!.getCountData()),
                                    (mTotalPrice * 0.3).toInt(),
                                    mTotalPrice
                            )
                    )

                    mAlertDialog!!.dismiss()
                }

                mAlertDialog = builder.create()
                mAlertDialog!!.show()
            }
        }
    }
}