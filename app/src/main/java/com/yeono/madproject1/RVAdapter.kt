package com.yeono.madproject1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class RVAdapter(private val imageList: List<Int>) :
    RecyclerView.Adapter<RVAdapter.ImageViewHolder>()
    {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
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
                        val clickedImageResId = imageList[position]
                        // TODO: 클릭한 이미지에 대한 동작 수행
                    }
                }
            }
        }
    }
