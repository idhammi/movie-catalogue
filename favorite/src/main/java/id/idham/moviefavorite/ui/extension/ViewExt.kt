package id.idham.moviefavorite.ui.extension

import android.view.View

/**
 * [visible] Ext func to set current view to be visible & set to enable
 */
fun View.visible() {
    this.visibility = View.VISIBLE
    this.isEnabled = true
}

/**
 * [invisible] Ext func to set current view to be invisible & not enabled
 */
fun View.invisible() {
    this.visibility = View.INVISIBLE
    this.isEnabled = false
}

/**
 * [gone] Ext func to set current View visibility to [View.GONE]
 */
fun View.gone() {
    this.visibility = View.GONE
}

/**
 * [isVisible] Ext Func to check whether current view visibility is [View.VISIBLE] or not
 * @return [Boolean] true is visible and false if invisible or gone
 */
fun View.isVisible(): Boolean {
    return this.visibility == View.VISIBLE
}

/**
 * [invisibleIf] Ext Func to set visibility of View based on conditional [condition]
 * if [condition] true, then current view will be [View.INVISIBLE] else [View.VISIBLE]
 * @author herisulistiyanto
 * @param condition boolean value to toggle visibility state between VISIBLE or INVISIBLE
 */
fun View.invisibleIf(condition: Boolean) {
    if (condition) {
        this.visibility = View.INVISIBLE
        this.isEnabled = false
    } else {
        this.visibility = View.VISIBLE
        this.isEnabled = true
    }
}

/**
 * [goneIf] Ext Func to set visibility of View based on conditional [condition]
 * if [condition] true, then current view will be [View.GONE] else [View.VISIBLE]
 * @author herisulistiyanto
 * @param condition boolean value to toggle visibility state between VISIBLE or GONE
 */
fun View.goneIf(condition: Boolean) {
    if (condition) this.visibility = View.GONE else this.visibility = View.VISIBLE
}