package com.fox.purchasinglist.domain

import javax.inject.Inject

class DeletePurchaseItemUseCase @Inject constructor(private val purchaseListRepository: PurchaseListRepository) {
    suspend operator fun invoke(
        purchaseItem: PurchaseItem
    ) = purchaseListRepository.deletePurchaseItem(purchaseItem)
}