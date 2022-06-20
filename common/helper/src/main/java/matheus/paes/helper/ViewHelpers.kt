package matheus.paes.helper

import android.view.View

fun View.makeVisible() {
    this.visibility = View.VISIBLE
}

fun View.makeInvisible() {
    this.visibility = View.INVISIBLE
}

fun View.makeGone() {
    this.visibility = View.GONE
}

fun View.toggleVisibility(visible: Boolean = true) {
    this.visibility = View.VISIBLE.takeIf { visible } ?: View.GONE
}