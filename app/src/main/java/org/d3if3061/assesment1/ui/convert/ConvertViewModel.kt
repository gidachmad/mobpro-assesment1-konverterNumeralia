package org.d3if3061.assesment1.ui.convert

import android.content.Context
import android.os.Debug
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.d3if3061.assesment1.R
import org.d3if3061.assesment1.db.NumeraliaDao
import org.d3if3061.assesment1.db.NumeraliaEntity

class ConvertViewModel(private val db: NumeraliaDao): ViewModel() {
    private val list = mutableListOf<Int>()
    private var stringResult = MutableLiveData<String>()

    val getStringRes: LiveData<String> get() = (stringResult)

    fun translateListtoString(list : MutableList<Int>, context: Context?) {
        var stringRes = ""
        for (indeks in list) {
            if (indeks == R.string.se) stringRes += context?.getString(indeks)
            else stringRes += context?.getString(indeks) + " "
        }
        stringResult.value = stringRes
    }

    fun convertNumeralia(bil1: Int, context: Context? ) {
        when (bil1.toString().length) {
            1 -> convertSatuan(bil1)
            2 -> convertPuluhan(bil1)
            3 -> convertRatusan(bil1)
            4 -> convertRibuan(bil1)
            else -> convertBelasan(bil1)
        }

        translateListtoString(list.toMutableList(), context)
        val dataNumeralia = NumeraliaEntity(
            bilangan = bil1,
            numeralia = getStringRes.value.toString()
        )
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                db.insert(dataNumeralia)
            }
        }

        list.clear()
    }

    private fun convertSatuan(bil1: Int){
        when(bil1){
            0 -> list.add(R.string.nol)
            1 -> list.add(R.string.satu)
            2 -> list.add(R.string.dua)
            3 -> list.add(R.string.tiga)
            4 -> list.add(R.string.empat)
            5 -> list.add(R.string.lima)
            6 -> list.add(R.string.enam)
            7 -> list.add(R.string.tujuh)
            8 -> list.add(R.string.delapan)
            9 -> list.add(R.string.sembilan)
            else -> list.add(R.string.error_msg)
        }
    }

    private fun convertBelasan(bil1: Int) {
        when(bil1%10){
            1 -> list.addAll(listOf(R.string.se, R.string.belas))
            2 -> list.addAll(listOf(R.string.dua, R.string.belas))
            3 -> list.addAll(listOf(R.string.tiga, R.string.belas))
            4 -> list.addAll(listOf(R.string.empat, R.string.belas))
            5 -> list.addAll(listOf(R.string.lima, R.string.belas))
            6 -> list.addAll(listOf(R.string.enam, R.string.belas))
            7 -> list.addAll(listOf(R.string.tujuh, R.string.belas))
            8 -> list.addAll(listOf(R.string.delapan, R.string.belas))
            9 -> list.addAll(listOf(R.string.sembilan, R.string.belas))
            else -> list.add(R.string.error_msg)
        }
    }

    private fun convertPuluhan(bil1: Int){
        when{
            // jika sepuluh
            bil1 == 10 -> list.addAll(listOf(R.string.se, R.string.puluh))

            // jika dibawah sepuluh
            bil1 < 10 -> convertSatuan(bil1)

            // jika di bukan belasan
            bil1 > 19 ->
                if (bil1 % 10 == 0){
                    convertSatuan(bil1/10)
                    list.add(R.string.puluh)
                }else {
                    convertSatuan(bil1 / 10)
                    list.add(R.string.puluh)
                    convertSatuan(bil1 % 10)
                }

            // jika belasan
            else -> convertBelasan(bil1)
        }
    }

    private fun convertRatusan(bil1: Int){
        when{
            // jika seratus
            bil1 == 100 -> list.addAll(listOf(R.string.se, R.string.ratus))

            // jika digit ke dua dan ketiga nol
            bil1 % 100 == 0 -> {
                convertSatuan(bil1/100)
                list.add(R.string.ratus)
            }

            // jika di range 100 < bil < 120, bilangan belasan dalam range seratus
            bil1 < 120 -> {
                list.addAll(listOf(R.string.se, R.string.ratus))
                convertBelasan(bil1%100)
            }

            // jika di range lainnya
            bil1 > 199 -> {
                convertSatuan(bil1 / 100)
                list.add(R.string.ratus)
                convertPuluhan(bil1 % 100)
            }

            // jika di range 120 < bil < 199
            else -> {
                list.addAll(listOf(R.string.se, R.string.ratus))
                convertPuluhan(bil1 % 100)
            }
        }
    }

    private fun convertRibuan(bil1: Int){
        when{
            // jika seribu
            bil1 == 1000 -> list.addAll(listOf(R.string.se, R.string.ribu))

            // jika digit ke dua, ketiga, dan keempat nol
            bil1 % 1000 == 0 -> {
                convertSatuan(bil1/1000)
                list.add(R.string.ribu)
            }

            // jika digit kedua adalah nol dan di range 1000
            ((bil1/100)%10) == 0 && bil1/1000 == 1 -> {
                list.addAll(listOf(R.string.se, R.string.ribu))
                convertPuluhan(bil1%100)
            }

            // jika digit kedua adalah nol bukan di range 1000
            ((bil1/100)%10) == 0 -> {
                convertSatuan(bil1 / 1000)
                list.add(R.string.ribu)
                convertPuluhan(bil1%100)
            }

            // jika di range 1000
            bil1 < 2000 -> {
                list.addAll(listOf(R.string.se, R.string.ribu))
                convertRatusan(bil1 % 1000)
            }

            // jika di range lainnya
            else -> {
                convertSatuan(bil1 / 1000)
                list.add(R.string.ribu)
                convertRatusan(bil1 % 1000)
            }
        }
    }

}