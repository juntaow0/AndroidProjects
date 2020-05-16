package dl.useless.textbookdust2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import dl.useless.textbookdust2.adapter.GalleryAdapter
import dl.useless.textbookdust2.data.Nade
import dl.useless.textbookdust2.data.PhotoManager
import dl.useless.textbookdust2.touch.RecyclerTouchListener
import kotlinx.android.synthetic.main.activity_gallery.*

class Gallery : AppCompatActivity(), RecyclerTouchListener {

    companion object{
        const val KEY_INT = "KEY_INT"
    }

    private lateinit var galleryAdapter: GalleryAdapter
    private lateinit var layoutManager: GridLayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)
        initRecyclerView()
    }

    private fun initRecyclerView(){
        val photoList = PhotoManager().makePhotos()
        galleryAdapter = GalleryAdapter(this, this, photoList)
        layoutManager = GridLayoutManager(this,2)
        recyclerPhotos.adapter = galleryAdapter
        recyclerPhotos.layoutManager = layoutManager
        recyclerPhotos.setHasFixedSize(true)
    }

    override fun onPhotoTouched(resid:Int) {
        var largeView = Intent(this@Gallery,GalleryZoomView::class.java)
        largeView.putExtra(KEY_INT,resid)
        startActivity(largeView)
    }

    override fun onItemTouched(nade: Nade) {

    }
}
