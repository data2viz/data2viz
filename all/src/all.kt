import test.htmlExecution


fun executeAll() {
    htmlExecution(
            TicksTests(),
            AxisTests(),
            ViridisTests(),
            EaseTests(),
            ExceptionMatchers(),
            StringMatchers(),
            IntMatchers(),
            LongMatchers(),
            DoubleMatchers(),
            TestCollectionMatchers()
    )
}
