package com.sjw.kbmarket

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sjw.kbmarket.databinding.ItemMainProductsBinding

class ProductAdapter(val items: MutableList<Product>) :
    RecyclerView.Adapter<ProductAdapter.Holder>() {
    interface ItemClick {
        fun onClick(view: View, position: Int)
        fun onLongClick(view: View, position: Int)
    }

    var itemClick: ItemClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding =
            ItemMainProductsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.itemView.setOnClickListener {
            itemClick?.onClick(it, position)
        }
        holder.itemView.setOnLongClickListener {
            itemClick?.onLongClick(it, position)
            true
        }

        holder.itemMainImg.setImageResource(items[position].imageId)
        holder.itemMainName.text = items[position].productName
        holder.itemMainLocation.text = items[position].location
        holder.itemMainChatCount.text = items[position].chats.toString()
        holder.itemMainFavoriteCount.text = items[position].favorites.toString()
        if (items[position].isChecked) {
            holder.itemMainFavorite.setImageResource(R.drawable.ic_favorite_full)
        } else {
            holder.itemMainFavorite.setImageResource(R.drawable.ic_favorite)
        }
    }


    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun removeItem(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, items.size)
    }

    inner class Holder(val binding: ItemMainProductsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val itemMainImg = binding.itemMainImg
        val itemMainName = binding.itemMainName
        val itemMainLocation = binding.itemMainLocation
        val itemMainChat = binding.itemMainChat
        val itemMainChatCount = binding.itemMainChatCount
        val itemMainFavorite = binding.itemMainFavorite
        val itemMainFavoriteCount = binding.itemMainFavoriteCount
    }
}