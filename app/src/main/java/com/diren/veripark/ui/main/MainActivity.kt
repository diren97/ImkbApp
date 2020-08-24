package com.diren.veripark.main


import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.diren.veripark.R
import com.diren.veripark.core.BaseActivity
import com.diren.veripark.databinding.ActivityMainBinding
import com.diren.veripark.ext.observeEvent
import com.diren.veripark.ui.main.MainActivityEvent
import com.diren.veripark.ui.splash.SplashViewEvent
import com.diren.veripark.ui.stocksAndIndices.StocksAndIndicesActivity

class MainActivity : BaseActivity<ActivityMainBinding, MainActivityViewModel>(R.layout.activity_main) {

    override fun viewModelClass() = MainActivityViewModel::class

    override fun onInitDataBinding() {
        viewBinding.viewModel = viewModel
        observeEvent(viewModel.event,::onViewEvent)
      }

    private fun onViewEvent(event: MainActivityEvent){
        when(event){
            MainActivityEvent.ClickOnButton ->{
               startActivity(StocksAndIndicesActivity.newIntent(this))
            }
        }
    }
    companion object{
        fun newIntent(context: Context):Intent=
            Intent(context,MainActivity::class.java)
    }

}
