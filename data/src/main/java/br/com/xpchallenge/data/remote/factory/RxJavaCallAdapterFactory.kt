package br.com.xpchallenge.data.remote.factory

import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import java.lang.reflect.Type

class RxJavaCallAdapterFactory : CallAdapter.Factory() {

    private val original: RxJava3CallAdapterFactory = RxJava3CallAdapterFactory.create()

    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        return RxCallAdapterWrapper(
            original[returnType, annotations, retrofit] as CallAdapter<Any, Any>
        )
    }
}
