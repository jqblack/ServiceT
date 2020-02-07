/**
 * Created by Pabel on 7/2/2017.
 */


function ejecutar() {
    
    solo_numero('horas');
    $("#prioridad").change(function () {

        if ($("#prioridad").val()!=$("#idprioridad_original").val())
        {
            $("#div_nota").show();
        }
        else {
            $("#div_nota").val('');
            $("#div_nota").hide();
        }
    });

    $('#nota').jqxEditor({
        height: "200px",
        width: '100%'
    });
  $('#nota_cancelacion').jqxEditor({
        height: "200px",
        width: '100%'
    });

    // $("#pabel").click(function () {
    //
    //  	var $this = $(this);
    //     var options = {
    //         type: $this.attr('data-type'),
    //        // namespace: 'animal'
    //         // namespace: 'pa_page_alerts_default'
    //     };
    //     options['auto_close'] = 3; // seconds
    //     PixelAdmin.plugins.alerts.add('pabe', options);
    // })
    $("#procesar_modal_asignacion").click(function () {



        var nota =String($("#nota").val());
        if ( $("#tecnico").val()=='0')
        {
            alert("Seleccione el tecnico ");
            return;
        }else if ($("#prioridad").val()!=$("#idprioridad_original").val())
        {

            nota = nota.replace(' ','').trim();
            nota = nota.replace('​','').trim();


            var comparador = "<"+"div"+">"+"<"+"/div"+">";


            // if (String(nota)=="<div>​</div>" || String(nota)=='')
            if (String(nota)==String(comparador) || String(nota)=='')
            {
                alert("Debe digitar un comentario por el cual cambio la prioridad del ticket..");
                return;
            }else {
                var nota =String($("#nota").val());
            }
        }


        $("#procesar_modal_asignacion" ).prop( "disabled", true );

        var broadcast = 'false';
        if ($("#broadcast").is(':checked')) {
            broadcast = 'true';
        }

        var vector ={
            f_idticket:$("#id_tickets").val(),
            f_idtecnico:$("#tecnico").val(),
            f_idprioridad:$("#prioridad").val(),
            f_tipo:$("#tipo_tk").val(),
            f_broadcast:broadcast,
            f_nota:nota
        };

       
        var location = String(window.location).substring(0,String(window.location).lastIndexOf('?'));
        $.ajax({
            type: "POST",
            url: location + "/salvar_asignacion_tickets",
            data: vector,
            success: function (data) {
                if (data=="1"){
                    alert("Ticket Asignado Correctamente");
                    $("#procesar_modal_asignacion" ).prop( "disabled", false );
                    $("#atras_modal_asignacion" ).click();

                    location = location.replace("asignar_tickets",'tickets_sin_asignar');
                    window.location=location;

                }
            }
        });
    });
    $("#atras_modal_asignacion").click(function () {
       $("#tecnico").val('0');
       //$("#horas").val('');
    });

    $("#atras_modal_cancelacion").click(function () {
        $("#nota_cancelacion").val('');
        //$("#horas").val('');
    });


    $("#procesar_modal_cancelacion").click(function () {


        var nota =String($("#nota_cancelacion").val());
            nota = nota.replace(' ','').trim();
        //aqui se esta reemplasando un caracter no imprimible o caracteres
            nota = nota.replace('​','').trim();


            var comparador = "<"+"div"+">"+"<"+"/div"+">";


            // if (String(nota)=="<div>​</div>" || String(nota)=='')
            if (String(nota)==String(comparador) || String(nota)=='')
            {
                alert("Debe digitar un comentario por el cual cambio desea cancelar el ticket..");
                return;
            }





         $("#procesar_modal_cancelacion" ).prop( "disabled", true );

        var vector ={
            f_idticket:$("#id_tickets").val(),
            f_nota:nota
        };


        var location = String(window.location).substring(0,String(window.location).lastIndexOf('?'));
        $.ajax({
            type: "POST",
            url: location + "/salvar_cancelacion_tickets",
            data: vector,
            success: function (data) {
                if (data=="1"){
                    alert("Ticket Cancelado Correctamente");
                    $("#procesar_modal_cancelacion" ).prop( "disabled", false );
                    $("#atras_modal_cancelacion" ).click();

                    location = location.replace("asignar_tickets",'tickets_sin_asignar');
                    window.location=location;

                }
            }
        });
    });
}



function notificacion_si(msj) {
    $("#body_notifificacion_si").html(msj);
    $("#notificacion_si").click();
}

function set_prioridad(id) {
    $("#prioridad").val(id);
}

function set_tipo_tk(id) {

    $("#tipo_tk").val(id);
}

function set_broadcast(valor) {
    if (valor==false)
    {
        $("#broadcast").parent().removeClass('checked');
        $("#broadcast").prop('checked',false);
    }else if(valor == true)
    {
        $("#broadcast").parent().addClass('checked');
        $("#broadcast").prop('checked',true);
    }

}

function notificacion_no(msj) {



    //
    // $("#body_notifificacion_no").html(msj);
    // $("#notificacion_no").click();
}


function set_error(location) {
    window.location = location;
}
