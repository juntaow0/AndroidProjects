package dl.useless.minesweep.gameview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.google.android.material.snackbar.Snackbar
import dl.useless.minesweep.R
import dl.useless.minesweep.model.MineSweepModel
import dl.useless.minesweep.ui.MainActivity
import kotlinx.android.synthetic.main.activity_main.view.*

class MineSweepView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    var paintBackGround = Paint()
    var paintLine = Paint()
    var paintText = Paint()
    var bitmapMine = BitmapFactory.decodeResource(context?.resources, R.drawable.mine)
    var bitmapFlag = BitmapFactory.decodeResource(context?.resources, R.drawable.flag)
    var bitmapEmpty = BitmapFactory.decodeResource(context?.resources, R.drawable.empty)
    var bitmapRedMine = BitmapFactory.decodeResource(context?.resources, R.drawable.rmine)
    var bitmapWrongFlag = BitmapFactory.decodeResource(context?.resources, R.drawable.wflag)

    init {
        paintBackGround.color = Color.GRAY
        paintBackGround.style = Paint.Style.FILL
        paintLine.color = Color.WHITE
        paintLine.style = Paint.Style.STROKE
        paintLine.strokeWidth = 2f
        paintText.textSize = 180f
        paintText.color = Color.BLUE
    }
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val w = View.MeasureSpec.getSize(widthMeasureSpec)
        val h = View.MeasureSpec.getSize(heightMeasureSpec)
        val d = if (w == 0) h else if (h == 0) w else if (w < h) w else h
        setMeasuredDimension(d, d)
    }

    fun restart(){
        MineSweepModel.resetModel()
        invalidate()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        bitmapMine = Bitmap.createScaledBitmap(bitmapMine,width/5,height/5,false)
        bitmapFlag = Bitmap.createScaledBitmap(bitmapFlag,width/5,height/5,false)
        bitmapEmpty = Bitmap.createScaledBitmap(bitmapEmpty,width/5,height/5,false)
        bitmapRedMine = Bitmap.createScaledBitmap(bitmapRedMine,width/5,height/5,false)
        bitmapWrongFlag = Bitmap.createScaledBitmap(bitmapWrongFlag,width/5,height/5,false)
    }

    override fun onDraw(canvas: Canvas?) {
        drawBoard(canvas)
        drawBlocks(canvas)
        update(canvas)
        invalidate()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN && !MineSweepModel.gameover) {
            val tX = event.x.toInt() / (width / 5)
            val tY = event.y.toInt() / (height / 5)
            if (MineSweepModel.mode==0.toShort()){
                if (tX < 5 && tY < 5 && !MineSweepModel.getTouch(tX,tY)) {
                    MineSweepModel.setFoot(tX,tY)
                    if (MineSweepModel.getFieldType(tX,tY)==MineSweepModel.MINE){
                        MineSweepModel.triggered = true
                        MineSweepModel.gameover = true
                        Snackbar.make(rootView,R.string.step_mine, Snackbar.LENGTH_LONG).show()
                    }
                }
            } else {
                MineSweepModel.setFlag(tX,tY)
                (context as MainActivity).showStatusMessage(
                    context.resources.getString(R.string.flags_count, MineSweepModel.flagCount.toString())
                )
            }
        }
        return true
    }

    private fun update(canvas: Canvas?){
        for (i in 0..4) {
            for (j in 0..4) {
                drawOneField(canvas,i,j)
            }
        }
    }

    private fun drawOneField(canvas: Canvas?,x:Int,y:Int){
        var fieldType = MineSweepModel.getFieldType(x,y)
        var isTouched = MineSweepModel.getTouch(x,y)
        var isFlaged = MineSweepModel.getFlag(x,y)
        var submitted = MineSweepModel.submitted
        var gameState = MineSweepModel.gameState
        var triggered = MineSweepModel.triggered
        if (fieldType==MineSweepModel.MINE && !isTouched && !isFlaged && ((submitted&&!gameState)||triggered)){
            canvas?.drawBitmap(bitmapMine,width*x/5f,height*y/5f,null)
        }else if (fieldType==MineSweepModel.MINE && isTouched && !isFlaged && triggered){
            canvas?.drawBitmap(bitmapRedMine,width*x/5f,height*y/5f,null)
        }else if (fieldType==MineSweepModel.NUMBER && isTouched && !isFlaged){
            drawNumber(canvas,x,y)
        }else if (fieldType!=MineSweepModel.MINE && !isTouched && isFlaged && ((submitted&&!gameState)||triggered)){
            canvas?.drawBitmap(bitmapWrongFlag,width*x/5f,height*y/5f,null)
        }else if (!isTouched&&isFlaged){
            canvas?.drawBitmap(bitmapFlag,width*x/5f,height*y/5f,null)
        }else if (!isTouched&&!isFlaged){
            canvas?.drawBitmap(bitmapEmpty,width*x/5f,height*y/5f,null)
        }
    }

    private fun drawNumber(canvas: Canvas?,x:Int,y:Int){
        var count = MineSweepModel.getMinesAround(x,y)
        if (count==1){
            paintText.color = Color.BLUE
        }else if (count==2){
            paintText.color = Color.YELLOW
        }else{
            paintText.color = Color.RED
        }
        canvas?.drawText(count.toString(),width*(x)/5+(width/20f),height*(y+1)/5-(height/25f),paintText)
    }

    private fun drawBoard(canvas: Canvas?){
        canvas?.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paintBackGround)
    }

    private fun drawBlocks(canvas: Canvas?) {
        paintLine.color = Color.WHITE
        for (i in 1..4){
            canvas?.drawLine(width.toFloat()  * i /5, 0f, width.toFloat() * i / 5, height.toFloat(), paintLine)
        }
        for (i in 1..4){
            canvas?.drawLine(0f, height.toFloat() * i / 5, width.toFloat(), height.toFloat() * i/ 5, paintLine)
        }
    }
}