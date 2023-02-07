package com.fox.purchasinglist.domain

import javax.inject.Inject

data class PurchaseItem @Inject constructor (
    val name: String,
    val count: Int,
    val enabled: Boolean,

    var id: Int = UNDEFINED_ID
) {
    companion object {
        const val UNDEFINED_ID = 0
    }
}