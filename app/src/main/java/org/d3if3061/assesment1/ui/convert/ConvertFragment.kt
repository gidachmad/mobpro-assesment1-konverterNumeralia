package org.d3if3061.assesment1.ui.convert

import android.os.Bundle
import android.text.TextUtils
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
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
        if (TextUtils.isEmpty(bil1)){
            Toast.makeText(context, R.string.invalid_bil, Toast.LENGTH_LONG).show()
        }else if(bil1.length > 2){
            Toast.makeText(context, R.string.invalid_bil2, Toast.LENGTH_LONG).show()
        }else {
            hasilString = convertAngkaKeKata(bil1.toInt())
        }

        binding.hasil.text = getString(R.string.hasil_x, hasilString.replaceFirstChar(Char::titlecase) )
    }

    private fun convertAngkaKeKata(bil1: Int): String {
        val length = bil1.toString().length
        val stringRes = when{
            bil1 == 10 -> getString(R.string.se) + getString(R.string.puluh)
            bil1 < 11 -> convertPerAngka(bil1, length)
            bil1 > 19 -> convertPerAngka(bil1, length)
            else -> convertBelasan(bil1)
        }
        return stringRes
    }

    private fun convertBelasan(bil1: Int): String {
        when(bil1%10){
            1 -> return getString(R.string.se) + " " + getString(R.string.belas)
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

    private fun convertPerAngka(bil1: Int, length: Int): String {
        var stringRes = ""
        if ( length == 1 ){
            stringRes = convertSatuan(bil1)
        }else if ( length == 2){
            if ( (bil1 % 10) == 0 ){
                stringRes = convertSatuan(bil1/10) + " " + getString(R.string.puluh)
            }else {
                stringRes = convertSatuan(bil1/10) + " " + getString(R.string.puluh) + " " + convertSatuan(bil1%10)
            }
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


}