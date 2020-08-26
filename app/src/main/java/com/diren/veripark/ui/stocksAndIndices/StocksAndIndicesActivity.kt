package com.diren.veripark.ui.stocksAndIndices

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Base64.encode
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.diren.veripark.R
import com.diren.veripark.core.BaseActivity
import com.diren.veripark.databinding.StocksAndIndicesActivityBinding
import com.diren.veripark.ext.observeEvent
import kotlinx.android.synthetic.main.stocks_and_indices_activity.*
import org.bouncycastle.jce.provider.BouncyCastleProvider
import java.io.UnsupportedEncodingException
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import java.security.Security
import java.util.*
import javax.crypto.*
import javax.crypto.spec.SecretKeySpec

class StocksAndIndicesActivity : BaseActivity<StocksAndIndicesActivityBinding, StocksAndIndicesViewModel>(R.layout.stocks_and_indices_activity) {
    private lateinit var drawerLayout : DrawerLayout
    private lateinit var navController : NavController
    lateinit var appBarConfiguration : AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val fragmentDestinations = setOf(R.id.mainFragment, R.id.stockAndIndicesFragment, R.id.fallenListFragment, R.id.thirtyByVolumeFragment)
        appBarConfiguration = AppBarConfiguration(topLevelDestinationIds = fragmentDestinations, drawerLayout = drawerLayout)
        navController = findNavController(R.id.nav_host_fragment)
        setupActionBarWithNavController(navController, appBarConfiguration)
        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView.setupWithNavController(navController)
    }
    override fun viewModelClass() = StocksAndIndicesViewModel::class
     override fun onInitDataBinding() {
        viewBinding.viewModel = viewModel
        observeEvent(viewModel.event,::onViewEvent)
        //viewModel.getStocks()
    }

    override fun onNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onNavigateUp()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            drawer_layout.openDrawer(Gravity.LEFT)
        }
        return super.onOptionsItemSelected(item)
    }

    @SuppressLint("NewApi")
    private fun onViewEvent(event: StocksAndIndicesViewEvent){
        when(event){
            StocksAndIndicesViewEvent.ClickOnButton ->{
               //startActivity(MainActivity.newIntent(this))

            }
            is StocksAndIndicesViewEvent.StockAES -> {

                encrypt(event.data.aesKey,event.data.aesIV).also {
                    Log.d("AESKEY",event.data.aesKey)

                    //startActivity(MainActivity.newIntent(this))
                }
            }
        }
    }
    companion object{
        fun newIntent(context: Context): Intent =
            Intent(context, StocksAndIndicesActivity::class.java)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun encrypt(strToEncrypt: String, secret_key: String): String? {
        Security.addProvider(BouncyCastleProvider())
        var keyBytes: ByteArray

        try {
            keyBytes = secret_key.toByteArray(charset("UTF8"))
            val skey = SecretKeySpec(keyBytes, "AES")
            val input = strToEncrypt.toByteArray(charset("UTF8"))

            synchronized(Cipher::class.java) {
                val cipher = Cipher.getInstance("AES/ECB/PKCS7Padding")
                cipher.init(Cipher.ENCRYPT_MODE, skey)

                val cipherText = ByteArray(cipher.getOutputSize(input.size))
                var ctLength = cipher.update(
                    input, 0, input.size,
                    cipherText, 0
                )
                ctLength += cipher.doFinal(cipherText, ctLength)
                val decryptedString = String(cipherText)
                viewModel.preferenceManager.aesKey = decryptedString
                viewModel.preferenceManager.aesIV =decryptedString
                Log.d("AESKEY",decryptedString)

          val encodedString: String =  Base64.getEncoder().encodeToString(cipherText)
                  // Base64.encode(cipherText)
                return encodedString
            }
        } catch (uee: UnsupportedEncodingException) {
            uee.printStackTrace()
        } catch (ibse: IllegalBlockSizeException) {
            ibse.printStackTrace()
        } catch (bpe: BadPaddingException) {
            bpe.printStackTrace()
        } catch (ike: InvalidKeyException) {
            ike.printStackTrace()
        } catch (nspe: NoSuchPaddingException) {
            nspe.printStackTrace()
        } catch (nsae: NoSuchAlgorithmException) {
            nsae.printStackTrace()
        } catch (e: ShortBufferException) {
            e.printStackTrace()
        }

        return null
    }



}