package com.fox.purchasinglist.domain

class EditPurchaseItemUseCase(private val purchaseListRepository: PurchaseListRepository) {

    suspend fun editPurchase(purchaseItem: PurchaseItem) {
        purchaseListRepository.editPurchaseItem(purchaseItem)
    }
}