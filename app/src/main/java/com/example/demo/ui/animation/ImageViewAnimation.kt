package com.example.demo.ui.animation

import android.animation.*
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.animation.Animation

class ImageViewAnimation (private val view: View) : Animation() {
    fun startAnimation() {
        view.scaleX = 1.0f
        view.scaleY = 1.0f

        val scaleXAnimator = ObjectAnimator.ofFloat(view, View.SCALE_X, 1.3f)
        val scaleYAnimator = ObjectAnimator.ofFloat(view, View.SCALE_Y, 1.3f)
        scaleXAnimator.duration = 500
        scaleYAnimator.duration = 500

        val alphaAnimator = ObjectAnimator.ofFloat(view, View.ALPHA, 0f, 1.0f)
        alphaAnimator.duration = 500

        val animatorSet = AnimatorSet()
        animatorSet.playTogether(scaleXAnimator, scaleYAnimator, alphaAnimator)

        val opacityAnimator = ValueAnimator.ofInt(0, 128)
        opacityAnimator.duration = 500
        opacityAnimator.addUpdateListener { valueAnimator ->
            val opacity = valueAnimator.animatedValue as Int
            val overlayDrawable = ColorDrawable(Color.argb(opacity, 0, 0, 0))
            view.foreground = overlayDrawable
        }

        animatorSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                opacityAnimator.start()
            }
        })
        animatorSet.start()
    }
}