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
