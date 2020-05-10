import org.apache.commons.io.IOUtils
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.io.UncheckedIOException
import java.net.URL
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths

private val PATH = Paths.get("data")
private const val TARGET_URL = "https://jsonplaceholder.typicode.com/posts"
private const val ID_KEY = "id"
private const val JSON_EXTENSION = ".json"
private const val OUTPUT_INDENT_FACTOR = 2

@Throws(IOException::class)
fun main() {
    downloadAndParse()
}

@Throws(IOException::class)
private fun downloadAndParse() {
    Files.createDirectories(PATH)
    JSONArray( IOUtils.toString(URL(TARGET_URL), StandardCharsets.UTF_8) ).forEach{
        saveToFile(it as JSONObject)
    }
}

private fun saveToFile(jsonObject: JSONObject) {
    try {
        Files.write(
                PATH.resolve(jsonObject.getInt(ID_KEY).toString() + JSON_EXTENSION),
                jsonObject.toString(OUTPUT_INDENT_FACTOR).toByteArray()
        )
    } catch (e: IOException) {
        throw UncheckedIOException(e)
    }
}