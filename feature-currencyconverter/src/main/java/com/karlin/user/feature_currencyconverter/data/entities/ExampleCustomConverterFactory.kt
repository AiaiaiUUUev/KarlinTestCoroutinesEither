package com.karlin.user.feature_currencyconverter.data.entities

import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import okio.Buffer
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type
import javax.inject.Inject

/*
* Simply example not used in project
* */
class ExampleCustomConverterFactory @Inject constructor(
    private val moshi: Moshi
) : Converter.Factory() {

    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<Annotation>,
        methodAnnotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<Any, RequestBody>? =
        Converter { any ->
            val buffer = Buffer()
            val writer = JsonWriter.of(buffer)
            moshi.adapter<Any>(type).toJson(writer, any)
            buffer.readByteString().toRequestBody(MEDIA_TYPE)
        }

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, Any>? =
        Converter { responseBody ->
            JsonReader.of(responseBody.source()).use { reader ->
                reader.beginObject()
                while (reader.hasNext()) {
                    if (reader.selectName(RESULT_NAME) == 0) {
                        if (reader.peek() == JsonReader.Token.BEGIN_ARRAY) reader.beginArray()
                        return@use moshi.adapter<Any>(type).fromJson(reader)
                    } else {
                        reader.skipName()
                        reader.skipValue()
                    }
                }
                return@use null
            }
        }
}

private val RESULT_NAME: JsonReader.Options = JsonReader.Options.of("result")
private val MEDIA_TYPE = "application/json; charset=UTF-8".toMediaType()