package com.example.roomcrud.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.roomcrud.R
import com.example.roomcrud.ui.components.DefaultTopAppBar
import com.example.roomcrud.ui.navigation.NavigationDestination
import com.example.roomcrud.ui.screens.users.UserListDestination
import com.example.roomcrud.ui.theme.RoomCRUDTheme

object DashboardDestination : NavigationDestination {
    override val route = "dashboard"
    override val titleRes = R.string.app_name
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    navController: NavController? = null
) {
    Scaffold(
        topBar = {
            DefaultTopAppBar(
                title = stringResource(id = R.string.dashboard),
                canNavigateBack = false
            )
        },
        content = { contentPadding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(align = Alignment.CenterHorizontally)
                    .widthIn(max = 840.dp),
                contentPadding = contentPadding
            ) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(4.dp),
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Text(
                            text = "Welcome!",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(8.dp)
                        )
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            CenteredCard(
                                text = stringResource(R.string.users),
                                onClick = {
                                    navController?.navigate(UserListDestination.route)
                                },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun CenteredCard(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier
) {
    Column(
        modifier = modifier
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            ),
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .height(96.dp)
                .clickable {
                    onClick()
                }
        ) {
            Text(
                text = text,
                modifier = Modifier.padding(8.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
fun DashboardScreenPreview() {
    RoomCRUDTheme {
        DashboardScreen()
    }
}