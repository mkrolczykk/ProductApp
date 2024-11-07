package pl.ztpproj2.lab2app.core.utils

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.text.SimpleDateFormat
import java.lang.reflect.Type
import java.util.Date
import java.util.Locale

class DateDeserializer : JsonDeserializer<Date> {
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Date {
        return SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.getDefault()).parse(json.asString) ?: Date()
    }
}