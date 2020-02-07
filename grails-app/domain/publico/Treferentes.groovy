package publico
/**
 * The Treferentes entity.
 *
 * @author    
 *
 *
 */
class Treferentes {
    static mapping = {
         table 't_referentes'
         // version is set to false, because this isn't available by default for legacy databases
         version false
         id generator:'identity', column:'f_idrecord'
         fPorcentaje column:'f_porciento'
         fBanco column:'f_banco'
    }
    String fNombre
    String fApellido
    String fDireccion
    String fEmail
    String fTelefono1
    String fTelefono2
    String fCiudad
    String fPais
    String fCuentaDeposito
    String fBanco
    BigDecimal fPorcentaje

    static constraints = {
        fNombre(size: 1..20, blank: false,nullable: true)
        fApellido(size: 0..20,nullable: true)
        fDireccion(size: 0..100,nullable: true)
        fEmail(size: 0..100,nullable: true)
        fTelefono1(size: 0..15,nullable: true)
        fTelefono2(size: 0..15,nullable: true)
        fCiudad(size: 0..20,nullable: true)
        fPais(size: 0..20,nullable: true)
        fCuentaDeposito(size: 0..20,nullable: true)
        fPorcentaje(nullable: true)
        fBanco(nullable: true)
    }
    String toString() {
    }
}
