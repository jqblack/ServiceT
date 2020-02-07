/**
 * The TopcionesMenu entity.
 *
 * @author    
 *
 *
 */
package cfg;
class TopcionesMenu {
    static mapping = {
         table 'cfg.t_opciones_menu'
         // version is set to false, because this isn't available by default for legacy databases
         version false
         id generator:'identity', column:'f_idrecord'
    }
    String fDescripcion
    String fIcono
    Long fIdpadre
    String fUrl

    static constraints = {
        fDescripcion()
        fIcono()
        fIdpadre(nullable: true, max: 9999999999L)
        fUrl()
    }
    String toString() {
    }
}
