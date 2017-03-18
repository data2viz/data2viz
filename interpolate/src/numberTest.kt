fun interpolateNumberTest():Color {
    val x = interpolateNumber(10, 20)
    return Color(x(0.2).toInt(), 2, 3)
}
