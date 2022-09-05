package com.fox.purchasinglist.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.fox.purchasinglist.R
import com.fox.purchasinglist.domain.PurchaseItem

class PurchaseListAdapter : RecyclerView.Adapter<PurchaseListAdapter.PurchaseItemViewHolder>() {
    var count = 0

    var purchaseList = listOf<PurchaseItem>()

        set(value) {
            val callback = PurchaseListDiffCallback(purchaseList, value)
            val diffResult = DiffUtil.calculateDiff(callback)
            diffResult.dispatchUpdatesTo(this)
            field = value

        }

     var onPurchaseItemLongClickListener: ((PurchaseItem)->Unit)? = null
     var onPurchaseItemClickListener: ((PurchaseItem)->Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PurchaseItemViewHolder {
        Log.d("ShopListAdapter", "onCreateViewHolder, count: ${++count}")
        val layout = when (viewType) {
            VIEW_TYPE_DISABLED -> R.layout.item_purchase_disabled
            VIEW_TYPE_ENABLED -> R.layout.item_purchase_enabled
            else -> throw RuntimeException("Unknown view type: $viewType")
        }
        val view = LayoutInflater.from(parent.context).inflate(
            layout,
            parent,
            false
        )
        return PurchaseItemViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: PurchaseItemViewHolder, position: Int) {
        Log.d("ShopListAdapter", "onBindViewHolder, count: ${++count}")
        val purchaseItem = purchaseList[position]

        viewHolder.view.setOnLongClickListener {
            onPurchaseItemLongClickListener?.invoke(purchaseItem)
            true
        }

        viewHolder.view.setOnClickListener {
            onPurchaseItemClickListener?.invoke(purchaseItem)
        }
        viewHolder.tvName.text = purchaseItem.name
        viewHolder.tvCount.text = purchaseItem.count.toString()
    }

    override fun onViewRecycled(viewHolder: PurchaseItemViewHolder) {
        super.onViewRecycled(viewHolder)
        viewHolder.tvName.text = ""
        viewHolder.tvCount.text = ""
        viewHolder.tvName.setTextColor(ContextCompat.getColor(viewHolder.view.context, android.R.color.white))
    }

    override fun getItemCount(): Int {
        return purchaseList.size
    }

    class PurchaseItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val tvName = view.findViewById<TextView>(R.id.tv_name)
        val tvCount = view.findViewById<TextView>(R.id.tv_count)
    }

    override fun getItemViewType(position: Int): Int {
        val item = purchaseList[position]
        return if (item.enabled) {
            VIEW_TYPE_ENABLED
        } else {
            VIEW_TYPE_DISABLED
        }
    }

    interface OnPurchaseItemLongClickListener {
        fun onPurchaseItemLongClick(purchaseItem: PurchaseItem)
    }

    companion object {

        const val VIEW_TYPE_ENABLED = 100
        const val VIEW_TYPE_DISABLED = 101

        const val MAX_POOL_SIZE = 30
    }
}