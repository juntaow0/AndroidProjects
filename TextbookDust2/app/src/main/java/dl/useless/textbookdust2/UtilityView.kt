package dl.useless.textbookdust2

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dl.useless.textbookdust2.data.Media
import dl.useless.textbookdust2.data.Nade
import kotlinx.android.synthetic.main.activity_utility_view.*
import java.io.IOException
import java.io.InputStream

class UtilityView : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_utility_view)
        var nade = intent.getSerializableExtra(UtilityMenu.KEY_NADE) as Nade
        var p = intent.getSerializableExtra(UtilityMenu.KEY_P) as Media
        var t = intent.getSerializableExtra(UtilityMenu.KEY_T) as Media
        var v = intent.getSerializableExtra(UtilityMenu.KEY_V) as Media
        initializeView(nade,p,t,v)
    }

    private fun initializeView(nade:Nade,p:Media,t:Media,v:Media){
        loadVideo(v.name)
        loadImages(p.name,t.name)
        tvNade.text = nade.name
        tvDes.text = nade.description
        tvMDes0.text = p.description
        tvMDes1.text = t.description
    }

    private fun loadImages(pname:String,tname:String){
        var pbit = getBitmapFromAsset(this,pname)
        var tbit = getBitmapFromAsset(this,tname)
        ivp.setImageBitmap(pbit)
        ivt.setImageBitmap(tbit)
    }

    private fun loadVideo(vname:String){
        var resid = resources.getIdentifier(vname.substringBefore('.'), "raw", packageName)
        var vuri = Uri.parse("android.resource://$packageName/raw/$resid")
        vvv.setVideoURI(vuri)
        vvv.setOnPreparedListener { mp ->
            mp.isLooping = true
        }
        vvv.start()
    }

    override fun onStop() {
        vvv.stopPlayback()
        super.onStop()
    }

    private fun getBitmapFromAsset(context: Context,path:String): Bitmap? {
        val assetManager = context.assets;
        var istr: InputStream
        var bitmap:Bitmap?= null
        var filepath = "img/$path"
        try{
            istr = assetManager.open(filepath)
            bitmap = BitmapFactory.decodeStream(istr)
        }catch (e: IOException){

        }
        return bitmap
    }
}
