package sourabh.pal.cricket.common.data.api

import android.util.Log.e
import androidx.test.platform.app.InstrumentationRegistry
import java.io.IOException
import java.io.InputStream
import java.util.logging.Logger

object JsonReader {
    fun getJson(path: String): String {
        return try {
            val context = InstrumentationRegistry.getInstrumentation().context
            val jsonStream: InputStream = context.assets.open("networkresponses/$path")
            String(jsonStream.readBytes())
        } catch (exception: IOException) {
            throw exception
        }
    }
}