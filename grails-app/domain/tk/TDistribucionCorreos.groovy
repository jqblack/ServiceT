/**
 * The TticketsUpdates entity.
 *
 * @author    
 *
 *
 */
package tk

class TDistribucionCorreos {
    static mapping = {
         table 'tk.t_distribucion_correos'
         // version is set to false, because this isn't available by default for legacy databases
         version false
         id generator:'identity', column:'f_idrecord'
        fIdcliente column:'f_idcliente'
        fCorreos column:'f_correos'

    }

    Long fIdcliente
    String fCorreos



    static constraints = {

        fIdcliente(nullable: true)
        fCorreos(nullable: false)

    }

}
