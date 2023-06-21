import UIKit
import Flutter
import PayMayaSDK

@UIApplicationMain
@objc class AppDelegate: FlutterAppDelegate {
  override func application(
    _ application: UIApplication,
    didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?
  ) -> Bool {
    GeneratedPluginRegistrant.register(with: self)

    PayMayaSDK.setup(environment: .sandbox, logLevel: .all, [
      .checkout: "pk-Z0OSzLvIcOI2UIvDhdTGVVfRSSeiGStnceqwUE7n0Ah",
      .payments: "pk-MOfNKu3FmHMVHtjyjG7vhr7vFevRkWxmxYL1Yq6iFk5",
      .cardToken: "pk-Z0OSzLvIcOI2UIvDhdTGVVfRSSeiGStnceqwUE7n0Ah"
    ])

    let controller : FlutterViewController = window?.rootViewController as! FlutterViewController
    let encryptionChannel = FlutterMethodChannel(name: "paymayaSDKV2", binaryMessenger: controller.binaryMessenger)
    encryptionChannel.setMethodCallHandler({
      [weak self] (call: FlutterMethodCall, result: FlutterResult) -> Void in
      
      // Note: this method is invoked on the UI thread.
      if(call.method == "executePayment"){
          // guard let arguments = call.arguments as? [String:FlutterStandardTypedData],
          // let data:FlutterStandardTypedData = arguments["image"] else {
          //     result("Failed")
          //     return
          // }
          // let uiImage = UIImage(data: data.data)!

          // //Save it to the gallery
          // UIImageWriteToSavedPhotosAlbum(uiImage, nil, nil, nil)
          result("Ok")
          return
      }else{
          result(FlutterMethodNotImplemented)
          return
      }
    })

    return super.application(application, didFinishLaunchingWithOptions: launchOptions)
  }
}
