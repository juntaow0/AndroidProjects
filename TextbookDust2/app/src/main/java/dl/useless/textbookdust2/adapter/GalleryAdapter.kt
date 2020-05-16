package dl.useless.textbookdust2.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dl.useless.textbookdust2.R
import dl.useless.textbookdust2.data.Photo
import dl.useless.textbookdust2.touch.RecyclerTouchListener
import kotlinx.android.synthetic.main.photo_cell.view.*

class GalleryAdapter: RecyclerView.Adapter<GalleryAdapter.ViewHolder> {
    var context: Context
    var itemClickListener: RecyclerTouchListener
    var photoList = mutableListOf<Photo>()

    constructor(context: Context, itemClickListener: RecyclerTouchListener, listPhotos: List<Photo>) {
        this.context = context
        this.itemClickListener = itemClickListener
        photoList.addAll(listPhotos)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(
            R.layout.photo_cell, parent, false
        )
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return photoList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var photo = photoList.get(holder.adapterPosition)
        holder.bind(photo, itemClickListener)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var photoName = view.title
        var photoItem = view.img
        fun bind(photo:Photo, clickListener: RecyclerTouchListener){
            photoName.text = photo.name
            var resID = photo.resid
            photoItem.setImageResource(resID)
            itemView.setOnClickListener {
                clickListener.onPhotoTouched(photo.presid)
            }
        }
    }
}