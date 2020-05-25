package com.example.ktest.custom.views

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.os.Bundle
import android.os.Parcelable
import android.text.TextPaint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.example.ktest.R
import com.example.ktest.utils.extensions.getColorCompat
import com.example.ktest.utils.extensions.toStandardDigits
import com.example.ktest.utils.view.DistanceUnit
import com.example.ktest.utils.view.ViewUtils
import kotlin.math.max
import kotlin.math.min


class CustomRuler : View {

    private val colorPaintMask = Paint(Paint.ANTI_ALIAS_FLAG)
    private val grayPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val grayPaintReplace: Paint
    private val textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG)
    private val textPaintReplace: TextPaint

    private var rulerX = 0f

    private var markCmWidth = ViewUtils.dpToPx(20f)
        set(value) {
            field = value
            invalidate()
        }
    private var markHalfCmWidth = ViewUtils.dpToPx(15f)
        set(value) {
            field = value
            invalidate()
        }
    private var markMmWidth = ViewUtils.dpToPx(10f)
        set(value) {
            field = value
            invalidate()
        }

    private var pointerX = 0f

    var unit: DistanceUnit = DistanceUnit.CM
        set(value) {
            field = value
            invalidate()
        }

    private var coefficient = 1f
        set(value) {
            field = value
            invalidate()
        }

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        colorPaintMask.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)

        grayPaint.color = Color.DKGRAY
        grayPaintReplace = Paint(grayPaint)
        grayPaintReplace.color = Color.WHITE
        grayPaintReplace.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OVER)

        textPaint.textSize = ViewUtils.dpToPx(20f)
        textPaint.color = context.getColorCompat(R.color.colorPrimary)
        textPaint.textAlign = Paint.Align.CENTER
        textPaintReplace = TextPaint(textPaint)
        textPaintReplace.color = Color.WHITE
        textPaintReplace.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OVER)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        drawMarks(canvas, grayPaint, textPaint)

        canvas?.drawRect(left.toFloat(), 0f, rulerX, height.toFloat(), colorPaintMask)

        drawMarks(canvas, grayPaintReplace, textPaintReplace)
    }

    private fun drawMarks(canvas: Canvas?, paint: Paint, textPaint: Paint) {
        val oneMmInPx = ViewUtils.mmToPx(1f, coefficient, resources.displayMetrics)
        for (i in 1..1000) {
            val x = oneMmInPx * i
            when {
                i % 10 == 0 -> {
                    canvas?.drawLine(x, 0f, x, markCmWidth, paint)
                    canvas?.drawLine(x, height.toFloat(), x, height - markCmWidth, paint)
                    canvas?.drawText(
                        "${i / 10}".toStandardDigits(),
                        x,
                        markCmWidth + textPaint.textSize,
                        textPaint
                    )
                    canvas?.drawText(
                        "${i / 10}".toStandardDigits(),
                        x,
                        height - markCmWidth - ViewUtils.dpToPx(2f),
                        textPaint
                    )
                }
                i % 5 == 0 -> {
                    canvas?.drawLine(x, 0f, x, markHalfCmWidth, paint)
                    canvas?.drawLine(x, height.toFloat(), x, height - markHalfCmWidth, paint)
                }
                else -> {
                    canvas?.drawLine(x, 0f, x, markMmWidth, paint)
                    canvas?.drawLine(x, height.toFloat(), x, height - markMmWidth, paint)
                }
            }
            if (x >= width)
                break
        }
        canvas?.drawText(
            unit.getUnitString(
                ViewUtils.pxToIn(rulerX, coefficient, resources.displayMetrics),
                context
            )
            , width * .5f, height * .5f + textPaint.textSize * .5f, textPaint
        )
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                pointerX = event.x
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                val dx = event.x - pointerX
                rulerX += dx
                // clamp
                rulerX = max(0f, min(width.toFloat(), rulerX))
                pointerX = event.x
                invalidate()
                return true
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                return false
            }
        }
        return false
    }

    override fun onSaveInstanceState(): Parcelable? {
        super.onSaveInstanceState()
        val bundle = Bundle()
        bundle.putParcelable("superState", super.onSaveInstanceState())
        bundle.putFloat("rulerX", rulerX)
        bundle.putFloat("coefficient", coefficient)
        return bundle
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        var nState = state
        val bundle = nState as Bundle
        rulerX = bundle.getFloat("rulerX")
        coefficient = bundle.getFloat("coefficient")
        nState = bundle.getParcelable("superState")
        super.onRestoreInstanceState(nState)
    }


}