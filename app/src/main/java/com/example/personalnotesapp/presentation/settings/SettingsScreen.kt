package com.example.personalnotesapp.presentation.settings

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.personalnotesapp.data.model.Note
import com.example.personalnotesapp.domain.usecase.ExportNoteUseCase
import com.example.personalnotesapp.ui.theme.PersonalNotesAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    note: Note,
    exportNoteUseCase: ExportNoteUseCase
) {
    val context = LocalContext.current

    val createFileLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CreateDocument("text/plain"),
        onResult = { uri ->
            if (uri != null) {
                val success = exportNoteUseCase.exportNote(context, note, uri)
                val msg = if (success) "Note exported!" else "Failed to export"
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
            }
        }
    )

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Settings") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Export Options", style = MaterialTheme.typography.titleMedium)

            Button(onClick = {
                createFileLauncher.launch("${note.title}.txt")
            }) {
                Text("Export Note as .txt")
            }
        }
    }
}

