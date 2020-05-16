package dl.useless.minesweep.model

import java.util.*

object MineSweepModel {
    val EMPTY: Short = 0
    val MINE: Short = 1
    val NUMBER: Short = 3
    var mode: Short = 0 // on foot, flag = 1
    var flagCount = 3
    var mine1: Int = 0
    var mine2: Int = 0
    var mine3: Int = 0
    var submitted: Boolean = false
    var gameState: Boolean = false
    var triggered: Boolean = false
    var gameover: Boolean = false

    private val model = arrayOf(
        arrayOf(
            MineSweepField(),
            MineSweepField(),
            MineSweepField(),
            MineSweepField(),
            MineSweepField()
        ),
        arrayOf(
            MineSweepField(),
            MineSweepField(),
            MineSweepField(),
            MineSweepField(),
            MineSweepField()
        ),
        arrayOf(
            MineSweepField(),
            MineSweepField(),
            MineSweepField(),
            MineSweepField(),
            MineSweepField()
        ),
        arrayOf(
            MineSweepField(),
            MineSweepField(),
            MineSweepField(),
            MineSweepField(),
            MineSweepField()
        ),
        arrayOf(
            MineSweepField(),
            MineSweepField(),
            MineSweepField(),
            MineSweepField(),
            MineSweepField()
        )
    )

    init {
        setUp()
    }

    fun resetModel() {
        for (i in 0..4){
            for (j in 0..4){
                model[i][j].type= EMPTY
                model[i][j].isTouched = false
                model[i][j].isFlaged = false
                model[i][j].minesAround = 0
            }
        }
        setUp()
    }

    fun getFieldType(x: Int, y: Int):Short{
        return model[x][y].type
    }

    fun getMinesAround(x: Int, y: Int): Int{
        return model[x][y].minesAround
    }

    fun getTouch(x: Int, y: Int): Boolean{
        return model[x][y].isTouched
    }

    fun getFlag(x: Int, y: Int): Boolean{
        return model[x][y].isFlaged
    }

    fun setFlag(x: Int, y: Int) {
        if (!model[x][y].isTouched && flagCount>-1){
            if (!(flagCount==0 && !model[x][y].isFlaged)){
                model[x][y].isFlaged = ! model[x][y].isFlaged
                if (model[x][y].isFlaged){
                    flagCount-=1
                }else{
                    flagCount+=1
                }
            }
        }
    }

    fun setFoot(x: Int, y: Int) {
        if (!model[x][y].isFlaged){
            model[x][y].isTouched = true
        }
    }

    fun checkWin(): Boolean{
        var (x1,y1) = fieldMapping(mine1)
        var (x2,y2) = fieldMapping(mine2)
        var (x3,y3) = fieldMapping(mine3)
        submitted = true
        gameover = true
        gameState = (model[x1][y1].isFlaged && model[x2][y2].isFlaged && model[x3][y3].isFlaged)
        return gameState
    }

    fun switchMode(): Short{
        mode = ((mode+1)%2).toShort()
        return mode
    }

    private fun setUp() {
        mode = 0
        flagCount = 3
        submitted = false
        gameState = false
        triggered = false
        gameover = false
        generateNumber()
        plantBomb(mine1)
        plantBomb(mine2)
        plantBomb(mine3)
    }

    private fun generateNumber() {
        var numbers = (0..24)
        var rand = Random(System.currentTimeMillis())
        var shuffled = numbers.shuffled(rand)
        mine1 = shuffled[0]
        mine2 = shuffled[1]
        mine3 = shuffled[2]
    }

    private fun plantBomb(m: Int) {
        var (x, y) = fieldMapping(m)
        model[x][y].type = MINE
        addNumberFields(x,y)
    }

    private fun addNumberFields(x:Int,y:Int){
        var (xc,yc) = getCoordinates(x,y)
        for (i in 0..2){
            for (j in 0..2){
                if (validateCoordinate(xc[i],yc[j])){
                    if (model[xc[i]][yc[j]].type != MINE){
                        model[xc[i]][yc[j]].type = NUMBER
                        model[xc[i]][yc[j]].minesAround += 1
                    }
                }
            }
        }
    }

    private fun getCoordinates(x:Int,y:Int):Pair<Array<Int>,Array<Int>>{
        var xc = arrayOf<Int>(x,x-1,x+1)
        var yc = arrayOf<Int>(y,y-1,y+1)
        return Pair<Array<Int>,Array<Int>>(xc,yc)
    }

    private fun validateCoordinate(x:Int,y:Int):Boolean {
        if (x in 0..4 && y in 0..4){
            return true
        }
        return false
    }

    private fun fieldMapping(index: Int): Pair<Int, Int> {
        var x = index % 5
        var y = ((index - (index % 5)) / 5)
        return Pair(x, y)
    }
}