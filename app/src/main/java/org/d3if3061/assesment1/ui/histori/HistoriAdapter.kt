package org.d3if3061.assesment1.ui.histori

import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.d3if3061.assesment1.R
import org.d3if3061.assesment1.databinding.ItemHistoryBinding
import org.d3if3061.assesment1.db.NumeraliaEntity
import java.text.SimpleDateFormat
import java.util.*

class HistoriAdapter:
    ListAdapter<NumeraliaEntity, HistoriAdapter.ViewHolder>(DIFF_CALLBACK) {
        lateinit var historiListener: HistoriListener

        fun setListener(historiListener: HistoriListener){
            this.historiListener = historiListener
        }

        companion object {
            private val DIFF_CALLBACK =
                object : DiffUtil.ItemCallback<NumeraliaEntity>(){
                    override fun areItemsTheSame(
                        oldItem: NumeraliaEntity,
                        newItem: NumeraliaEntity
                    ): Boolean {
                        return oldItem.id == newItem.id
                    }

                    override fun areContentsTheSame(
                        oldItem: NumeraliaEntity,
                        newItem: NumeraliaEntity
                    ): Boolean {
                        return oldItem == newItem
                    }
                }
        }

        override fun onCreateViewHolder(
            parent: ViewGroup, viewType: Int
        ): ViewHolder{
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemHistoryBinding.inflate(inflater, parent, false)
            return ViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int){
            holder.bind(getItem(position))
        }


        inner class ViewHolder(
            private val binding: ItemHistoryBinding
        ) : RecyclerView.ViewHolder(binding.root){
            private val dateFormatter = SimpleDateFormat("dd MMMM yyyy", Locale("id", "ID"))

            fun bind(item: NumeraliaEntity) = with(binding){
                tanggalTextView.text = dateFormatter.format(Date(item.tanggal))
                NumeraliaTextView.text = item.numeralia.replaceFirstChar(Char::titlecase)
                val hasilAngka = item.bilangan
                AngkaTextView.text = hasilAngka.toString()
                val colorRes = when(hasilAngka.toString().length){
                    1 -> R.color.deep_purple_500
                    2 -> R.color.red_500
                    3 -> R.color.brown_500
                    else -> R.color.green_500
                }

                val rectBg = AngkaTextView.background as GradientDrawable
                rectBg.setColor(ContextCompat.getColor(root.context, colorRes))

                deleteButton.setOnClickListener{
                    historiListener.onItemClick(item, true)
                }

            }
        }

}

interface HistoriListener {
    fun onItemClick(numeralia: NumeraliaEntity, isClicked: Boolean)
}