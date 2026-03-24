package com.cloudemployee.baseproject.presentation.screen.users

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cloudemployee.baseproject.domain.model.User
import com.cloudemployee.baseproject.presentation.theme.CloudEmployeeTheme

@Composable
fun UsersScreen(
    onUserClick: (Long) -> Unit,
    viewModel: UsersViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    UsersScreenContent(
        uiState = uiState,
        onSearchQueryChange = viewModel::onSearchQueryChange,
        onUserClick = onUserClick,
        onRefresh = viewModel::refreshUsers,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun UsersScreenContent(
    uiState: UsersUiState,
    onSearchQueryChange: (String) -> Unit,
    onUserClick: (Long) -> Unit,
    onRefresh: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Users") },
            )
        },
    ) { innerPadding ->
        when {
            uiState.isLoading -> LoadingState(modifier = Modifier.padding(innerPadding))
            uiState.users.isEmpty() -> EmptyState(
                modifier = Modifier.padding(innerPadding),
                message = uiState.errorMessage ?: "No users available yet.",
                searchQuery = uiState.searchQuery,
                onSearchQueryChange = onSearchQueryChange,
                onRefresh = onRefresh,
            )

            else -> UsersList(
                modifier = Modifier.padding(innerPadding),
                users = uiState.users,
                isRefreshing = uiState.isRefreshing,
                searchQuery = uiState.searchQuery,
                errorMessage = uiState.errorMessage,
                onSearchQueryChange = onSearchQueryChange,
                onUserClick = onUserClick,
                onRefresh = onRefresh,
            )
        }
    }
}

@Composable
private fun LoadingState(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun EmptyState(
    modifier: Modifier = Modifier,
    message: String,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onRefresh: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = onSearchQueryChange,
                label = { Text(text = "Search name") },
                singleLine = true,
            )
            Text(text = message, style = MaterialTheme.typography.bodyLarge)
            Button(onClick = onRefresh) {
                Text(text = "Retry")
            }
        }
    }
}

@Composable
private fun UsersList(
    modifier: Modifier = Modifier,
    users: List<User>,
    isRefreshing: Boolean,
    searchQuery: String,
    errorMessage: String?,
    onSearchQueryChange: (String) -> Unit,
    onUserClick: (Long) -> Unit,
    onRefresh: () -> Unit,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "Stack Overflow Users",
                        style = MaterialTheme.typography.headlineSmall,
                    )
                    Text(
                        text = errorMessage ?: "Search by display name. Results are cached in Room.",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                OutlinedTextField(
                    modifier = Modifier.weight(1f),
                    value = searchQuery,
                    onValueChange = onSearchQueryChange,
                    label = { Text(text = "Search name") },
                    singleLine = true,
                )
                Button(onClick = onRefresh, enabled = !isRefreshing) {
                    Text(text = if (isRefreshing) "Loading..." else "Search")
                }
            }
        }

        items(
            items = users,
            key = { user -> user.accountId },
        ) { user ->
            UserCard(
                user = user,
                onClick = { onUserClick(user.accountId) },
            )
        }
    }
}

@Composable
private fun UserCard(
    user: User,
    onClick: () -> Unit,
) {
    Card(onClick = onClick) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "${user.accountId}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
            )
            Text(
                text = user.displayName,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun UsersScreenPreview() {
    CloudEmployeeTheme {
        UsersScreenContent(
            uiState = UsersUiState(
                isLoading = false,
                users = listOf(
                    User(
                        accountId = 1L,
                        reputation = 12000,
                        creationDate = 1_600_000_000L,
                        profileImage = "",
                        displayName = "Kevin Example",
                    ),
                ),
            ),
            onSearchQueryChange = {},
            onUserClick = {},
            onRefresh = {},
        )
    }
}
