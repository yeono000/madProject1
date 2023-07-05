package com.yeono.madproject1

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.yeono.madproject1.ui.contact.ContactLVAdapter

class RVAdapter(private val imageList: List<Int>) :
    RecyclerView.Adapter<RVAdapter.ImageViewHolder>()
    {
        interface OnItemClickListener {
            fun onClick(position: Int)
        }

        fun setItemClickListener(onItemClickListener: RVAdapter.OnItemClickListener) {
            this.itemClickListener = onItemClickListener
        }

        private lateinit var itemClickListener : RVAdapter.OnItemClickListener

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
            Log.d("adapter", "!!!!visit")
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_image, parent, false)
            return ImageViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
            val imageResId = imageList[position]
            holder.imageView.setImageResource(imageResId)
        }

        override fun getItemCount(): Int {
            return imageList.size
        }

        inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val imageView: ImageView = itemView.findViewById(R.id.imageView)

            init {
                itemView.setOnClickListener {
                    // 아이템 클릭 이벤트 처리
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        // 클릭한 이미지 처리
                        itemClickListener.onClick(position)
                    }
                }
            }
        }
    }
