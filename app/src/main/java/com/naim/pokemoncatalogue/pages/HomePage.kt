package com.naim.pokemoncatalogue.pages

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
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
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HomeContent()
                }
            }
        }
    }
}

@Composable
fun HomeContent() {
    var pokemonState by remember { mutableStateOf<PokemonState>(PokemonState()) }
    val repository = PokemonRepository(PokemonRemoteDatasource(), PokemonLocalDatasource())
    val onGetPokemonCallback = object : OnGetPokemonCallback {
        override fun onGetPokemonCallback(pokemonResponse: PokemonResponse?) {
            pokemonState =
                pokemonState.copy(
                    value = pokemonResponse,
                    status = PokemonStateStatus.SUCCESS
                )
        }
    }
    val dbHelper = DBHelper(LocalContext.current, null)
    LaunchedEffect(Unit) {
        pokemonState = pokemonState.copy(status = PokemonStateStatus.LOADING)
        repository
            .getPokemon(dbHelper, onGetPokemonCallback)
    }
    Log.d("HomeContent", "HomeContent: ${pokemonState.status}")
    val pokemonResponse = pokemonState.value
    if (pokemonState.status == PokemonStateStatus.SUCCESS) {
        if (pokemonResponse?.results != null && pokemonResponse.results.isNotEmpty()) {
            LazyColumn(userScrollEnabled = true) {
                items(pokemonResponse.results.size) { index ->
                    Text(text = pokemonResponse.results[index]?.name ?: "")
                }
                item {
                    Row {
                        TextButton(
                            enabled = pokemonResponse.previous != null,
                            onClick = {
                                CoroutineScope(Dispatchers.Default).launch {
                                    pokemonState =
                                        pokemonState.copy(status = PokemonStateStatus.LOADING)
                                    repository.getPokemon(
                                        dbHelper,
                                        onGetPokemonCallback,
                                        url = pokemonResponse.previous
                                    )
                                }
                            }
                        ) {
                            Text(text = "Prev")
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        TextButton(
                            enabled = pokemonResponse.next != null,
                            onClick = {
                                CoroutineScope(Dispatchers.Default).launch {
                                    pokemonState =
                                        pokemonState.copy(status = PokemonStateStatus.LOADING)
                                    repository.getPokemon(
                                        dbHelper,
                                        onGetPokemonCallback,
                                        url = pokemonResponse.next
                                    )
                                }
                            }) {
                            Text(text = "Next")
                        }
                    }
                }
            }
        } else {
            Text(text = "Empty Data")
        }
    } else if (pokemonState.status == PokemonStateStatus.LOADING) {
        Text(text = "Loading...")
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