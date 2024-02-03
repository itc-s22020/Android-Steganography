package jp.ac.it_college.stds.androidsteganography

import android.graphics.Bitmap
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
    navController: NavHostController = rememberNavController(),
) {
    var titleText by remember { mutableStateOf("") }
    var bm by remember {
        mutableStateOf(
            Bitmap.createBitmap(
                1,
                1,
                Bitmap.Config.ARGB_8888
            )
        )
    }
    var showText by remember { mutableStateOf(false) }
    var selectNum by remember { mutableIntStateOf(0) }

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        Scaffold(

            topBar = {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    title = { Text(text = titleText) },
                    navigationIcon = {
                        if (titleText != "スタート画面") {
                            IconButton(onClick = { navController.popBackStack() }) {
                                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                            }
                        }
                    },
                    actions = {
                        IconButton(onClick = { showText = true }) {
                            Icon(imageVector = Icons.Filled.Menu, contentDescription = "Menu")
                        }
                    }
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
                        onEncryptClick = { bitmap, intValue ->
                            bm = bitmap
                            selectNum = intValue
                            navController.navigate(Destinations.ENCRYPTION)
                        },
                        onDecryptClick = { bitmap, intValue ->
                            bm = bitmap
                            selectNum = intValue
                            navController.navigate(Destinations.DECRYPTION)
                        }
                    )
                }

                composable(Destinations.ENCRYPTION) {
                    titleText = "暗号画面"
                    EncryptScene(
                        bm = bm,
                        encSelect = selectNum,
                        onEncryptResult = {bitmap ->
                            bm = bitmap
                            navController.navigate(Destinations.RESULT_ENC)
                        }
                    )
                }

                composable(Destinations.RESULT_ENC) {
                    titleText = "暗号化結果画面"
                    EncryptResultScene(
                        bm = bm,
                        onEndClick = { navController.navigate(Destinations.START) }
                    )
                }

                composable(Destinations.DECRYPTION) {
                    titleText = "復号化画面"

                    DecodeScene(
                        bm = bm,
                        decSelect = selectNum,
                        onDecodeClick = { navController.navigate(Destinations.START) }
                    )
                }
            }
        }
    }
}
