/**
 * The Tarchivoordenes entity.
 *
 * @author    
 *
 *
 */
package clientes
class Tarchivoordenes {
    static mapping = {
         table 'clientes.t_archivoordenes'
         // version is set to false, because this isn't available by default for legacy databases
         version false
         id generator:'identity', column:'f_idrecord'
    }
    byte[] fArchivo
    Boolean fOrden
    String fNamearchivo
    Long fIdordentrabajo

    static constraints = {
        fArchivo(nullable: true)
        fOrden(nullable: true)
        fNamearchivo(nullable: true)
        fIdordentrabajo()
    }

}
