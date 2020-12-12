package com.spbstu.reversemarket.sell.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.spbstu.reversemarket.di.scope.FeatureScope
import com.spbstu.reversemarket.profile.data.model.toDomainModel
import com.spbstu.reversemarket.profile.domain.model.Address
import com.spbstu.reversemarket.sell.data.api.SellApi
import com.spbstu.reversemarket.sell.data.model.ProposalBody
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@FeatureScope
class SellInfoViewModel @Inject constructor(
    private val sellApi: SellApi
) : ViewModel() {

    private lateinit var addressData: MutableLiveData<List<Address>>

    fun addProposal(proposalBody: ProposalBody) {
        sellApi.addProposal(proposalBody)
    }

    fun getAddress(): LiveData<List<Address>> {
        if (!this::addressData.isInitialized) {
            addressData = MutableLiveData()
            loadAddress()
        }
        return addressData
    }

    private fun loadAddress() {
        sellApi.getAddresses().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError {
            }
            .subscribe {
                if (it.code() == 200) {
                    addressData.value = it.body()?.map { address -> address.toDomainModel() }
                }
            }
    }

}