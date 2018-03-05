import Stats.Category
import org.w3c.dom.*
import java.io.File
import java.nio.file.*
import java.nio.file.Files.walkFileTree
import java.nio.file.attribute.BasicFileAttributes
import javax.xml.parsers.*


data class Category(val fileName: String, val loc: Int, val fileCount: Int, val name: String)

class ReportsVisitor(val matcher: PathMatcher, val reports: MutableList<Category>) : SimpleFileVisitor<Path>() {

    val builder = DocumentBuilderFactory.newInstance()!!.newDocumentBuilder()!!

    override fun preVisitDirectory(dir: Path, attrs: BasicFileAttributes?): FileVisitResult {
        if (dir.nameCount == 4 && dir.getName(3).toString() != "build") {
            return FileVisitResult.SKIP_SUBTREE
        }
        return FileVisitResult.CONTINUE
    }

    override fun visitFile(fileAsPath: Path, attrs: BasicFileAttributes?): FileVisitResult {

        fun getInt(nodes:NodeList,name:String):Int{
            (0 until nodes.length).forEach {
                val item = nodes.item(it)
                if(item is Element && item.tagName == name){
                    return item.textContent.toInt()
                }
            }
            throw IllegalStateException()
        }

        if (matcher.matches(fileAsPath)) {
            val file = fileAsPath.toFile()
            val root = builder.parse(file).documentElement
            val categories = (0 until root.childNodes.length)
                .map { root.childNodes.item(it) }
                .filterIsInstance<Element>()
                .filter { it.tagName == "category" }
                .map {
                    val name = it.attributes.getNamedItem("name")?.nodeValue ?: "total"
                    val fileCount = getInt(it.childNodes, "fileCount")
                    val loc = getInt(it.childNodes, "loc")
                    Category(file.name.removeSuffix(".xml"), loc, fileCount, name)
                }
            reports.addAll(categories)
        }
        return FileVisitResult.CONTINUE
    }
}


println("Starting reports analysis")
val path: PathMatcher = FileSystems.getDefault().getPathMatcher("glob:**/build/reports/stats/*.xml")
val root = Paths.get(".")!!
val reports = mutableListOf<Category>()
val files = walkFileTree(root, ReportsVisitor(path, reports))!!


var totalCommon = 0
var totalJs = 0
var totalJVM = 0

var testTotalCommon = 0
var testTotalJs = 0
var testTotalJVM = 0

reports.forEach {
    println(it)
    val platform = it.fileName.substringAfterLast('-')
    if(it.name =="Kotlin Sources"){
        when (platform) {
            "common"    -> totalCommon      += it.loc
            "js"        -> totalJs          += it.loc
            "jvm"       -> totalJVM         += it.loc
        }
    }
    if(it.name =="Kotlin Test Sources"){
        when (platform) {
            "common"    -> testTotalCommon  += it.loc
            "js"        -> testTotalJs      += it.loc
            "jvm"       -> testTotalJVM     += it.loc
        }
    }
}


println("""
Data2viz Kotlin Lines Of Code
=============================
Sources
    common :: $totalCommon
    js     :: $totalJs (${(100 * totalJs / (totalJs + totalCommon))} %)
    jvm    :: $totalJVM (${(100 * totalJVM / (totalJVM + totalCommon))} %)

Test Sources
    common :: $testTotalCommon
    js     :: $testTotalJs
    jvm    :: $testTotalJVM
    """)


if (reports.size == 0){
    println("""
        
        You have to call the gradle task stats before running this script.
        """.trimIndent())
}
