package com.icongkhanh.gameworld.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.transition.Transition
import androidx.transition.TransitionInflater
import androidx.transition.TransitionManager
import com.icongkhanh.gameworld.R

class CollapsingCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    var oldContentHeight: Int = 100

    private var expended: Boolean = false
    private var title: String = ""
    private var content: String = ""

    private val root: View
    private val tvTitle: TextView
    private val tvContent: TextView
    private val tvReadMore: TextView

    private val toggleTransition: Transition

    init {

        root = LayoutInflater.from(context).inflate(R.layout.collasing_card, this, true)
        tvTitle = root.findViewById(R.id.title)
        tvContent = root.findViewById(R.id.content)
        tvReadMore = root.findViewById(R.id.read_more)

        toggleTransition =
            TransitionInflater.from(context).inflateTransition(R.transition.collapsing)

        val arr = context.obtainStyledAttributes(attrs, R.styleable.CollapsingCard, 0, 0)
        title = arr.getString(R.styleable.CollapsingCard_cardTitle) ?: ""
        content = arr.getString(R.styleable.CollapsingCard_cardContent) ?: ""
        expended = !arr.getBoolean(R.styleable.CollapsingCard_collapse, true)

        tvContent.addOnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
            if (expended && tvContent.height < oldContentHeight) oldContentHeight = tvContent.height
        }

        tvReadMore.setOnClickListener {
            toggleExpended()
        }

//        root.setOnClickListener {
//            toggleExpended()
//        }

        setTitle(title)
        setContent(content)
        onChangedExpend()
    }

    private fun toggleExpended() {
        expended = !expended
        onChangedExpend()
    }

    fun setTitle(title: String) {
        tvTitle.text = title
    }

    fun setContent(content: String) {
        TransitionManager.beginDelayedTransition(root as ViewGroup, toggleTransition)
        tvContent.text = content
    }

    fun setExpended(expend: Boolean) {
        this.expended = expended
        onChangedExpend()
    }

    private fun onChangedExpend() {
        Log.d("AppLog", "Toggle: ${oldContentHeight}")

        TransitionManager.beginDelayedTransition(root as ViewGroup, toggleTransition)
        val param = tvContent.layoutParams
        param.height = if (expended) ViewGroup.LayoutParams.WRAP_CONTENT else oldContentHeight
        tvContent.layoutParams = param

        tvReadMore.text = if (expended) "Collapse..." else "Expend..."
    }
}