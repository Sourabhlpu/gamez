package sourabh.pal.cricket.utils

import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneOffset
import org.threeten.bp.format.DateTimeFormatter
import java.lang.Exception
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities

object DateTimeUtils {
  fun parse(dateTimeString: String): LocalDateTime = try {
      LocalDateTime.parse(dateTimeString)
    } catch (e: Exception) {
      val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ")
      LocalDateTime.parse(dateTimeString, dateFormatter)
    }

  fun parse(epochTime: Long): LocalDateTime = try{
    LocalDateTime.ofEpochSecond(epochTime, 0, ZoneOffset.UTC)
  } catch (e: Exception){
    LocalDateTime.ofEpochSecond(0, 0, ZoneOffset.UTC)
  }
}