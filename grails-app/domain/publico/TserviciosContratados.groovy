package publico
/**
 * The TserviciosContratados entity.
 *
 * @author    
 *
 *
 */
class TserviciosContratados {
    static mapping = {
         table 't_servicios_contratados'
         // version is set to false, because this isn't available by default for legacy databases
         version false
         id generator:'identity', column:'f_id'
         fCantidad column:'f_cantidad'
         fItbis column:'f_itbis'
         fCVariable column:'f_cvariable'
         fCobrable column:'f_cobrable'
         fUrlServidorActualizacion column:'f_url_servidor_actualizacion'
    }
    Long fIdProducto
    Long fIdCliente
    java.math.BigDecimal fPrecio
    Long fDiaFacturacion
    Boolean fActivo
    Boolean fCVariable
    String fKeyserial
    Boolean fCobrable
    Boolean fEnviar
    Long fIdreferente
    java.math.BigDecimal fMontoComision
    java.math.BigDecimal fPorcentaje
    java.math.BigDecimal fCantidad
    java.math.BigDecimal fItbis
    Long fUrlServidorActualizacion;

    static constraints = {
        fIdProducto(nullable: true, max: 9999999999L)
        fIdCliente(nullable: true, max: 9999999999L)
        fPrecio(nullable: true)
        fDiaFacturacion(nullable: true, max: 9999999999L)
        fActivo(nullable: true)
        fKeyserial(nullable: true,size: 0..20)
        fEnviar(nullable: true)
        fIdreferente(max: 9999999999L)
        fMontoComision()
        fPorcentaje(nullable: true)
        fCantidad(nullable: true)
        fItbis(nullable: true)
        fCVariable(nullable: true)
        fUrlServidorActualizacion nullable: true;
    }
    String toString() {
    }
}
