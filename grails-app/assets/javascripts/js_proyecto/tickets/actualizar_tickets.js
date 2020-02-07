/**
 * Created by Pabel on 7/2/2017.
 */

function agregar_comentari(nombre) {
    $("#id_tickets_actualizar").val( $("#"+nombre).attr("name"));
}

var extencion='';
var nombre_archivo='';
function ejecutar() {




    var options2 = {
        orientation: $('body').hasClass('right-to-left') ? "auto right" : 'auto auto'

    }
    $('#bs-datepicker-range').datepicker(options2);

    $('#FileUpload').jqxFileUpload({
        width: '100%',
        rtl : true,
        browseTemplate: 'success',
        uploadTemplate: 'primary',
        cancelTemplate: 'danger',
        multipleFilesUpload: false,
        uploadUrl: window.location + '/salvar_archivo_update',
        fileInputName: 'archivo',
        localization:{
            browseButton:'Examinar',
            uploadButton:'Subir todos',
            cancelButton:'Cancelar todos'

        }
    });

    $('#FileUpload').on('select', function (event) {
        var args = event.args;
        var fileName = args.file;
        var fileSize = args.size;

        nombre_archivo= fileName.substr(0,fileName.lastIndexOf('.'));
        extencion= fileName.substr(fileName.lastIndexOf('.'),fileName.length);

        console.log(nombre_archivo);
        console.log(extencion);

    });
    $('#FileUpload').on('remove', function (event) {
       nombre_archivo='';
        extencion='';
    });



    solo_numero('horas');
    $('#nota').jqxEditor({
        height: "200px",
        width: '100%'
    });


    $('#nota_cerrar').jqxEditor({
        height: "200px",
        width: '100%'
    });

    $("#procesar_modal_comentario").click(function () {


        if ($("#horas").val()=='' || $("#horas").val()=='0')
        {
            alert("Digite las horas del ticket");
            return;
        }

        var horas = 0;
        if ($("#horas").val()!='')
        {
            horas =$("#horas").val();
        }

        var nota =String($("#nota").val());


            nota = nota.replace(' ','').trim();
          //reemplazamos un caracter no imprimible
            nota = nota.replace('​','').trim();


            var comparador = "<"+"div"+">"+"<"+"/div"+">";
            if (String(nota)==String(comparador) || String(nota)=='')
            {
                alert("Debe digitar un comentario por el cual cambio la prioridad del ticket..");
                return;
            }


        $("#procesar_modal_comentario" ).prop( "disabled", true );

        var vector ={
            f_idticket:$("#id_tickets_actualizar").val(),
            f_nota:nota,
            f_horas:horas,
            f_nombre_archivo:nombre_archivo,
            f_extencion:extencion
        };

       
        // var location = String(window.location).substring(0,String(window.location).lastIndexOf('?'));
        var location = String(window.location);
        $.ajax({
            type: "POST",
            url: location + "/salvar_comentario",
            data: vector,
            success: function (data) {
                if (data=="1"){
                    $('#FileUpload').jqxFileUpload('uploadAll');
                    alert("Comentario Creado Correctamente");
                    $("#procesar_modal_comentario" ).prop( "disabled", false );
                    $("#atras_modal_comentario" ).click();

                    // location = location.replace("reasignar_tickets",'tickets_asignados');
                    window.location=window.location;

                }
            }
        });
    });
    $("#atras_modal_comentario").click(function () {
       $("#horas").val('0');
       $("#nota").val('');
        $('#FileUpload').jqxFileUpload('cancelAll');
    });

    $("#atras_modal_cerrar").click(function () {

        $("#nota_cerrar").val('');

    });


    solo_numero("horas_cerrar");

    $("#procesar_modal_cerrar").click(function () {




        if ($("#horas_cerrar").val()=='' || $("#horas_cerrar").val()=='0')
        {
            alert("Digite la hora en que se cerro el ticket");
            return;
        }


        var nota =String($("#nota_cerrar").val());


        nota = nota.replace(' ','').trim();
        //reemplazamos un caracter no imprimible
        nota = nota.replace('​','').trim();


        var comparador = "<"+"div"+">"+"<"+"/div"+">";
        if (String(nota)==String(comparador) || String(nota)=='')
        {
            alert("Debe digitar un comentario para cerrar el ticket..");
            return;
        }


        $("#procesar_modal_cerrar" ).prop( "disabled", true );

        var vector ={
            f_idticket:$("#id_tickets_cerrar").val(),
            f_nota:nota,
            f_horas:parseFloat($("#horas_cerrar").val())
        };


        // var location = String(window.location).substring(0,String(window.location).lastIndexOf('?'));
        var location = String(window.location);
        $.ajax({
            type: "POST",
            url: location + "/cerrar_ticket",
            data: vector,
            success: function (data) {
                if (data=="1"){

                    alert("Ticket Cerrardo Correctamente");
                    $("#procesar_modal_cerrar" ).prop( "disabled", false );
                    $("#atras_modal_cerrar" ).click();
                    // location = location.replace("reasignar_tickets",'tickets_asignados');
                    window.location=window.location;

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


function set_tecnico(id) {
    $("#tecnico").val(id);
}


function notificacion_no(msj) {

    // $("#body_notifificacion_no").html(msj);
    // $("#notificacion_no").click();
}


function get_comentario(nombre) {
   var id =$("#"+nombre).attr("name");


    var vector ={
        idrecord:id
    }

   // console.log(vector);
    
    
    $.ajax({
        type: "POST",
        url: location + "/get_update_by_id",
        data: vector,
        success: function (data) {

            // console.log("klok: "+$("#url_imagen").attr('src'));
            if (data !='[]')
            {

               var json =eval(data);
                $("#lista_cometario").html("");
                var html = '';

                for(var i=0;i< json.length;i++)
                {
                    var descargar ='';


                    if (json[i].f_picture!=null)
                    {
                        descargar = '<form action="'+window.location+'/descargarUpdate" method="post" target="_blank">' +
                            '<input hidden type="text" name="idrecord" value="'+json[i].f_idrecord+'">' +
                            '&nbsp;&nbsp;<button type="submit" class="btn-primary btn btn-sm" ><span class="fa fa-arrow-circle-down"></span>&nbsp;Descargar archivo</button>' +
                            '</form>';
                    }
                    $("#titulo_principal").text(json[i].f_titulo);
                    html = html +'<li class="" style="list-style: none;">';
                    html = html +'<img src="'+$("#url_imagen").attr('src')+'" width="50" height="50" class="img-circle">';
                    html = html +'<span class="tableFont"> <strong>User :</strong> </span>';
                    html = html +'<span class="tableFont2" > '+json[i].f_hecho_por+'</span>';
                    html = html +descargar;
                    html = html +'<h6 class="fk"><strong>Porteado :</strong><span>'+json[i].f_fecha+'</span></h6>';
                    html = html +'<div class="alert alert-info " style="width: 95%;">';
                    html = html +json[i].f_update;
                    html = html +'</div>';
                    html = html +' </li>';
                }
                $("#lista_cometario").html(html);

            }
        }
    });




}

function descargarArchivo(id) {
    $.ajax({
        type: "POST",
        url: location + "/descargarUpdate",
        data: {idrecord:id},
        success: function (data) {
            
        }
    })
}

function get_id_cerrar_ticket(nombre) {
    $("#id_tickets_cerrar").val( $("#"+nombre).attr("name"));
}
