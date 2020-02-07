/**
 * The TticketsPrioridad entity.
 *
 * @author    
 *
 *
 */
package tk

class Ttipotickets {
    static mapping = {
         table 'tk.t_tipo_tikets'
         // version is set to false, because this isn't available by default for legacy databases
         version false
         id generator:'identity', column:'f_idrecord'

        fDescripcion column: "f_descripcion"
        fDescripcionIngles column: "f_descripcion_ingles"
    }

    String fDescripcion
    String fDescripcionIngles

    static constraints = {

        fDescripcion(nullable: true)
        fDescripcionIngles(nullable: true)
    }

}
