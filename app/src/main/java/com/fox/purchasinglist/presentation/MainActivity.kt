package com.fox.purchasinglist.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.fox.purchasinglist.R

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
        val rvPurchaseList = findViewById<RecyclerView>(R.id.rv_shop_list)
        with(rvPurchaseList) {
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
        setupLongClickListener()
        setupClickListener()
        setupSwipeListener(rvPurchaseList)


    }

    private fun setupSwipeListener(rvPurchaseList: RecyclerView) {
        val callback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = purchaseListAdapter.purchaseList[viewHolder.adapterPosition]
                viewModel.deletePurchaseItem(item)
            }
        }

        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rvPurchaseList)
    }

    private fun setupClickListener() {
        purchaseListAdapter.onPurchaseItemClickListener = {
            viewModel.printLog(it)
            Log.d("MainActivity", "$it")
        }
    }

    private fun setupLongClickListener() {
        purchaseListAdapter.onPurchaseItemLongClickListener = {
            viewModel.changeEnableState(it)
        }
    }

}