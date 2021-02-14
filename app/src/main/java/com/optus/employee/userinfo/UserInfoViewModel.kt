package com.optus.employee.userinfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.optus.employee.base.ReactiveViewModel
import com.optus.employee.model.UserDetail
import com.optus.employee.service.UserInfoService
import com.optus.employee.utils.Resource
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class UserInfoViewModel @Inject constructor(private val userInfoService: UserInfoService):
    ReactiveViewModel() {

    private val user = MutableLiveData<Resource<List<UserDetail>>>()

    fun fetchUserDetail(userId: Int){
        user.postValue(Resource.loading(null))
        userInfoService.getUserDetail()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { it.filter { userDetail -> userDetail.albumId == userId } }
            .subscribe(object : DisposableObserver<List<UserDetail>>(){
                override fun onComplete() {
                    Timber.i("User detail complete block executed")
                }

                override fun onNext(userDetailList: List<UserDetail>) {
                    Timber.i("User detail success response received")

                    user.postValue(Resource.success(userDetailList))
                }

                override fun onError(e: Throwable?) {
                    Timber.e("User detail error response received $e")
                    user.postValue(Resource.error("${e?.localizedMessage}", null))
                }

            })
    }

    fun getUser(): LiveData<Resource<List<UserDetail>>> {
        return user;
    }
}