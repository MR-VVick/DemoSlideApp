package com.example.demo.ui.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.demo.R
import com.example.demo.data.model.Discover
import com.example.demo.databinding.FragmentHomeBinding
import com.example.demo.ui.adapter.HomeBannerRecyclerAdapter
import com.example.demo.ui.adapter.HomeGridViewAdapter

class HomeFragment : Fragment() {

    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val recyclerView = binding.recyclerViewHomeBanner
        val adapter = HomeBannerRecyclerAdapter(getItems(), findNavController())
        val gridView = binding.gridViewHome
        val gridViewAdapter = context?.let { HomeGridViewAdapter(it, getItems()) }
        gridView.adapter = gridViewAdapter
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(recyclerView)

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val centerView = snapHelper.findSnapView(layoutManager)
                if (centerView != null) {
                    val centerPosition = layoutManager.getPosition(centerView)
                    for (i in 0 until layoutManager.childCount) {
                        val childView = layoutManager.getChildAt(i)
                        val imageView = childView?.findViewById<ImageView>(R.id.discover_item_image_view)
                        val textViewTitle = childView?.findViewById<TextView>(R.id.discover_item_title)
                        val textViewSubTitle = childView?.findViewById<TextView>(R.id.discover_item_sub_title)
                        if (childView?.let { layoutManager.getPosition(it) } == centerPosition) {
                            if (imageView != null) {
                                val layoutParams = imageView.layoutParams
                                layoutParams.height = 1000
                                imageView.layoutParams = layoutParams
                                textViewTitle?.textSize = 25F
                                textViewSubTitle?.textSize = 20F
                            }
                        } else {
                            if (imageView != null) {
                                val layoutParams = imageView.layoutParams
                                layoutParams.height = 850
                                imageView.layoutParams = layoutParams
                                textViewTitle?.textSize = 15F
                                textViewSubTitle?.textSize = 10F
                            }
                        }
                    }
                }
            }
        })

        return binding.root
    }

    private fun getItems(): List<Discover> {
        return listOf(
            Discover(R.drawable.first_location, "Title 1", "Subtitle 1"),
            Discover(R.drawable.second_location, "Title 2", "Subtitle 2"),
            Discover(R.drawable.third_location, "Title 3", "Subtitle 3"),
            Discover(R.drawable.fourth_location, "Title 4", "Subtitle 4"),
            Discover(R.drawable.fifth_location, "Title 5", "Subtitle 5"),
            Discover(R.drawable.sixth_location, "Title 6", "Subtitle 6")
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}