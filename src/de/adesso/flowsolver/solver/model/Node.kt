package de.adesso.flowsolver.solver.model

/**
 * FlowSolve
 * adesso AG
 * @author kaiser
 * Created on 28.04.2016
 */

data class Node(val x: Int, val y: Int, var color: Int = 0) {
    constructor(compressed: Byte, color: Int = 0) : this(x(compressed), y(compressed), color)
    
    fun compressed() = (((x shl 4) or y) + 1).toByte()
    
    companion object {
        fun x(compressed: Byte) = (compressed.toInt() - 1) shr 4 and 0xF
        fun y(compressed: Byte) = (compressed.toInt() - 1) and 0xF
    }
}