package jp.ac.it_college.stds.androidsteganography.components.desing

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

val Coral = Color(15,35,240)
val LightYellow = Color(18,53,151)

@Composable
fun GradientTopAppBar(
    title: String,
    navController: NavController,
    backKey: Boolean
) {
    val gradient = Brush.linearGradient(
        colors = listOf(Coral, LightYellow),
    )
    val mainWhite = Color(254,247,255)
    val mainGray = Color(217,217,217)

    Box (
        Modifier
            .height(70.dp)
            .fillMaxWidth()
            .background(color = mainGray)
    ){}
    Box(
        modifier = Modifier
            .height(56.dp)
            .background(
//                brush = gradient,
                color = mainWhite,
                shape = RoundedCornerShape( bottomEnd = 120f)
            )
    ) {


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // ナビゲーションアイコンを設定
            if (backKey) {
                IconButton(
                    onClick = { navController.popBackStack() }

                ) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.DarkGray)
                }
            } else {
                Spacer(modifier = Modifier.width(48.dp))
            }

            // タイトルを設定
            Text(
                text = title,
                color = Color.DarkGray,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier
                    .weight(1f) // タイトルができるだけ中央に配置されるようにする
                    .wrapContentWidth(Alignment.CenterHorizontally)
            )

            // アクションアイコンを設定
            if (false) {
                // 非表示の場合でも適切な幅を確保
                IconButton(onClick = { /* Handle action click */ }) {
                    Icon(Icons.Default.Menu, contentDescription = "Menu")
                }
            } else {
                Spacer(modifier = Modifier.width(48.dp))
            }
        }


    }
}


@Composable
@Preview
fun GradientTopAppBarPreview() {
    val navController = rememberNavController()

    // Preview用のデータを指定
    GradientTopAppBar(
        title = "Title",
        navController = navController,
        backKey = true // または false
    )
}
