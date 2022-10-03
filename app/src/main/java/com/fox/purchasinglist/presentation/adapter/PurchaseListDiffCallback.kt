package com.fox.purchasinglist.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.fox.purchasinglist.domain.PurchaseItem

class PurchaseListDiffCallback(
    private val oldList: List<PurchaseItem>,
    private val newList: List<PurchaseItem>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return newItem.id == oldItem.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
       return oldItem == newItem
    }

}