package br.com.xpchallenge.data.remote.factory

import br.com.xpchallenge.domain.errors.NetworkError
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.Call
import retrofit2.CallAdapter
import java.io.IOException
import java.lang.reflect.Type
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class RxCallAdapterWrapper<R : Any>(private val wrapped: CallAdapter<R, *>) : CallAdapter<R, Any> {

    override fun responseType(): Type {
        return wrapped.responseType()
    }

    override fun adapt(call: Call<R>): Any {
        return when (val result = wrapped.adapt(call)) {

            is Observable<*> -> {
                result.onErrorResumeNext {
                    Observable.error(transformIfNetworkError(it))
                }
            }

            is Completable -> {
                result.onErrorResumeNext {
                    Completable.error(transformIfNetworkError(it))
                }
            }

            is Single<*> -> {
                result.onErrorResumeNext {
                    Single.error(transformIfNetworkError(it))
                }
            }

            else -> result
        }
    }

    private fun transformIfNetworkError(error: Throwable): Throwable {
        return when (error) {

            is UnknownHostException -> {
                NetworkError.NotConnected()
            }

            is SocketTimeoutException -> {
                NetworkError.SlowConnection()
            }

            is IOException -> {
                if (error.cause?.message?.contentEquals("Canceled") == true) {
                    NetworkError.Canceled()
                } else {
                    error
                }
            }

            else -> {
                error
            }
        }
    }
}
