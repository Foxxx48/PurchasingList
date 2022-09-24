package com.fox.purchasinglist.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "purchase_list")
data class PurchaseItemDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val count: Int,
    val enabled: Boolean
    )
