package publico
/**
 * The TcategoriasGastos entity.
 *
 * @author    
 *
 *
 */
class TcategoriasGastos {
    static mapping = {
         table 't_categorias_gastos'
         // version is set to false, because this isn't available by default for legacy databases
         version false
         id generator:'identity', column:'f_id'
    }

    String fDescripcion

    static constraints = {

        fDescripcion(size: 0..20)
    }
    String toString() {

    }
}
