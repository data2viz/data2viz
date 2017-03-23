import test.htmlExecution


fun executeAll() {
    htmlExecution(
            EaseTests(),
            ExceptionMatchers(),
            StringMatchers(),
            IntMatchers(),
            LongMatchers(),
            DoubleMatchers(),
            TestCollectionMatchers()
    )
}
