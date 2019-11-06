package ca.jhudson.jdbiSample.type

import org.jdbi.v3.core.argument.AbstractArgumentFactory
import org.jdbi.v3.core.argument.Argument
import org.jdbi.v3.core.config.ConfigRegistry
import java.sql.Types
import java.util.UUID

internal class JdbiUuidType : JdbiType<UUID>() {

    override val typeClass = UUID::class.java

    override val columnMapper: Nothing? = null

    override val argumentFactory = object : AbstractArgumentFactory<UUID>(Types.VARCHAR) {
        override fun build(value: UUID, config: ConfigRegistry): Argument {
            return Argument { position, statement, _ ->
                statement.setString(position, value.toString())
            }
        }
    }
}
