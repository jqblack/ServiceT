/**
 * The TticketsPrioridad entity.
 *
 * @author    
 *
 *
 */
package tk
class TticketsPrioridad {
    static mapping = {
         table 'tk.t_tickets_prioridad'
         // version is set to false, because this isn't available by default for legacy databases
         version false
         id generator:'identity', column:'f_idrecord'
    }

    Long fTipoTk
    Long fPrioridad
    Long fTiempoSolucion

    static constraints = {

        fTipoTk(max: 9999999999L)
        fPrioridad(max: 9999999999L)
        fTiempoSolucion(max: 9999999999L)
    }

}
