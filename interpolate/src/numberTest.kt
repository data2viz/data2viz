import test.StringSpec

class InterpolateTests: StringSpec(){
    init {
        "interpolate" {
            val x = interpolateNumber(10, 20)
            x(0.2) shouldBe 12.0
        }
    }
}
