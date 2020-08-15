package com.example.applicationtesting_wewillapp.presenter

import android.util.Log
import com.example.applicationtesting_wewillapp.model.ResponseData
import com.example.applicationtesting_wewillapp.retrofit.Retrotit
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class PresenterData {
    var mDisposable: Disposable? = null

    fun OpenDataPresenterRX(
        lat :Double,
        lon :Double,
        exclude :String,
        units :String,
        IDapp :String,
        dataResponse:(ResponseData)->Unit,
        MessageError:(String)->Unit
    ){
        mDisposable = Retrotit.myAppService.openweathermap(lat,lon,exclude,units,IDapp)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<ResponseData>(){
                override fun onComplete() {}
                override fun onNext(responseLogin: ResponseData) {
                    Log.d("Data",responseLogin.toString())
                    dataResponse.invoke(responseLogin)
                }
                override fun onError(e: Throwable) {
                    MessageError.invoke(e.message!!)
                    Log.d("Data",e.message.toString())
                }

            })
    }
}