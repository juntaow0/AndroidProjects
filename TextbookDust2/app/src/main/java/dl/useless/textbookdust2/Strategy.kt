package dl.useless.textbookdust2

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_strategy.*

class Strategy : AppCompatActivity(), MediaPlayer.OnPreparedListener {

    private lateinit var mediaPlayer: MediaPlayer
    var playStarted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_strategy)

        btnPlayTone.setOnClickListener {
            if (!playStarted){
                mediaPlayer = MediaPlayer.create(this, R.raw.rushb)
                mediaPlayer.setOnPreparedListener(this)
            }
        }
    }

    override fun onPrepared(mp: MediaPlayer?) {
        if (!mediaPlayer.isPlaying){
            mediaPlayer.start()
            playStarted = true
        }
    }

    override fun onStop() {
        if (playStarted){
            mediaPlayer.stop()
        }
        super.onStop()
    }
}
