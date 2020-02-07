package publico
/**
 * The Tusuario entity.
 *
 * @author    
 *
 *
 */
class Tusuario {
    static mapping = {
         table 't_usuario'
         // version is set to false, because this isn't available by default for legacy databases
         version false
         id generator:'identity', column:'f_codigo_usuario'
         fTickets column:'f_tickets'
    }
    String fApellido
    String fNombre
    String fDireccion
    String fTelefono
    String fEmail
    String fIdUsuario
    Date fFechaCaducidad
    Boolean fActivo
    Boolean fTickets
    String fPassword
    Long fIdempleado

    static constraints = {
        fApellido(size: 0..25,nullable: true)
        fNombre(size: 0..40,nullable: true)
        fDireccion(size: 0..60,nullable: true)
        fTelefono(size: 0..16,nullable: true)
        fEmail(size: 0..60,nullable: true)
        fIdUsuario(size: 0..60,nullable: true)
        fFechaCaducidad(nullable: true)
        fTickets(nullable: true)
        fActivo(nullable: true)
        fPassword(size: 0..60,nullable: true)
        fIdempleado(nullable: true, max: 9999999999L)
    }
    String toString() {
    }
}
