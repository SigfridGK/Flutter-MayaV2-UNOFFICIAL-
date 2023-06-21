package com.example.mayav2

import android.content.Intent
import android.widget.Toast
import androidx.annotation.NonNull

import com.paymaya.sdk.android.common.LogLevel
import com.paymaya.sdk.android.common.PayMayaEnvironment
import com.paymaya.sdk.android.common.models.AmountDetails
import com.paymaya.sdk.android.common.models.RedirectUrl
import com.paymaya.sdk.android.common.models.TotalAmount
import com.paymaya.sdk.android.paywithpaymaya.PayWithPayMaya
import com.paymaya.sdk.android.paywithpaymaya.PayWithPayMayaResult
import com.paymaya.sdk.android.paywithpaymaya.SinglePaymentResult
import com.paymaya.sdk.android.paywithpaymaya.models.SinglePaymentRequest
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel
import org.json.JSONObject
import java.math.BigDecimal


class MainActivity: FlutterActivity() {
    private var resl = "";
    private val mthdChannel = "paymayaSDKV2";
    private val payMayaCheckoutClient = PayWithPayMaya.newBuilder()
        .clientPublicKey("cGstbUJOSjZabGQ0am5oZGlKRTVrQ3UwbUN1UHRpTXZMSDA1b0lPV3ZNVGtLUTo=")
        .environment(PayMayaEnvironment.SANDBOX)
        .logLevel(LogLevel.ERROR)
        .build()

    override fun configureFlutterEngine(@NonNull flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, mthdChannel)
            .setMethodCallHandler { call, result ->
            if (call.method == "executePayment"){
                var amountDetails = AmountDetails(
                                    BigDecimal("0.0"),
                                    BigDecimal("0.0"), BigDecimal("0.0"),
                                    BigDecimal("0.0"), BigDecimal("00.0"))
                var totalAmount = TotalAmount(BigDecimal("100.0"),"PHP",amountDetails)
                var redirectUrl = RedirectUrl(
                            "http://success.com",
                            "http://failure.com",
                            "http://cancel.com")
//                var json = "{"metadata":{"subMerchantRequestReferenceNumber":"11231313232","pf":{"smi":"SUB034221","smn":"Maya Merchant","mci":"MANILA","mpc":"608","mco":"PHL","mcc":"3415","postalCode":"1001","contactNo":"+6329112345","state":"Metro Manila","addressLine1":"Quezon Boulevard, Quiapo"}}}"
//                "\"subMerchantRequestReferenceNumber\":\"11231313232\",\"pf\":{\"smi\":\"SUB034221\",\"smn\":\"Maya Merchant\",\"mci\":\"MANILA\",\"mpc\":\"608\",\"mco\":\"PHL\",\"mcc\":\"3415\",\"postalCode\":\"1001\",\"contactNo\":\"+6329112345\",\"state\":\"Metro Manila\",\"addressLine1\":\"Quezon Boulevard, Quiapo\"}"
//                var json: JSONObject  =  JSONObject("{\"subMerchantRequestReferenceNumber\": \"11231313232\",\"pf\": {\"smi\": \"SUB034221\",\"smn\": \"Maya Merchant\",\"mci\": \"MANILA\",\"mpc\": \"608\",\"mco\": \"PHL\",\"mcc\": \"3415\",\"postalCode\": \"1001\",\"contactNo\": \"+6329112345\",\"state\": \"Metro Manila\",\"addressLine1\": \"Quezon Boulevard, Quiapo\"}}")
                var json = JSONObject("{}")
                val request = SinglePaymentRequest(
                    totalAmount,
                    "1123221313232",
                    redirectUrl,
                    json
                )
                payMayaCheckoutClient.startSinglePaymentActivityForResult(this,request)
                result.success(resl);
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        payMayaCheckoutClient.onActivityResult(requestCode, resultCode, data)?.let {
            processCheckoutResult(it)
        }
    }

    private fun processCheckoutResult(result: PayWithPayMayaResult) {
        when (result) {
            is SinglePaymentResult.Success -> {
                val message = "Success, checkoutId: ${result.paymentId}"
                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
            }

            is SinglePaymentResult.Cancel -> {
                val message = "Canceled, checkoutId: ${result.paymentId}"
                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
            }

            is SinglePaymentResult.Failure -> {
                resl = "${result.exception}"
//                val message =
//                    "Failure, checkoutId: ${result.paymentId}, exception: ${result.exception}"
                val message = "exception: ${result.exception}"
//                if (result.exception is BadRequestException) {
//                    Log.d(TAG, (result.exception as BadRequestException).error.toString())
//                }
                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
            }
            else -> {}
        }
    }
}
