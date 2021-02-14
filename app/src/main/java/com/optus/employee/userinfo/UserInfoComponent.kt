package com.optus.employee.userinfo

import com.optus.employee.di.ActivityScope
import com.optus.employee.di.ViewModelModule
import dagger.Subcomponent

@Subcomponent(modules = [ViewModelModule::class])
@ActivityScope
interface UserInfoComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): UserInfoComponent
    }

    fun inject(activity: UserInfoActivity)

}