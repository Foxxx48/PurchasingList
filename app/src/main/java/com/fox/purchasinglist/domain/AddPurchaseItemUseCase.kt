package com.fox.purchasinglist.domain

class AddPurchaseItemUseCase(private val purchaseListRepository: PurchaseListRepository) {

    fun addPurchase(purchaseItem: PurchaseItem) {
        purchaseListRepository.addPurchaseItem(purchaseItem)
    }
}