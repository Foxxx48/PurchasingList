package com.fox.purchasinglist.domain

class GetPurchaseItemUseCase(private val purchaseListRepository: PurchaseListRepository) {

    suspend fun getPurchase(purchaseItemId: Int): PurchaseItem {
        return purchaseListRepository.getPurchaseItem(purchaseItemId)
    }
}