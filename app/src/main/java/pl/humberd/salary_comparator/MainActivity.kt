package pl.humberd.salary_comparator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import pl.humberd.salary_comparator.ui.theme.SalarycomparatorTheme
import pl.humberd.salary_comparator.ui.views.main.MainForm
import pl.humberd.salary_comparator.ui.views.main.MainResult

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SalarycomparatorTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    MainForm()
                    MainResult(emptyMap())
                }
            }
        }
    }
}
