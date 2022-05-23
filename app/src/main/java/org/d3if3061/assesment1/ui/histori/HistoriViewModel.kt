package org.d3if3061.assesment1.ui.histori

import androidx.lifecycle.ViewModel
import org.d3if3061.assesment1.db.NumeraliaDao

class HistoriViewModel(db: NumeraliaDao): ViewModel() {
    val data = db.getNumeralia()
}