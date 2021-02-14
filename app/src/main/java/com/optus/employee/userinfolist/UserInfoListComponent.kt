package com.optus.employee.userinfolist

import com.optus.employee.di.ActivityScope
import com.optus.employee.di.ViewModelModule
import dagger.Subcomponent

@Subcomponent(modules = [ViewModelModule::class])
@ActivityScope
interface UserInfoListComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): UserInfoListComponent
    }

    fun inject(activity: UserInfoListActivity)

}