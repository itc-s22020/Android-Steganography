package jp.ac.it_college.stds.androidsteganography

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
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
    navController: NavHostController = rememberNavController()
) {
    var titleText by remember { mutableStateOf("") }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    Scaffold(
        // 上部のバー
        topBar = {
            TopAppBar(title = {
                Text(text = titleText)
            })
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = Destinations.START,
            modifier = Modifier.padding(it)
        ) {
            composable(Destinations.START) {
                titleText = "最初の画面"
                StartScene (
                    OnEncryptClick = {
                        navController.navigate(Destinations.ENCRYPTION)
                    },
                    OnDecryptClick = {
                        navController.navigate(Destinations.DECRYPTION)
                    }
                )
            }

            composable(Destinations.ENCRYPTION) {
                EncryptScene (
                    OnEncryptResult = { navController.navigate(Destinations.RESULT_ENC) }
                )
            }

            composable(Destinations.RESULT_ENC) {
                 EncryptResultScene (
                    OnEndClick = { navController.navigate(Destinations.START) }
                )
            }

            composable(Destinations.DECRYPTION) {
                DecodeScene(
                    OnDecodeClick = { navController.navigate(Destinations.START) }
                )
            }
        }
    }

}