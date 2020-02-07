package publico
/**
 * The Tproductos entity.
 *
 * @author    
 *
 *
 */
class Tproductos {
    static mapping = {
         table 't_productos'
         // version is set to false, because this isn't available by default for legacy databases
         version false
         id generator:'identity', column:'f_id'
         fActivo column:'f_activo'
    }
    String fReferencia
    String fDescripcion
    Long fCantidad
    Boolean fServicio
    java.math.BigDecimal fPrecio
    Boolean fGeneraSerial
    String fVersion
    String fSistema
    Boolean fItbis
    Boolean fActivo

    static constraints = {
        fReferencia(size: 0..20,nullable: true)
        fDescripcion(size: 0..80,nullable: true)
        fCantidad(nullable: true, max: 9999999999L)
        fServicio(nullable: true)
        fPrecio(nullable: true)
        fGeneraSerial(nullable: true)
        fVersion(size: 0..20,nullable: true)
        fSistema(size: 0..60,nullable: true)
        fItbis(nullable: true)
        fActivo(nullable: true)
    }
    String toString() {
    }
}
