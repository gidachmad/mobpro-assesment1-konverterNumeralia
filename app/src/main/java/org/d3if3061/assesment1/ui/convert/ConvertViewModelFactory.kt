package org.d3if3061.assesment1.ui.convert

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.d3if3061.assesment1.db.NumeraliaDao
import java.lang.IllegalArgumentException

class ConvertViewModelFactory(
    private val db: NumeraliaDao
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ConvertViewModel::class.java)){
            return ConvertViewModel(db) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}