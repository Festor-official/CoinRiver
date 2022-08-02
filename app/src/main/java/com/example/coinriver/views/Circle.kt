package com.example.coinriver.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.example.coinriver.R

class Circle @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr:Int = 0
): View(context,attrs,defStyleAttr) {

    private val paint:Paint = Paint()
    private var rectangle: RectF? =  null
    private var margin:Float


    init {
        paint.isAntiAlias = true
        paint.color = ContextCompat.getColor(context, R.color.gray)
        paint.style  =Paint.Style.STROKE
        paint.strokeWidth = 135F
        margin  = 230F
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if(rectangle  == null){

            rectangle = if(width>height){
                RectF(0f +  margin,0f +  margin,height.toFloat() - margin,height.toFloat()-margin)
            } else{
                RectF(0f +  margin,0f +  margin,width.toFloat() - margin,width.toFloat()-margin)
            }


        }

        canvas.drawArc(rectangle!!,0f,360f,false,paint)



    }


}



























