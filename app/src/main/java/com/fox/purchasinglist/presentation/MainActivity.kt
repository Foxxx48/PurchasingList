package com.fox.purchasinglist.presentation

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.fox.purchasinglist.PurchaseItemApp
import com.fox.purchasinglist.R
import com.fox.purchasinglist.databinding.ActivityMainBinding
import com.fox.purchasinglist.domain.PurchaseItem
import com.fox.purchasinglist.presentation.adapter.PurchaseListAdapter
import javax.inject.Inject
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity(), PurchaseItemFragment.OnEditingFinishListener {

    private var _binding: ActivityMainBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("ActivityMainBinding is null")

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {

        ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
    }

    private lateinit var purchaseListAdapter: PurchaseListAdapter
    private var purchaseItemContainer: FragmentContainerView? = null

    private val myComponent by lazy {
        (application as PurchaseItemApp).myComponent

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        myComponent.inject(this)
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        purchaseItemContainer = binding.purchaseItemContainer

        setupRecyclerView()

        viewModel.purchaseList.observe(this) {
            purchaseListAdapter.submitList(it)
        }
        binding.buttonAddPurchaseItem.setOnClickListener {
            if (isOnePaneMode()) {
                val intent = PurchaseItemActivity.newIntentAddItem(this)
                startActivity(intent)
            } else {
                launchFragment(PurchaseItemFragment.newInstanceAddItem())
            }

        }

        thread {
            val cursor = contentResolver.query(
                Uri.parse("content://com.fox.purchasinglist/purchase_list"),
                null,
                null,
                null,
                null
            )
            while (cursor?.moveToNext() == true) {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                val name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
                val count = cursor.getInt(cursor.getColumnIndexOrThrow("count"))
                val enabled = cursor.getInt(cursor.getColumnIndexOrThrow("enabled")) > 0

                val purchaseItem = PurchaseItem(
                    id = id,
                    name = name,
                    count = count,
                    enabled = enabled
                )
                Log.d ("MainActivity", purchaseItem.toString())
            }
            cursor?.close()
        }
    }

    override fun onEditingFinished() {
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
        supportFragmentManager.popBackStack()
    }

    private fun isOnePaneMode(): Boolean {
        return purchaseItemContainer == null
    }

    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.purchase_item_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun setupRecyclerView() {
        val rvPurchaseList = binding.rvPurchaseList
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
//                viewModel.deletePurchaseItem(item)
                thread {
                    contentResolver.delete(
                        Uri.parse("content://com.fox.purchasinglist/purchase_list"),
                        null,
                        arrayOf(item.id.toString())
                    )
                }
            }
        }

        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rvPurchaseList)
    }

    private fun setupClickListener() {
        purchaseListAdapter.onPurchaseItemClickListener = {
            if (isOnePaneMode()) {
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}