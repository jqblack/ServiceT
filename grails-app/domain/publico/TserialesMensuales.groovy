package publico
/**
 * The TserialesMensuales entity.
 *
 * @author    
 *
 *
 */
class TserialesMensuales {
    static mapping = {
         table 't_seriales_mensuales'
         // version is set to false, because this isn't available by default for legacy databases
         version false
         id generator:'identity', column:'f_id'
    }
    Long fIdCliente
    String fSerialMensual
    Date fFechaGenerado
    String fMesYear
    Long fIdServicio
    Boolean fEnviado
    Date fFechaEnviado

    static constraints = {
        fIdCliente(nullable: true, max: 9999999999L)
        fSerialMensual(size: 0..20)
        fFechaGenerado(nullable: true)
        fMesYear(size: 0..20)
        fIdServicio(nullable: true, max: 9999999999L)
        fEnviado(nullable: true)
        fFechaEnviado(nullable: true)
    }
    String toString() {
    }
}
