package com.fox.purchasinglist.domain

class DeletePurchaseItemUseCase(private val purchaseListRepository: PurchaseListRepository) {

    fun deletePurchase(purchaseItem: PurchaseItem) {
        purchaseListRepository.deletePurchaseItem(purchaseItem)
    }
}