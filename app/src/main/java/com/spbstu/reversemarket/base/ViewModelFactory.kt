package com.spbstu.reversemarket.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.spbstu.reversemarket.di.scope.FeatureScope
import javax.inject.Inject
import javax.inject.Provider

@FeatureScope
class ViewModelFactory @Inject constructor(
    private val creators: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val creator = creators[modelClass] ?: creators.entries.firstOrNull {
            modelClass.isAssignableFrom(it.key)
        }?.value ?: throw IllegalArgumentException("unknown model class $modelClass")
        @Suppress("UNCHECKED_CAST")
        return creator.get() as T
    }
}