package fbo.costa.news.data.room

import androidx.room.TypeConverter
import fbo.costa.news.data.model.SourceApiEntity

class Converters {

    @TypeConverter
    fun fromSource(source: SourceApiEntity): String {
        return source.name
    }

    @TypeConverter
    fun toSource(name: String): SourceApiEntity {
        return SourceApiEntity("", name)
    }
}
