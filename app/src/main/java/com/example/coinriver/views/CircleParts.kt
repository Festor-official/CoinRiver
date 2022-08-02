package com.example.coinriver.views


import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.example.coinriver.R
import com.example.coinriver.data.CircleData
import com.example.coinriver.data.Expenses
import java.lang.Math.*
import java.util.*


data class TextXY(
    var xCor:Float,
    var yCor:Float,
)

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

    private var startAngle =  -90f
    private var sweepAngle =  0f
    private var fullSum = 0f
    private var spending = arrayListOf<Expenses>(Expenses(2,"food",200, Date(),false),Expenses(2,"food",100, Date(),false),Expenses(0,"food",29, Date(),false),Expenses(1,"food",200, Date(),false),Expenses(0,"food",70, Date(),false))
    var oneAngle = 0f
    var index = -1
    var newSweepAngle = 0f
    private var drawnCircleParts = arrayListOf<CircleData>()
    private var colorList = arrayListOf<Int>(Color.argb(255,139,0,255),Color.argb(255,182,0,255),Color.argb(255,156, 70, 175),Color.argb(255,76, 70, 195),Color.BLUE,Color.argb(255,96, 10, 125))




    init{
        paintText.textSize=48f
        paintText.color = ContextCompat.getColor(context, R.color.purple_200)
        paint.isAntiAlias = true
        paint.color =ContextCompat.getColor(context, R.color.purple_500)
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 135f
        margin =  230f

        fullSum = spending.sumOf { it.expenses_sum }.toFloat()

        oneAngle = 360/fullSum

    }
    val marginBigger = margin-100
    var xCor = 0f
    var yCor = 0f

    override fun onDraw(canvas: Canvas){
        super.onDraw(canvas)
        if(sweepAngle>=newSweepAngle){
            index+=1
            startAngle += newSweepAngle
            if(sweepAngle!=0f){
                drawnCircleParts.add(CircleData((startAngle -newSweepAngle),newSweepAngle))
            }



            if(index <=spending.size-1){
                newSweepAngle =  oneAngle * spending.get(index).expenses_sum
            }
            //Log.v("MainActivity","start:${startAngle -sweepAngle},sweep:$sweepAngle,new:$newSweepAngle")
            sweepAngle = 0f

        }


        // 1=8 10=12 20=8 30=2 40 =-6 50 =-12 60 =-18 70=-30 80 =38 90 =-45
        //9,22,28,32,32,38,42,43,44,45
        //240,280
        //0..360
        //12,6,4,0,6,4,1,1,1
        //     4,   -4,   -4,  -8,    -6      ,-6     -12    ,-8    ,-7
        //14 6 4 2 2
//        for (i in 0..60 step 6 ) {
        //10 = 1 20=2 30=3 40=6 50=9 60= 10 70=16 80=20 90=24-22
//            startAngle = -90f
//            sweepAngle = i.toFloat()


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


        //canvas.drawArc(100F,100F,100F,100F,startAngle.toFloat(), sweepAngle.toFloat(), false, paint)


        if (width > height) {



        } else {
            var range = rectangleBigger!!.right - rectangleBigger!!.left
            var r = range / 2
            sweepAngle += 3f
            canvas.drawArc(rectangle!!, startAngle, sweepAngle, false, paint.apply { color = colorList[index]})
            var colorNum = 0

            drawnCircleParts.forEach {

                canvas.drawArc(rectangle!!, it.startAngle, it.sweepAngle, false, Paint().apply {
                color = colorList[colorNum]
                colorNum+=1
                isAntiAlias = true
                style = Paint.Style.STROKE
                strokeWidth = 135f
                margin =  230f })

            }


            if (index <= spending.size-1) {
                invalidate()
            } else {
                    drawnCircleParts.forEach {
                        //start90 sweep 181 end271 180.5 359.5
                        var i:Double = 0.0
                        var sumAngle = it.startAngle+it.sweepAngle
                        var startAngle = it.startAngle+90
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

                            var oneSide = 180-it.startAngle-90 //64
                            //15
                            var secondSide = sumAngle+90-180 //206

                            if(secondSide-oneSide<0){

                            }
                            else{
                                i = (360- ((secondSide-oneSide)/2).toDouble())
                            }
                        }


                        var angle = toRadians(i)

//                        var yTest =cos(angle)*r
//                        var xTest = sin(angle)*r
//                        xTest+=marginBigger*4-20
//                        yTest+=marginBigger*4+30
                        var yTest =cos(angle)*(r+10)
                        var xTest = sin(angle)*(r+10)
                        xTest+=width/2-20
                        yTest+=width/2
                        canvas.drawText("food",xTest.toFloat(),yTest.toFloat(),paintText.apply{})



                    }

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






//                xCor =  range- (range *sweepAngle/360)+200
//440
//                if(sweepAngle<=180){
//                    yCor = (range)/2 + (range+200)/2*(sweepAngle/180)+200
//                }else{
//                    yCor = (range+200) - (range+200)/2*((sweepAngle-180)/180)
//                }



//width.toFloat()* 0.3 - margin
//            width - margin  * angle/180 /2
//(x - (width/2))**2+(y-(width/2))**2 = r**2
//y = cutSliceLength/2
//x = r**2-(y-width/2)**2
//x = sqrt(x)+width/2
//            for(i in 360){
//
//            }
//                val r = (width - margin * 2) / 2
//                val pCircle = 2 * r * 3.1415
//                val sliceAngle = sweepAngle / 360
//                val cutSliceLength = pCircle * sliceAngle
//                val angleSum = startAngle + sweepAngle
//
//                if (angleSum >= 0) {
//                    //yCor =margin + (width - margin * 2) / 2 + (width * (sweepAngle / 180)) / 2
//                    //yCor =width - width*((sweepAngle-180)/360)+(sweepAngle/180*margin)
//                    var pos = 0f
//                    var angle = 0f
//                    //r = 440 width/2..width/4 1..0.5 0..90
//                    //pos = width- r * ((180-sweepAngle)/180)
//                    if (sweepAngle <= 90) {
//                        pos = (width / 2 + cutSliceLength * (180 / (sweepAngle + 90)) / 2).toFloat()
//                    }
//
//
//                    if (sweepAngle <= 180 && sweepAngle > 90) {
//                        pos = (width - width * 0.25).toFloat()
//                        angle = sweepAngle - 90
//                        //width - width*0.25..width*1
//                        var length = (width * 0.5 * (angle / 90)).toFloat()
//                        pos += length
//                    }
//                    if (sweepAngle <= 270 && sweepAngle > 180) {
//                        pos = width.toFloat()
//                        angle = sweepAngle - 180
//                        //width - width*0.25..width*1
//                        var length = (width * 0.25 * (angle / 90)).toFloat()
//                        pos -= length
//                        //!!!!!!
//                    }
//                    if (sweepAngle <= 360 && sweepAngle > 270) {
//                        pos = width * 0.75f
//                        angle = sweepAngle - 270
//                        //width - width*0.25..width*1
//                        var length = (width * 0.25 * (angle / 90)).toFloat()
//                        pos -= length
//                    }
//                    yCor = pos
//                    //yCor = width*(180/sweepAngle)
//                } else {
//                    //yCor = width*(sweepAngle/180)
//                    yCor = width - (cutSliceLength / 2).toFloat()
//                    //yCor =width*((sweepAngle+180)/360)+(sweepAngle/180*margin)
//                }

// width-margin -  sweepAngle/90*r +270
//            xCor = if(sweepAngle<=90){
//                // width-((sweepAngle/180) /2 +200)
//                width - (width-margin*2)*(sweepAngle/180) + margin
//
//                //width - ((width-margin*2)/2 * (sweepAngle/180)  +margin)
//            } else{
//                width - width*(sweepAngle/360)
//            }

//xCor = width-margin
//                xCor = r.pow(2) - (yCor - width / 2 - margin).pow(2)


//yCor += (135*(sweepAngle/360))
//                xCor = (sqrt(xCor.toDouble()) + width / 2).toFloat()
//                if (sweepAngle > 180) {
//                    xCor = width - xCor
//
//                }




