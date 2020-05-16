package dl.useless.textbookdust2.touch

import dl.useless.textbookdust2.data.Nade

interface RecyclerTouchListener {
    fun onItemTouched(nade: Nade)
    fun onPhotoTouched(resid: Int)
}