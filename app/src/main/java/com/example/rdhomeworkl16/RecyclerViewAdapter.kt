package com.example.rdhomeworkl16

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class RecyclerViewAdapter(private val items:List<JsData>, val callBack:(result: String) -> Unit): RecyclerView.Adapter<RecyclerViewViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewViewHolder {
        val listItemViewHolder = LayoutInflater.from(parent.context).inflate(R.layout.rc_item_layout, parent, false)
        return RecyclerViewViewHolder(listItemViewHolder)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerViewViewHolder, position: Int) {
        holder.title.text = items[position].name
        Glide.with(holder.imageView)
            .load(items[position].images)
            .into(holder.imageView)

        holder.itemView.setOnClickListener {
            callBack(position.toString())
        }
    }

}
class RecyclerViewViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){
    val title: TextView = itemView.findViewById(R.id.title)
    val imageView: ImageView = itemView.findViewById(R.id.image)
}