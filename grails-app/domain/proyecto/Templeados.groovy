package proyecto
/**
 * The proyecto.Templeados entity.
 *
 * @author    
 *
 *
 */
class Templeados {
    static mapping = {
         table 'proyectos.t_empleados'
         // version is set to false, because this isn't available by default for legacy databases
        version false
        id generator:'identity', column:'f_id'
       fEmailtickets column:'f_email_tickets'
    }
    String fDescripcion
    Boolean fActivo
    String fEmail
    String fEmailtickets

    static constraints = {
        fDescripcion(size: 0..100)
        fActivo(nullable: true)
        fEmail(nullable: true)
        fEmailtickets(nullable: true)
    }
    String toString() {
    }
}
