/**
 * Created by Pabel on 14/2/2017.
 */


function ejecutar() {

    var options2 = {
        orientation: $('body').hasClass('right-to-left') ? "auto right" : 'auto auto'

    }

    $('#cerrado').switcher();
    $('#abierto').switcher();
    $('#jq-datatables-example').dataTable();
    $('#jq-datatables-example_wrapper .table-caption').text('Resultados');
    $('#jq-datatables-example_wrapper .dataTables_filter input').attr('placeholder', 'Buscar...');




    $('#bs-datepicker-range').datepicker(options2);


    // $("#actualizar").click(function () {
    //     var cerrado = 'false';
    //
    //
    //     if ($("#cerrado").is(':checked')) {
    //         cerrado = 'true';
    //     }
    //
    //     var vector={
    //         idcliente:111,
    //         titulo:$("#titulo").val(),
    //         fecha_desde:$("#fecha_desde").val(),
    //         fecha_hasta:$("#fecha_hasta").val(),
    //         cerrado:cerrado
    //
    //
    //     };
    //
    //
    //     $.ajax({
    //         type: "POST",
    //         url: window.location + '/get_tickets_clientes_abiertos_cerrados',
    //         data: vector,
    //         success: function (data) {
    //
    //
    //             // $("#cuerpo_reporte").html('');
    //             $("#jq-datatables-example tbody").html('');
    //             var html='';
    //           // console.log(data);
    //             if (data!='[]')
    //             {
    //
    //                 var json = eval(data);
    //                 for(var i = 0; i< json.length;i++)
    //                 {
    //                     html = html +  '<tr class="odd gradeX">';
    //                     html = html +  '<td style="display: none">'+json[i].f_idrecord+'</td>';
    //                     html = html +  '<td style="text-align: center">'+json[i].f_nombre+'</td>';
    //                     html = html +  '<td style="text-align: center">'+json[i].f_titulo+'</td>';
    //                     html = html +  '<td style="text-align: center" class="center">'+json[i].f_fecha+'</td>';
    //                     html = html +  '<td style="text-align: center" class="center">'+json[i].f_fecha_modificacion+'</td>';
    //                     // html = html +  '<td class="center"></td>';
    //                     html = html +  '</tr>';
    //
    //                 }
    //
    //                 $('#jq-datatables-example').dataTable();
    //
    //                 $("#jq-datatables-example tbody").html(html);
    //
    //
    //
    //             }
    //         }
    //
    //     });
    //
    // })



    $('#abierto').change(function () {
        if ($(this).is(":checked")) {
            $("#cerrado").parent().removeClass('checked');
            $("#cerrado").prop('checked', false);

        }
        else {
            $("#cerrado").parent().addClass('checked');
            $("#cerrado").prop('checked', true);


        }

    });


    $('#cerrado').change(function () {
        if ($(this).is(":checked")) {
            $("#abierto").parent().removeClass('checked');
            $("#abierto").prop('checked', false);


        }
        else {
            $("#abierto").parent().addClass('checked');
            $("#abierto").prop('checked', true);

        }


    });
   // poner_check_abierto();

    // $("#abierto").parent().addClass('checked');
    // $("#abierto").prop('checked', true);
}

function  poner_check() {
    $("#cerrado").parent().addClass('checked');
    $("#cerrado").prop('checked',true);

}

function  quitar_check() {
    $("#cerrado").parent().removeClass('checked');
    $("#cerrado").prop('checked',false);

}
function  poner_check_abierto() {
    $("#abierto").parent().addClass('checked');
    $("#abierto").prop('checked',true);

}

function  quitar_check_abierto() {
    $("#abierto").parent().removeClass('checked');
    $("#abierto").prop('checked',false);

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


function get_archivos(nombre) {
    var id =$("#"+nombre).attr("name");


    var vector ={
        idrecord:id
    }

  //  console.log(vector);


    $.ajax({
        type: "POST",
        url: location + "/get_archivos_by_idtickets",
        data: vector,
        success: function (data) {

            if (data !='[]')
            {

                console.log(data);
                var json =eval(data);
                $("#cuerpo_archivos").html("");
                var html = '';

                for(var i=0;i< json.length;i++)
                {
                        var descargar ='';
                        if (json[i].f_file!=null)
                        {
                            descargar = '<form action="descargar_archivo" method="post" target="_blank">' +
                                '<input hidden type="text" name="idrecord" value="'+json[i].f_idrecord+'">' +
                                '&nbsp;&nbsp;<button type="submit" class="btn-primary btn btn-sm" ><span class="fa fa-arrow-circle-down"></span>&nbsp;Descargar archivo</button>' +
                                '</form>';
                        }

                    html = html +' <tr>';
                    html = html +' <td>'+json[i].f_nombre+'</td>';
                    html = html +' <td>'+json[i].f_extencion+'</td>';
                    html = html +'  <td>'+descargar+'</td>';
                    html = html +' </tr>';
                    

                //
                //

                //    // $("#titulo_principal").text(json[i].f_titulo);
                //     html = html +'<li class="" style="list-style: none;">';
                //     html = html +'<img src="'+$("#url_imagen").val()+'" width="50" height="50" class="img-circle">';
                //     html = html +'<span class="tableFont"> <strong>User :</strong> </span>';
                //     html = html +'<span class="tableFont2" > '+json[i].f_hecho_por+'</span>';
                //     html = html +descargar;
                //
                //     html = html +'<h6 class="fk"><strong>Porteado :</strong><span>'+json[i].f_fecha+'</span></h6>';
                //     html = html +'<div class="alert alert-info " style="width: 95%;">';
                //     html = html +json[i].f_update;
                //     html = html +'</div>';
                //     html = html +' </li>';
                }
                $("#cuerpo_archivos").html(html);

            }
        }
    });




}
