package com.fox.purchasinglist.domain

class AddPurchaseItemUseCase(private val purchaseListRepository: PurchaseListRepository) {

    suspend fun addPurchase(purchaseItem: PurchaseItem) {
        purchaseListRepository.addPurchaseItem(purchaseItem)
    }
}