package publico
/**
 * The Tgastos entity.
 *
 * @author    
 *
 *
 */
class Tgastos {
    static mapping = {
         table 't_gastos'
         // version is set to false, because this isn't available by default for legacy databases
         version false
         id generator:'identity', column:'f_id'
    }
    Date fFecha
    java.math.BigDecimal fMonto
    Long fCategoria
    String fConcepto

    static constraints = {
        fFecha(nullable: true)
        fMonto(nullable: true)
        fCategoria(nullable: true, max: 9999999999L)
        fConcepto()
    }
    String toString() {
    }
}
