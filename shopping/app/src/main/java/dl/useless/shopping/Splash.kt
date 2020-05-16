package dl.useless.shopping

import android.content.Intent
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_splash.*

class Splash : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        var splashAnim = AnimationUtils.loadAnimation(this, R.anim.splash_anim)
        val mainIntent = Intent(this, ScrollingActivity::class.java)
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
        splashscreen.startAnimation(splashAnim)
    }
}