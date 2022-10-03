package com.fox.purchasinglist.data.mapper

import com.fox.purchasinglist.data.database.PurchaseItemDbModel
import com.fox.purchasinglist.domain.PurchaseItem
import javax.inject.Inject

class PurchaseListMapper @Inject constructor() {

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