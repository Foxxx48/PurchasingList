package com.fox.purchasinglist.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.fox.purchasinglist.domain.PurchaseItem

object PurchaseItemDiffCallback : DiffUtil.ItemCallback<PurchaseItem>() {
    override fun areItemsTheSame(oldItem: PurchaseItem, newItem: PurchaseItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PurchaseItem, newItem: PurchaseItem): Boolean {
        return oldItem == newItem
    }
}