package jq_servicios

import clientes.Tordentrabajo
import clientes.Tusuarioclientes
import grails.gorm.transactions.Transactional

@Transactional
class ClientesService {

    def serviceMethod() { }

    Tusuarioclientes iniciar(Long user, String pass){

        Tusuarioclientes tusuarioclientes = Tusuarioclientes.findByFUsernameAndFContrasena(user,pass.encodeAsMD5() as String);
        return tusuarioclientes
    }

    Tusuarioclientes cambiarfirstpass(Long idrecord,String pass){

        Tusuarioclientes tusuarioclientes = Tusuarioclientes.findById(idrecord);

        if (tusuarioclientes){
            tusuarioclientes.setfFirstlogin(false);
            tusuarioclientes.setfContrasena(pass.encodeAsMD5() as String)

            tusuarioclientes.save(failOnError: true);
            return tusuarioclientes;
        }
    }

    Boolean validarbyid(Long idrecord){
        Tusuarioclientes tusuarioclientes = Tusuarioclientes.findById(idrecord)
        return tusuarioclientes.fFirstlogin;
    }

    Map Dash(){
        Map mapa = new HashMap();

        int num = 0, num2 = 0, num3 = 0, num4 = 0;
        if (Tordentrabajo.findAllByFStatus(1).size() > 0){
            num = Tordentrabajo.findAllByFStatus(1).size();
        }
        mapa.put("pendientes",num);

        if (Tordentrabajo.findAllByFStatus(2).size() > 0){
            num2 = Tordentrabajo.findAllByFStatus(2).size()
        }
        mapa.put("proceso",num2);

        if (Tordentrabajo.findAll().size() > 0){
            num3 = Tordentrabajo.findAll().size();
        }
        mapa.put("total",num3);

        if (Tordentrabajo.findAllByFStatus(4).size() > 0){
            num4 = Tordentrabajo.findAllByFStatus(4).size();
        }
        mapa.put("canceladas",num4);


        return mapa;

    }
}
