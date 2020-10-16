package com.nntuan317.yum.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import com.nntuan317.yum.R
import com.nntuan317.yum.extensions.dpToPx

// AAbAAbAAbAA : dash Gap = b, AA = dash length

private const val DEFAULT_DIVIDER_COLOR = 0xFFFFFFFF.toInt()
private const val DEFAULT_DIVIDER_THICKNESS = 1.0F // dp
private const val DEFAULT_DIVIDER_TEXT_PADDING = 1.0F // dp
private const val DEFAULT_DASH_LENGTH = 2.0F // dp
private const val DEFAULT_DASH_GAP = 1.0F // dp

class SeparatorTextView(
    context: Context,
    attributeSet: AttributeSet?
) : AppCompatTextView(context, attributeSet) {

    private var paint: Paint
    private val divTextPadding: Float

    init {
        val ta = context.obtainStyledAttributes(attributeSet, R.styleable.SeparatorTextView)
        val color: Int
        val divThickness: Float
        val dashLength: Float
        val dashGap: Float
        try {
            color = ta.getColor(R.styleable.SeparatorTextView_dividerColor, DEFAULT_DIVIDER_COLOR)
            divThickness = ta.getDimension(
                R.styleable.SeparatorTextView_dividerThickness,
                context.dpToPx(DEFAULT_DIVIDER_THICKNESS)
            )
            this.divTextPadding = ta.getDimension(
                R.styleable.SeparatorTextView_dividerTextPadding,
                context.dpToPx(DEFAULT_DIVIDER_TEXT_PADDING)
            )
            dashLength = ta.getDimension(R.styleable.SeparatorTextView_dashLength, -1F)
            dashGap = ta.getDimension(R.styleable.SeparatorTextView_dashGap, -1F)
        } finally {
            ta.recycle()
        }

        this.paint = Paint().apply {
            this.isAntiAlias = true
            this.color = color
            this.style = Paint.Style.STROKE
            this.strokeWidth = divThickness
            if (dashLength != -1F && dashGap != -1F) {
                this.pathEffect = DashPathEffect(floatArrayOf(dashLength, dashGap), 0F)
            }
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        setLayerType(View.LAYER_TYPE_SOFTWARE, null)

        val top = (height + paddingTop) / 2F - paint.strokeWidth / 2F
        val bottom = top + paint.strokeWidth
        val left = paddingLeft
        val rights = width - paddingRight

        val textWidth = textSize

        when (gravity) {
            Gravity.START -> {
                canvas?.drawLine(
                    left + textWidth + this.divTextPadding,
                    bottom,
                    right.toFloat(),
                    bottom,
                    paint
                )
            }
            Gravity.END -> {
                canvas?.drawLine(
                    left.toFloat(),
                    bottom,
                    rights - textWidth - this.divTextPadding,
                    bottom,
                    paint
                )
            }
            else -> {
                val w = width - paddingLeft - paddingRight
                val xPos = (w / 2) - textWidth / 2

                canvas?.drawLine(
                    left.toFloat(),
                    bottom,
                    xPos - divTextPadding,
                    bottom,
                    paint
                )
                canvas?.drawLine(
                    xPos + textWidth + this.divTextPadding,
                    bottom,
                    rights.toFloat(),
                    bottom,
                    paint
                )
            }
        }
    }
}