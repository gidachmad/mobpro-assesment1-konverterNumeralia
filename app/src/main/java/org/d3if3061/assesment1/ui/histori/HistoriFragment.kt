package org.d3if3061.assesment1.ui.histori

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.d3if3061.assesment1.R
import org.d3if3061.assesment1.databinding.FragmentHistoriBinding
import org.d3if3061.assesment1.db.NumeraliaDb
import org.d3if3061.assesment1.db.NumeraliaEntity

class HistoriFragment : Fragment() {
    private val viewModel: HistoriViewModel by lazy {
        val db = NumeraliaDb.getInstance(requireContext())
        val factory = HistoriViewModelFactory(db.dao)
        ViewModelProvider(this, factory)[HistoriViewModel::class.java]
    }

    private lateinit var binding: FragmentHistoriBinding
    private lateinit var myAdapter: HistoriAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoriBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        myAdapter = HistoriAdapter()
        with(binding.recyclerView){
            addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
            adapter = myAdapter
            setHasFixedSize(true)
        }
        viewModel.data.observe(viewLifecycleOwner, {
            binding.emptyView.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
            myAdapter.submitList(it)
        })

        setUpOnItemClick()
    }

    private fun setUpOnItemClick() {
        myAdapter.setListener(object : HistoriListener{
            override fun onItemClick(numeralia: NumeraliaEntity, isClicked: Boolean) {
                if (isClicked) hapusDataHistori(numeralia)
            }
        })
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.histori_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_hapus){
            hapusDataAll()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun hapusDataAll(){
        MaterialAlertDialogBuilder(requireContext())
            .setMessage(R.string.konfirmasi_hapus_all)
            .setPositiveButton(getString(R.string.hapus)) {
                    _, _ -> viewModel.hapusHistoriAll()
            }
            .setNegativeButton(getString(R.string.batal)) {
                    dialog, _ -> dialog.cancel()
            }
            .show()
    }

    private fun hapusDataHistori(numeralia: NumeraliaEntity) {
        MaterialAlertDialogBuilder(requireContext())
            .setMessage(R.string.konfirmasi_hapus)
            .setPositiveButton(getString(R.string.hapus)) {
                    _, _ -> viewModel.hapusHistori(numeralia)
            }
            .setNegativeButton(getString(R.string.batal)) {
                    dialog, _ -> dialog.cancel()
            }
            .show()
    }

}
