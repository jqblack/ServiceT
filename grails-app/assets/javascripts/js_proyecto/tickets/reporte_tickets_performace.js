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
})

}



function crear_tabla() {
    var datas = '';
    var source;
    datas = [];
    source =
        {
            datatype: "array",
            datafields: [
                {name: 'f_fecha'},
                {name: 'f_tecnico'},
                {name: 'f_nombre_empresa'},
                {name: 'f_error'},
                {name: 'f_peticion'},
                {name: 'f_porciento_error'},
                {name: 'f_porciento_peticion'},
                {name: 'f_tickets_cerrados'},
                {name: 'f_horas_cerrados'},
                {name: 'f_performace'}
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

                {text: 'FECHA', columntype: 'textbox', filtertype: 'textbox', datafield: 'f_fecha', width: "10%"},
                {text: 'TECNICO', columntype: 'textbox', filtertype: 'textbox', datafield: 'f_tecnico', width: "20%", cellsalign: 'right'},
                //{text: 'CLIENTE', columntype: 'textbox', filtertype: 'textbox', datafield: 'f_nombre_empresa', width: "20%"},
                {text: 'ERROR', columntype: 'textbox', filtertype: 'textbox', datafield: 'f_error', width: "10%", cellsalign: 'right'},
                {text: 'PETICIONES', columntype: 'textbox', filtertype: 'textbox', datafield: 'f_peticion', width: "10%", cellsalign: 'right'},
                {text: '%ERROR', columntype: 'textbox', filtertype: 'textbox', datafield: 'f_porciento_error', width: "10%", cellsalign: 'right'},
                {text: '%PETICIONES', columntype: 'textbox', filtertype: 'textbox', datafield: 'f_porciento_peticion', width: "10%", cellsalign: 'right'},
                {text: 'CERRADOS', columntype: 'textbox', filtertype: 'textbox', datafield: 'f_tickets_cerrados', width: "10%", cellsalign: 'right'},
                {text: 'C/TIEMPO', columntype: 'textbox', filtertype: 'textbox', datafield: 'f_horas_cerrados', width: "10%", cellsalign: 'right'},
                {text: '%PERFORMACE', filtertype: 'textbox', datafield: 'f_performace', width: "10%", cellsalign: 'right'}

            ]
        });
}


function crear_tabla_parametros() {

    var vector = {
        fecha_desde:$("#fecha_desde").val(),
        fecha_hasta:$("#fecha_hasta").val()
    };


    $.ajax({
        type: "POST",
        url: window.location + '/get_reporte_tickes_performace',
        data: vector,
        success: function (data) {

            var datas = '';
            var source;
            datas = data;
            source =
                {
                    datatype: "json",
                    datafields: [
                        {name: 'f_fecha'},
                        {name: 'f_tecnico'},
                        {name: 'f_nombre_empresa'},
                        {name: 'f_error'},
                        {name: 'f_peticion'},
                        {name: 'f_porciento_error'},
                        {name: 'f_porciento_peticion'},
                        {name: 'f_tickets_cerrados'},
                        {name: 'f_horas_cerrados'},
                        {name: 'f_performace'}
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

                        {text: 'FECHA', columntype: 'textbox', filtertype: 'textbox', datafield: 'f_fecha', width: "10%"},
                        {text: 'TECNICO', columntype: 'textbox', filtertype: 'textbox', datafield: 'f_tecnico', width: "20%"},
                       // {text: 'CLIENTE', columntype: 'textbox', filtertype: 'textbox', datafield: 'f_nombre_empresa', width: "20%"},
                        {text: 'ERROR', columntype: 'textbox', filtertype: 'textbox', datafield: 'f_error', width: "10%", cellsalign: 'right'},
                        {text: 'PETICIONES', columntype: 'textbox', filtertype: 'textbox', datafield: 'f_peticion', width: "10%", cellsalign: 'right'},
                        {text: '%ERROR', columntype: 'textbox', filtertype: 'textbox', datafield: 'f_porciento_error', width: "10%", cellsalign: 'right'},
                        {text: '%PETICIONES', columntype: 'textbox', filtertype: 'textbox', datafield: 'f_porciento_peticion', width: "10%", cellsalign: 'right'},
                        {text: 'CERRADOS', columntype: 'textbox', filtertype: 'textbox', datafield: 'f_tickets_cerrados', width: "10%", cellsalign: 'right'},
                        {text: 'C/TIEMPO', columntype: 'textbox', filtertype: 'textbox', datafield: 'f_horas_cerrados', width: "10%", cellsalign: 'right'},
                        {text: '%PERFORMACE', filtertype: 'textbox', datafield: 'f_performace', width: "10%", cellsalign: 'right'}

                    ]
                });
            // $('#tabla_datos').jqxGrid('hidecolumn', 'f_idrecord');
        }
    });
}
