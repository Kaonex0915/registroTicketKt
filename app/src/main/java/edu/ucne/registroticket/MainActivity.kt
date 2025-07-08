package edu.ucne.registroticket

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import edu.ucne.registroticket.Presentation.Navigation.GastoNavHost
import edu.ucne.registroticket.ui.theme.RegistroTicketTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RegistroTicketTheme {
                val navHost = rememberNavController()
                GastoNavHost(navHost)

            }
        }
    }
}