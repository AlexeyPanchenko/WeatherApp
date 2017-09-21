package ru.aol_panchenko.weatherapp.utils.ui

import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.FloatingActionButton
import android.support.v4.view.ViewCompat
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator


/**
 * Created by alexey on 21.09.17.
 */
class FABScrollingBehaviour(context: Context, attrs: AttributeSet) : FloatingActionButton.Behavior() {

    override fun onNestedScroll(coordinatorLayout: CoordinatorLayout?, child: FloatingActionButton?, target: View?, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed)

        if (dyConsumed > 0) {
            val layoutParams = child?.layoutParams as CoordinatorLayout.LayoutParams
            val margin = layoutParams.bottomMargin
            child.animate()
                    .translationY(child.height.toFloat() + margin)
                    .setInterpolator(LinearInterpolator()).start()
        } else if (dyConsumed < 0) {
            child?.animate()?.translationY(0F)?.setInterpolator(LinearInterpolator())?.start()
        }
    }

    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout?, child: FloatingActionButton?, directTargetChild: View?, target: View?, axes: Int): Boolean =
            axes == ViewCompat.SCROLL_AXIS_VERTICAL


}