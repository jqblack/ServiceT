package publico
/**
 * The TlogConexiones entity.
 *
 * @author    
 *
 *
 */
class TlogConexiones {
    static mapping = {
         table 't_log_conexiones'
         // version is set to false, because this isn't available by default for legacy databases
         version false
         id generator:'identity', column:'f_idrecord'
    }
    Long fIduser
    Date fFecha
    java.sql.Time fHoraEntrada
    String fIp
    String fNombreMaquina

    static constraints = {
        fIduser(max: 9999999999L)
        fFecha()
        fHoraEntrada()
        fIp(size: 0..16)
        fNombreMaquina(size: 0..40)
    }
    String toString() {
    }
}
