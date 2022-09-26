package com.fox.purchasinglist.domain

class DeletePurchaseItemUseCase(private val purchaseListRepository: PurchaseListRepository) {

    suspend fun deletePurchase(purchaseItem: PurchaseItem) {
        purchaseListRepository.deletePurchaseItem(purchaseItem)
    }
}