package dl.useless.andwallet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnOK.setOnClickListener {
            if (etPin.text.isNotEmpty()){
                if (etPin.text.toString()==getString(R.string.pass_1234)){
                    startActivity(Intent(this@MainActivity, Expense::class.java))
                    finish()
                } else{
                    etPin.error = getString(R.string.wrong_pin)
                }
            } else {
                etPin.error = getString(R.string.can_not_be_empty)
            }
        }
    }
}
