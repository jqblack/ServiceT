import groovy.sql.Sql
import org.springframework.jdbc.core.JdbcTemplate

// Place your Spring DSL code here
beans = {
    groovySql(Sql, ref('dataSource'))
    jdbcTemplate(JdbcTemplate) {
        dataSource = ref('dataSource')
    }
}
