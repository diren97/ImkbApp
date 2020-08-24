package com.diren.veripark.core
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.diren.veripark.ext.observeEvent
import kotlin.reflect.KClass
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.parametersOf

abstract class BaseActivity<B : ViewDataBinding, M : BaseViewModel>(
    @LayoutRes private val layoutId: Int
) : AppCompatActivity() {

    lateinit var viewBinding: B
    val viewModel: M by lazy {
        getViewModel(
            clazz = viewModelClass(),
            parameters = { parametersOf(arguments()) }
        )
    }

    abstract fun onInitDataBinding()

    abstract fun viewModelClass(): KClass<M>

    private fun arguments() = intent.extras ?: Bundle()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = DataBindingUtil.setContentView(this, layoutId)
        viewBinding.lifecycleOwner = this

        onInitDataBinding()
        observeEvent(viewModel.baseEvent, ::onViewEvent)

    }

    private fun onViewEvent(event: BaseViewEvent) {
        when (event) {
            is BaseViewEvent.ShowCustomError -> showError(event.message)
        }
    }

    fun showError(error: String) {
        Toast.makeText(this, error,Toast.LENGTH_LONG).show()
    }
}
