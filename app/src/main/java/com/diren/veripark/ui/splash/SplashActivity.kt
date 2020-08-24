package com.diren.veripark.main


import android.annotation.SuppressLint
import android.os.Build
import android.provider.Settings
import android.util.Log
import com.diren.veripark.R
import com.diren.veripark.core.BaseActivity
import com.diren.veripark.data.DeviceInfo
import com.diren.veripark.databinding.SplashActivityBinding
import com.diren.veripark.ext.observeEvent
import com.diren.veripark.ui.splash.SplashViewEvent
import org.bouncycastle.jce.provider.BouncyCastleProvider
import java.io.UnsupportedEncodingException
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import java.security.Security
import javax.crypto.*
import javax.crypto.spec.SecretKeySpec


class SplashActivity :
    BaseActivity<SplashActivityBinding, SplashViewModel>(R.layout.splash_activity) {

    override fun viewModelClass() = SplashViewModel::class

    @SuppressLint("HardwareIds")
    override fun onInitDataBinding() {
        viewBinding.viewModel = viewModel
        observeEvent(viewModel.event, ::onViewEvent)

        viewModel.preferenceManager.deviceInfo = DeviceInfo(
            deviceId = Settings.Secure.getString(
                contentResolver,
                Settings.Secure.ANDROID_ID
            ),
            systemVersion = Build.VERSION.SDK_INT.toString(),
            platformName = "Android",
            deviceModel = Build.MODEL,
            manifacturer = Build.MANUFACTURER
        ).also {

            viewModel.handShake(DeviceInfo(
                deviceId = Settings.Secure.getString(
                    contentResolver,
                    Settings.Secure.ANDROID_ID
                ),
                systemVersion = Build.VERSION.SDK_INT.toString(),
                platformName = "Android",
                deviceModel = Build.MODEL,
                manifacturer = Build.MANUFACTURER
            ))
        }
    }

    private fun onViewEvent(event: SplashViewEvent) {
        when (event) {
            SplashViewEvent.NavigateToMain -> {
                startActivity(MainActivity.newIntent(this))
            }
            is SplashViewEvent.HandShakeDecoder -> {

                handShakeDecoder(event.data.aesKey, event.data.aesIV).also {
                    startActivity(MainActivity.newIntent(this))
                }
            }
        }
    }


    @SuppressLint("GetInstance")
    fun handShakeDecoder(key: String, strToDecrypt: String?): String? {
        Security.addProvider(BouncyCastleProvider())
        var keyBytes: ByteArray

        try {
            keyBytes = key.toByteArray(charset("UTF8"))
            val skey = SecretKeySpec(keyBytes, "AES")
            val input = org.bouncycastle.util.encoders.Base64
                .decode(strToDecrypt?.trim { it <= ' ' }?.toByteArray(charset("UTF8")))

            synchronized(Cipher::class.java) {
                val cipher = Cipher.getInstance("AES/ECB/PKCS7Padding")
                cipher.init(Cipher.DECRYPT_MODE, skey)

                val plainText = ByteArray(cipher.getOutputSize(input.size))
                var ptLength = cipher.update(input, 0, input.size, plainText, 0)
                ptLength += cipher.doFinal(plainText, ptLength)
                val decryptedString = String(plainText)
                viewModel.preferenceManager.aesKey = decryptedString
                viewModel.preferenceManager.aesIV =decryptedString
                Log.d("AESKEY",decryptedString)
                return decryptedString.trim { it <= ' ' }
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
    }}


