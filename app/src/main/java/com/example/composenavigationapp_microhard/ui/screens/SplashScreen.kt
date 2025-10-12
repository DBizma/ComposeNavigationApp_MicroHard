package com.example.composenavigationapp_microhard.ui.screens


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onFinished: () -> Unit,
    delayMillis: Long = 2000L
) {
    // LaunchedEffect(Unit) digunakan untuk menjalankan kode suspend (seperti delay)
    // hanya satu kali saat composable pertama kali ditampilkan.
    LaunchedEffect(Unit) {
        delay(delayMillis) // Menunggu selama 2 detik
        onFinished()       // Memanggil fungsi callback untuk trigger navigasi
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center // Memposisikan konten di tengah Box
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Compose Navigation App",
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(Modifier.height(16.dp))
            CircularProgressIndicator() // Menampilkan ikon loading
        }
    }
}

// @Preview digunakan untuk melihat tampilan composable di Android Studio
// tanpa harus menjalankan aplikasi di emulator.
@Preview(showBackground = true)
@Composable
private fun SplashPreview() {
    SplashScreen(onFinished = {}) // onFinished diberi fungsi kosong untuk preview
}