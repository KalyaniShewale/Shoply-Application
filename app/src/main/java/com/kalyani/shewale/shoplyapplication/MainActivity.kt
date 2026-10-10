package com.kalyani.shewale.shoplyapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.kalyani.shewale.shoplyapplication.ui.screen.LoginScreen
import com.kalyani.shewale.shoplyapplication.ui.screen.RegisterScreen
import com.kalyani.shewale.shoplyapplication.ui.theme.ShoplyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShoplyApplicationTheme {
               // LoginScreen(errorMessage = stringResource(R.string.error_login_failed))
                RegisterScreen()
            }
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
    ShoplyApplicationTheme {
        Greeting("Android")
    }
}