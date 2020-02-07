/**
 * The TticketsFiles entity.
 *
 * @author    
 *
 *
 */
package tk
class TticketsFiles {
    static mapping = {
         table 'tk.t_tickets_files'
         // version is set to false, because this isn't available by default for legacy databases
         version false
         id generator:'identity', column:'f_idrecord'
    }

    Long fIdticket
    //java.sql.Blob fFile
    byte[] fFile
    String fNombre
    String fExtencion

    static constraints = {

        fIdticket(nullable: true, max: 9999999999L)
        fFile(nullable: true)
       // fNombre(size: 0..50)
        fExtencion(size: 0..20)
    }
    String toString() {

    }
}
