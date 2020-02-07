/**
 * Created by Pabel on 16/6/2016.
 */
function get_tema() {
    return 'material';
}
var idcliente = 0;
function ejecutar() {


    crear_tabla();
    crear_tabla_correos('');

    $("#ok_notifificacion_si").click(function () {
       // $("#atras").click();
    });
    $("#nuevo_referente").click(function () {
        if ($("#idrecord").val()=='')
        {
            nuevo();
        }

    });


    $("#salvar").click(function () {



        var datainformations = $('#tabla_correos').jqxGrid('getdatainformation');
        var rowscounts = datainformations.rowscount;

        if (rowscounts ==0)
        {
            notificacion_no("No se ah agregado ningun correo..");
            return;
        }
        var correos='';

        for (var i = 0; i < rowscounts; i++) {
            var dataRecord = $("#tabla_correos").jqxGrid('getrowdata', i);
            if(dataRecord.f_correo!="")
            {
                correos = correos + dataRecord.f_correo+",";
            }
        }

        correos = correos.substring(0,correos.length-1);
        var vector = {
            idrecord:idcliente,
            correos:correos
        };


            $.ajax({
                type: "POST",
                url: window.location + '/salvar_distribucion_correos',
                data: vector,
                success: function (data) {
                   
                    notificacion_si('Datos Guardados Correctamente');
                    $("#tabla_correos").jqxGrid('clear');
                    idcliente=0;
                    $("#nombre_del_cliente").text("NO SE AH SELECCIONADO UN CLIENTE");
                    
                }
            });


    });

    $("#add").click(function () {

        var datainformations = $('#tabla_correos').jqxGrid('getdatainformation');
        var rowscounts = datainformations.rowscount +1;
        $('#tabla_correos').jqxGrid('addrow', rowscounts, {  f_idrecord:rowscounts, f_correo:'',f_eliminar:''});

    });

    $('#tabla_clientes').on('rowclick', function (event) {
        var args = event.args;
        var pocision = args.rowindex;
        var data = $('#tabla_clientes').jqxGrid('getrowdata', pocision);
        $("#nombre_del_cliente").text(data.f_nombre_empresa);
        $("#add").prop("disabled",false);
        idcliente = data.f_id;
        $.ajax({
            type: "POST",
            url: window.location + '/get_distribucion_correos_by_cliente',
            data: {idrecord:data.f_id},
            success: function (data) {



                if (String(data)!="[]" && String(data)!="")
                {
                    var tupla = eval(data);
                    var split_correos = String(tupla[0].f_correos).split(",");

                    var json = [];
                    for (var i=0;i<split_correos.length;i++)
                    {
                        if(split_correos[i]!='' || split_correos[i]!=null)
                        {
                            json.push({
                                f_idrecord:i,
                                f_correo:split_correos[i]
                            })
                        }


                    }


                    crear_tabla_correos(json);
                }
                else {
                    $("#tabla_correos").jqxGrid('clear');
                }



            }
        });

    });

}

function crear_tabla() {
    $.ajax({
        type: "POST",
        url: window.location + '/get_clientes_activos_no_activos',
        data: {activo:true},
        success: function (data) {

            var datas = '';
            var source;

            datas = data;
            source =
                {
                    datatype: "json",

                    datafields: [
                        {name: 'f_id'},
                        {name: 'f_nombre_empresa'},
                        {name: 'f_fecha'},
                        {name: 'f_email'}
                    ],
                    localdata: datas

                };


            var dataAdapter = new $.jqx.dataAdapter(source);


            $("#tabla_clientes").jqxGrid(
                {
                    width: '100%',
                    source: dataAdapter,
                    theme: get_tema(),
                    showfilterrow: true,
                    filterable: true,
                    pageable: true,
                    autoheight: false,
                    editable: false,
                    //localization: getLocalization('de'),
                    selectionmode: 'singlerow',
                    columns: [
                        {text: 'ID', columntype: 'textbox', filtertype: 'textbox', datafield: 'f_id', width: "10%"},
                        {text: 'Nombre', filtertype: 'textbox', datafield: 'f_nombre_empresa', width: "50%"},
                        {text: 'E-mail', filtertype: 'textbox', datafield: 'f_email', width: "40%"}
                    ]
                });
            // $('#tabla_clientes').jqxGrid('hidecolumn', 'f_idrecord');
        }
    });
}


function crear_tabla_correos(id) {

    if(id=='')
    {



                var source;

                datas = [];
                source =
                    {
                        datatype: "array",

                        datafields: [
                            {name: 'f_idrecord'},
                            {name: 'f_correo'}
                        ],
                        localdata: datas

                    };


                var dataAdapter = new $.jqx.dataAdapter(source);


                $("#tabla_correos").jqxGrid(
                    {
                        width: '100%',
                        source: dataAdapter,
                        theme: 'energyblue',
                        showfilterrow: false,
                        filterable: false,
                        pageable: false,
                        autoheight: false,
                        editable: true,
                        //localization: getLocalization('de'),
                        selectionmode: 'singlerow',
                        columns: [
                            {text: 'ID', columntype: 'textbox', filtertype: 'textbox', datafield: 'f_idrecord', width: "10%"},
                            {text: 'Correo', filtertype: 'textbox', datafield: 'f_correo', width: "100%"}
                        ]
                    });
                $('#tabla_correos').jqxGrid('hidecolumn', 'f_idrecord');

    }else {

        var source;

        datas = id;
        source =
        {
            datatype: "json",

            datafields: [
                {name: 'f_idrecord'},
                {name: 'f_correo'},
                {name: 'f_eliminar'}
            ],
            localdata: datas

        };


        var dataAdapter = new $.jqx.dataAdapter(source);


        $("#tabla_correos").jqxGrid(
            {
                width: '100%',
                source: dataAdapter,
                theme: 'energyblue',
                showfilterrow: false,
                filterable: false,
                pageable: false,
                autoheight: false,
                editable: true,
                //localization: getLocalization('de'),
                selectionmode: 'singlerow',
                columns: [
                    {text: 'ID', columntype: 'textbox', filtertype: 'textbox', datafield: 'f_idrecord', width: "10%"},
                    {text: 'Correo', filtertype: 'textbox', datafield: 'f_correo', width: "90%"},
                    {
                        text: '-', datafield: 'f_eliminar', columntype: 'button', cellsrenderer: function () {
                        return '-';
                    }, buttonclick: function (row) {
                        var id = $('#tabla_correos').jqxGrid('getrowid', row);
                        $('#tabla_correos').jqxGrid('deleterow', id);
                    }, theme: 'energyblue', width: "10%"
                    }
                ]
            });
        $('#tabla_correos').jqxGrid('hidecolumn', 'f_idrecord');


    }

}

function notificacion_si(msj) {
    $("#body_notifificacion_si").html(msj);
    $("#notificacion_si").click();
}
function notificacion_no(msj) {

    $("#body_notifificacion_no").html(msj);
    $("#notificacion_no").click();
}




