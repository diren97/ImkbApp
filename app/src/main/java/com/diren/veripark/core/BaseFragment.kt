package com.diren.operation.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.diren.veripark.R
import com.diren.veripark.core.BaseViewEvent
import com.diren.veripark.core.BaseViewModel
import com.diren.veripark.ext.observeEvent
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.parametersOf
import kotlin.reflect.KClass


abstract class BaseFragment<B : ViewDataBinding, M : BaseViewModel>(
    @LayoutRes private val layoutId: Int
) : Fragment() {

    lateinit var viewBinding: B
    val viewModel: M by lazy {
        getViewModel(
            clazz = viewModelClass(),
            parameters = { parametersOf(arguments()) }
        )
    }

    abstract fun viewModelClass(): KClass<M>

    abstract fun onInitDataBinding()

    private fun arguments(): Bundle {
        val fragmentArgs = arguments ?: Bundle()
        val activityArgs = activity?.intent?.extras ?: Bundle()
        return activityArgs.apply { putAll(fragmentArgs) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        viewBinding.lifecycleOwner = viewLifecycleOwner
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onInitDataBinding()
        observeEvent(viewModel.baseEvent, ::onViewEvent)
    }

    private fun onViewEvent(event: BaseViewEvent) {
        when (event) {
        }
    }

    fun showCommonError() {
        (requireActivity() as? AppCompatActivity)?.let {
            Toast.makeText(it, getString(R.string.something_went_wrong), Toast.LENGTH_LONG)
                .show()
        }
    }

    fun showError(@StringRes error: Int) {
        (requireActivity() as? AppCompatActivity)?.let {
            Toast.makeText(it, getString(error), Toast.LENGTH_LONG).show()
        }
    }
}
