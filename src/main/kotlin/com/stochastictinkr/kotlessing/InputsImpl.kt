package com.stochastictinkr.kotlessing

import kotlessing.*

internal class InputsImpl : Inputs {
    val mouseImpl = MouseImpl()
    override val mouse: Mouse = mouseImpl
    override val keyboard: Keyboard get() = TODO()
}