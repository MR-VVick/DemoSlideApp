package com.example.demo.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.demo.R
import com.example.demo.data.model.Discover
import com.example.demo.ui.view.fragments.HomeFragmentDirections

class HomeBannerRecyclerAdapter(private val items: List<Discover>, private val navController: NavController) :
    RecyclerView.Adapter<HomeBannerRecyclerAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_home_banner_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.imageView.setImageResource(items[position].image)
        holder.imageView.setOnClickListener {
            val navDirections = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(items[position].image)
            navController.navigate(navDirections)
        }
        holder.titleTextView.text = items[position].title
        holder.subTitleTextView.text = items[position].subtitle
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.discover_item_image_view)
        val titleTextView: TextView = itemView.findViewById(R.id.discover_item_title)
        val subTitleTextView: TextView = itemView.findViewById(R.id.discover_item_sub_title)
    }
}