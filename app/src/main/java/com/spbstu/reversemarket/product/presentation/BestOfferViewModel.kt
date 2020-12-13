package com.spbstu.reversemarket.product.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.spbstu.reversemarket.di.scope.FeatureScope
import com.spbstu.reversemarket.product.data.api.ProductApi
import com.spbstu.reversemarket.product.data.model.Proposal
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject



@FeatureScope
class BestOfferViewModel @Inject constructor(
    private val productApi: ProductApi
) : ViewModel() {
    private lateinit var proposalData: MutableLiveData<List<Proposal>>
    private val proposals = mutableListOf<Proposal>()

    fun getProposal(bestProposalId: Int): LiveData<List<Proposal>> {
        if (!this::proposalData.isInitialized) {
            proposalData = MutableLiveData()
            loadProposal(bestProposalId)
        }
        return proposalData
    }

    private fun loadProposal(bestProposalId: Int) {
        productApi.getBestProposal(bestProposalId).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError {
            }
            .subscribe {
                if (it.code() == 200) {
                    it.body()?.let { proposal -> proposals.add(proposal) }
                    proposalData.value = proposals
                }
            }
    }

}