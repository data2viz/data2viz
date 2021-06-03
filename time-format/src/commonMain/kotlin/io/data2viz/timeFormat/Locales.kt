/*
 * Copyright (c) 2018-2021. data2viz sàrl.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

@file:Suppress("unused")

package io.data2viz.timeFormat

public data class TimeLocale(
        val dateTime: String,
        val date: String,
        val time: String,
        val periods: List<String>,
        val days: List<String>,
        val shortDays: List<String>,
        val months: List<String>,
        val shortMonths: List<String>
)

public class Locales {
    public companion object {
        public val default: Locale  by lazy { Locale(defaultTimeLocale()) }
        public val ar_EG: Locale    by lazy { Locale(ar_EG()) }
        public val ca_ES: Locale    by lazy { Locale(ca_ES()) }
        public val cs_CZ: Locale    by lazy { Locale(cs_CZ()) }
        public val da_DK: Locale    by lazy { Locale(da_DK()) }
        public val de_CH: Locale    by lazy { Locale(de_CH()) }
        public val de_DE: Locale    by lazy { Locale(de_DE()) }
        public val en_CA: Locale    by lazy { Locale(en_CA()) }
        public val en_GB: Locale    by lazy { Locale(en_GB()) }
        public val en_US: Locale    by lazy { Locale(en_US()) }
        public val es_ES: Locale    by lazy { Locale(es_ES()) }
        public val es_MX: Locale    by lazy { Locale(es_MX()) }
        public val fa_IR: Locale    by lazy { Locale(fa_IR()) }
        public val fi_FI: Locale    by lazy { Locale(fi_FI()) }
        public val fr_CA: Locale    by lazy { Locale(fr_CA()) }
        public val fr_FR: Locale    by lazy { Locale(fr_FR()) }
        public val he_IL: Locale    by lazy { Locale(he_IL()) }
        public val hu_HU: Locale    by lazy { Locale(hu_HU()) }
        public val it_IT: Locale    by lazy { Locale(it_IT()) }
        public val ja_JP: Locale    by lazy { Locale(ja_JP()) }
        public val ko_KR: Locale    by lazy { Locale(ko_KR()) }
        public val mk_MK: Locale    by lazy { Locale(mk_MK()) }
        public val nb_NO: Locale    by lazy { Locale(nb_NO()) }
        public val nl_NL: Locale    by lazy { Locale(nl_NL()) }
        public val pl_PL: Locale    by lazy { Locale(pl_PL()) }
        public val pt_BR: Locale    by lazy { Locale(pt_BR()) }
        public val ru_RU: Locale    by lazy { Locale(ru_RU()) }
        public val sv_SE: Locale    by lazy { Locale(sv_SE()) }
        public val tr_TR: Locale    by lazy { Locale(tr_TR()) }
        public val uk_UA: Locale    by lazy { Locale(uk_UA()) }
        public val zh_CN: Locale    by lazy { Locale(zh_CN()) }
        public val zh_TW: Locale    by lazy { Locale(zh_TW()) }

        private fun timeLocale(
            dateTime: String = "%x, %X",
            date: String = "%-m/%-d/%Y",
            time: String = "%-I:%M:%S %p",
            periods: List<String> = listOf("AM", "PM"),
            days: List<String> = listOf("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"),
            shortDays: List<String> = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"),
            months: List<String> = listOf("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"),
            shortMonths: List<String> = listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")
        ): TimeLocale = TimeLocale(dateTime, date, time, periods, days, shortDays, months, shortMonths)

        internal fun defaultTimeLocale(): TimeLocale = timeLocale()

        private fun ar_EG(): TimeLocale = timeLocale(
            "%x, %X",
            "%-d/%-m/%Y",
            "%-I:%M:%S %p",
            listOf("ص", "م"),
            listOf("الأحد", "الإثنين", "الثلاثاء", "الأربعاء", "الخميس", "الجمعة", "السبت"),
            listOf("أحد", "إثنين", "ثلاثاء", "أربعاء", "خميس", "جمعة", "سبت"),
            listOf("يناير", "فبراير", "مارس", "أبريل", "مايو", "يونيو", "يوليو", "أغسطس", "سبتمبر", "أكتوبر", "نوفمبر", "ديسمبر"),
            listOf("يناير", "فبراير", "مارس", "أبريل", "مايو", "يونيو", "يوليو", "أغسطس", "سبتمبر", "أكتوبر", "نوفمبر", "ديسمبر")
        )

        private fun ca_ES(): TimeLocale = timeLocale(
            "%A, %e de %B de %Y, %X",
            "%d/%m/%Y",
            "%H:%M:%S",
            listOf("AM", "PM"),
            listOf("diumenge", "dilluns", "dimarts", "dimecres", "dijous", "divendres", "dissabte"),
            listOf("dg.", "dl.", "dt.", "dc.", "dj.", "dv.", "ds."),
            listOf("gener", "febrer", "març", "abril", "maig", "juny", "juliol", "agost", "setembre", "octubre", "novembre", "desembre"),
            listOf("gen.", "febr.", "març", "abr.", "maig", "juny", "jul.", "ag.", "set.", "oct.", "nov.", "des.")
        )

        private fun cs_CZ(): TimeLocale = timeLocale(
            "%A,%e.%B %Y, %X",
            "%-d.%-m.%Y",
            "%H:%M:%S",
            listOf("AM", "PM"),
            listOf("neděle", "pondělí", "úterý", "středa", "čvrtek", "pátek", "sobota"),
            listOf("ne.", "po.", "út.", "st.", "čt.", "pá.", "so."),
            listOf("leden", "únor", "březen", "duben", "květen", "červen", "červenec", "srpen", "září", "říjen", "listopad", "prosinec"),
            listOf("led", "úno", "břez", "dub", "kvě", "čer", "červ", "srp", "zář", "říj", "list", "pros")
        )

        private fun da_DK(): TimeLocale = timeLocale(
            "%A den %d %B %Y %X",
            "%d-%m-%Y",
            "%H:%M:%S",
            listOf("AM", "PM"),
            listOf("søndag", "mandag", "tirsdag", "onsdag", "torsdag", "fredag", "lørdag"),
            listOf("søn", "man", "tir", "ons", "tor", "fre", "lør"),
            listOf("januar", "februar", "marts", "april", "maj", "juni", "juli", "august", "september", "oktober", "november", "december"),
            listOf("jan", "feb", "mar", "apr", "maj", "jun", "jul", "aug", "sep", "okt", "nov", "dec")
        )

        private fun de_CH(): TimeLocale = timeLocale(
            "%A, der %e. %B %Y, %X",
            "%d.%m.%Y",
            "%H:%M:%S",
            listOf("AM", "PM"),
            listOf("Sonntag", "Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag", "Samstag"),
            listOf("So", "Mo", "Di", "Mi", "Do", "Fr", "Sa"),
            listOf("Januar", "Februar", "März", "April", "Mai", "Juni", "Juli", "August", "September", "Oktober", "November", "Dezember"),
            listOf("Jan", "Feb", "Mrz", "Apr", "Mai", "Jun", "Jul", "Aug", "Sep", "Okt", "Nov", "Dez")
        )

        private fun de_DE(): TimeLocale = timeLocale(
            "%A, der %e. %B %Y, %X",
            "%d.%m.%Y",
            "%H:%M:%S",
            listOf("AM", "PM"),
            listOf("Sonntag", "Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag", "Samstag"),
            listOf("So", "Mo", "Di", "Mi", "Do", "Fr", "Sa"),
            listOf("Januar", "Februar", "März", "April", "Mai", "Juni", "Juli", "August", "September", "Oktober", "November", "Dezember"),
            listOf("Jan", "Feb", "Mrz", "Apr", "Mai", "Jun", "Jul", "Aug", "Sep", "Okt", "Nov", "Dez")
        )

        private fun en_CA(): TimeLocale = timeLocale(
            "%a %b %e %X %Y",
            "%Y-%m-%d",
            "%H:%M:%S",
            listOf("AM", "PM"),
            listOf("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"),
            listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"),
            listOf("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"),
            listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")
        )

        private fun en_GB(): TimeLocale = timeLocale(
            "%a %e %b %X %Y",
            "%d/%m/%Y",
            "%H:%M:%S",
            listOf("AM", "PM"),
            listOf("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"),
            listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"),
            listOf("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"),
            listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")
        )

        private fun en_US(): TimeLocale = timeLocale(
            "%x, %X",
            "%-m/%-d/%Y",
            "%-I:%M:%S %p",
            listOf("AM", "PM"),
            listOf("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"),
            listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"),
            listOf("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"),
            listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")
        )

        private fun es_ES(): TimeLocale = timeLocale(
            "%A, %e de %B de %Y, %X",
            "%d/%m/%Y",
            "%H:%M:%S",
            listOf("AM", "PM"),
            listOf("domingo", "lunes", "martes", "miércoles", "jueves", "viernes", "sábado"),
            listOf("dom", "lun", "mar", "mié", "jue", "vie", "sáb"),
            listOf("enero", "febrero", "marzo", "abril", "mayo", "junio", "julio", "agosto", "septiembre", "octubre", "noviembre", "diciembre"),
            listOf("ene", "feb", "mar", "abr", "may", "jun", "jul", "ago", "sep", "oct", "nov", "dic")
        )

        private fun es_MX(): TimeLocale = timeLocale(
            "%x, %X",
            "%d/%m/%Y",
            "%-I:%M:%S %p",
            listOf("AM", "PM"),
            listOf("domingo", "lunes", "martes", "miércoles", "jueves", "viernes", "sábado"),
            listOf("dom", "lun", "mar", "mié", "jue", "vie", "sáb"),
            listOf("enero", "febrero", "marzo", "abril", "mayo", "junio", "julio", "agosto", "septiembre", "octubre", "noviembre", "diciembre"),
            listOf("ene", "feb", "mar", "abr", "may", "jun", "jul", "ago", "sep", "oct", "nov", "dic")
        )

        private fun fa_IR(): TimeLocale = timeLocale(
            "%x, %X",
            "%-d/%-m/%Y",
            "%-I:%M:%S %p",
            listOf("صبح", "عصر"),
            listOf("یکشنبه", "دوشنبه", "سه شنبه", "چهارشنبه", "پنجشنبه", "جمعه", "شنبه"),
            listOf("یکشنبه", "دوشنبه", "سه شنبه", "چهارشنبه", "پنجشنبه", "جمعه", "شنبه"),
            listOf("ژانویه", "فوریه", "مارس", "آوریل", "مه", "ژوئن", "ژوئیه", "اوت", "سپتامبر", "اکتبر", "نوامبر", "دسامبر"),
            listOf("ژانویه", "فوریه", "مارس", "آوریل", "مه", "ژوئن", "ژوئیه", "اوت", "سپتامبر", "اکتبر", "نوامبر", "دسامبر")
        )

        private fun fi_FI(): TimeLocale = timeLocale(
            "%A, %-d. %Bta %Y klo %X",
            "%-d.%-m.%Y",
            "%H:%M:%S",
            listOf("a.m.", "p.m."),
            listOf("sunnuntai", "maanantai", "tiistai", "keskiviikko", "torstai", "perjantai", "lauantai"),
            listOf("Su", "Ma", "Ti", "Ke", "To", "Pe", "La"),
            listOf("tammikuu", "helmikuu", "maaliskuu", "huhtikuu", "toukokuu", "kesäkuu", "heinäkuu", "elokuu", "syyskuu", "lokakuu", "marraskuu", "joulukuu"),
            listOf("Tammi", "Helmi", "Maalis", "Huhti", "Touko", "Kesä", "Heinä", "Elo", "Syys", "Loka", "Marras", "Joulu")
        )

        private fun fr_CA(): TimeLocale = timeLocale(
            "%a %e %b %Y %X",
            "%Y-%m-%d",
            "%H:%M:%S",
            listOf("", ""),
            listOf("dimanche", "lundi", "mardi", "mercredi", "jeudi", "vendredi", "samedi"),
            listOf("dim", "lun", "mar", "mer", "jeu", "ven", "sam"),
            listOf("janvier", "février", "mars", "avril", "mai", "juin", "juillet", "août", "septembre", "octobre", "novembre", "décembre"),
            listOf("jan", "fév", "mar", "avr", "mai", "jui", "jul", "aoû", "sep", "oct", "nov", "déc")
        )

        private fun fr_FR(): TimeLocale = timeLocale(
            "%A, le %e %B %Y, %X",
            "%d/%m/%Y",
            "%H:%M:%S",
            listOf("AM", "PM"),
            listOf("dimanche", "lundi", "mardi", "mercredi", "jeudi", "vendredi", "samedi"),
            listOf("dim.", "lun.", "mar.", "mer.", "jeu.", "ven.", "sam."),
            listOf("janvier", "février", "mars", "avril", "mai", "juin", "juillet", "août", "septembre", "octobre", "novembre", "décembre"),
            listOf("janv.", "févr.", "mars", "avr.", "mai", "juin", "juil.", "août", "sept.", "oct.", "nov.", "déc.")
        )

        private fun he_IL(): TimeLocale = timeLocale(
            "%A, %e ב%B %Y %X",
            "%d.%m.%Y",
            "%H:%M:%S",
            listOf("AM", "PM"),
            listOf("ראשון", "שני", "שלישי", "רביעי", "חמישי", "שישי", "שבת"),
            listOf("א׳", "ב׳", "ג׳", "ד׳", "ה׳", "ו׳", "ש׳"),
            listOf("ינואר", "פברואר", "מרץ", "אפריל", "מאי", "יוני", "יולי", "אוגוסט", "ספטמבר", "אוקטובר", "נובמבר", "דצמבר"),
            listOf("ינו׳", "פבר׳", "מרץ", "אפר׳", "מאי", "יוני", "יולי", "אוג׳", "ספט׳", "אוק׳", "נוב׳", "דצמ׳")
        )

        private fun hu_HU(): TimeLocale = timeLocale(
            "%Y. %B %-e., %A %X",
            "%Y. %m. %d.",
            "%H:%M:%S",
            listOf("de.", "du."),
            listOf("vasárnap", "hétfő", "kedd", "szerda", "csütörtök", "péntek", "szombat"),
            listOf("V", "H", "K", "Sze", "Cs", "P", "Szo"),
            listOf("január", "február", "március", "április", "május", "június", "július", "augusztus", "szeptember", "október", "november", "december"),
            listOf("jan.", "feb.", "már.", "ápr.", "máj.", "jún.", "júl.", "aug.", "szept.", "okt.", "nov.", "dec.")
        )

        private fun it_IT(): TimeLocale = timeLocale(
            "%A %e %B %Y, %X",
            "%d/%m/%Y",
            "%H:%M:%S",
            listOf("AM", "PM"),
            listOf("Domenica", "Lunedì", "Martedì", "Mercoledì", "Giovedì", "Venerdì", "Sabato"),
            listOf("Dom", "Lun", "Mar", "Mer", "Gio", "Ven", "Sab"),
            listOf("Gennaio", "Febbraio", "Marzo", "Aprile", "Maggio", "Giugno", "Luglio", "Agosto", "Settembre", "Ottobre", "Novembre", "Dicembre"),
            listOf("Gen", "Feb", "Mar", "Apr", "Mag", "Giu", "Lug", "Ago", "Set", "Ott", "Nov", "Dic")
        )

        private fun ja_JP(): TimeLocale = timeLocale(
            "%x %a %X",
            "%Y/%m/%d",
            "%H:%M:%S",
            listOf("AM", "PM"),
            listOf("日曜日", "月曜日", "火曜日", "水曜日", "木曜日", "金曜日", "土曜日"),
            listOf("日", "月", "火", "水", "木", "金", "土"),
            listOf("睦月", "如月", "弥生", "卯月", "皐月", "水無月", "文月", "葉月", "長月", "神無月", "霜月", "師走"),
            listOf("1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月")
        )

        private fun ko_KR(): TimeLocale = timeLocale(
            "%Y/%m/%d %a %X",
            "%Y/%m/%d",
            "%H:%M:%S",
            listOf("오전", "오후"),
            listOf("일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일"),
            listOf("일", "월", "화", "수", "목", "금", "토"),
            listOf("1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"),
            listOf("1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월")
        )

        private fun mk_MK(): TimeLocale = timeLocale(
            "%A, %e %B %Y г. %X",
            "%d.%m.%Y",
            "%H:%M:%S",
            listOf("AM", "PM"),
            listOf("недела", "понеделник", "вторник", "среда", "четврток", "петок", "сабота"),
            listOf("нед", "пон", "вто", "сре", "чет", "пет", "саб"),
            listOf("јануари", "февруари", "март", "април", "мај", "јуни", "јули", "август", "септември", "октомври", "ноември", "декември"),
            listOf("јан", "фев", "мар", "апр", "мај", "јун", "јул", "авг", "сеп", "окт", "ное", "дек")
        )

        private fun nb_NO(): TimeLocale = timeLocale(
            "%A den %d. %B %Y %X",
            "%d.%m.%Y",
            "%H:%M:%S",
            listOf("AM", "PM"),
            listOf("søndag", "mandag", "tirsdag", "onsdag", "torsdag", "fredag", "lørdag"),
            listOf("søn", "man", "tir", "ons", "tor", "fre", "lør"),
            listOf("januar", "februar", "mars", "april", "mai", "juni", "juli", "august", "september", "oktober", "november", "desember"),
            listOf("jan", "feb", "mars", "apr", "mai", "juni", "juli", "aug", "sep", "okt", "nov", "des")
        )

        private fun nl_NL(): TimeLocale = timeLocale(
            "%a %e %B %Y %X",
            "%d-%m-%Y",
            "%H:%M:%S",
            listOf("AM", "PM"),
            listOf("zondag", "maandag", "dinsdag", "woensdag", "donderdag", "vrijdag", "zaterdag"),
            listOf("zo", "ma", "di", "wo", "do", "vr", "za"),
            listOf("januari", "februari", "maart", "april", "mei", "juni", "juli", "augustus", "september", "oktober", "november", "december"),
            listOf("jan", "feb", "mrt", "apr", "mei", "jun", "jul", "aug", "sep", "okt", "nov", "dec")
        )

        private fun pl_PL(): TimeLocale = timeLocale(
            "%A, %e %B %Y, %X",
            "%d/%m/%Y",
            "%H:%M:%S",
            listOf("AM", "PM"),
            listOf("Niedziela", "Poniedziałek", "Wtorek", "Środa", "Czwartek", "Piątek", "Sobota"),
            listOf("Niedz.", "Pon.", "Wt.", "Śr.", "Czw.", "Pt.", "Sob."),
            listOf("Styczeń", "Luty", "Marzec", "Kwiecień", "Maj", "Czerwiec", "Lipiec", "Sierpień", "Wrzesień", "Październik", "Listopad", "Grudzień"),
            listOf("Stycz.", "Luty", "Marz.", "Kwie.", "Maj", "Czerw.", "Lipc.", "Sierp.", "Wrz.", "Paźdz.", "Listop.", "Grudz.")
        )

        private fun pt_BR(): TimeLocale = timeLocale(
            "%A, %e de %B de %Y. %X",
            "%d/%m/%Y",
            "%H:%M:%S",
            listOf("AM", "PM"),
            listOf("Domingo", "Segunda", "Terça", "Quarta", "Quinta", "Sexta", "Sábado"),
            listOf("Dom", "Seg", "Ter", "Qua", "Qui", "Sex", "Sáb"),
            listOf("Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"),
            listOf("Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez")
        )

        private fun ru_RU(): TimeLocale = timeLocale(
            "%A, %e %B %Y г. %X",
            "%d.%m.%Y",
            "%H:%M:%S",
            listOf("AM", "PM"),
            listOf("воскресенье", "понедельник", "вторник", "среда", "четверг", "пятница", "суббота"),
            listOf("вс", "пн", "вт", "ср", "чт", "пт", "сб"),
            listOf("января", "февраля", "марта", "апреля", "мая", "июня", "июля", "августа", "сентября", "октября", "ноября", "декабря"),
            listOf("янв", "фев", "мар", "апр", "май", "июн", "июл", "авг", "сен", "окт", "ноя", "дек")
        )

        private fun sv_SE(): TimeLocale = timeLocale(
            "%A den %d %B %Y %X",
            "%Y-%m-%d",
            "%H:%M:%S",
            listOf("fm", "em"),
            listOf("Söndag", "Måndag", "Tisdag", "Onsdag", "Torsdag", "Fredag", "Lördag"),
            listOf("Sön", "Mån", "Tis", "Ons", "Tor", "Fre", "Lör"),
            listOf("Januari", "Februari", "Mars", "April", "Maj", "Juni", "Juli", "Augusti", "September", "Oktober", "November", "December"),
            listOf("Jan", "Feb", "Mar", "Apr", "Maj", "Jun", "Jul", "Aug", "Sep", "Okt", "Nov", "Dec")
        )

        private fun tr_TR(): TimeLocale = timeLocale(
            "%a %e %b %X %Y",
            "%d/%m/%Y",
            "%H:%M:%S",
            listOf("AM", "PM"),
            listOf("Pazar", "Pazartesi", "Salı", "Çarşamba", "Perşembe", "Cuma", "Cumartesi"),
            listOf("Paz", "Pzt", "Sal", "Çar", "Per", "Cum", "Cmt"),
            listOf("Ocak", "Şubat", "Mart", "Nisan", "Mayıs", "Haziran", "Temmuz", "Ağustos", "Eylül", "Ekim", "Kasım", "Aralık"),
            listOf("Oca", "Şub", "Mar", "Nis", "May", "Haz", "Tem", "Ağu", "Eyl", "Eki", "Kas", "Ara")
        )

        private fun uk_UA(): TimeLocale = timeLocale(
            "%A, %e %B %Y р. %X",
            "%d.%m.%Y",
            "%H:%M:%S",
            listOf("дп", "пп"),
            listOf("неділя", "понеділок", "вівторок", "середа", "четвер", "п'ятниця", "субота"),
            listOf("нд", "пн", "вт", "ср", "чт", "пт", "сб"),
            listOf("січня", "лютого", "березня", "квітня", "травня", "червня", "липня", "серпня", "вересня", "жовтня", "листопада", "грудня"),
            listOf("січ.", "лют.", "бер.", "квіт.", "трав.", "черв.", "лип.", "серп.", "вер.", "жовт.", "лист.", "груд.")
        )

        private fun zh_CN(): TimeLocale = timeLocale(
            "%x %A %X",
            "%Y年%-m月%-d日",
            "%H:%M:%S",
            listOf("上午", "下午"),
            listOf("星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"),
            listOf("周日", "周一", "周二", "周三", "周四", "周五", "周六"),
            listOf("一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"),
            listOf("一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月")
        )

        private fun zh_TW(): TimeLocale = timeLocale(
            "%x %A %X",
            "%Y年%-m月%-d日",
            "%H:%M:%S",
            listOf("上午", "下午"),
            listOf("星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"),
            listOf("日", "一", "二", "三", "四", "五", "六"),
            listOf("一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"),
            listOf("1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月")
        )
    }
}
