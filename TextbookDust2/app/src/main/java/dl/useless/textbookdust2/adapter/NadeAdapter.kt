package dl.useless.textbookdust2.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dl.useless.textbookdust2.R
import dl.useless.textbookdust2.data.Nade
import dl.useless.textbookdust2.touch.RecyclerTouchListener
import kotlinx.android.synthetic.main.item_row.view.*

class NadeAdapter: RecyclerView.Adapter<NadeAdapter.ViewHolder> {
    var context: Context
    var itemClickListener: RecyclerTouchListener
    var nadeList = mutableListOf<Nade>()

    constructor(context: Context, itemClickListener: RecyclerTouchListener, listNades: List<Nade>) {
        this.context = context
        this.itemClickListener = itemClickListener
        nadeList.addAll(listNades)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(
            R.layout.item_row, parent, false
        )
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return nadeList.size
    }

    fun refreshView(listNades: List<Nade>){
        nadeList.clear()
        nadeList.addAll(listNades)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var nade = nadeList.get(holder.adapterPosition)
        holder.bind(nade, itemClickListener)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var tvName = view.tvName
        var itemIcon = view.nadeIcon
        fun bind(nade:Nade, clickListener: RecyclerTouchListener){
            tvName.text = nade.name
            var resID = R.drawable.nades_flashbang
            if (nade.type==1){
                resID = R.drawable.nades_flashbang
            } else if (nade.type==2){
                resID = R.drawable.nades_smokegrenade
            }else if (nade.type==3){
                resID = R.drawable.nades_incgrenade
            }
            itemIcon.setImageResource(resID)
            itemView.setOnClickListener {
                clickListener.onItemTouched(nade)
            }
        }
    }
}