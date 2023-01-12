package com.fox.purchasinglist.data.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.util.Log
import com.fox.purchasinglist.PurchaseItemApp
import com.fox.purchasinglist.data.database.PurchaseListDao
import com.fox.purchasinglist.data.mapper.PurchaseListMapper
import com.fox.purchasinglist.domain.PurchaseItem
import javax.inject.Inject

class PurchaseListProvider : ContentProvider() {

    private val myComponent by lazy {
        (context as PurchaseItemApp).myComponent
    }



    @Inject
    lateinit var purchaseListDao: PurchaseListDao

    @Inject
    lateinit var mapper: PurchaseListMapper

    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
        addURI("com.fox.purchasinglist", "purchase_list", GET_PURCHASE_ITEM_QUERY)
    }

    override fun onCreate(): Boolean {
        myComponent.inject(this)
        return true
    }

    override fun query(
        p0: Uri,
        p1: Array<out String>?,
        p2: String?,
        p3: Array<out String>?,
        p4: String?
    ): Cursor? {
        return when (uriMatcher.match(p0)) {
            GET_PURCHASE_ITEM_QUERY -> {
                purchaseListDao.getPurchaseListCursor()
            }
            else -> {
                null
            }
        }
//        Log.d("PurchaseListProvider", "PurchaseListProvider fun query() uri: $p0 code: $code ")

    }

    override fun getType(p0: Uri): String? {
        TODO("Not yet implemented")
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        when (uriMatcher.match(uri)) {
            GET_PURCHASE_ITEM_QUERY -> {
                if (values == null) return null
                val id = values.getAsInteger("id")
                val name = values.getAsString("name")
                val count = values.getAsInteger("count")
                val enabled = values.getAsBoolean("enabled")

                val purchaseItem = PurchaseItem(
                    id = id,
                    name = name,
                    count = count,
                    enabled = enabled
                )
                purchaseListDao.addPurchaseItemSync(mapper.mapEntityToDbModel(purchaseItem))
            }
        }
        return null
    }

    override fun delete(uri: Uri, p1: String?, p2: Array<out String>?): Int {
        when (uriMatcher.match(uri)) {
            GET_PURCHASE_ITEM_QUERY -> {
                val id = p2?.get(0)?.toInt() ?: -1
                return purchaseListDao.deletePurchaseItemSync(id)

            }
        }
        return 0
    }

    override fun update(uri: Uri, values: ContentValues?, p2: String?, p3: Array<out String>?): Int {
        when (uriMatcher.match(uri)) {
            GET_PURCHASE_ITEM_QUERY -> {
                if (values == null) return 0
                val id = values.getAsInteger("id")
                val name = values.getAsString("name")
                val count = values.getAsInteger("count")
                val enabled = values.getAsBoolean("enabled")

                Log.d("MainActivity", "id $id name $name count $count")

                val purchaseItem = PurchaseItem(
                    id = id,
                    name = name,
                    count = count,
                    enabled = enabled
                )
                Log.d("MainActivity", "New id ${purchaseItem.id}" +
                        " new name ${purchaseItem.name} " +
                        "new count ${purchaseItem.count}")
                purchaseListDao.addPurchaseItemSync(mapper.mapEntityToDbModel(purchaseItem))
            }
        }
        return 0
    }

    companion object {
        private const val GET_PURCHASE_ITEM_QUERY = 101
    }
}