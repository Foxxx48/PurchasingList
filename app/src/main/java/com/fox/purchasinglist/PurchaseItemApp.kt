package com.fox.purchasinglist

import android.app.Application
import com.fox.purchasinglist.di.DaggerAppComponent

class PurchaseItemApp : Application() {

    val myComponent by lazy { DaggerAppComponent.factory()
        .create(this)

    }
}