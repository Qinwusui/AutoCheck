package zyx.and.dyj.autocheck

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import zyx.and.dyj.autocheck.theme.AutoCheckTheme
import zyx.and.dyj.autocheck.ui.mainScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AutoCheckTheme {
                // A surface container using the 'background' color from the theme
                mainScreen()
            }
        }
    }
}
