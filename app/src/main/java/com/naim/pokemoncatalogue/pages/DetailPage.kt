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
import androidx.compose.material3.CardColors
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.naim.pokemoncatalogue.R
import com.naim.pokemoncatalogue.core.local.DBHelper
import com.naim.pokemoncatalogue.data.OnGetDetailPokemonCallback
import com.naim.pokemoncatalogue.data.datasource.PokemonLocalDatasource
import com.naim.pokemoncatalogue.data.models.PokemonDetailResponse
import com.naim.pokemoncatalogue.pages.state.PokemonDetailState

import com.naim.pokemoncatalogue.pages.state.PokemonStateStatus

class DetailPage : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokemonCatalogueTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    val name = intent.getStringExtra(POKEMON_ID_EXTRA)
                    DetailContent(name!!)
                }
            }
        }
    }

    companion object {
        const val POKEMON_ID_EXTRA = "pokemonIdExtra"
    }
}

@Composable
fun DetailContent(name: String) {
    var pokemonDetailState by remember { mutableStateOf(PokemonDetailState()) }
    val repository = PokemonRepository(PokemonRemoteDatasource(), PokemonLocalDatasource())
    LaunchedEffect(Unit) {
        pokemonDetailState = pokemonDetailState.copy(status = PokemonStateStatus.LOADING)
        repository
            .getPokemonDetail(name, object : OnGetDetailPokemonCallback {
                override fun onGetDetailPokemonCallback(
                    pokemonDetailResponse: PokemonDetailResponse?
                ) {
                    pokemonDetailState = pokemonDetailState.copy(
                        value = pokemonDetailResponse,
                        status = PokemonStateStatus.SUCCESS
                    )
                }
            })
    }
    val pokemonDetail = pokemonDetailState.value
    Column {
        Card(
            modifier = Modifier.padding(all = 16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            )
        ) {
            if (pokemonDetailState.status == PokemonStateStatus.SUCCESS &&
                pokemonDetail?.sprites?.frontDefault != null
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Box(
                        modifier = Modifier.weight(1f, true)
                    ) {
                        AsyncImage(
                            model = pokemonDetail.sprites.frontDefault,
                            contentDescription = stringResource(
                                id = R.string.pokemon_detail_sprite
                            ),
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    Column(
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp)
                            .weight(1f, true),
                    ) {
                        Text(
                            text = pokemonDetail.name?.uppercase() ?: "-",
                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 24.sp
                            )
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Abilities",
                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 24.sp
                            )
                        )
                        pokemonDetail.abilities?.forEach {
                            Text(text = ("- " + it?.ability?.name) ?: "")
                        }
                    }
                }
            } else if (pokemonDetailState.status == PokemonStateStatus.LOADING) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}