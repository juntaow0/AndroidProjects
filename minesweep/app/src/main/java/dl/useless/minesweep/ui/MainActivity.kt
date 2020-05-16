package dl.useless.minesweep.ui

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewAnimationUtils
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import dl.useless.minesweep.R
import dl.useless.minesweep.gameview.MineSweepView
import dl.useless.minesweep.model.MineSweepModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val defaultMode = getString(R.string.mode_foot)
        val defaultFlag = getString(R.string.default_count)
        tStat.text = getString(R.string.mode_toggle, defaultMode)
        fStat.text = getString(R.string.flags_count, defaultFlag)
        btnRestart.setOnClickListener {
            mineView.restart()
            tStat.text = getString(R.string.mode_toggle, defaultMode)
            fStat.text = getString(R.string.flags_count, defaultFlag)
            revealBoard()
        }
        btnToggle.setOnClickListener{
            switchMode()
        }
        btnSubmit.setOnClickListener{
            if(MineSweepModel.checkWin()){
                Snackbar.make(MainActivity, R.string.submit_win, Snackbar.LENGTH_LONG).show()
            } else{
                Snackbar.make(MainActivity, R.string.submit_lose, Snackbar.LENGTH_LONG).show()
            }
        }
    }

    public fun showStatusMessage(message: String) {
        fStat.text = message
    }

    private fun switchMode(){
        var mode = MineSweepModel.switchMode()
        if (mode==(1).toShort()){
            tStat.text = getString(R.string.mode_toggle, getString(R.string.mode_flag))
        } else{
            tStat.text = getString(R.string.mode_toggle, getString(R.string.mode_foot))
        }
    }

    private fun revealBoard() {
        val x = mineView.getRight()
        val y = mineView.getBottom()

        val startRadius = 0
        val endRadius = Math.hypot(
            mineView.getWidth().toDouble(),
            mineView.getHeight().toDouble()
        ).toInt()

        val anim = ViewAnimationUtils.createCircularReveal(mineView, x, y, startRadius.toFloat(), endRadius.toFloat())

        mineView.setVisibility(View.VISIBLE)
        anim.start()
    }
}
