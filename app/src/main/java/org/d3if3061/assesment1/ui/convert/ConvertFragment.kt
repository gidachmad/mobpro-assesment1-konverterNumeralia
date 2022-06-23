package org.d3if3061.assesment1.ui.convert

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import org.d3if3061.assesment1.R
import org.d3if3061.assesment1.databinding.FragmentConvertBinding
import org.d3if3061.assesment1.db.NumeraliaDb
import org.d3if3061.assesment1.network.ApiStatus
import org.d3if3061.assesment1.network.GambarApi
import org.d3if3061.assesment1.network.GambarStatus

class ConvertFragment : Fragment() {
    private lateinit var binding: FragmentConvertBinding

    private val viewModel: ConvertViewModel by lazy {
        val db = NumeraliaDb.getInstance(requireContext())
        val factory = ConvertViewModelFactory(db.dao)
        ViewModelProvider(this, factory)[ConvertViewModel::class.java]
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

        viewModel.getStringRes.observe(viewLifecycleOwner) {
            binding.hasil.text = getString(R.string.hasil_x, it.replaceFirstChar(Char::titlecase) )
        }

        viewModel.getDataGambar.observe(viewLifecycleOwner) {
            updateGambar(viewModel.getGambarStatus.value)
        }

        viewModel.getStatus.observe(viewLifecycleOwner) {
            updateProgress(it)
        }

    }

    private fun updateGambar(status: GambarStatus?) {
        when (status) {
            GambarStatus.CONVERTED -> {
                Glide.with(binding.gambar.context)
                    .load(viewModel.getDataGambar.value?.get(0)
                        ?.let { GambarApi.getGambarUrl(it.gambar) })
                    .error(R.drawable.ic_baseline_broken_image_24)
                    .into(binding.gambar)
            }
            GambarStatus.WAITING -> {
                Glide.with(binding.gambar.context)
                    .load(viewModel.getDataGambar.value?.get(1)
                        ?.let { GambarApi.getGambarUrl(it.gambar) })
                    .error(R.drawable.ic_baseline_broken_image_24)
                    .into(binding.gambar)
            }
            null -> {
                binding.gambar.setImageResource(R.drawable.ic_baseline_broken_image_24)
            }
        }
    }

    private fun updateProgress(status: ApiStatus) {
        when (status) {
            ApiStatus.LOADING -> {
                binding.progressBar.visibility = View.VISIBLE
            }
            ApiStatus.SUCCESS -> {
                binding.progressBar.visibility = View.GONE
            }
            ApiStatus.FAILED -> {
                binding.progressBar.visibility = View.GONE
                binding.gambar.setImageResource(R.drawable.ic_baseline_broken_image_24)
            }

        }
    }

    private fun convert() {
        val bil1 = binding.input1.text.toString()
        when {
            // jika input kosong
            TextUtils.isEmpty(bil1) -> Toast.makeText(context, R.string.invalid_bil, Toast.LENGTH_LONG).show()

            // jika input tidak sesuai range yang dapat dikonversi
            bil1.length > 4 -> Toast.makeText(context, R.string.invalid_bil2, Toast.LENGTH_LONG).show()

            // jika input sesuai
            else -> {
                viewModel.convertNumeralia(bil1.toInt(), context)
                Glide.with(binding.gambar.context)
                    .load(viewModel.getDataGambar.value?.get(0)
                        ?.let { GambarApi.getGambarUrl(it.gambar) })
                    .error(R.drawable.ic_baseline_broken_image_24)
                    .into(binding.gambar)
            }
        }
    }
}