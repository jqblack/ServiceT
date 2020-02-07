package publico
/**
 * The Tclientes entity.
 *
 * @author    
 *
 *
 */


class Tclientes {
    static mapping = {
         table 't_clientes'
         // version is set to false, because this isn't available by default for legacy databases
         version false
         id generator:'identity', column:'f_id'
         fMoneda column:'f_moneda'
         fActivo column:'f_activo'
    }

    String fNombreEmpresa
    String fContacto
    String fDireccion
    String fCiudad
    String fEstado
    String fZipcode
    String fPais
    String fTelefono1
    String fTelefono2
    String fCelContacto
    String fFax
    String fContactoCobro
    String fIdIdentificacion
    String fEmail
    Boolean fEnviarEmail
    Boolean fRetieneIsr
    Boolean fRetieneItbis
    Boolean fFacturarleTax
    Integer fEmpresaPersona
    Long fMoneda
    Boolean fActivo

    static constraints = {

        fNombreEmpresa(size: 0..50)
        fContacto()
        fDireccion(nullable: true)
        fCiudad(nullable: true,size: 0..50)
        fEstado(nullable: true,size: 0..50)
        fZipcode(nullable: true,size: 0..20)
        fPais(nullable: true)
        fTelefono1(nullable: true)
        fTelefono2(nullable: true)
        fCelContacto(nullable: true)
        fFax(nullable: true)
        fContactoCobro(nullable: true)
        fIdIdentificacion(nullable: true)
        fEmail(nullable: true)
        fEnviarEmail(nullable: true)
        fRetieneIsr(nullable: true)
        fRetieneItbis(nullable: true)
        fFacturarleTax(nullable: true)
        fMoneda(nullable: true)
        fActivo(nullable: true)
        fEmpresaPersona(nullable: true, max: 99999)
    }
    String toString() {


    }
}
