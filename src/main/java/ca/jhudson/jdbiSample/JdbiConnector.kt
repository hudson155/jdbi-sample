package ca.jhudson.jdbiSample

import ca.jhudson.jdbiSample.type.JdbiDateTimeType
import ca.jhudson.jdbiSample.type.JdbiLongRangeType
import ca.jhudson.jdbiSample.type.JdbiMoneyType
import ca.jhudson.jdbiSample.type.JdbiPermissionsType
import ca.jhudson.jdbiSample.type.JdbiType
import ca.jhudson.jdbiSample.type.JdbiUuidType
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.KotlinPlugin
import org.jdbi.v3.postgres.PostgresPlugin
import org.jdbi.v3.sqlobject.SqlObjectPlugin
import org.jdbi.v3.sqlobject.kotlin.KotlinSqlObjectPlugin
import javax.sql.DataSource

class JdbiConnector(dataSource: DataSource) {

    val jdbi: Jdbi = Jdbi.create(dataSource)

    init {
        installPlugins()
        registerJdbiTypes()
    }

    private fun installPlugins(): Unit = with(jdbi) {
        installPlugin(SqlObjectPlugin())
        installPlugin(KotlinSqlObjectPlugin())
        installPlugin(KotlinPlugin())
        installPlugin(PostgresPlugin())
    }

    private fun registerJdbiTypes(): Unit = with(jdbi) {
        registerJdbiType(JdbiDateTimeType())
        registerJdbiType(JdbiLongRangeType())
        registerJdbiType(JdbiMoneyType())
        registerJdbiType(JdbiPermissionsType())
        registerJdbiType(JdbiUuidType())
    }

    private fun <T> Jdbi.registerJdbiType(jdbiType: JdbiType<T>): Jdbi {
        return this.apply {
            jdbiType.columnMapper?.let { registerColumnMapper(jdbiType.type, it) }
            jdbiType.argumentFactory?.let { registerArgument(it) }
        }
    }
}
