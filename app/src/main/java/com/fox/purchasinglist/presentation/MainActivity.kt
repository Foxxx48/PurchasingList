package com.fox.purchasinglist.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.fox.purchasinglist.R
import com.fox.purchasinglist.domain.PurchaseItem

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var purchaseListAdapter: PurchaseListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRecyclerView()

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.purchaseList.observe(this) {
            purchaseListAdapter.purchaseList = it
        }
    }

    private fun setupRecyclerView() {
        val rvShopList = findViewById<RecyclerView>(R.id.rv_shop_list)
        with(rvShopList) {
            purchaseListAdapter = PurchaseListAdapter()
            adapter = purchaseListAdapter
            recycledViewPool.setMaxRecycledViews(
                PurchaseListAdapter.VIEW_TYPE_ENABLED,
                PurchaseListAdapter.MAX_POOL_SIZE
            )
            recycledViewPool.setMaxRecycledViews(
                PurchaseListAdapter.VIEW_TYPE_DISABLED,
                PurchaseListAdapter.MAX_POOL_SIZE
            )
        }
        purchaseListAdapter.onPurchaseItemLongClickListener = object : PurchaseListAdapter.OnPurchaseItemLongClickListener {
            override fun onPurchaseItemLongClick(purchaseItem: PurchaseItem) {
                viewModel.changeEnableState(purchaseItem)
            }

        }
    }

}