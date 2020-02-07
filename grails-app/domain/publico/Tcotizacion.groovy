package publico
/**
 * The Tcotizacion entity.
 *
 * @author    
 *
 *
 */
class Tcotizacion {
    static mapping = {
         table 't_cotizacion'
         // version is set to false, because this isn't available by default for legacy databases
         version false
         id generator:'identity', column:'f_id'
    }

    Date fFecha
    Long fIdCliente
    Long fHechoPor
    java.math.BigDecimal fMonto
    java.math.BigDecimal fItbis
    java.math.BigDecimal fEnvio
    String fComentario

    static constraints = {
        fFecha(nullable: true)
        fIdCliente(nullable: true, max: 9999999999L)
        fHechoPor(nullable: true, max: 9999999999L)
        fMonto(nullable: true)
        fItbis(nullable: true)
        fEnvio(nullable: true)
        fComentario()
    }
    String toString() {
    }
}
