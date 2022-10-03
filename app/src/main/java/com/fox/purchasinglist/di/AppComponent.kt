package com.fox.purchasinglist.di

import android.app.Application
import com.fox.purchasinglist.presentation.MainActivity
import com.fox.purchasinglist.presentation.PurchaseItemFragment
import dagger.BindsInstance
import dagger.Component

@AppScope
@Component(modules = [DataModule::class, ViewModelModule::class])
interface AppComponent {

    fun inject(activity: MainActivity)

//    fun inject(activity: PurchaseItemActivity)

    fun inject(fragment: PurchaseItemFragment)

    @Component.Factory
    interface AppComponentFactory {
        fun create(@BindsInstance application: Application
        ): AppComponent
    }
}