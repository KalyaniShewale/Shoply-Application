package com.kalyani.shewale.shoplyapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.kalyani.shewale.shoplyapplication.navigation.AppNavGraph
import com.kalyani.shewale.shoplyapplication.ui.theme.ShoplyApplicationTheme
import com.kalyani.shewale.shoplyapplication.usecase.LoginUseCase
import com.kalyani.shewale.shoplyapplication.usecase.RegisterUseCase
import dagger.hilt.android.AndroidEntryPoint
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var registerUseCase: RegisterUseCase

    @Inject
    lateinit var loginUseCase: LoginUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TEMPORARY test:
        lifecycleScope.launch {
            val result = registerUseCase("Test User 2", "test2@shoply.com", "+911234567891", "123456")
            Log.d("AuthCheck 222222", result.toString())
        }

        lifecycleScope.launch {
            val result = loginUseCase("test1@shoply.com", "123456")
            Log.d("AuthCheck 111", result.toString())
        }

        enableEdgeToEdge()
        setContent {
            ShoplyApplicationTheme {
                AppNavGraph()
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