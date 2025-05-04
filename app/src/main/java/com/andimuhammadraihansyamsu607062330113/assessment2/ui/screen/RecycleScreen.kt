package com.andimuhammadraihansyamsu607062330113.assessment2.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.andimuhammadraihansyamsu607062330113.assessment2.R
import com.andimuhammadraihansyamsu607062330113.assessment2.database.RecipeDb
import com.andimuhammadraihansyamsu607062330113.assessment2.model.Recipe
import com.andimuhammadraihansyamsu607062330113.assessment2.ui.theme.Assessment2Theme
import com.andimuhammadraihansyamsu607062330113.assessment2.util.ViewModelFactory
import androidx.compose.material3.Divider as HorizontalDivider

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
                title = { Text("Tempat Sampah") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.kembali),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
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
                Text("Tidak ada data di tempat sampah.")
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
                    HorizontalDivider()
                }
            }
        }

        if (showDialog && selectedItem != null) {
            AlertDialog(
                onDismissRequest = { setShowDialog(false) },
                title = { Text("Konfirmasi Hapus Permanen") },
                text = { Text("Apakah Anda yakin ingin menghapus ${selectedItem?.nama} secara permanen?") },
                confirmButton = {
                    Button(
                        onClick = {
                            selectedItem?.let {
                                viewModel.deletePermanent(it)
                            }
                            setShowDialog(false)
                        }
                    ) {
                        Text("Hapus")
                    }
                },
                dismissButton = {
                    Button(onClick = { setShowDialog(false) }) {
                        Text("Batal")
                    }
                },
                properties = DialogProperties(usePlatformDefaultWidth = false)
            )
        }
    }
}

@Composable
fun DeletedListItem(recipe: Recipe, onDelete: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(recipe.nama, fontWeight = FontWeight.Bold)
        Text(recipe.deskripsi)
        Text(recipe.kategori)
        IconButton(onClick = onDelete) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_delete_forever_24),
                contentDescription = "Hapus Permanen",
                tint = MaterialTheme.colorScheme.error
            )
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun ScreenPreview() {
    Assessment2Theme {
        RecycleScreen(rememberNavController())
    }
}
