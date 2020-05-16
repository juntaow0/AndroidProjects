package dl.useless.textbookdust2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainMenu : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)
    }

    fun utilityMenu(view:View){
        startActivity(Intent(this@MainMenu, UtilityMenu::class.java))
    }

    fun stratMenu(view: View){
        startActivity(Intent(this@MainMenu,Strategy::class.java))
    }

    fun mapOverview(view: View){
        startActivity(Intent(this@MainMenu,MapOverview::class.java))
    }

    fun gallery(view: View){
        startActivity(Intent(this@MainMenu,Gallery::class.java))
    }
}
