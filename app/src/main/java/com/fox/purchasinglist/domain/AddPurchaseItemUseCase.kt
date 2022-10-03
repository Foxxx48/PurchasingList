package com.fox.purchasinglist.domain

import javax.inject.Inject

class AddPurchaseItemUseCase @Inject constructor(private val purchaseListRepository: PurchaseListRepository) {

    suspend fun addPurchase(purchaseItem: PurchaseItem) {
        purchaseListRepository.addPurchaseItem(purchaseItem)
    }
}