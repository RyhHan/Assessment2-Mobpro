package com.andimuhammadraihansyamsu607062330113.assessment2.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.andimuhammadraihansyamsu607062330113.assessment2.R
import com.andimuhammadraihansyamsu607062330113.assessment2.database.RecipeDb
import com.andimuhammadraihansyamsu607062330113.assessment2.model.Recipe
import com.andimuhammadraihansyamsu607062330113.assessment2.util.ViewModelFactory
import androidx.compose.material3.Divider as HorizontalDivider

// Import tetap seperti sebelumnya

@Suppress("DEPRECATION")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecycleScreen(navController: NavHostController) {
    val context = LocalContext.current
    val db = RecipeDb.getInstance(context)
    val factory = ViewModelFactory(db.dao)
    val viewModel: MainViewModel = viewModel(factory = factory)
    val deletedData by viewModel.dataSampah.collectAsState(emptyList())

    val (showDialog, setShowDialog) = remember { mutableStateOf(false) }
    val (selectedItem, setSelectedItem) = remember { mutableStateOf<Recipe?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.judul_tempat_sampah),
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.kembali),
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { padding ->

        if (deletedData.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.tidak_ada_data_sampah),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(bottom = 84.dp)
            ) {
                items(deletedData) { item ->
                    DeletedListItem(recipe = item, onDelete = {
                        setSelectedItem(item)
                        setShowDialog(true)
                    })
                    HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))
                }
            }
        }

        if (showDialog && selectedItem != null) {
            AlertDialog(
                onDismissRequest = { setShowDialog(false) },
                title = {
                    Text(
                        text = stringResource(R.string.judul_konfirmasi_hapus_permanen),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                },
                text = {
                    Text(
                        text = stringResource(R.string.konfirmasi_hapus_permanen, selectedItem.nama),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                },
                confirmButton = {
                    Button(onClick = {
                        selectedItem.let {
                            viewModel.deletePermanent(it)
                        }
                        setShowDialog(false)
                    }) {
                        Text(
                            text = stringResource(R.string.hapus),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                dismissButton = {
                    Button(onClick = { setShowDialog(false) }) {
                        Text(
                            text = stringResource(R.string.tombol_batal),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 6.dp,
                properties = DialogProperties(usePlatformDefaultWidth = false)
            )
        }
    }
}

@Composable
fun DeletedListItem(recipe: Recipe, onDelete: () -> Unit) {
    val context = LocalContext.current
    val db = RecipeDb.getInstance(context)
    val factory = ViewModelFactory(db.dao)
    val viewModel: MainViewModel = viewModel(factory = factory)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = recipe.nama,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = recipe.deskripsi,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = recipe.kategori,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.secondary
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onDelete) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_delete_forever_24),
                    contentDescription = stringResource(R.string.hapus_permanen),
                    tint = MaterialTheme.colorScheme.error
                )
            }
            IconButton(onClick = { viewModel.restore(id = recipe.id) }) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_restore_24),
                    contentDescription = stringResource(R.string.restore),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

