package ca.jhudson.jdbiSample.type

import org.jdbi.v3.core.argument.AbstractArgumentFactory
import org.jdbi.v3.core.argument.Argument
import org.jdbi.v3.core.config.ConfigRegistry
import org.jdbi.v3.core.mapper.ColumnMapper
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import java.sql.Timestamp
import java.sql.Types

internal class JdbiDateTimeType : JdbiType<DateTime>() {

    override val typeClass = DateTime::class.java

    override val columnMapper = ColumnMapper<DateTime> { r, columnNumber, _ ->
        with(r!!) {
            return@with getTimestamp(columnNumber)?.let {
                DateTime(it.time, DateTimeZone.UTC).withZoneRetainFields(DateTimeZone.getDefault())
            }
        }
    }

    override val argumentFactory = object : AbstractArgumentFactory<DateTime>(Types.TIMESTAMP) {
        override fun build(value: DateTime, config: ConfigRegistry): Argument {
            return Argument { position, statement, _ ->
                statement.setTimestamp(
                    position,
                    Timestamp(value.withZoneRetainFields(DateTimeZone.UTC).millis)
                )
            }
        }
    }
}
