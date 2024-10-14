package com.mohsenmashkour.multilanguageappyt

import android.app.Activity
import android.app.LocaleManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.LocaleList
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.LocaleListCompat
import com.mohsenmashkour.multilanguageappyt.ui.theme.MultiLanguageAppYtTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val languageCode = getSavedLanguage(this) ?: "en"
        setLanguage(this, languageCode)

        //enableEdgeToEdge()
        setContent {
            MultiLanguageAppYtTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = stringResource(id = R.string.greeting),
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
    private fun getSavedLanguage(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences("language_prefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("language", null)
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    LazyColumn(Modifier.padding(16.dp)) {
        item {
            Text(
                text = stringResource(id = R.string.title),
                modifier = modifier,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
            HorizontalDivider(Modifier.fillMaxWidth())
        }
        items(10){
            Text(
                text = name,
                modifier = modifier,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        }
        item{
            HorizontalDivider(Modifier.fillMaxWidth())
            LanguageSwitcher()
        }
    }

}

@Composable
fun LanguageSwitcher(){
    val context = LocalContext.current
    Column {
        Button(onClick = {
            setLanguage(context, "en")

        }) {
            Text("Switch to English")
        }
        Button(onClick = {
            setLanguage(context, "es")
            (context as? Activity)?.recreate()
        }) {
            Text("Switch to Spanish")
        }
        Button(onClick = {
            setLanguage(context, "fa")
            (context as? Activity)?.recreate()
        }) {
            Text("Switch to Farsi")
        }
    }
}

fun setLanguage(context: Context, languageCode: String) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        context.getSystemService(LocaleManager::class.java)
            .applicationLocales = LocaleList.forLanguageTags(languageCode)
    }else{
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(languageCode))
    }
saveLanguagePreference(context, languageCode)
}

fun saveLanguagePreference(context: Context, languageCode: String) {
    val pref = context.getSharedPreferences("language_prefs", Context.MODE_PRIVATE)
    pref.edit().putString("language", languageCode).apply()
}
