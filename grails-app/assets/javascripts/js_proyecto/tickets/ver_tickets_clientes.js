

/**
 * Created by Pabel on 10/2/2017.
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


    $('#nota').jqxEditor({
        height: "200px",
        width: '100%'
    });

}


function get_comentario(nombre) {
    var id =$("#"+nombre).attr("name");


    var vector ={
        idrecord:id
    }

    console.log(vector);


    $.ajax({
        type: "POST",
        url: location + "/get_update_by_id",
        data: vector,
        success: function (data) {

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
                        descargar = '<form action="descargarUpdate" method="post" target="_blank">' +
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
