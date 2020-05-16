package dl.useless.ezweather.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dl.useless.ezweather.R
import dl.useless.ezweather.touch.RecyclerTouchListener
import kotlinx.android.synthetic.main.city_row.view.*

class CityAdapter : RecyclerView.Adapter<CityAdapter.ViewHolder> {

    var context: Context
    var itemClickListener: RecyclerTouchListener
    var cityList = mutableListOf<String>()

    constructor(context: Context, itemClickListener: RecyclerTouchListener) {
        this.context = context
        this.itemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(
            R.layout.city_row, parent, false
        )
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return cityList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var city = cityList.get(holder.adapterPosition)
        holder.bind(city,itemClickListener)

        holder.btnDelete.setOnClickListener {
            removeCity(holder.adapterPosition)
        }
    }

    fun addCity(city: String) {
        cityList.add(city)
        notifyItemInserted(cityList.lastIndex)
    }

    private fun removeCity(index: Int) {
        cityList.removeAt(index)
        notifyItemRemoved(index)
    }

    inner class ViewHolder(cityView: View) : RecyclerView.ViewHolder(cityView){
        var tvName = cityView.tvName
        var btnDelete = cityView.btnDelete

        fun bind(city:String, clickListener: RecyclerTouchListener){
            tvName.text = city
            itemView.setOnClickListener {
                clickListener.onItemTouched(city)
            }
        }
    }
}