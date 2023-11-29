package com.example.roomcrud.ui.screens.users

import android.util.Log
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.roomcrud.R
import com.example.roomcrud.data.model.User
import com.example.roomcrud.ui.AppViewModelProvider
import com.example.roomcrud.ui.components.DefaultTopAppBar
import com.example.roomcrud.ui.navigation.NavigationDestination
import com.example.roomcrud.ui.theme.RoomCRUDTheme
import com.example.roomcrud.ui.viewmodel.users.UserDetailsViewModel
import com.example.roomcrud.ui.viewmodel.users.toUser
import kotlinx.coroutines.launch

object UserDetailsDestination : NavigationDestination {
    override val route = "user_details"
    override val titleRes = R.string.user_detail_title
    const val userIdArg = "userId"
    val routeWithArgs = "$route/{$userIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    navigateToEditUser: (Int) -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UserDetailsViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState = viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    val user = uiState.value.userDetails.toUser()

    Scaffold(
        topBar = {
            DefaultTopAppBar(
                title = user.email,
                canNavigateBack = true,
                navigateUp = navigateBack,
                actions = {
                    IconButton(onClick = { navigateToEditUser(uiState.value.userDetails.id) }) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = stringResource(R.string.edit_user_title)
                        )
                    }
                    IconButton(
                        onClick = {
                            coroutineScope.launch {
                                try {
                                    viewModel.deleteUser()
                                    navigateBack()
                                } catch (e: Exception) {
                                    Log.e("RoomCRUD", "exception", e)
                                    Toast.makeText(
                                        context,
                                        "An unknown error occurred",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = stringResource(id = R.string.delete),
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }

            )
        }, modifier = modifier
    ) { innerPadding ->
        DetailsBody(
            user = user,
            modifier = Modifier.padding(innerPadding),
            onDelete = {
                coroutineScope.launch {
                    try {
                        viewModel.deleteUser()
                        navigateBack()
                    } catch (e: Exception) {
                        Log.e("RoomCRUD", "exception", e)
                        Toast.makeText(context, "An unknown error occurred", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        )
    }
}

@Composable
private fun DetailsBody(
    user: User,
    modifier: Modifier = Modifier,
    onDelete: () -> Unit,
) {
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }
        Details(
            user = user,
            modifier = Modifier.fillMaxWidth()
        )
        if (deleteConfirmationRequired) {
            DeleteConfirmationDialog(
                onDeleteConfirm = {
                    deleteConfirmationRequired = false
                    onDelete()
                },
                onDeleteCancel = { deleteConfirmationRequired = false },
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}


@Composable
fun Details(
    user: User,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier, colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            DetailsRow(
                labelResID = R.string.email,
                incidentDetail = user.email,
                modifier = Modifier.padding(
                    horizontal = 16.dp
                )
            )
            DetailsRow(
                labelResID = R.string.first_name,
                incidentDetail = user.firstName,
                modifier = Modifier.padding(
                    horizontal = 16.dp
                )
            )
            DetailsRow(
                labelResID = R.string.last_name,
                incidentDetail = user.lastName,
                modifier = Modifier.padding(
                    horizontal = 16.dp
                )
            )
        }
    }
}

@Composable
private fun DetailsRow(
    @StringRes labelResID: Int, incidentDetail: String, modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        Text(text = stringResource(labelResID))
        Spacer(modifier = Modifier.weight(1f))
        Text(text = incidentDetail, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit, onDeleteCancel: () -> Unit, modifier: Modifier = Modifier
) {
    AlertDialog(onDismissRequest = { /* Do nothing */ },
        title = { Text(stringResource(R.string.attention)) },
        text = { Text(stringResource(R.string.delete_question)) },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(text = stringResource(R.string.no))
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(text = stringResource(R.string.yes))
            }
        })
}

@Preview
@Composable
fun DetailsScreenPreview() {
    RoomCRUDTheme {
        DetailsScreen(
            navigateToEditUser = {},
            navigateBack = {}
        )
    }
}