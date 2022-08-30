package com.fox.purchasinglist.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.fox.purchasinglist.R
import com.fox.purchasinglist.domain.PurchaseItem

class PurchaseListAdapter : RecyclerView.Adapter<PurchaseListAdapter.PurchaseItemViewHolder>() {

    var purchaseList = listOf<PurchaseItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PurchaseItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_purchase_disabled,
            parent,
            false
        )
        return PurchaseItemViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: PurchaseItemViewHolder, position: Int) {
        val purchaseItem = purchaseList[position]
        val status = if (purchaseItem.enabled) {
            "Active"
        } else {
            "Not active"
        }
        viewHolder.view.setOnLongClickListener {
            true
        }
        if (purchaseItem.enabled) {
            viewHolder.tvName.text = "${purchaseItem.name} $status"
            viewHolder.tvCount.text = purchaseItem.count.toString()
            viewHolder.tvName.setTextColor(ContextCompat.getColor(viewHolder.view.context, android.R.color.holo_red_light))
        }
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
}