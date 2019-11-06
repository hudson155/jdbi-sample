package ca.jhudson.jdbiSample.type

import com.inunison.corgi.model.util.Money
import org.jdbi.v3.core.argument.AbstractArgumentFactory
import org.jdbi.v3.core.argument.Argument
import org.jdbi.v3.core.config.ConfigRegistry
import org.jdbi.v3.core.mapper.ColumnMapper
import java.sql.Types

internal class JdbiMoneyType : JdbiType<Money>() {

    override val typeClass = Money::class.java

    override val columnMapper = ColumnMapper<Money> { r, columnNumber, _ ->
        with(r!!) {
            if (wasNull()) return@with null
            val cents = getLong(columnNumber)
            return@with Money(cents)
        }
    }

    override val argumentFactory = object : AbstractArgumentFactory<Money>(Types.BIGINT) {
        override fun build(value: Money, config: ConfigRegistry): Argument =
            Argument { position, statement, _ -> statement.setLong(position, value.cents) }
    }
}
