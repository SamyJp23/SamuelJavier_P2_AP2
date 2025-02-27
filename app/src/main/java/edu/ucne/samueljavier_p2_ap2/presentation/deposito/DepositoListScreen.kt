package edu.ucne.samueljavier_p2_ap2.presentation.deposito

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.samueljavier_p2_ap2.data.remote.dto.DepositoDto
import edu.ucne.samueljavier_p2_ap2.presentation.home.verde

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DepositoListScreen(
    viewModel: DepositoViewModel = hiltViewModel(),
    createDeposito: () -> Unit,
    goToMenu: () -> Unit,
    goToDeposito: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = { TopAppBar(
            title = { Text("Lista de Depositos") },
            actions = {
                IconButton(onClick = { viewModel.refreshDepositos() }) {
                    Icon(imageVector = Icons.Filled.Refresh, contentDescription = "Refrescar")
                }
            }
        )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { createDeposito() },
                containerColor = Color.Blue,
                contentColor = Color.White
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Crear Deposito"
                )
            }

        },

        ) { innerPadding ->
        DepositoListBodyScreen(
            uiState = uiState,
            createDeposito = createDeposito,
            goToMenu = goToMenu,
            goToDeposito = goToDeposito,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun DepositoListBodyScreen(
    uiState: DepositoUiState,
    createDeposito: () -> Unit,
    goToMenu: () -> Unit,
    goToDeposito: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {

                items(uiState.depositos) {
                    DepositoRow(it, goToDeposito)
                }
            }
        }
    }
}



@Composable
private fun DepositoRow(
    it: DepositoDto,
    goToDeposito: (Int) -> Unit
) {

    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .clickable { goToDeposito(it.idDeposito) },
        colors = CardDefaults.cardColors(containerColor = verde),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(verde),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(5f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "DepositoId: ${it.idDeposito}",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
                Text(
                    text = "Concepto: ${it.concepto}",
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = Color.White
                    )
                )
                Text(
                    text = "Monto: ${it.monto}",
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = Color.White
                    )
                )

                Text(
                    text = "IdCuenta: ${it.idCuenta}",
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = Color.White
                    )
                )
                Text(
                    text = "Fecha: ${it.fecha}",
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = Color.White
                    )
                )


            }
        }
    }
}



