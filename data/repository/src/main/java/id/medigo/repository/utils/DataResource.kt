package id.medigo.repository.utils

import android.annotation.SuppressLint
import android.util.Log
import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

abstract class DataResource<ResultType, RequestType> {

    val TAG = DataResource::class.java.simpleName

    lateinit var result: Observable<ResultType>

    @SuppressLint("CheckResult")
    fun build(): DataResource<ResultType, RequestType> {
        result = loadFromDb()
        loadFromDb().doOnNext {
            if (shouldFetch(it))
                result = networkData().map { requestType -> processResponse(requestType) }
        }
        return this
    }

    private fun networkData(): Observable<RequestType> {
        return if (shouldSaveOnIO()) {
            createCall().doOnNext {
                storeData(processResponse(it))
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .subscribe {
                        Log.d(TAG,"Data saved on disk")
                    }
            }
        } else {
            createCall()
        }
    }

    fun storeData(data: ResultType): Completable {
        return Completable.mergeArray(saveCallResults(data))
    }

    @WorkerThread
    protected abstract fun processResponse(response: RequestType): ResultType

    @WorkerThread
    protected abstract fun saveCallResults(data: ResultType): Completable

    @MainThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    @MainThread
    protected abstract fun shouldSaveOnIO(): Boolean

    @MainThread
    protected abstract fun loadFromDb(): Observable<ResultType>

    @MainThread
    protected abstract fun createCall(): Observable<RequestType>
}