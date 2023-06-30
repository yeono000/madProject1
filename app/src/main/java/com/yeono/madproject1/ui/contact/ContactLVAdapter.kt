package com.yeono.madproject1.ui.contact

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yeono.madproject1.R
import com.yeono.madproject1.databinding.ItemContactBinding

class ContactLVAdapter(private val data:List<ContactDataModel>)
    : RecyclerView.Adapter<ContactLVAdapter.ViewHolder>() {
    interface OnItemClickListener {
        fun onClick(position: Int)
    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    private lateinit var itemClickListener : OnItemClickListener

    inner class ViewHolder(private val binding: ItemContactBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(itemData : ContactDataModel){
            binding.nameText.text = itemData.name
            binding.numberText.text = itemData.number
        }
        init {
            binding.detailButton.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    itemClickListener.onClick(position)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContactLVAdapter.ViewHolder {
        val binding = ItemContactBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactLVAdapter.ViewHolder, position: Int) {
        val itemData = data[position]
        holder.bind(itemData)
    }

    override fun getItemCount(): Int {
        return data.size
    }
}