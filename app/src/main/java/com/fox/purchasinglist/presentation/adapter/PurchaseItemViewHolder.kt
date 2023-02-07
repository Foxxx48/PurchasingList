package com.fox.purchasinglist.presentation.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fox.purchasinglist.R
import com.fox.purchasinglist.databinding.ActivityMainBinding
import com.fox.purchasinglist.databinding.ActivityPurchaseItemBinding
import com.fox.purchasinglist.databinding.ItemPurchaseDisabledBinding
import com.fox.purchasinglist.databinding.ItemPurchaseEnabledBinding
import javax.inject.Inject

class PurchaseItemViewHolder @Inject constructor(
    val binding: ItemPurchaseEnabledBinding

) : RecyclerView.ViewHolder(binding.root)