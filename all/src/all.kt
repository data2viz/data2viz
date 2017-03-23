import test.htmlExecution


fun executeAll() {
    htmlExecution(
            InterpolateTests(),
            ExceptionMatchers(),
            StringMatchers(),
            IntMatchers(),
            LongMatchers(),
            DoubleMatchers(),
            TestCollectionMatchers()
    )
}
