package com.andimuhammadraihansyamsu607062330113.assessment2.ui.screen

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.andimuhammadraihansyamsu607062330113.assessment2.R
import com.andimuhammadraihansyamsu607062330113.assessment2.database.RecipeDb
import com.andimuhammadraihansyamsu607062330113.assessment2.ui.theme.Assessment2Theme
import com.andimuhammadraihansyamsu607062330113.assessment2.util.ViewModelFactory

const val KEY_ID_MAHASISWA = "id_mahasiswa"

@SuppressLint("MutableCollectionMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavController, id: Long? = null) {
    val context = LocalContext.current
    val db = RecipeDb.getInstance(context)
    val factory = ViewModelFactory(db.dao)
    val viewModel: DetailViewModel = viewModel(factory = factory)

    var nama by remember { mutableStateOf("") }
    var deskripsi by remember { mutableStateOf("") }
    var bahan by remember { mutableStateOf("") }
    var kelas by remember { mutableStateOf("Makanan Berat") }
    var showDialog by remember { mutableStateOf(false) }
    var bahanList by remember { mutableStateOf<List<String>>(emptyList()) }

    LaunchedEffect(Unit) {
        if (id == null) return@LaunchedEffect
        val data = viewModel.getMahasiswa(id) ?: return@LaunchedEffect
        nama = data.nama
        deskripsi = data.deskripsi
        bahan = data.bahan
        kelas = data.kategori.ifEmpty { "Makanan Berat" }
        bahanList = data.bahan.split(", ").toMutableList()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.kembali),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                title = {
                    Text(text = if (id == null)
                        stringResource(id = R.string.tambah_mahasiswa)
                    else
                        stringResource(id = R.string.edit_mahasiswa))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                actions = {
                    IconButton(onClick = {
                        if (deskripsi.isBlank() || nama.isBlank()) {
                            Toast.makeText(context, R.string.invalid, Toast.LENGTH_LONG).show()
                            return@IconButton
                        }
                        val bahanGabung = bahanList.joinToString(", ")
                        if (id == null) {
                            viewModel.insert(nama, deskripsi, kelas, bahanGabung)
                        } else {
                            viewModel.update(id, nama, deskripsi, kelas, bahanGabung)
                        }
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Check,
                            contentDescription = stringResource(R.string.simpan),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    if (id != null) {
                        DeleteAction {
                            showDialog = true
                        }
                    }
                }
            )
        }
    ) { padding ->
        FormRecipe(
            name = nama,
            onNameChange = { nama = it },
            deskripsi = deskripsi,
            onDeskripsiChange = { deskripsi = it },
            kelas = kelas,
            onKelasChange = { kelas = it },
            bahanList = bahanList,
            onAddBahan = {
                if (it.isNotBlank()) {
                    bahanList = bahanList + it
                }
            },
            onRemoveBahan = {
                bahanList = bahanList - it
            },
            modifier = Modifier.padding(padding)
        )

        if (id != null && showDialog) {
            DisplayAlertDialog(
                onDismissRequest = { showDialog = false }) {
                showDialog = false
                viewModel.sampah(id)
                navController.popBackStack()
            }
        }
    }
}

@Composable
fun DeleteAction(delete: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    IconButton(onClick = { expanded = true }) {
        Icon(
            imageVector = Icons.Filled.MoreVert,
            contentDescription = stringResource(R.string.lainnya),
            tint = MaterialTheme.colorScheme.primary
        )
    }
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
    ) {
        DropdownMenuItem(
            text = {
                Text(text = stringResource(id = R.string.hapus))
            },
            onClick = {
                expanded = false
                delete()
            }
        )
    }
}

@Composable
fun FormRecipe(
    name: String, onNameChange: (String) -> Unit,
    deskripsi: String, onDeskripsiChange: (String) -> Unit,
    kelas: String, onKelasChange: (String) -> Unit,
    bahanList: List<String>, onAddBahan: (String) -> Unit, onRemoveBahan: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var currentBahan by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()) // Tambah scroll
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = onNameChange,
            label = { Text(stringResource(id = R.string.nama_recipe)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = deskripsi,
            onValueChange = onDeskripsiChange,
            label = { Text(stringResource(id = R.string.deskripsi_recipe)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )

        val radioOptions = listOf("Makanan Berat", "Makanan Ringan", "Minuman", "Kue", "Snack")
        OutlinedCard(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(stringResource(id = R.string.kategori_recipe))
                radioOptions.forEach { option ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = kelas == option,
                            onClick = { onKelasChange(option) }
                        )
                        Text(option, modifier = Modifier.padding(start = 8.dp))
                    }
                }
            }
        }

        OutlinedTextField(
            value = currentBahan,
            onValueChange = {
                currentBahan = it
                showError = false
            },
            isError = showError,
            label = { Text("Tambah Bahan (pisah dengan koma)") },
            modifier = Modifier.fillMaxWidth(),
            supportingText = {
                if (showError) Text("Bahan tidak boleh kosong", color = MaterialTheme.colorScheme.error)
            }
        )

        Button(
            onClick = {
                if (currentBahan.isNotBlank()) {
                    currentBahan.split(",").map { it.trim() }.filter { it.isNotEmpty() }.forEach {
                        onAddBahan(it)
                    }
                    currentBahan = ""
                } else {
                    showError = true
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Tambah")
        }

        if (bahanList.isNotEmpty()) {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text("Daftar Bahan:")
                bahanList.forEach { bahan ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                MaterialTheme.colorScheme.primaryContainer,
                                shape = MaterialTheme.shapes.extraLarge
                            )
                            .padding(horizontal = 20.dp, vertical = 8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(text = bahan)
                            IconButton(onClick = {
                                onRemoveBahan(bahan)
                                currentBahan = ""
                            }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.baseline_delete_forever_24),
                                    contentDescription = "Hapus bahan",
                                    tint = MaterialTheme.colorScheme.error
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun DetailScreenPreview() {
    Assessment2Theme {
        DetailScreen(rememberNavController())
    }
}