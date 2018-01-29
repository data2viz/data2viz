package io.data2viz.viz

import org.w3c.dom.Element

class TextElement(override val element: Element) : ElementWrapper, TextVizItem,
    StyledElement by StyledDelegate(element),
    HasFill by FillDelegate(element),
        Transformable by TransformableDelegate(element) {
    
    override var baseline: TextAlignmentBaseline
        get() = getAttribute("alignment-baseline")?.let { 
            when (it) {
                "hanging" -> TextAlignmentBaseline.HANGING
                "middle" -> TextAlignmentBaseline.MIDDLE
                else -> TextAlignmentBaseline.BASELINE
            } 
        } ?: TextAlignmentBaseline.BASELINE
        
        set(value) {
            setAttribute("alignment-baseline",
                    when(value) {
                        TextAlignmentBaseline.BASELINE -> "baseline"
                        TextAlignmentBaseline.HANGING -> "hanging"
                        TextAlignmentBaseline.MIDDLE -> "middle"
                    })
        }
    
    
    override var anchor: TextAnchor
        get() = getAttribute("text-anchor")?.let { 
            when (it) {
                "middle" -> TextAnchor.MIDDLE
                "end" -> TextAnchor.END
                else -> TextAnchor.START
            }
        } ?: TextAnchor.START
        
        set(value) {
            setAttribute("text-anchor",
                    when (value) {
                        TextAnchor.START -> "start"
                        TextAnchor.MIDDLE -> "middle"
                        TextAnchor.END -> "end"
                    }
            )

        }
    override var textContent: String
        get() = element.textContent ?: ""
        set(value) {
            element.textContent = value
        }

    override var x: Double by DoubleAttributePropertyDelegate()
    override var y: Double by DoubleAttributePropertyDelegate()
}