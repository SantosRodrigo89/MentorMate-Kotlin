package br.com.fiap.mentormate

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.fiap.mentormate.ui.ChatListScreen
import br.com.fiap.mentormate.ui.LoginScreen
import br.com.fiap.mentormate.ui.ProfileScreen
import br.com.fiap.mentormate.ui.SearchScreen
import br.com.fiap.mentormate.ui.SignupScreen
import br.com.fiap.mentormate.ui.SingleChatScreen
import br.com.fiap.mentormate.ui.SwipeScreen
import br.com.fiap.mentormate.ui.theme.MentorMateTheme
import dagger.hilt.android.AndroidEntryPoint

sealed class DestinationScreen(val rout: String) {
    object Signup : DestinationScreen("signup")
    object Login : DestinationScreen("login")
    object Profile : DestinationScreen("profile")
    object Swipe : DestinationScreen("swipe")
    object ChatList : DestinationScreen("chatlist")
    object SingleChat : DestinationScreen("singleChat/{chatId}") {
        fun createRoute(id: String) = "singleChat/$id"
    }

    object Search : DestinationScreen("search")

}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MentorMateTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SwipeAppNavigation(this)
                }
            }
        }
    }
}

private fun requestNotificationPermission(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        val hasPermission = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED

        if (!hasPermission) {
            ActivityCompat.requestPermissions(
                context as Activity, // Cast para Activity se necessário
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                0
            )
        }
    }
}

@Composable
fun SwipeAppNavigation(context: Context) {
    val navController = rememberNavController()
    val vm = hiltViewModel<MMViewModel>()

    NotificationMessage(vm = vm)

    NavHost(navController = navController, startDestination = DestinationScreen.Signup.rout) {
        composable(DestinationScreen.Signup.rout) {
            SignupScreen(navController, vm)
        }
        composable(DestinationScreen.Login.rout) {
            LoginScreen(navController, vm)
        }
        composable(DestinationScreen.Profile.rout) {
            ProfileScreen(navController, vm)
        }
        composable(DestinationScreen.Swipe.rout) {
            SwipeScreen(navController, vm, context)
        }
        composable(DestinationScreen.ChatList.rout) {
            ChatListScreen(navController, vm)
        }
        composable(DestinationScreen.SingleChat.rout) {
            val chatId = it.arguments?.getString("chatId")
            chatId?.let {
                SingleChatScreen(navController = navController, vm = vm, chatId = it)
            }
        }
        composable(DestinationScreen.Search.rout) {
            SearchScreen(navController, vm)
        }
    }

}