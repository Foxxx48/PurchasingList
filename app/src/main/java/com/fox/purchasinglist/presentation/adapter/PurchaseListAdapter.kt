package com.fox.purchasinglist.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.fox.purchasinglist.R
import com.fox.purchasinglist.databinding.ItemPurchaseEnabledBinding
import com.fox.purchasinglist.domain.PurchaseItem
import javax.inject.Inject

class PurchaseListAdapter @Inject constructor() : ListAdapter<PurchaseItem, PurchaseItemViewHolder>(
    PurchaseItemDiffCallback
) {
    var onPurchaseItemLongClickListener: ((PurchaseItem)->Unit)? = null
    var onPurchaseItemClickListener: ((PurchaseItem)->Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PurchaseItemViewHolder {
        val layout = when (viewType) {
            VIEW_TYPE_DISABLED -> R.layout.item_purchase_disabled
            VIEW_TYPE_ENABLED -> R.layout.item_purchase_enabled
            else -> throw RuntimeException("Unknown view type: $viewType")
        }
        val binding = ItemPurchaseEnabledBinding.bind(LayoutInflater.from(parent.context).inflate(layout, parent, false))
        return PurchaseItemViewHolder(binding)

    }

//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PurchaseItemViewHolder {
//        val layout = when (viewType) {
//            VIEW_TYPE_DISABLED -> R.layout.item_purchase_disabled
//            VIEW_TYPE_ENABLED -> R.layout.item_purchase_enabled
//            else -> throw RuntimeException("Unknown view type: $viewType")
//        }
//        val view = LayoutInflater.from(parent.context).inflate(
//            layout,
//            parent,
//            false
//        )
//        return PurchaseItemViewHolder(view)
//    }

    override fun onBindViewHolder(viewHolder: PurchaseItemViewHolder, position: Int) {
        val purchaseItem = getItem(position)

        viewHolder.binding.root.setOnLongClickListener {
            onPurchaseItemLongClickListener?.invoke(purchaseItem)
            true
        }

        viewHolder.binding.root.setOnClickListener {
            onPurchaseItemClickListener?.invoke(purchaseItem)
        }

        viewHolder.binding.tvName.text = purchaseItem.name
        viewHolder.binding.tvCount.text = purchaseItem.count.toString()
//        viewHolder.tvName.text = purchaseItem.name
//        viewHolder.tvCount.text = purchaseItem.count.toString()
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return if (item.enabled) {
            VIEW_TYPE_ENABLED
        } else {
            VIEW_TYPE_DISABLED
        }
    }

    companion object {

        const val VIEW_TYPE_ENABLED = 100
        const val VIEW_TYPE_DISABLED = 101

        const val MAX_POOL_SIZE = 30
    }
}