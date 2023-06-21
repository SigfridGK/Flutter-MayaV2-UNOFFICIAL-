import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatefulWidget {

  @override
  State<MyApp> createState() => _MyApp();
}

class _MyApp extends State<MyApp> {
  @override
  Widget build(BuildContext context) {
    return
    MaterialApp(
      color: Colors.white,
      debugShowCheckedModeBanner: true,
      initialRoute: '/',
      routes: {
        '/': (context) => HomeVC(),
      },
    );
  }
}

class HomeVC extends StatelessWidget {
  static const MethodChannel _channel = MethodChannel('paymayaSDKV2');

  @override
  Widget build(BuildContext context) {
    const style1 = TextStyle(fontSize: 17.5, color: Colors.white, fontWeight: FontWeight.w500);

    return
    Scaffold(
      body: 
      Column(
        crossAxisAlignment: CrossAxisAlignment.stretch,
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Padding(
            padding: const EdgeInsets.symmetric(horizontal: 18),
            child: MaterialButton(
              elevation: 2.5,
              color: Colors.blue[200],
              onPressed: () async{
                // var jsonob = "{\"metadata\":{\"subMerchantRequestReferenceNumber\": \"SUBMER-12345\",\"pf\":{\"smi\": \"SUB034221\",\"smn\": \"Maya Merchant\",\"mci\": \"MANILA\",\"mpc\": \"608\",\"mco\": \"PHL\",\"mcc\": \"3415\",\"postalCode\": \"1001\",\"contactNo\": \"+6329112345\",\"state\": \"Metro Manila\",\"addressLine1\": \"Quezon Boulevard, Quiapo\"}}}";
                // var test  = jsonDecode(jsonob);
                // print(test["metadata"]["subMerchantRequestReferenceNumber"]);
                final tet = "pk-mBNJ6Zld4jnhdiJE5kCu0mCuPtiMvLH05oIOWvMTkKQ:";
                Codec<String, String> stringToBase64 = utf8.fuse(base64);
                String encoded = stringToBase64.encode(tet);      
                String decoded = stringToBase64.decode(encoded);   
                print(decoded);
                print(encoded);
                String encoded1 = base64.encode(utf8.encode(tet));
                print(encoded1);
                print(await _channel.invokeMethod("executePayment", "params"));
              },
          
              child: 
              const Padding(
                padding: EdgeInsets.symmetric(vertical: 12.5),
                child: Text("START", style: style1),
              ),
            ),
          )
        ],
      ),
    );
  }
}
