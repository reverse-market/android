package com.spbstu.reversemarket.sell.presentation

import android.content.ContentResolver
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.spbstu.reversemarket.buy.data.model.ImageResponse
import com.spbstu.reversemarket.category.data.api.CategoryApi
import com.spbstu.reversemarket.category.data.model.Category
import com.spbstu.reversemarket.di.scope.FeatureScope
import com.spbstu.reversemarket.sell.data.api.SellInfoApi
import com.spbstu.reversemarket.sell.data.model.ProposalBody
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Response
import java.io.File
import javax.inject.Inject

@FeatureScope
class SellInfoViewModel @Inject constructor(
    private val sellInfoApi: SellInfoApi
) : ViewModel() {

    private lateinit var photosData: MutableLiveData<List<String>>
    val filesMap: MutableMap<Uri, File> = mutableMapOf()

    fun copyUriDataToFile(contentResolver: ContentResolver, uri: Uri, file: File) {
        contentResolver.openInputStream(uri)?.let {
            val buffer = ByteArray(1024)
            var length = it.read(buffer)
            val fileStream = file.outputStream()
            while (length > 0) {
                fileStream.write(buffer, 0, length)
                length = it.read(buffer)
            }

            fileStream.close()
            it.close()
        }
    }

    fun addImageFile(uri: Uri, file: File) {
        filesMap[uri] = file
    }

    fun uploadRequest(): LiveData<List<String>> {
        photosData = MutableLiveData<List<String>>()
        // firstly upload each photo
        val files = filesMap.values
        if (files.isNotEmpty()) {
            val observables =
                mutableListOf<Observable<Response<ImageResponse>>>()
            files.forEach {
                val requestFile = it.asRequestBody("image/jpeg".toMediaTypeOrNull())
                val body = MultipartBody.Part.createFormData("file", it.name, requestFile)

                observables.add(
                    sellInfoApi.uploadImage(body)
                        .subscribeOn(Schedulers.io())
                )
            }
            Observable.zip(observables) { items ->
                items.map { (it as Response<ImageResponse>) }
            }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { res ->
                        Log.d("WWWW", "Images responses: $res")
                        Log.d("WWWW", "Images bodies: ${res.map { it.body() }}")
                        // here all data ready - we can create requests
                        photosData.postValue(res.map { it.body()!!.url })
                    },
                    {
                        photosData.postValue(emptyList())
                    })
        } else {
            // create without photos
            photosData.postValue(emptyList())
        }
        return photosData
    }

    fun createProposal(proposal: ProposalBody): LiveData<Boolean> {
        val res = MutableLiveData<Boolean>()
        uploadRequest().observeForever {
            proposal.photos = it
            Log.d("WWWW", "Trying to send $proposal")
            sellInfoApi.addProposal(proposal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    Log.d("WWWW", "Request response: $it")
                    if (it.isSuccessful) {
                        res.postValue(true)
                    } else {
                        res.postValue(false)
                    }
                }
        }
        return res
    }


}