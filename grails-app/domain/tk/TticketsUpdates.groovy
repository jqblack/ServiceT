/**
 * The TticketsUpdates entity.
 *
 * @author    
 *
 *
 */
package tk
class TticketsUpdates {
    static mapping = {
         table 'tk.t_tickets_updates'
         // version is set to false, because this isn't available by default for legacy databases
         version false
         id generator:'identity', column:'f_idrecord'
         fHoras column:'f_horas'
         fHechoPor column:'f_hecho_por'
         fExtencionArchivo column:'f_extencion_archivo'
         fNombreArchivo column:'f_nombre_archivo'
    }

    Long fIdticket
    Long fHechoPor
    String fUpdate
    String fExtencionArchivo
    String fNombreArchivo
    byte [] fPicture
    Date fFecha
    BigDecimal fHoras

    static constraints = {

        fIdticket(max: 9999999999L)
        fUpdate(blank: false)
        fPicture(nullable: true)
        fFecha()
        fHoras(nullable: true);
        fHechoPor(nullable: true);
        fNombreArchivo(nullable: true);
        fExtencionArchivo(nullable: true);
    }

}
