package com.fox.purchasinglist.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import javax.inject.Inject

@Entity(tableName = "purchase_list")
data class PurchaseItemDbModel @Inject constructor(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val count: Int,
    val enabled: Boolean
    )
