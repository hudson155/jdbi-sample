package ca.jhudson.jdbiSample.type

import org.jdbi.v3.core.argument.AbstractArgumentFactory
import org.jdbi.v3.core.argument.Argument
import org.jdbi.v3.core.config.ConfigRegistry
import org.jdbi.v3.core.mapper.ColumnMapper
import org.jdbi.v3.core.mapper.MappingException
import java.sql.Types

internal class JdbiLongRangeType : JdbiType<LongRange>() {

    override val typeClass = LongRange::class.java

    override val columnMapper = ColumnMapper<LongRange> { r, columnNumber, _ ->
        with(r!!) {
            val string = getString(columnNumber) ?: return@with null
            val matchResult = Regex("\\[([0-9]+),([0-9]+)\\)").matchEntire(string)
                ?: throw MappingException("Invalid LongRange")
            return@with LongRange(
                start = matchResult.groupValues[1].toLong(),
                endInclusive = matchResult.groupValues[2].toLong() - 1
            )
        }
    }

    override val argumentFactory = object : AbstractArgumentFactory<LongRange>(Types.VARCHAR) {
        override fun build(value: LongRange, config: ConfigRegistry): Argument {
            return Argument { position, statement, _ ->
                statement.setString(position, "[${value.start},${value.endInclusive + 1})")
            }
        }
    }
}
