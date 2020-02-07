

function get_tema() {
    return 'web';
}

function ejecutar() {


    $("#jqxLoader").jqxLoader({rtl: true, width: 100, height: 60, imagePosition: 'top'});


    var options2 = {
        orientation: $('body').hasClass('right-to-left') ? "auto right" : 'auto auto'
    };
    $('#fecha').datepicker(options2);


    crear_tabla();


    $("#boton_buscar").click(function () {

        if ($("#fecha_inicio").val() == '' && $("#fecha_fin").val() == '' && $("#parametro").val() == '') {
            show_no("Error","Seleccione por lo menos un parametro de busqueda");
            return;
        }
        else if($("#miselect").val() == 0){
            show_no("Error","Seleccione Un Status para la busqueda");
        }
        else {

            crear_tabla_parametros();
            $("#boton_buscar").prop('disabled', true);
            $("#icono_cargando").show();
        }
    });

}

function crear_tabla() {


    var datas = '';
    var source;

    datas = datas;
    source =
        {
            datatype: "array",

            datafields: [
                {name: 'f_id'},
                {name: 'f_titulo'},
                {name: 'f_fecha', type: 'date'},
                {name: 'f_ver'}
            ],
            localdata: datas

        };

    var dataAdapter = new $.jqx.dataAdapter(source);

    $("#tabla_facturas").jqxGrid(
        {
            width: '100%',
            source: dataAdapter,
            theme: get_tema(),
            //showfilterrow: true,
            //filterable: true,
            //  pageable: true,
            columnsresize: true,
            autoheight: false,
            editable: true,
            sortable: true,
            // groupable: true,
            //localization: getLocalization('de'),
            selectionmode: 'multiplerowsextended',
            columns: [
                {text: 'ID', columntype: 'textbox', datafield: 'f_id', width: "0%", editable: false},
                {
                    text: 'Título',
                    filtertype: 'textbox',
                    datafield: 'f_titulo',
                    width: "50%",
                    editable: false,
                    filtrable: true
                },
                {
                    text: 'Fecha',
                    filtertype: 'date',
                    cellsalign: 'right',
                    datafield: 'f_fecha',
                    width: "30%",
                    editable: false,
                    cellsformat: 'd'
                },

                {text: 'Ver', datafield: 'f_ver', columntype: 'button', cellsrenderer: function () {
                        return 'Ver';
                    },
                    buttonclick: function (row) {
                        var dataRecord = $("#tabla_facturas").jqxGrid('getrowdata', row);

                        var id = dataRecord.f_id;
                        $('#jqxLoader').jqxLoader('open');
                        $.ajax({
                            type: "POST",
                            url: window.location + '/get_reporte_factura_by_id',
                            data: {idrecord: id},
                            success: function (data) {
                                var dato = data;
                                $("#pdf").html('');
                                if (dato != '-1') {
                                    $("#pdf").html('<embed src="data:application/pdf;base64,' + dato + '" width="100%" height="600"></embed>')
                                    $('#jqxLoader').jqxLoader('close');
                                    $("#cerrar_reporte").show();

                                }

                            }
                        });

                    }, theme: get_tema(), width: "20%"},

            ]/*,
         groups: ['f_cliente']*/
        });

    $('#tabla_facturas').jqxGrid('hidecolumn', 'f_id');

}

function crear_tabla_parametros() {

    var split_f1 = "";
    var f1 = "";

    if ($("#fecha_inicio").val() != '') {
        split_f1 = $("#fecha_inicio").val().split('/');
        f1 = split_f1[2].replace(' ', '') + '/' + split_f1[0].replace(' ', '') + '/' + split_f1[1].replace(' ', '');

    }

    var split_f2 = "";
    var f2 = "";
    if ($("#fecha_fin").val()) {
        split_f2 = $("#fecha_fin").val().split('/');
        f2 = split_f2[2].replace(' ', '') + '/' + split_f2[0].replace(' ', '') + '/' + split_f2[1].replace(' ', '');
    }

    var tipobusqueda = $("#miselect").val();

    $.ajax({
        type: "POST",
        url: window.location + '/get_ver_ordenesclientes',
        data: {parametro: $("#parametro").val(), f1: f1, f2: f2, tipobusqueda: tipobusqueda},
        success: function (data) {


            $("#boton_buscar").prop('disabled', false);
            $("#icono_cargando").hide();


            var datas = '';
            var source;

            datas = data;
            source =
                {
                    datatype: "json",

                    datafields: [
                        {name: 'f_id'},
                        {name: 'f_titulo'},
                        {name: 'f_fecha', type: 'date'},
                        {name: 'f_ver'}
                    ],
                    localdata: datas

                };


            var dataAdapter = new $.jqx.dataAdapter(source);


            $("#tabla_facturas").jqxGrid(
                {
                    width: '100%',
                    source: dataAdapter,
                    theme: get_tema(),
                    showfilterrow: true,
                    filterable: true,
                    //  pageable: true,
                    autoheight: false,
                    editable: true,
                    sortable: true,
                    // groupable: true,
                    //localization: getLocalization('de'),
                    selectionmode: 'multiplerowsextended',
                    columns: [

                        {text: 'ID', columntype: 'textbox', datafield: 'f_id', width: "0%", editable: false},
                        {text: 'Título', filtertype: 'textbox', datafield: 'f_titulo', width: "50%",editable:false},

                        {
                            text: 'Fecha',
                            filtertype: 'date',
                            cellsalign: 'right',
                            datafield: 'f_fecha',
                            width: "30%",
                            editable: false,
                            cellsformat: 'dd/MM/yyyy'
                        },
                        {
                            text: 'Ver', datafield: 'f_ver', columntype: 'button', cellsrenderer: function () {
                                return 'Ver';
                            },
                            buttonclick: function (row) {
                                var dataRecord = $("#tabla_facturas").jqxGrid('getrowdata', row);

                                var id = dataRecord.f_id;
                                // $('#jqxLoader').jqxLoader('open');
                                $.ajax({
                                    type: "POST",
                                    url: window.location + '/get_archivos_ordenes',
                                    data: {idrecord: id},
                                    success: function (data) {
                                        var json = JSON.parse(data);
                                        // $('#jqxLoader').jqxLoader('close');
                                        console.log(json);
                                        if (json.length > 0){
                                            $('#modalArchivos').modal({backdrop: 'static', keyboard: false});

                                            var html = "";
                                            for (let i = 0; i < json.length ; i++) {
                                                html += `<tr>
                                                <th scope="row">${json[i].f_namearchivo}</th>
                                               <td>
                                                  <button onclick="descargarImg('${json[i].id}')" class="btn btn-success"><i class="fa fa-download"></i> Descargar</button>
                                               </td>
                                                </tr>`;
                                            }
                                            $("#bodytable").html(html);
                                        }
                                        else{
                                            show_no("Vaya!","Esta orden no tiene archivos adjuntos");
                                        }

                                    }
                                });

                            }, theme: get_tema(), width: "20%"
                        }
                    ]
                });
            $('#tabla_facturas').jqxGrid('hidecolumn', 'f_id');
            // sumar_montos_adeudados();

            // calcular_totales();
        }
    });


    $("#boton_imprimir").click(function () {


        var gridContent = $("#tabla_facturas").jqxGrid('exportdata', 'html');
        var newWindow = window.open('', '', 'width="100%", height="100%"'),
            document = newWindow.document.open(),
            pageContent =
                '<!DOCTYPE html>\n' +
                '<html>\n' +
                '<head>\n' +
                '<meta charset="utf-8" />\n' +
                '<title>Estado Cuentas</title>\n' +
                '</head>\n' +
                '<body>\n' + gridContent + '\n</body>\n</html>';
        document.write(pageContent);
        document.close();
        newWindow.print();


    })

}

function descargarImg(idimg) {

    window.open(window.location +"/getDescargarArchivo?id="+idimg);
}
