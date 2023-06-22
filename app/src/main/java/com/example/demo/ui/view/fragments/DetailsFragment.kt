package com.example.demo.ui.view.fragments

import android.animation.*
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import com.example.demo.R
import com.example.demo.databinding.FragmentDetailsBinding
import com.example.demo.ui.animation.ImageViewAnimation
import com.google.android.material.bottomsheet.BottomSheetBehavior

class DetailsFragment : Fragment() {

    private var _binding : FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var parentSheetBehavior: BottomSheetBehavior<*>
    private lateinit var childSheetBehavior: BottomSheetBehavior<*>
    private lateinit var subChildSheetBehavior: BottomSheetBehavior<*>

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        val imageData = arguments?.let { DetailsFragmentArgs.fromBundle(it).imageData }
        val parentSheet = binding.parentBottomSheet
        val childSheet = binding.childBottomSheet
        val subChildSheet = binding.subChildBottomSheet
        val parentBottomSheetButton = binding.parentBottomSheetButton
        val parentBottomSheetText = binding.parentBottomSheetText
        val childBottomSheetButton = binding.childBottomSheetButton
        val childBottomSheetText = binding.childBottomSheetText
        val subChildBottomSheetButton = binding.subChildBottomSheetButton
        val titleText = binding.detailsTitle
        val descriptionText = binding.detailsDescription
        val scheduleButton = binding.detailsScheduleButton

        imageData?.let { binding.backgroundImageView.setImageResource(it) }

        val imageViewAnimation = ImageViewAnimation(binding.backgroundImageView)
        imageViewAnimation.startAnimation()

        val animation = AnimationUtils.loadAnimation(context, R.anim.fade_in_bottom_top)
        titleText.startAnimation(animation)
        descriptionText.startAnimation(animation)
        scheduleButton.startAnimation((animation))
        scheduleButton.setOnClickListener {
            parentSheet.startAnimation(animation)
            parentSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }

        parentSheetBehavior = BottomSheetBehavior.from(parentSheet)
        parentSheetBehavior.apply {
            peekHeight = 0
            state = BottomSheetBehavior.STATE_COLLAPSED
            addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    val alpha = 1 - slideOffset
                    scheduleButton.alpha = alpha
                    titleText.alpha = alpha
                    descriptionText.alpha = alpha
                }
            })
        }

        parentBottomSheetButton.setOnClickListener {
            childSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }

        childSheetBehavior = BottomSheetBehavior.from(childSheet)
        childSheetBehavior.apply {
            peekHeight = 100
            state = BottomSheetBehavior.STATE_COLLAPSED
            addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                        parentSheetBehavior.isDraggable = false
                    } else if (newState == BottomSheetBehavior.STATE_COLLAPSED || newState == BottomSheetBehavior.STATE_HIDDEN) {
                        parentSheetBehavior.isDraggable = true
                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    val visibility = if (slideOffset > 0) View.VISIBLE else View.GONE
                    val alpha = 1 - slideOffset
                    parentBottomSheetButton.alpha = alpha
                    parentBottomSheetText.alpha = slideOffset
                    parentBottomSheetText.visibility = visibility
                    childBottomSheetButton.alpha = slideOffset
                    childBottomSheetButton.visibility = visibility
                }
            })
        }

        childBottomSheetButton.setOnClickListener {
            subChildSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }

        subChildSheetBehavior = BottomSheetBehavior.from(subChildSheet)
        subChildSheetBehavior.apply {
            peekHeight = 100
            state = BottomSheetBehavior.STATE_COLLAPSED
            addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                        childSheetBehavior.isDraggable = false
                    } else if (newState == BottomSheetBehavior.STATE_COLLAPSED || newState == BottomSheetBehavior.STATE_HIDDEN) {
                        childSheetBehavior.isDraggable = true
                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    val visibility = if (slideOffset > 0) View.VISIBLE else View.GONE
                    val alpha = 1 - slideOffset
                    childBottomSheetButton.alpha = alpha
                    childBottomSheetText.alpha = slideOffset
                    childBottomSheetText.visibility = visibility
                    subChildBottomSheetButton.alpha = slideOffset
                    subChildBottomSheetButton.visibility = visibility
                }
            })
        }

        subChildBottomSheetButton.setOnClickListener {
            subChildSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            childSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            parentSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        binding.childBottomSheet.setOnTouchListener { _, _ ->
            if (subChildSheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED) {
                parentSheetBehavior.isDraggable = false
                childSheetBehavior.isDraggable = true
            }
            false
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}