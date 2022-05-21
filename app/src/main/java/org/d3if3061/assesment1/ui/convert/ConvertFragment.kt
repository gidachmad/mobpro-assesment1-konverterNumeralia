package org.d3if3061.assesment1.ui.convert

import android.os.Bundle
import android.text.TextUtils
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import org.d3if3061.assesment1.R
import org.d3if3061.assesment1.databinding.FragmentConvertBinding

class ConvertFragment : Fragment() {
    private lateinit var binding: FragmentConvertBinding

//    private val viewModel: ConvertViewModel by lazy {
//        ViewModelProvider(requireActivity())[ConvertViewModel::class.java]
//    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_histori -> {
                findNavController().navigate(R.id.action_convertFragment_to_historiFragment)
                return true
            }
            R.id.menu_about -> {
                findNavController().navigate(R.id.action_convertFragment_to_tentangFragment)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentConvertBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.submitButton.setOnClickListener{ convert() }
    }


    private fun convert() {
        var hasilString = ""
        val bil1 = binding.input1.text.toString()
        when {
            // jika input kosong
            TextUtils.isEmpty(bil1) -> Toast.makeText(context, R.string.invalid_bil, Toast.LENGTH_LONG).show()

            // jika input tidak sesuai range yang dapat dikonversi
            bil1.length > 4 -> Toast.makeText(context, R.string.invalid_bil2, Toast.LENGTH_LONG).show()

            // jika input sesuai range
            else -> hasilString = convertNumeralia(bil1.toInt())
        }

        binding.hasil.text = getString(R.string.hasil_x, hasilString.replaceFirstChar(Char::titlecase) )
    }

    private fun convertNumeralia(bil1: Int): String {
        val length = bil1.toString().length

        var stringRes = when (length) {
            1 -> convertSatuan(bil1)
            2 -> convertPuluhan(bil1)
            3 -> convertRatusan(bil1)
            else -> convertRibuan(bil1)
        }

        return stringRes
    }

    private fun convertSatuan(bil1: Int): String{
        when(bil1){
            0 -> return getString(R.string.nol)
            1 -> return getString(R.string.satu)
            2 -> return getString(R.string.dua)
            3 -> return getString(R.string.tiga)
            4 -> return getString(R.string.empat)
            5 -> return getString(R.string.lima)
            6 -> return getString(R.string.enam)
            7 -> return getString(R.string.tujuh)
            8 -> return getString(R.string.delapan)
            9 -> return getString(R.string.sembilan)
        }
        return getString(R.string.error_msg)
    }

    private fun convertBelasan(bil1: Int): String {
        when(bil1%10){
            1 -> return getString(R.string.se) + getString(R.string.belas)
            2 -> return getString(R.string.dua) + " " + getString(R.string.belas)
            3 -> return getString(R.string.tiga) + " " + getString(R.string.belas)
            4 -> return getString(R.string.empat) + " " + getString(R.string.belas)
            5 -> return getString(R.string.lima) + " " + getString(R.string.belas)
            6 -> return getString(R.string.enam) + " " + getString(R.string.belas)
            7 -> return getString(R.string.tujuh) + " " + getString(R.string.belas)
            8 -> return getString(R.string.delapan) + " " + getString(R.string.belas)
            9 -> return getString(R.string.sembilan) + " " + getString(R.string.belas)
        }
        return getString(R.string.error_msg)
    }

    private fun convertPuluhan(bil1: Int): String{
        return when{
            // jika sepuluh
            bil1 == 10 -> getString(R.string.se) + getString(R.string.puluh)

            // jika dibawah sepuluh
            bil1 < 10 -> convertSatuan(bil1)

            // jika di bukan belasan
            bil1 > 19 ->
                if (bil1 % 10 == 0){
                    convertSatuan(bil1/10) + " " + getString(R.string.puluh)
                }else {
                    convertSatuan(bil1 / 10) + " " + getString(R.string.puluh) + " " + convertSatuan(bil1 % 10)
                }

            // jika belasan
            else -> convertBelasan(bil1)
        }
    }

    private fun convertRatusan(bil1: Int): String{
        return when{
            // jika seratus
            bil1 == 100 -> getString(R.string.se) + getString(R.string.ratus)

            // jika digit ke dua dan ketiga nol
            bil1 % 100 == 0 -> convertSatuan(bil1/100) + " " + getString(R.string.ratus)

            // jika di range 100 < bil < 120, bilangan belasan dalam range seratus
            bil1 < 120 -> getString(R.string.se) + getString(R.string.ratus) + " " + convertBelasan(bil1%100)

            // jika di range lainnya
            bil1 > 199 -> convertSatuan(bil1 / 100) + " " + getString(R.string.ratus) + " " + convertPuluhan(bil1 % 100)

            // jika di range 120 < bil < 199
            else -> getString(R.string.se) + getString(R.string.ratus) + " " + convertPuluhan(bil1 % 100)
        }
    }

    private fun convertRibuan(bil1: Int): String{
        return when{
            // jika seribu
            bil1 == 1000 -> getString(R.string.se) + getString(R.string.ribu)

            // jika digit ke dua, ketiga, dan keempat nol
            bil1 % 1000 == 0 -> convertSatuan(bil1/1000) + " " + getString(R.string.ribu)

            // jika digit kedua adalah nol dan di range 1000
            ((bil1/100)%10) == 0 && bil1/1000 == 1 -> getString(R.string.se) + getString(R.string.ribu) + " " + convertPuluhan(bil1%100)

            // jika digit kedua adalah nol bukan di range 1000
            ((bil1/100)%10) == 0 -> convertSatuan(bil1 / 1000) + " " + getString(R.string.ribu) + " " + convertPuluhan(bil1%100)

            // jika di range 1000
            bil1 < 2000 -> getString(R.string.se) + getString(R.string.ribu) + " " + convertRatusan(bil1 % 1000)

            // jika di range lainnya
            else -> convertSatuan(bil1 / 1000) + " " + getString(R.string.ribu) + " " + convertRatusan(bil1 % 1000)
        }
    }

}