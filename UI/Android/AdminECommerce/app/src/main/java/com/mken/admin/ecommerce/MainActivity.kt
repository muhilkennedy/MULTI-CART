package com.mken.admin.ecommerce

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mken.admin.ecommerce.ui.theme.AdminECommerceTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*setContent {
            AdminECommerceTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Greeting("Android")
                }
            }
        }*/
        setContentView(R.layout.activity_main)

        // 1st, Insert webview item in acitivity.
        var mywebView = findViewById<WebView>(R.id.MyWebView)

        // Block to run chrome unexpectedly
        mywebView.webViewClient = WebViewClient()

        // 3rd, Run webview loading code.
        mywebView?.apply {
            loadUrl("https://admin.mken.buzz")
            settings.javaScriptEnabled = true
        }
    }

    override fun onBackPressed() {
        var mywebView = findViewById<WebView>(R.id.MyWebView)
        if (mywebView.canGoBack()){
            mywebView.goBack()
        }
        else {
            super.onBackPressed()
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
            text = "Hello $name!",
            modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AdminECommerceTheme {
        Greeting("Android")
    }
}