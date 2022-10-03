package com.fox.purchasinglist.domain

import javax.inject.Inject

class DeletePurchaseItemUseCase @Inject constructor(private val purchaseListRepository: PurchaseListRepository) {

    suspend fun deletePurchase(purchaseItem: PurchaseItem) {
        purchaseListRepository.deletePurchaseItem(purchaseItem)
    }
}