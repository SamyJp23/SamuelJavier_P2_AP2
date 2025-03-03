package edu.ucne.samueljavier_p2_ap2.presentation.deposito

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.samueljavier_p2_ap2.presentation.home.verde

@Composable
fun DepositoScreen(
    viewModel: DepositoViewModel = hiltViewModel(),
    depositoId: Int,
    goBackToList: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    DepositoBodyScreen(
        depositoId = depositoId,
        viewModel = viewModel,
        uiState = uiState,
        goBackToList = goBackToList
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DepositoBodyScreen(
    depositoId: Int,
    viewModel: DepositoViewModel,
    uiState: DepositoUiState,
    goBackToList: () -> Unit
) {
    LaunchedEffect(depositoId) {
        if (depositoId > 0) viewModel.find(depositoId)
    }

    Scaffold(

    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(8.dp)
                .background(Color.White)
        ) {
            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .background(Color.White)
                ) {


                    OutlinedTextField(
                        label = { Text(text = "IdCuenta") },
                        value = uiState.idCuenta.toString(),
                        onValueChange = { value ->
                            viewModel.onCuentaIdChange(value.toIntOrNull() ?: 0)},
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = LocalTextStyle.current.copy(color = Color.Black),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            disabledTextColor = Color.Black,
                            focusedLabelColor = Color.Black,
                            unfocusedLabelColor = Color.Black,
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = Color.Black
                        )
                    )
                    OutlinedTextField(
                        label = { Text(text = "Concepto") },
                        value = uiState.concepto,
                        onValueChange = viewModel::onConceptoChange,
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = LocalTextStyle.current.copy(color = Color.Black),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            disabledTextColor = Color.Black,
                            focusedLabelColor = Color.Black,
                            unfocusedLabelColor = Color.Black,
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = Color.Black
                        )
                    )

                    OutlinedTextField(
                        label = { Text(text = "Fecha") },
                        value = uiState.fecha,
                        onValueChange = viewModel::onFechaChange,
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = LocalTextStyle.current.copy(color = Color.Black),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            disabledTextColor = Color.Black,
                            focusedLabelColor = Color.Black,
                            unfocusedLabelColor = Color.Black,
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = Color.Black
                        )
                    )

                    OutlinedTextField(
                        label = { Text(text = "Monto") },
                        value = uiState.monto.toString(),
                        onValueChange = { value ->
                            viewModel.onMontoChange(value.toDoubleOrNull() ?: 0.0)},
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = LocalTextStyle.current.copy(color = Color.Black),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            disabledTextColor = Color.Black,
                            focusedLabelColor = Color.Black,
                            unfocusedLabelColor = Color.Black,
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = Color.Black
                        )
                    )
                    Spacer(modifier = Modifier.padding(2.dp))
                    uiState.errorMessage?.let {
                        Text(text = it, color = Color.Red)
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedButton(
                            onClick = { goBackToList() },
                            colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.Blue)
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Go back",
                                tint = Color.White
                            )
                            Text(
                                text = "Atrás",
                                color = Color.White
                            )
                        }

                        OutlinedButton(
                            onClick = {
                                if (viewModel.isValid()) {
                                    viewModel.saveDeposito()
                                    goBackToList()
                                }
                            }, colors = ButtonDefaults.outlinedButtonColors(containerColor = verde)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Save button",
                                tint = Color.White

                            )
                            Text(
                                text = "Guardar",
                                color = Color.White
                            )

                        }
                        OutlinedButton(
                            onClick = {
                                viewModel.delete(depositoId)
                                goBackToList()
                            },
                            colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.Red)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete button",
                                tint = Color.White

                            )
                            Text(
                                text = "Eliminar",
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}