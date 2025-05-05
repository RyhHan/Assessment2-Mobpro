package com.andimuhammadraihansyamsu607062330113.assessment2.ui.screen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.andimuhammadraihansyamsu607062330113.assessment2.R
import com.andimuhammadraihansyamsu607062330113.assessment2.database.RecipeDb
import com.andimuhammadraihansyamsu607062330113.assessment2.util.ViewModelFactory

const val KEY_ID_RECIPE = "id_recipe"

@SuppressLint("MutableCollectionMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditScreen(navController: NavController, id: Long? = null) {
    val context = LocalContext.current
    val db = RecipeDb.getInstance(context)
    val factory = ViewModelFactory(db.dao)
    val viewModel: DetailViewModel = viewModel(factory = factory)

    var nama by remember { mutableStateOf("") }
    var deskripsi by remember { mutableStateOf("") }
    var bahan by remember { mutableStateOf("") }
    var langkah by remember { mutableStateOf("") }
    var kelas by remember { mutableStateOf("Makanan Berat") }
    var bahanList by remember { mutableStateOf<List<String>>(emptyList()) }

    LaunchedEffect(Unit) {
        if (id == null) return@LaunchedEffect
        val data = viewModel.getRecipeById(id) ?: return@LaunchedEffect
        nama = data.nama
        deskripsi = data.deskripsi
        bahan = data.bahan
        langkah = data.langkah
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
                        stringResource(id = R.string.tambah_recipe)
                    else
                        stringResource(id = R.string.edit_recipe))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                actions = {
                    IconButton(onClick = {
                        if (deskripsi.isBlank() || nama.isBlank() || kelas.isBlank() || bahanList.isEmpty() || langkah.isBlank()) {
                            Toast.makeText(context, R.string.invalid, Toast.LENGTH_LONG).show()
                            return@IconButton
                        }
                        val bahanGabung = bahanList.joinToString(", ")
                        if (id == null) {
                            viewModel.insert(nama, deskripsi, kelas,langkah, bahanGabung)
                        } else {
                            viewModel.update(id, nama, deskripsi, kelas,langkah, bahanGabung)
                        }
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Check,
                            contentDescription = stringResource(R.string.simpan),
                            tint = MaterialTheme.colorScheme.primary
                        )
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
            langkah = langkah,
            onLangkahChange = { langkah = it },
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
    }
}

@Composable
fun FormRecipe(
    name: String, onNameChange: (String) -> Unit,
    deskripsi: String, onDeskripsiChange: (String) -> Unit,
    kelas: String, onKelasChange: (String) -> Unit,
    langkah : String,onLangkahChange: (String) -> Unit,
    bahanList: List<String>, onAddBahan: (String) -> Unit, onRemoveBahan: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var currentBahan by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
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

        OutlinedTextField(
            value = langkah,
            onValueChange = onLangkahChange,
            label = { Text(stringResource(id = R.string.langkah_recipe)) },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 5
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
            label = { Text(stringResource( id = R.string.tambah_bahan)) },
            modifier = Modifier.fillMaxWidth(),
            supportingText = {
                if (showError) Text(stringResource(id = R.string.bahan_tidak_kosong), color = MaterialTheme.colorScheme.error)
            }
        )

        Button(
            onClick = {
                if (currentBahan.isNotBlank()) {
                        onAddBahan(currentBahan)
                    currentBahan = ""
                } else {
                    showError = true
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(stringResource(id = R.string.tambah))
        }

        if (bahanList.isNotEmpty()) {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(stringResource(id = R.string.daftar_bahan))
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
                                    contentDescription = stringResource(id = R.string.hapus_bahan),
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



//@Preview(showBackground = true)
//@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
//@Composable
//fun AddEditPreview() {
//    ThemeController {
//        AddEditScreen(rememberNavController())
//    }
//}