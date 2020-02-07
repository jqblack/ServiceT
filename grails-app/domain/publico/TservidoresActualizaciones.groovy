/**
 * The TservidoresActualizaciones entity.
 *
 * @author    
 *
 *
 */
package publico
class TservidoresActualizaciones {
    static mapping = {
         table 'public.t_servidores_actualizaciones'
         // version is set to false, because this isn't available by default for legacy databases
         version false
         id generator:'identity', column:'f_idrecord'
    }
    String fProyecto
    String fUrlServidor

    static constraints = {
        fProyecto(nullable: true)
        fUrlServidor(nullable: true)
    }
    String toString() {
    }
}
