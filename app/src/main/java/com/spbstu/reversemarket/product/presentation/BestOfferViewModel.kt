package com.spbstu.reversemarket.product.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.spbstu.reversemarket.di.scope.FeatureScope
import com.spbstu.reversemarket.product.data.api.ProductApi
import com.spbstu.reversemarket.product.data.model.BestProposalToBuyBody
import com.spbstu.reversemarket.profile.data.model.Order
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject


@FeatureScope
class BestOfferViewModel @Inject constructor(
    private val productApi: ProductApi
) : ViewModel() {
    private lateinit var proposalData: MutableLiveData<ProposalResult?>

    fun buyProposal(bestId: Int): LiveData<Boolean> {
        val res = MutableLiveData<Boolean>()
        productApi.buyProposal(BestProposalToBuyBody(bestId))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it.isSuccessful) {
                    res.postValue(true)
                } else {
                    res.postValue(false)
                }
            }, {
                res.postValue(false)
            })
        return res
    }

    fun getProposal(bestProposalId: Int, sell: Boolean): LiveData<ProposalResult?> {
        if (!this::proposalData.isInitialized) {
            proposalData = MutableLiveData()
            if (sell) {
                productApi.getBestProposal(bestProposalId).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        if (it.isSuccessful) {
                            val order = it.body()
                            if (order != null) {
                                proposalData.value = ProposalResult.Data(order)
                            } else {
                                proposalData.value = ProposalResult.EMPTY
                            }
                        } else {
                            proposalData.value = ProposalResult.EMPTY
                        }
                    }, {
                        proposalData.value = null
                    })
            } else {
                productApi.getUserBestProposal(bestProposalId).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        if (it.isSuccessful) {
                            val order = it.body()
                            if (order != null) {
                                proposalData.value = ProposalResult.Data(order)
                            } else {
                                proposalData.value = ProposalResult.EMPTY
                            }
                        } else {
                            proposalData.value = ProposalResult.EMPTY
                        }
                    }, {
                        proposalData.value = null
                    })
            }
        }
        return proposalData
    }

    sealed class ProposalResult {
        data class Data(val order: Order): ProposalResult()
        object EMPTY: ProposalResult()
    }

}