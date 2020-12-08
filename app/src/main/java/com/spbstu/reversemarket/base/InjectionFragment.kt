package com.spbstu.reversemarket.base

import android.app.Activity
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.spbstu.reversemarket.di.injector.Injectable
import java.lang.reflect.ParameterizedType
import javax.inject.Inject

abstract class InjectionFragment<VM : ViewModel>(
    @LayoutRes contentLayoutId: Int
) : Fragment(contentLayoutId), Injectable {

    @Inject
    lateinit var activityContext: Activity

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    protected lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = obtainViewModel(getGenericType())
    }

    @Suppress("UNCHECKED_CAST")
    private fun getGenericType(): Class<VM> =
        (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<VM>

    private fun <T : ViewModel> obtainViewModel(viewModelClass: Class<T>) =
        ViewModelProvider(this, viewModelFactory).get(viewModelClass)
}