package ca.jhudson.jdbiSample.type

import org.jdbi.v3.core.argument.AbstractArgumentFactory
import org.jdbi.v3.core.mapper.ColumnMapper
import java.lang.reflect.Type

internal abstract class JdbiType<T> {
    protected abstract val typeClass: Class<T>
    @Suppress("UnsafeCast")
    val type get() = typeClass as Type
    abstract val columnMapper: ColumnMapper<T>?
    abstract val argumentFactory: AbstractArgumentFactory<T>?
}
