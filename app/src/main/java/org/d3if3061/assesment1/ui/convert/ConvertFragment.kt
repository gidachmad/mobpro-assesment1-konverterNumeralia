package org.d3if3061.assesment1.ui.convert

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import org.d3if3061.assesment1.R
import org.d3if3061.assesment1.databinding.FragmentConvertBinding

class ConvertFragment : Fragment() {
    private lateinit var binding: FragmentConvertBinding

    private val viewModel: ConvertViewModel by lazy {
        ViewModelProvider(requireActivity())[ConvertViewModel::class.java]
    }

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
            else -> hasilString = translateListtoString( viewModel.convertNumeralia(bil1.toInt()) )
        }
        binding.hasil.text = getString(R.string.hasil_x, hasilString.replaceFirstChar(Char::titlecase) )
    }

    private fun translateListtoString(list : List<Int>): String{
        var stringRes = ""
        for (indeks in list){
            if (indeks == R.string.se) stringRes += getString(indeks)
            else stringRes += getString(indeks) + " "
        }
        return stringRes
    }

}