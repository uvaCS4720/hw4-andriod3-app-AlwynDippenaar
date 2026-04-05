package edu.nd.pmcburne.hello

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.room.Room
import edu.nd.pmcburne.hello.data.local.AppDatabase
import edu.nd.pmcburne.hello.data.repository.LocationRepository
import edu.nd.pmcburne.hello.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "uva-db"
        ).build()

        val repo = LocationRepository(db.locationDao())
        val viewModel = MainViewModel(repo)

        setContent {
            MyApplicationTheme {
                MainScreen(viewModel)
            }
        }
    }
}