package com.fox.purchasinglist.domain

import javax.inject.Inject

class GetPurchaseItemUseCase @Inject constructor(private val purchaseListRepository: PurchaseListRepository) {
    suspend operator fun invoke(
        purchaseItemId: Int
    ) = purchaseListRepository.getPurchaseItem(purchaseItemId)
}