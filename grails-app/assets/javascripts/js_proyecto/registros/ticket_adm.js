/**
 * Created by Michael Jared Diaz on 6/1/2017.
 */

var listArchivos = new Array();
var idrecord_envio_archivo;


function get_tema() {
    return 'material';
}

function ejecutar() {

    $('#descripcion').jqxEditor({
        height: "200px",
        width: '100%'
    });


    $('#FileUpload').jqxFileUpload({
        width: '100%',
        rtl : true,
        browseTemplate: 'success',
        uploadTemplate: 'primary',
        cancelTemplate: 'danger',
        multipleFilesUpload: true,
        uploadUrl: window.location + '/salvar_imagen_ticket_admin',
        fileInputName: 'archivo'});

    $('#FileUpload').on('select', function (event) {
        var args = event.args;
        var fileName = args.file;
        var fileSize = args.size;
        listArchivos.push(fileName);
    });

    $('#FileUpload').on('remove', function (event) {
        var fileName = event.args.file;

        for (var i=0;i<listArchivos.length;i++){
            if(listArchivos[i] == fileName){
                listArchivos.splice(i,1);
            }
        }
    });
    
    $("#nuevo").click(function () {
        nuevo();
    });
    

    $("#salvar").click(function () {

        $( "#salvar" ).prop( "disabled", true );
       // notificacion_no("Seleccione el proyecto antes de salvar.");
        var json = "[";
        for(var i=0;i<listArchivos.length;i++){
            json+=',{"f_nombre":"'+listArchivos[i]+'",' +
                '"f_extencion":"'+listArchivos[i].substring(listArchivos[i].lastIndexOf("."),listArchivos[i].length)+'"}';
        }

        json = json.replace("[,{","[{")+"]";

        if ($("#proyecto").val() == '0') {
            notificacion_no("Seleccione el proyecto antes de salvar.");
            return;
        } else if ($("#prioridad").val() == "0") {
            notificacion_no("Seleccione la prioridad");
            return;
        } else if ($("#titulo").val() == "") {
            notificacion_no("Escriba el titulo del ticket antes de salvar");
            return;
        } else if ($("#tipo_tk").val() == "0") {

                notificacion_no("Seleccione el tipo de ticket");

            return;
        }  else if ($("#descripcion").val() == "") {
            notificacion_no("Escriba la descripcion antes de salvar");
            return;
        }

        var broadcast = 'false';
        // if ($("#broadcast").is(':checked')) {
        //     broadcast = 'true';
        // }

        var datos = {
            f_titulo: $("#titulo").val(),
            f_descripcion: $("#descripcion").val(),
            f_prioridad: $("#prioridad").val(),
            f_proyecto: $("#proyecto").val(),
            f_idusuario:$("#idUsuario").val(),
            f_tipo_tk:$("#tipo_tk").val(),
            f_broadcast:broadcast,
            f_json: json
        };

        $.ajax({
            type: "POST",
            url: window.location + "/salvar_ticket_admin",
            data: datos,
            success: function (data) {
               // if (data=="1"){
                var datto = String(data).split("-");
                idrecord_envio_archivo  =datto[1];
                    $('#FileUpload').jqxFileUpload('uploadAll');
                    $("#nuevo").click();
                    notificacion_si("Ticket Salvado Correctamente");
                    $("#salvar" ).prop( "disabled", false );
                //}
            }
        });
    });


    $('#FileUpload').on('uploadEnd', function (event) {
        var datos ={
            idrecord:idrecord_envio_archivo
        }
        $.ajax({
            type: "POST",
            url: window.location + "/enviar_archivos_tickets_admin",
            data: datos,
            success: function (data) {
                //if (data=="1"){

                // $('#FileUpload').jqxFileUpload('uploadAll');
                // $("#nuevo").click();
                // notificacion_si("Ticket Salvado Correctamente");
                // $("#salvar" ).prop( "disabled", false );
                //}
            }
        });
    });

}
function nuevo() {
$("#titulo").val('');
$("#descripcion").val('');
$("#prioridad").val(0);
$("#proyecto").val(0);
$("#tipo_tk").val(0);
// $("#broadcast").parent().removeClass('checked');
// $("#broadcast").prop('checked',false);


}


function notificacion_si(msj) {
    $("#body_notifificacion_si").html(msj);
    $("#notificacion_si").click();
}

function notificacion_no(msj) {

    $("#body_notifificacion_no").html(msj);
    $("#notificacion_no").click();
}




