package codes.whytea.persistence

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

fun connectToDatabase() = HikariDataSource(HikariConfig().apply{
    driverClassName = "org.h2.Driver"
    jdbcUrl = "jdbc:h2:mem:test"
    maximumPoolSize = 3
    isAutoCommit = false
    transactionIsolation = "TRANSACTION_REPEATABLE_READ"
    validate()
})

suspend fun <T> query(block: suspend() -> T) = newSuspendedTransaction { block() }