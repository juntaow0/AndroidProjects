package dl.useless.shopping

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dl.useless.shopping.data.Item
import kotlinx.android.synthetic.main.activity_detail.*

class ItemDetail : AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        var item = intent.getSerializableExtra(ScrollingActivity.KEY_VIEW) as Item

        if(item.category == 0) {
            ivIcon.setImageResource(R.drawable.cpu)
        } else if(item.category == 1) {
            ivIcon.setImageResource(R.drawable.cooler)
        } else if(item.category == 2) {
            ivIcon.setImageResource(R.drawable.motherboard)
        } else if(item.category == 3) {
            ivIcon.setImageResource(R.drawable.memory)
        } else if(item.category == 4) {
            ivIcon.setImageResource(R.drawable.storage)
        } else if(item.category == 5) {
            ivIcon.setImageResource(R.drawable.videocard)
        } else if(item.category == 6) {
            ivIcon.setImageResource(R.drawable.psu)
        } else if(item.category == 7) {
            ivIcon.setImageResource(R.drawable.casenzxt)
        } else if(item.category == 8) {
            ivIcon.setImageResource(R.drawable.monitor)
        } else if(item.category == 9) {
            ivIcon.setImageResource(R.drawable.peripherals)
        } else if(item.category == 10) {
            ivIcon.setImageResource(R.drawable.videogame)
        } else if(item.category == 11) {
            ivIcon.setImageResource(R.drawable.mugi)
        }

        tvName.text = item.itemName
        tvPrice.text = getString(R.string.display_price, item.itemPrice)
        tvQuantity.text = getString(R.string.display_quantity, item.quantity)
        tvData.text = item.description

        btnBack.setOnClickListener {
            finish()
        }
    }
}