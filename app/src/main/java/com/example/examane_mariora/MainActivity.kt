package com.example.examane_mariora

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.Start
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.examane_mariora.data.DataSource
import com.example.examane_mariora.data.DataSource.loterias
import com.example.examane_mariora.data.LoteriaTipo
import com.example.examane_mariora.ui.theme.Examane_MarioRATheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Examane_MarioRATheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppExamne()
                }
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppExamne(modifier: Modifier = Modifier) {
    var dinero by remember { mutableStateOf("0") }
    var loteria by remember { mutableStateOf("Loteria") }
    var dineroApostado by remember { mutableStateOf(0) }
    var mensajeResultado by remember { mutableStateOf("No has jugado ninguna loteria") }
    var numeroLoteria by remember { mutableStateOf(0) }
    var numeroTicketGanador by remember { mutableStateOf(0) }
    var vecesJugadas by remember { mutableStateOf(0) }
    var totalGanado by remember { mutableStateOf(0) }
    var totalGastado by remember { mutableStateOf(0) }
    Column(modifier = Modifier) {
        Text(
            text = "Bienvenido a apuestas MRA",
            Modifier
                .align(Start)
                .background(color = Color.LightGray)
                .padding(top = 50.dp, start = 20.dp)
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(1.dp))
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            items(loterias) { loteria ->
                Caja(modifier = Modifier.padding(8.dp), loteriaTipo = loteria)
            }
        }
        Spacer(modifier = Modifier)
        Row {
            TextField(
                value = loteria,
                onValueChange = {loteria = it},
                modifier = modifier.padding(5.dp)
            )
            Spacer(modifier = Modifier)
            TextField(
                value = dinero,
                onValueChange = {dinero = it
                    dineroApostado = it.toIntOrNull() ?: 0},
                modifier = modifier.padding(5.dp),
                label = { Text(text = "Dinero Apostado")}
            )
        }
        Button(onClick = {
            if (dineroApostado>0){
            if (loterias.any { it.nombre == loteria }) {
                vecesJugadas++
                numeroLoteria = (1..4).random()
                numeroTicketGanador = (1..4).random()
                if (numeroLoteria == numeroTicketGanador) {
                    val premio = loterias.first { it.nombre == loteria }.premio
                    dineroApostado = dinero.toIntOrNull() ?: 0
                    totalGanado += premio * dineroApostado
                    mensajeResultado = "Has ganado la lotería"
                } else {
                    mensajeResultado = "Has perdido a la lotería"
                }
                totalGastado += dinero.toIntOrNull() ?: 0
            } else {
                mensajeResultado = "No existe ninguna lotería con ese nombre"
            } }}, modifier = Modifier.align(CenterHorizontally)) {
            Text(text = "Jugar loteria escrita")

        }
        Column(
            modifier = Modifier
                .background(color = Color.LightGray)
                .align(CenterHorizontally)
                .padding(50.dp)
                .fillMaxWidth()
        ) {
            Text(text = mensajeResultado)
            Text(text = "")
            Text(text = "Has jugado $vecesJugadas veces en loteria")
            Text(text = "Has gastado $totalGastado euros en loteria")
            Text(text = "Has ganado $totalGanado euros en loteria")
        }
        Button(onClick = { /*TODO*/ }, modifier = Modifier.align(CenterHorizontally)) {
            Text(text = "Cambiar de pantalla")
        }

    }
}
    
    
@Composable
fun Caja(modifier: Modifier = Modifier, loteriaTipo:LoteriaTipo) {
    Box(
        modifier = Modifier
            .padding(25.dp)
    ) {
        Column(modifier = Modifier.align(Center)) {

            Text(
                text = "Nombre:"+loteriaTipo.nombre,
                Modifier
                    .align(Start)
                    .background(color = Color.Yellow)
                    .padding(20.dp)
            )

            Text(
                text = "Premio: "+loteriaTipo.premio,
                Modifier
                    .align(Start)
                    .background(color = Color.Cyan)
                    .padding(20.dp)
            )
            Button(onClick = { /*TODO*/ }, modifier = Modifier.align(CenterHorizontally)) {
                Text(text = "Apostar")
            }
        }
    }

}

@Preview
@Composable
fun Ver() {
    AppExamne()
}