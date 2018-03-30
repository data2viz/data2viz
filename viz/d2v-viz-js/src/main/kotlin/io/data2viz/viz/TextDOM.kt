package io.data2viz.viz

import org.w3c.dom.Element

class TextDOM(override val domElement: Element = createSVGElement("text")) : ElementWrapper, Text,
    StyledElement by StyledDelegate(domElement),
    HasFill,
        Transformable by TransformableDelegate(domElement) {

    override var fill by FillDelegate()

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
        get() = domElement.textContent ?: ""
        set(value) {
            domElement.textContent = value
        }

    override var x: Double by DoubleAttributePropertyDelegate()
    override var y: Double by DoubleAttributePropertyDelegate()
}