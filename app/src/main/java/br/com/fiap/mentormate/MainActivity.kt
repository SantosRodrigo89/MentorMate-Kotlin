package br.com.fiap.mentormate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.fiap.mentormate.ui.ChatListScreen
import br.com.fiap.mentormate.ui.LoginScreen
import br.com.fiap.mentormate.ui.ProfileScreen
import br.com.fiap.mentormate.ui.SignupScreen
import br.com.fiap.mentormate.ui.SingleChatScreen
import br.com.fiap.mentormate.ui.SwipeCards
import br.com.fiap.mentormate.ui.theme.MentorMateTheme

sealed class DestinationScreen(val rout: String) {
    object Signup: DestinationScreen("signup")
    object Login: DestinationScreen("login")
    object Profile: DestinationScreen("profile")
    object Swipe: DestinationScreen("swipe")
    object ChatList: DestinationScreen("chatlist")
    object SingleChat: DestinationScreen("singleChat/{chatId}"){
        fun createRoute(id: String) = "singleChat/$id"
    }

}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MentorMateTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SwipeAppNavigation()
                }
            }
        }
    }
}

@Composable
fun SwipeAppNavigation(){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = DestinationScreen.Swipe.rout){
        composable(DestinationScreen.Signup.rout) {
            SignupScreen()
        }
        composable(DestinationScreen.Login.rout) {
            LoginScreen()
        }
        composable(DestinationScreen.Profile.rout) {
            ProfileScreen()
        }
        composable(DestinationScreen.Swipe.rout) {
            SwipeCards()
        }
        composable(DestinationScreen.ChatList.rout) {
            ChatListScreen()
        }
        composable(DestinationScreen.SingleChat.rout) {
            SingleChatScreen(chatId = "123")
        }
    }

}