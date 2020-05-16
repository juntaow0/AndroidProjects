package dl.useless.ezweather

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var splashAnim = AnimationUtils.loadAnimation(this, R.anim.splash_anim)
        var mainIntent = Intent(this@MainActivity, ActivityCities::class.java)
        splashAnim.setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationRepeat(animation: Animation?) {
            }
            override fun onAnimationEnd(animation: Animation?) {
                startActivity(mainIntent)
                finish()
            }
            override fun onAnimationStart(animation: Animation?) {
            }
        })
        animObj.startAnimation(splashAnim)
    }
}
