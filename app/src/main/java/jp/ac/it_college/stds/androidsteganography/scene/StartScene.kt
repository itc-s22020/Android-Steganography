package jp.ac.it_college.stds.androidsteganography.scene

import android.graphics.Bitmap
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import jp.ac.it_college.stds.androidsteganography.components.common.ImagePickerView
import jp.ac.it_college.stds.androidsteganography.ui.theme.AndroidSteganographyTheme

@Composable
fun StartScene(modifier: Modifier = Modifier, onEncryptClick: (Bitmap, Int) -> Unit = { _, _ -> }, onDecryptClick: (Bitmap, Int) -> Unit = { _, _ -> })  {
    var bm by remember { mutableStateOf(Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)) }
    val initialBitmap by remember { mutableStateOf(Bitmap.createBitmap(3500, 2200, Bitmap.Config.ARGB_8888)) }
    var selectNum: Int by remember { mutableIntStateOf(0) }
    var bool: Boolean by remember { mutableStateOf(false) }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
    ) {
        OutlinedCard(
            modifier = Modifier
                .width(350.dp)
                .height(220.dp)
                .align(Alignment.CenterHorizontally),

            ) {
            Box(modifier.fillMaxSize()){
                Text(text = "画像選択", modifier.align(Alignment.Center))
                ImagePickerView(
                    modifier = Modifier.align(Alignment.Center),
                    initialBitmap = initialBitmap,
                    context = LocalContext.current,
                    onBitmapChange = {bm = it}
                )
            }
        }

        Text(text = "保存できる推定容量: ${bm.width*bm.height*3/8} byte", modifier.align(Alignment.CenterHorizontally))

        selectNum = demoExposedDropdownMenuBox() {
            bool = it
        }

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth(1f),
        ) {
            Button(
                onClick = { onEncryptClick(bm, selectNum) },
                enabled = bm.width + bm.height > 2 && bool,
            ) {
                Text("暗号化")
            }
            Spacer(modifier = Modifier.width(30.dp))
            Button(
                onClick = { onDecryptClick(bm, selectNum) },
                enabled = bm.width + bm.height > 2 && bool,
            ) {
                Text("復号化")
            }
        }

    }
}


@Preview(showBackground = true)
@Composable
fun StartScenePreview() {
    AndroidSteganographyTheme {
        StartScene(Modifier)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun demoExposedDropdownMenuBox(onSelectionChange: (Boolean) -> Unit): Int {
    val context = LocalContext.current
    val selectPattern = arrayOf("選択１", "選択２", "選択３")
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("方式") }
    var selectIndex: Int by remember { mutableIntStateOf(0) }
    var bool: Boolean by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp)
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            TextField(
                value = selectedText,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                selectPattern.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item) },
                        onClick = {
                            selectedText = item
                            expanded = false
                            onSelectionChange(!bool)
                            Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
                        }
                    )
                }
                selectIndex = selectPattern.indexOf(selectedText)
            }
        }
    }
    return selectIndex
}

