/**
 * Created by Pabel on 12/4/2017.
 */

function get_tema() {
    return 'web';
}
function ejecutar() {

crear_tabla();

    var options2 = {
        orientation: $('body').hasClass('right-to-left') ? "auto right" : 'auto auto'

    }
    $('#bs-datepicker-range').datepicker(options2);

    $("#actualizar").click(function () {
        crear_tabla_parametros();

    });

}



function crear_tabla_parametros() {

    var vector = {
        fecha_desde:$("#fecha_desde").val(),
        fecha_hasta:$("#fecha_hasta").val()
    };

    $.ajax({
        type: "POST",
        url: window.location + '/get_reporte_tickes_asignados',
         data: vector,
        success: function (data) {

            var datas = '';
            var source;
            datas = data;
            source =
                {
                    datatype: "json",
                    datafields: [
                        {name: 'f_idrecord'},
                        {name: 'f_tipo_espanol'},
                        {name: 'f_numero'},
                        {name: 'f_tiempo_asignacio'},
                        {name: 'f_cliente'},
                        {name: 'f_titulo'},
                        {name: 'f_fecha'},
                        {name: 'f_due_fecha'},
                        {name: 'f_cierre'},
                        {name: 'f_tecnico'},
                        {name: 'f_descipcion_ticket'},
                        {name: 'f_ver'}

                    ],
                    localdata: datas

                };


            var dataAdapter = new $.jqx.dataAdapter(source);


            $("#tabla_datos").jqxGrid(
                {
                    width: '100%',
                    source: dataAdapter,
                    theme: get_tema(),
                    showfilterrow: false,
                    filterable: false,
                    pageable: false,
                    autoheight: false,
                    editable: false,
                    sortable: true,
                    //groupable: true,

                    //localization: getLocalization('de'),
                    selectionmode: 'singlerow',
                    columns: [
                        {text: 'Ver', datafield: '', columntype: 'button', cellsrenderer: function () {
                            return 'Ver';


                        },
                            buttonclick: function (row) {
                                var dataRecord = $("#tabla_datos").jqxGrid('getrowdata', row);

                                $("#numero_ticket").text(dataRecord.f_numero)
                                $("#cliente").text(dataRecord.f_cliente)
                                $("#titulo").text(dataRecord.f_titulo)
                                $("#tipo").text(dataRecord.f_tipo_espanol)
                                $("#fecha").text(dataRecord.f_fecha)
                                $("#due_fecha").text(dataRecord.f_due_fecha)
                                $("#cierre").text(dataRecord.f_cierre)
                                $("#tiempo_asignacion").text(dataRecord.f_tiempo_asignacio)
                                $("#tecnico").text(dataRecord.f_tecnico)
                                $("#descripcion").html(dataRecord.f_descipcion_ticket);

                                $("#ver").click();

                            },
                            theme: get_tema(), width: "5%"
                        },
                        {text: 'ID', columntype: 'textbox', filtertype: 'textbox', datafield: 'f_idrecord', width: "0%"},
                        {text: '#TICKET', columntype: 'textbox', filtertype: 'textbox', datafield: 'f_numero', width: "10%"},
                        {text: 'CLIENTE', columntype: 'textbox', filtertype: 'textbox', datafield: 'f_cliente', width: "25%"},
                        {text: 'TITULO', columntype: 'textbox', filtertype: 'textbox', datafield: 'f_titulo', width: "25%"},
                        {text: 'TIPO', columntype: 'textbox', filtertype: 'textbox', datafield: 'f_tipo_espanol', width: "10%"},
                        {text: 'Descripcion', columntype: 'textbox', filtertype: 'textbox', datafield: 'f_descipcion_ticket', width: "25%"},
                        {text: 'FECHA', columntype: 'textbox', filtertype: 'textbox', datafield: 'f_fecha', width: "15%"},
                        {text: 'DUE FECHA', columntype: 'textbox', filtertype: 'textbox', datafield: 'f_due_fecha', width: "15%"},
                        {text: 'CIERRE', columntype: 'textbox', filtertype: 'textbox', datafield: 'f_cierre', width: "10%"},
                        {text: 'T/ASIG', columntype: 'textbox', filtertype: 'textbox', datafield: 'f_tiempo_asignacio', width: "10%",cellsalign: 'right'},
                        {text: 'TECNICO', filtertype: 'textbox', datafield: 'f_tecnico', width: "25%"},

                    ]
                });
            $('#tabla_datos').jqxGrid('hidecolumn', 'f_idrecord');
            $('#tabla_datos').jqxGrid('hidecolumn', 'f_descipcion_ticket');

           // $('#tabla_datos button').popover();


        }
    });



}


function crear_tabla() {
    // $.ajax({
    //     type: "POST",
    //     url: window.location + '/get_reporte_tickes_asignados',
    //     // data: 'parametro='+$("#parametro").val(),
    //     success: function (data) {

            var datas = '';
            var source;
            datas = [];
            source =
                {
                    datatype: "array",
                    datafields: [
                        {name: 'f_idrecord'},
                        {name: 'f_numero'},
                        {name: 'f_tiempo_asignacio'},
                        {name: 'f_cliente'},
                        {name: 'f_titulo'},
                        {name: 'f_fecha'},
                        {name: 'f_due_fecha'},
                        {name: 'f_cierre'},
                        {name: 'f_tecnico'}
                    ],
                    localdata: datas

                };


            var dataAdapter = new $.jqx.dataAdapter(source);


            $("#tabla_datos").jqxGrid(
                {
                    width: '100%',
                    source: dataAdapter,
                    theme: get_tema(),
                    showfilterrow: false,
                    filterable: false,
                    pageable: false,
                    autoheight: false,
                    editable: false,
                    sortable: true,
                    //groupable: true,

                    //localization: getLocalization('de'),
                    selectionmode: 'singlerow',
                    columns: [

                        {text: 'ID', columntype: 'textbox', filtertype: 'textbox', datafield: 'f_idrecord', width: "0%"},
                        {text: '#TICKET', columntype: 'textbox', filtertype: 'textbox', datafield: 'f_numero', width: "10%"},
                        {text: 'CLIENTE', columntype: 'textbox', filtertype: 'textbox', datafield: 'f_cliente', width: "15%"},
                        {text: 'TITULO', columntype: 'textbox', filtertype: 'textbox', datafield: 'f_titulo', width: "15%"},
                        {text: 'FECHA', columntype: 'textbox', filtertype: 'textbox', datafield: 'f_fecha', width: "10%"},
                        {text: 'DUE FECHA', columntype: 'textbox', filtertype: 'textbox', datafield: 'f_due_fecha', width: "10%"},
                        {text: 'CIERRE', columntype: 'textbox', filtertype: 'textbox', datafield: 'f_cierre', width: "10%"},
                        {text: 'T/ASIG', columntype: 'textbox', filtertype: 'textbox', datafield: 'f_tiempo_asignacio', width: "10%"},
                        {text: 'TECNICO', filtertype: 'textbox', datafield: 'f_tecnico', width: "20%"}

                    ]
                });
            $('#tabla_datos').jqxGrid('hidecolumn', 'f_idrecord');
    //     }
    // });
}
