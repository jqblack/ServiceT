package publico
/**
 * The Tcuentasxpagar entity.
 *
 * @author    
 *
 *
 */
class Tcuentasxpagar {
    static mapping = {
         table 't_cuentasxpagar'
         // version is set to false, because this isn't available by default for legacy databases
         version false
         id generator:'identity', column:'f_id'
    }
    Date fFecha
    Date fFechaVencimiento
    java.math.BigDecimal fMonto
    Long fSuplidor
    String fConcepto
    Boolean fPagado
    String fDocumento

    static constraints = {
        fFecha(nullable: true)
        fFechaVencimiento(nullable: true)
        fMonto(nullable: true)
        fSuplidor(nullable: true, max: 9999999999L)
        fConcepto()
        fPagado(nullable: true)
        fDocumento(size: 0..20)
    }
    String toString() {
    }
}
