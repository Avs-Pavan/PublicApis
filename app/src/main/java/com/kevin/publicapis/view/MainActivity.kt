package com.kevin.publicapis.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kevin.publicapis.model.Entry
import com.kevin.publicapis.view.ui.theme.PublicApisTheme
import com.kevin.publicapis.viewmodel.PublicApisViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PublicApisTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PublicApiScreen()
                }
            }
        }
    }
}

@Composable
fun PublicApiScreen(publicApisViewModel: PublicApisViewModel = hiltViewModel()) {

    val data = publicApisViewModel.entries.observeAsState()
    val loading = publicApisViewModel.loading.observeAsState()
    val error = publicApisViewModel.error.observeAsState()

    data.value?.let {
        NewsList(data = it)
    }

    loading.value?.let { LoadingIndicator(isLoading = it) }

    error?.value?.let { message ->
        Toast.makeText(LocalContext.current, message, Toast.LENGTH_LONG).show()
    }
    publicApisViewModel.getEntries()

}


@Composable
fun NewsList(data: List<Entry>) {
    LazyColumn {
        items(data) {
            EntryRow(entry = it)
        }
    }
}

@Composable
fun EntryRow(entry: Entry) {
    Box(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .background(Color.Cyan, RoundedCornerShape(10.dp))
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            Text(text = entry.API, fontWeight = FontWeight.Bold)
            Text(text = entry.Description, fontStyle = FontStyle.Italic)

        }
    }

}

@Composable
fun LoadingIndicator(isLoading: Boolean) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(color = Color.Gray.copy(alpha = 0.1f))
                    .padding(4.dp)
            )
        }
    }
}