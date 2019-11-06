package ca.jhudson.jdbiSample.type

import com.inunison.lib.jwt.jwt.Permissions
import org.jdbi.v3.core.argument.AbstractArgumentFactory
import org.jdbi.v3.core.argument.Argument
import org.jdbi.v3.core.config.ConfigRegistry
import org.jdbi.v3.core.mapper.ColumnMapper
import java.sql.Types

internal class JdbiPermissionsType : JdbiType<Permissions>() {

    override val typeClass = Permissions::class.java

    override val columnMapper = ColumnMapper<Permissions> { r, columnNumber, _ ->
        with(r!!) {
            return@with getString(columnNumber)?.let { Permissions.fromBitString(it) }
        }
    }

    override val argumentFactory = object : AbstractArgumentFactory<Permissions>(Types.DATE) {
        override fun build(value: Permissions, config: ConfigRegistry): Argument {
            return Argument { position, statement, _ ->
                statement.setString(position, value.toBitString())
            }
        }
    }
}
