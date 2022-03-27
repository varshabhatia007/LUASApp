package com.example.luasapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.luasapp.databinding.ItemTramInfoComponentBinding
import com.example.luasapp.model.TramInfo

class TramInfoAdapter(private var items: List<TramInfo> = emptyList()) : RecyclerView.Adapter<TramInfoViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TramInfoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemTramInfoComponentBinding.inflate(layoutInflater, parent, false)
        return TramInfoViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: TramInfoViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    fun submit(items: List<TramInfo>?) {
        this.items = items ?: emptyList()
        notifyDataSetChanged()
    }
}

class TramInfoViewHolder(var binding: ItemTramInfoComponentBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: TramInfo) {
        when {
            item.dueMins.isEmpty() -> binding.dueTime.visibility = View.GONE
            else -> binding.dueTime.visibility = View.VISIBLE
        }
        binding.destinationName.text = item.destination
        binding.dueTime.text = dueTimeInMins(item.dueMins)
        dueVisibility(
            visibility = when {
                item.dueMins.contains("due", true) -> View.GONE
                else -> View.VISIBLE
            }
        )
    }

    private fun dueVisibility(visibility: Int) {
        binding.dueLabel.visibility = visibility
        binding.minLabel.visibility = visibility
    }

    private fun dueTimeInMins(dueMins: String) = if (dueMins.length == 1) {
        "0$dueMins"
    } else dueMins
}