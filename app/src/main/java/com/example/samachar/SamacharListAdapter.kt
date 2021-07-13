package com.example.samachar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class SamacharListAdapter(private val listener: SamacharItemClicked) :
    RecyclerView.Adapter<SamacharViewHolder>() {

    private val items: ArrayList<Samachar> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SamacharViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_samachar, parent, false)
        val viewHolder = SamacharViewHolder(view)
        view.setOnClickListener {
            listener.onItemClicked(items[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: SamacharViewHolder, position: Int) {
        val currentItem = items[position]
        holder.titleView.text = currentItem.title
        holder.author.text = currentItem.author
        holder.published.text = currentItem.published
        Glide.with(holder.itemView.context).load(currentItem.imageUrl).into(holder.image)
    }

    fun updateSamachar(updatedSamachar: ArrayList<Samachar>) {
        items.clear()
        items.addAll(updatedSamachar)

        notifyDataSetChanged()
    }

}

class SamacharViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val titleView: TextView = itemView.findViewById(R.id.title)
    val image: ImageView = itemView.findViewById(R.id.image)
    val author: TextView = itemView.findViewById(R.id.author)
    val published: TextView = itemView.findViewById(R.id.published)
}

interface SamacharItemClicked {
    fun onItemClicked(item: Samachar)
}