package com.fox.purchasinglist.data

import com.fox.purchasinglist.domain.PurchaseItem

class PurchaseListMapper {

    fun mapEntityToDbModel(purchaseItem: PurchaseItem): PurchaseItemDbModel {
        return PurchaseItemDbModel(
            id = purchaseItem.id,
            name = purchaseItem.name,
            count = purchaseItem.count,
            enabled = purchaseItem.enabled
        )
    }

    fun mapDbModelToEntity(purchaseItemDbModel: PurchaseItemDbModel): PurchaseItem {
        return PurchaseItem(
            id = purchaseItemDbModel.id,
            name = purchaseItemDbModel.name,
            count = purchaseItemDbModel.count,
            enabled = purchaseItemDbModel.enabled
        )
    }

    fun mapListDbModelToListEntity(list: List<PurchaseItemDbModel>): List<PurchaseItem> {
        return list.map {
            mapDbModelToEntity(it)
        }
    }
}