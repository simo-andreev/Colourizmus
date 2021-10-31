package bg.o.sim.colourizmus.view.widgets

import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.PixelFormat.OPAQUE
import android.graphics.drawable.Drawable
import android.text.TextPaint
import androidx.annotation.ColorInt
import kotlin.math.min


class CharDrawable(
    private val char: Char,
    @ColorInt private val backgroundColour: Int,
    @ColorInt private val textColour: Int,
    private val textPaint: TextPaint = TextPaint()
) : Drawable() {
    override fun setAlpha(alpha: Int) {
        textPaint.alpha = alpha
    }

    override fun getOpacity(): Int {
        return OPAQUE
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        textPaint.colorFilter = colorFilter
    }


    override fun draw(canvas: Canvas) {
        canvas.drawColor(backgroundColour) // set background
        textPaint.color = textColour


        textPaint.textSize = min(bounds.width(), bounds.height()).toFloat()

        canvas.drawText(char.toString(), 0.toFloat(), bounds.height().toFloat() - textPaint.descent(), textPaint)
    }

}