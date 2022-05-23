package org.d3if3061.assesment1.ui.histori

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.d3if3061.assesment1.db.NumeraliaDao
import org.d3if3061.assesment1.db.NumeraliaEntity

class HistoriViewModel(private val db: NumeraliaDao): ViewModel() {
    val data = db.getNumeralia()

    fun hapusHistoriAll() = viewModelScope.launch {
        withContext(Dispatchers.IO){
            db.clearData()
        }
    }

    fun hapusHistori(numeralia: NumeraliaEntity) = viewModelScope.launch {
        withContext(Dispatchers.IO){
            db.deleteHistori(numeralia)
        }
    }
}