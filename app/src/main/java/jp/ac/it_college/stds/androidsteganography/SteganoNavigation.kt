package jp.ac.it_college.stds.androidsteganography

import android.graphics.Bitmap
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import jp.ac.it_college.stds.androidsteganography.scene.DecodeScene
import jp.ac.it_college.stds.androidsteganography.scene.EncryptResultScene
import jp.ac.it_college.stds.androidsteganography.scene.EncryptScene
import jp.ac.it_college.stds.androidsteganography.scene.StartScene

object Destinations {
    const val START = "start"
    const val ENCRYPTION = "encryption"
    const val DECRYPTION = "decryption"
    const val RESULT_ENC = "result_enc"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SteganoNavigation(
    navController: NavHostController = rememberNavController(),
) {
    var titleText by remember { mutableStateOf("") }
    var testBitmap by remember {
        mutableStateOf(
            Bitmap.createBitmap(
                1,
                1,
                Bitmap.Config.ARGB_8888
            )
        )
    }

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = titleText) },
            )
        },
    ) {
        NavHost(
            navController = navController,
            startDestination = Destinations.START,
            modifier = Modifier.padding(it)
        ) {
            composable(Destinations.START) {
                titleText = "スタート画面"
                StartScene(
                    onEncryptClick = {
                        testBitmap = it
                        navController.navigate(Destinations.ENCRYPTION)
                    },
                    onDecryptClick = {
                        navController.navigate(Destinations.DECRYPTION)
                    }
                )
            }

            composable(Destinations.ENCRYPTION) {
                titleText = "暗号化画面"
                EncryptScene(
                    receive = testBitmap,
                    onEncryptResult = { navController.navigate(Destinations.RESULT_ENC) }
                )
            }

            composable(Destinations.RESULT_ENC) {
                EncryptResultScene(
                    onEndClick = { navController.navigate(Destinations.START) }

                )
            }

            composable(Destinations.DECRYPTION) {
                DecodeScene(
                    onDecodeClick = { navController.navigate(Destinations.START) }
                )
            }
        }
    }

}