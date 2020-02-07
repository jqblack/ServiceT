package publico
/**
 * The TdetCotizacion entity.
 *
 * @author    
 *
 *
 */
class TdetCotizacion {
    static mapping = {
         table 't_det_cotizacion'
         // version is set to false, because this isn't available by default for legacy databases
         version false
         id generator:'identity', column:'f_idrecord'
    }
    Long fIdCotizacion
    String fReferencia
    Long fCantidad
    java.math.BigDecimal fPrecio
    String fDescripcion

    static constraints = {
        fIdCotizacion(nullable: true, max: 9999999999L)
        fReferencia(size: 0..20)
        fCantidad(nullable: true, max: 9999999999L)
        fPrecio(nullable: true)
        fDescripcion()
    }
    String toString() {
    }
}
