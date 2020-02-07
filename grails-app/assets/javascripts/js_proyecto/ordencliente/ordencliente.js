var listArchivos = new Array();

function ejecutar() {

    $('#fileupload').jqxFileUpload({
        width: '100%',
        rtl: true,
        browseTemplate: 'success',
        uploadTemplate: 'primary',
        cancelTemplate: 'danger',
        multipleFilesUpload: true,
        uploadUrl: window.location + '/salvar_archivo_orden',
        fileInputName: 'archivo'
    });

    $('#fileupload').on('select', function (event) {
        var args = event.args;
        var fileName = args.file;
        var fileSize = args.size;
        $('#fileupload').jqxFileUpload('uploadAll');
    });

    // $('#fileupload').on('remove', function (event) {
    //     var fileName = event.args.file;
    //
    //     for (var i=0;i<listArchivos.length;i++){
    //         if(listArchivos[i] == fileName){
    //             listArchivos.splice(i,1);
    //         }
    //     }
    // });

    // $('#FileUpload').on('uploadEnd', function (event) {
    //     var datos ={
    //         idrecord:idrecord_envio_archivo
    //     }
    //     $.ajax({
    //         type: "POST",
    //         url: window.location + "/enviar_archivos_tickets_clientes",
    //         data: datos,
    //         success: function (data) {
    //             //if (data=="1"){
    //
    //             // $('#FileUpload').jqxFileUpload('uploadAll');
    //             // $("#nuevo").click();
    //             // notificacion_si("Ticket Salvado Correctamente");
    //             // $("#salvar" ).prop( "disabled", false );
    //             //}
    //         }
    //     });
    // });
    $('#fileupload').on('uploadEnd', function (event) {
        var args = event.args;
        var fileName = args.file;
        var serverResponce = args.response;

        if (serverResponce === 'false') {
            window.location.reload();
        } else {
            let item = {};
            item.archivo = serverResponce;
            item.orden = false;
            item.nombre = fileName;
            $("#tablaArchivos").jqxGrid('addRow', null, item);
        }

    });

    crearTabla();
}

function crearTabla() {
    var source =
        {
            localdata: {},
            datafields:
                [
                    {name: 'archivo', type: 'string'},
                    {name: 'orden', type: 'boolean'},
                    {name: 'nombre', type: 'string'},
                    {name: 'borrar'}
                ]
        };
    var dataAdapter = new $.jqx.dataAdapter(source);

    $("#tablaArchivos").jqxGrid(
        {
            width: "100%",
            height: 200,
            theme: "metro",
            source: dataAdapter,
            editable: true,
            showtoolbar: false,
            columns: [
                {text: '<b>Nombre</b>', width: '30%', datafield: 'nombre', editable: false},
                {text: '<b>Archivo</b>', width: '30%', datafield: 'archivo', editable: false},
                {text: '<b>Orden</b>', width: '15%', datafield: 'orden', columntype: 'checkbox', editable: true},
                {text: '<b>Borrar</b>', width: '25%', datafield: 'borrar', columntype: 'button',cellsrenderer: function () {
                        return 'Borrar';
                    },
                    buttonclick: function (row) {
                        var rowid = $('#tablaArchivos').jqxGrid('getrowid', row);

                        $('#tablaArchivos').jqxGrid('deleterow', rowid);

                    }
                }
            ]
        });
}

function salvarDatos() {
    $("#frm").submit();
}

$("#frm").submit(function (e) {
    e.preventDefault();
    var params = {};
    params = formToJson(this);

    params.texto =  $("#texto").jqxEditor('val');
    if (validarForm(params) != true) {
        return;
    }

    var datainformation = $('#tablaArchivos').jqxGrid('getdatainformation');
    var rowscount = datainformation.rowscount;

    var key = false;
    var ob = {};
    var data = [];

    for (var i = 0; i < rowscount; i++) {
        data.push($('#tablaArchivos').jqxGrid('getrowdata', i));
        ob = $('#tablaArchivos').jqxGrid('getrowdata', i);
        if (ob.orden) {
            key = true;
        }
    }

    if (!key) {
        show_no("Error", "Tiene que seleccionar por lo menos un dato como la orden de trabajo");
        return;
    }
    params.tabla = JSON.stringify(data);

    $("#salvar").prop('disabled', true);
    GuardarOrden(params);
});

function validarForm(params) {

    if (params.ttitulo == "") {
        show_no("Alerta", "Primero debe colocar el título de la orden de trabajo");
        return false;
    } else if (params.texto == "") {
        show_no("Alerta", "Primero debe colocar una descripcion");
        return false;
    }

    var datainformation = $('#tablaArchivos').jqxGrid('getdatainformation');
    var rowscount = datainformation.rowscount;

    if (rowscount <= 0) {
        show_no("Alerta", "Primero debe colocar un archivo para la orden de trabajo");
        return false;
    }


    return true;
}

function GuardarOrden(params) {
    $.ajax({
        url: window.location + "/GuardarOrden",
        data: params,
        type: 'POST',
        success: function (response) {
            var json = JSON.parse(response);
            if (json.key) {
                show_yes("Correcto", json.msj);
                $("#salvar").prop('disabled', false);
                LimpiarTodo();
            } else {
                show_no("Error", json.msj);
            }
        }
    });

}

function LimpiarTodo() {
    $("#frm").trigger('reset');
    $("#texto").val("");
    $('#tablaArchivos').jqxGrid('clear');
}

