package com.example.coinriver.views


import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import com.example.coinriver.R
import com.example.coinriver.data.CircleData
import com.example.coinriver.data.Expenses
import java.lang.Math.*

class CircleParts @JvmOverloads constructor(
    context: Context,
    attrs:AttributeSet? =  null,
    defStyleAttr:Int=0,
): View(context,attrs,defStyleAttr) {

    private var paint:Paint = Paint()
    private val paintText = Paint()
    private var rectangle: RectF? = null
    private var rectangleBigger: RectF? = null
    private var margin:Float

    private var spending:List<Expenses> = listOf<Expenses>()

    private var startAngle =  -90f
    private var sweepAngle =  0f
    private var fullSum = 0f

    var oneAngle = 0f
    private var index = -1
    var newSweepAngle = 0f
    private var drawnCircleParts = arrayListOf<CircleData>()
    private var categoryList  =HashMap<String,Float>()
    private var colorList = arrayListOf<Int>(
        Color.argb(255,139,0,255),
        Color.argb(255,182,0,255),
        Color.argb(255,156, 70, 175),
        Color.argb(255,76, 70, 195),
        Color.BLUE,
        Color.argb(255,96, 10, 125),
        Color.argb(255,45, 80, 175),
        Color.argb(255,180,100 ,80 ),
        Color.argb(255,0,155 ,118 ),
        Color.argb(255,127,255 ,212 ),

        Color.argb(255,216,222 ,186 ),
        Color.argb(255,245,245 ,220 ),
        Color.argb(255,250,219 ,200 ),

        )


    fun setSpending(spend:List<Expenses>){
        this.spending = spend
        fullSum = spending.sumOf { it.expenses_sum }.toFloat()
        spend.forEach(){
            if(it.expenses_category !in categoryList){
                categoryList[it.expenses_category] = it.expenses_sum.toFloat()
            }else{
                categoryList[it.expenses_category] = categoryList[it.expenses_category]!! + it.expenses_sum.toFloat()
            }
        }
        Log.v("MainActivity",categoryList.toString())
        oneAngle = 360/fullSum
    }

    init{
        paintText.textSize=48f
        paintText.color = ContextCompat.getColor(context, R.color.purple_200)
        paint.isAntiAlias = true
        paint.color =ContextCompat.getColor(context, R.color.purple_500)
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 135f
        margin =  230f


    }
    val marginBigger = margin-100

    override fun onDraw(canvas: Canvas){

        super.onDraw(canvas)

        if(sweepAngle>=newSweepAngle){
            index+=1
            startAngle += newSweepAngle
            if(sweepAngle!=0f){
                drawnCircleParts.add(CircleData((startAngle -newSweepAngle),newSweepAngle))
            }



            if(index <=categoryList.size-1){
                //newSweepAngle =  oneAngle * spending.get(index).expenses_sum
                newSweepAngle =  oneAngle * categoryList.values.toList().get(index)
            }
            sweepAngle = 0f

        }


        if (rectangle == null) {

            rectangle = if (width > height) {
                RectF(0f + margin, 0f + margin, height.toFloat() - margin, height.toFloat() - margin)
            } else {
                RectF(0f + margin, 0f + margin, width.toFloat() - margin, width.toFloat() - margin)
            }

            rectangleBigger = if (width > height) {
                RectF(
                    0f + marginBigger,
                    0f + marginBigger,
                    height.toFloat() - marginBigger,
                    height.toFloat() - marginBigger
                )
            } else {
                RectF(
                    0f + marginBigger,
                    0f + marginBigger,
                    width.toFloat() - marginBigger,
                    width.toFloat() - marginBigger
                )
            }

        }

        var range = rectangleBigger!!.right - rectangleBigger!!.left
        var r = range / 2
        sweepAngle += 3f
        canvas.drawArc(rectangle!!, startAngle, sweepAngle, false, paint.apply { color = colorList[index]})
        var colorNum = 0

        drawnCircleParts.forEach {

            canvas.drawArc(rectangle!!, it.startAngle, it.sweepAngle, false, Paint().apply {
                color = colorList[colorNum]
                colorNum+=1
                setShadowLayer(r/2,0f,0f,colorList[colorNum])
                isAntiAlias = true
                style = Paint.Style.STROKE
                strokeWidth = 135f
                margin =  230f })

        }

        if (index <= categoryList.size-1) {
            invalidate()
        } else {
            var nameNum =0
            drawnCircleParts.forEach {
                //start90 sweep 181 end271 180.5 359.5
                var i:Double = 0.0

                val sumAngle = it.startAngle+it.sweepAngle
                val startAngle = it.startAngle+90
                if (it.sweepAngle<180){
                    i = 180-(it.sweepAngle/2+startAngle ).toDouble()

                }else{
                    i = 180-(it.sweepAngle/2).toDouble()
                }
//                        if(it.startAngle>0){
//                            i -=it.startAngle*2
//                            Log.v("MainActivity","start:${it.startAngle} swep:${it.sweepAngle} i:$i")
//                        }

                if(sumAngle>180){
                    val oneSide = 180-it.startAngle-90 //64
                    //15
                    val secondSide = sumAngle+90-180 //206

                    if(secondSide-oneSide<0){
                    }
                    else{
                        i = (360- ((secondSide-oneSide)/2).toDouble())
                    }
                }

                val angle = toRadians(i)
        if (width > height) {
            var yTest =cos(angle)*(r+10)
            var xTest = sin(angle)*(r+10)
            xTest+=height/2-20
            yTest+=height/2
            canvas.drawText(categoryList.keys.toList()[nameNum],xTest.toFloat(),yTest.toFloat(),paintText.apply{})

        } else {
//                        var yTest =cos(angle)*r
//                        var xTest = sin(angle)*r
//                        xTest+=marginBigger*4-20
//                        yTest+=marginBigger*4+30
                        var yTest =cos(angle)*(r+10)
                        var xTest = sin(angle)*(r+10)
                        xTest+=width/2-20
                        yTest+=width/2
//                        canvas.drawText("food",xTest.toFloat(),yTest.toFloat(),paintText.apply{})
                        canvas.drawText(categoryList.keys.toList()[nameNum],xTest.toFloat(),yTest.toFloat(),paintText.apply{})
                    }
                    nameNum+=1
//                for(i in 0..360 step(10)){
//                    //x**2 + y**2 = r**2
//                    var angle = toRadians(i.toDouble())
//                    var yTest =cos(angle)*(r+10)
//                    var xTest = sin(angle)*(r+10)
//                    xTest+=width/2-20
//                    yTest+=width/2
//                    canvas.drawText("$i",xTest.toFloat(),yTest.toFloat(),paintText)
//                }
            }
        }
    }
}





