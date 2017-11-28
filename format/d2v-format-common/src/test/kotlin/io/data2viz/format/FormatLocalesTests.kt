@file:Suppress("FunctionName", "unused")

package io.data2viz.format

import io.data2viz.test.TestBase
import kotlin.test.Test

class FormatLocalesTests : TestBase() {
    @Test
    fun arabic_locales() {
        Locales.ar_001().format("$,.2f")(-1234.56) shouldBe "-١٬٢٣٤٫٥٦"
        Locales.ar_AE().format("$,.2f")(1234.56) shouldBe "١٬٢٣٤٫٥٦ د.إ."
        Locales.ar_BH().format("$,.2f")(1234.56) shouldBe "١٬٢٣٤٫٥٦ د.ب."
        Locales.ar_DJ().format("$,.2f")(1234.56) shouldBe "\u200fFdj ١٬٢٣٤٫٥٦"
        Locales.ar_DZ().format("$,.2f")(1234.56) shouldBe "د.ج. 1.234,56"
        Locales.ar_EG().format("$,.2f")(1234.56) shouldBe "١٬٢٣٤٫٥٦ ج.م."
        Locales.ar_EH().format("$,.2f")(1234.56) shouldBe "د.م. 1,234.56"
        Locales.ar_ER().format("$,.2f")(1234.56) shouldBe "Nfk ١٬٢٣٤٫٥٦"
        Locales.ar_IL().format("$,.2f")(1234.56) shouldBe "₪ ١٬٢٣٤٫٥٦"
        Locales.ar_IQ().format("$,.2f")(1234.56) shouldBe "١٬٢٣٤٫٥٦ د.ع."
        Locales.ar_JO().format("$,.2f")(1234.56) shouldBe "١٬٢٣٤٫٥٦ د.أ."
        Locales.ar_KM().format("$,.2f")(1234.56) shouldBe "١٬٢٣٤٫٥٦ ف.ج.ق."
        Locales.ar_KW().format("$,.2f")(1234.56) shouldBe "١٬٢٣٤٫٥٦ د.ك."
        Locales.ar_LB().format("$,.2f")(1234.56) shouldBe "١٬٢٣٤٫٥٦ ل.ل."
        Locales.ar_MA().format("$,.2f")(1234.56) shouldBe "د.م. 1.234,56"
        Locales.ar_MR().format("$,.2f")(1234.56) shouldBe "١٬٢٣٤٫٥٦ أ.م."
        Locales.ar_OM().format("$,.2f")(1234.56) shouldBe "١٬٢٣٤٫٥٦ ر.ع."
        Locales.ar_PS().format("$,.2f")(1234.56) shouldBe "₪ ١٬٢٣٤٫٥٦"
        Locales.ar_QA().format("$,.2f")(1234.56) shouldBe "١٬٢٣٤٫٥٦ ر.ق."
        Locales.ar_SA().format("$,.2f")(1234.56) shouldBe "١٬٢٣٤٫٥٦ ر.س."
        Locales.ar_SD().format("$,.2f")(1234.56) shouldBe "١٬٢٣٤٫٥٦ ج.س."
        Locales.ar_SO().format("$,.2f")(1234.56) shouldBe "‏S ١٬٢٣٤٫٥٦"
        Locales.ar_SS().format("$,.2f")(1234.56) shouldBe "£ ١٬٢٣٤٫٥٦"
        Locales.ar_SY().format("$,.2f")(1234.56) shouldBe "١٬٢٣٤٫٥٦ ل.س."
        Locales.ar_TD().format("$,.2f")(1234.56) shouldBe "\u200fFCFA ١٬٢٣٤٫٥٦"
        Locales.ar_TN().format("$,.2f")(1234.56) shouldBe "د.ت. 1.234,56"
    }

    @Test
    fun frFR_locale() {
        Locales.fr_FR().format("$,.2f")(12345678.90) shouldBe "12 345 678,90 €"
    }

}
