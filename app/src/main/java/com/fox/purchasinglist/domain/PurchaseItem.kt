package com.fox.purchasinglist.domain

data class PurchaseItem (
    val id: Int,
    val name: String,
    val count: Int,
    val enabled: Boolean
)