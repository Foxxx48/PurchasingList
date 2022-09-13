package com.fox.purchasinglist.presentation

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainer
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.fox.purchasinglist.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), PurchaseItemFragment.OnEditingFinishListener {

    private lateinit var viewModel: MainViewModel
    private lateinit var purchaseListAdapter: PurchaseListAdapter
    private var purchaseItemContainer: FragmentContainerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        purchaseItemContainer = findViewById(R.id.purchase_item_container)

        setupRecyclerView()

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.purchaseList.observe(this) {
            purchaseListAdapter.submitList(it)
        }
        val addButton = findViewById<FloatingActionButton>(R.id.button_add_shop_item)
        addButton.setOnClickListener {
            if(isOnePaneMode()) {
                val intent = PurchaseItemActivity.newIntentAddItem(this)
                startActivity(intent)
            } else {
                launchFragment(PurchaseItemFragment.newInstanceAddItem())
            }

        }
    }

    override fun onEditingFinished() {
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
        supportFragmentManager.popBackStack()
    }

    private fun isOnePaneMode(): Boolean {
        return purchaseItemContainer == null
    }

    private fun launchFragment(fragment: Fragment){
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.purchase_item_container, fragment)
            .addToBackStack(null)
            .commit()
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
                val item = purchaseListAdapter.currentList[viewHolder.adapterPosition]
                viewModel.deletePurchaseItem(item)
            }
        }

        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rvPurchaseList)
    }

    private fun setupClickListener() {
        purchaseListAdapter.onPurchaseItemClickListener = {
            if(isOnePaneMode()){
                val intent = PurchaseItemActivity.newIntentEditItem(this, it.id)
                Log.d("MainActivity", "${it.id}")
                startActivity(intent)
            } else {
                launchFragment(PurchaseItemFragment.newInstanceEditItem(it.id))
            }

        }
    }

    private fun setupLongClickListener() {
        purchaseListAdapter.onPurchaseItemLongClickListener = {
            viewModel.changeEnableState(it)
        }
    }

}