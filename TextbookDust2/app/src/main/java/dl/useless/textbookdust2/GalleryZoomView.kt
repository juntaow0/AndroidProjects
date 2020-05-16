package dl.useless.textbookdust2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_gallery_zoom_view.*

class GalleryZoomView : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery_zoom_view)
        val resid = intent.getIntExtra(Gallery.KEY_INT,0)
        photoZoom.setImageResource(resid)
    }
}
