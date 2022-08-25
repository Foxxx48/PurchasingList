package com.fox.purchasinglist.domain

class EditPurchaseItemUseCase(private val purchaseListRepository: PurchaseListRepository) {

    fun editPurchase(purchaseItem: PurchaseItem) {
        purchaseListRepository.editPurchaseItem(purchaseItem)
    }
}