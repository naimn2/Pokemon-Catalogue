package com.naim.pokemoncatalogue.pages

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.naim.pokemoncatalogue.data.OnGetPokemonCallback
import com.naim.pokemoncatalogue.data.datasource.PokemonRemoteDatasource
import com.naim.pokemoncatalogue.data.models.PokemonResponse
import com.naim.pokemoncatalogue.data.repository.PokemonRepository
import com.naim.pokemoncatalogue.ui.theme.PokemonCatalogueTheme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.naim.pokemoncatalogue.R
import com.naim.pokemoncatalogue.core.local.DBHelper
import com.naim.pokemoncatalogue.data.datasource.PokemonLocalDatasource
import com.naim.pokemoncatalogue.pages.state.PokemonState
import com.naim.pokemoncatalogue.pages.state.PokemonStateStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomePage : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokemonCatalogueTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    HomeContent()
                }
            }
        }
    }
}

@Composable
fun HomeContent() {
    val context = LocalContext.current
    var pokemonState by remember { mutableStateOf<PokemonState>(PokemonState()) }
    val repository = PokemonRepository(PokemonRemoteDatasource(), PokemonLocalDatasource())
    val onGetPokemonCallback = object : OnGetPokemonCallback {
        override fun onGetPokemonCallback(pokemonResponse: PokemonResponse?) {
            pokemonState = pokemonState.copy(
                value = pokemonResponse, status = PokemonStateStatus.SUCCESS
            )
        }
    }
    val dbHelper = DBHelper(LocalContext.current, null)
    LaunchedEffect(Unit) {
        pokemonState = pokemonState.copy(status = PokemonStateStatus.LOADING)
        repository.getPokemon(dbHelper, onGetPokemonCallback)
    }
    Log.d("HomeContent", "HomeContent: ${pokemonState.status}")
    val pokemonResponse = pokemonState.value
    if (pokemonState.status == PokemonStateStatus.SUCCESS) {
        if (pokemonResponse?.results != null && pokemonResponse.results.isNotEmpty()) {
            Column {
                LazyColumn(
                    userScrollEnabled = true, modifier = Modifier.weight(weight = 1f, fill = true)
                ) {
                    items(pokemonResponse.results.size) { index ->
                        val pokemon = pokemonResponse.results[index]
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White
                            ),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 6.dp
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                                .clickable {
                                    val intent = Intent(context, DetailPage::class.java).apply {
                                        putExtra(
                                            DetailPage.POKEMON_ID_EXTRA, pokemon?.name
                                        )
                                    }
                                    context.startActivity(intent)
                                }) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(
                                    vertical = 12.dp, horizontal = 16.dp
                                ),
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_pokemon_ball),
                                    contentDescription = stringResource(id = R.string.ic_pokemon_content_desc),
                                    modifier = Modifier.size(28.dp)
                                )
                                Spacer(modifier = Modifier.width(16.dp))
                                Text(
                                    text = pokemon?.name ?: "",
                                )
                            }
                        }
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    TextButton(enabled = pokemonResponse.previous != null, onClick = {
                        CoroutineScope(Dispatchers.Default).launch {
                            pokemonState = pokemonState.copy(status = PokemonStateStatus.LOADING)
                            repository.getPokemon(
                                dbHelper, onGetPokemonCallback, url = pokemonResponse.previous
                            )
                        }
                    }) {
                        Text(text = "Prev")
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    TextButton(enabled = pokemonResponse.next != null, onClick = {
                        CoroutineScope(Dispatchers.Default).launch {
                            pokemonState = pokemonState.copy(status = PokemonStateStatus.LOADING)
                            repository.getPokemon(
                                dbHelper, onGetPokemonCallback, url = pokemonResponse.next
                            )
                        }
                    }) {
                        Text(text = "Next")
                    }
                }
            }
        } else {
            Text(text = "Empty Data")
        }
    } else if (pokemonState.status == PokemonStateStatus.LOADING) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator()
        }
    } else if (pokemonState.status == PokemonStateStatus.FAILED) {
        Text(text = "Failed")
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    PokemonCatalogueTheme {
        HomeContent()
    }
}