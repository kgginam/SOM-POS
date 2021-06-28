package seoil.capstone.som_pos.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import seoil.capstone.som_pos.R
import seoil.capstone.som_pos.ui.menu.MenuManagementActivity
import seoil.capstone.som_pos.ui.sell.SellManagementActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var mBtnMenu : Button?= null
    private var mBtnSell : Button?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()

        initListener()

    }

    private fun initView() {

        mBtnMenu = findViewById(R.id.btnMainMenuManagement)
        mBtnSell = findViewById(R.id.btnMainSellManagement)
    }

    private fun initListener() {


        mBtnMenu!!.setOnClickListener(this)
        mBtnSell!!.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val id = v!!.id
        var intent : Intent?= null

        if (id == R.id.btnMainMenuManagement) {

            intent = Intent(this, MenuManagementActivity::class.java)
        }

        if (id == R.id.btnMainSellManagement) {

            intent = Intent(this, SellManagementActivity::class.java)
        }

        if (intent != null) {

            startActivity(intent)
        }
    }
}