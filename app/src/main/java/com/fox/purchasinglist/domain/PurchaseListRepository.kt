package com.fox.purchasinglist.domain

interface PurchaseListRepository {

     fun addPurchaseItem(purchaseItem: PurchaseItem)

     fun deletePurchaseItem(purchaseItem: PurchaseItem)

     fun editPurchaseItem(purchaseItem: PurchaseItem)

     fun getPurchaseItem(purchaseItemId: Int): PurchaseItem

     fun getListPurchaseItem(): List<PurchaseItem>
}