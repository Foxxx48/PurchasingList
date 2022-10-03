package com.fox.purchasinglist.domain

import javax.inject.Inject

class GetPurchaseItemUseCase @Inject constructor(private val purchaseListRepository: PurchaseListRepository) {

    suspend fun getPurchase(purchaseItemId: Int): PurchaseItem {
        return purchaseListRepository.getPurchaseItem(purchaseItemId)
    }
}