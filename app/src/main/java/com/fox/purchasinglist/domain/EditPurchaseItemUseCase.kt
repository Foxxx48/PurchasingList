package com.fox.purchasinglist.domain

import javax.inject.Inject

class EditPurchaseItemUseCase @Inject constructor(private val purchaseListRepository: PurchaseListRepository) {

    suspend fun editPurchase(purchaseItem: PurchaseItem) {
        purchaseListRepository.editPurchaseItem(purchaseItem)
    }
}