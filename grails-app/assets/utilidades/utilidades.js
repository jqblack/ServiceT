const ONLY_NUMBERS_MESSAGE = "Only numbers in this field.";
const FIELD_REQUIRED_MESSAGE = "This field is required.";
//const FIELD_TO_LONG_MESSAGE = "";
const INVALID_FORMAT_MESSAGE = "Invalid Format.";
const NO_SELECTED_MESSAGE = "Select an option.";
const INVALID_DATE_RANGE_MESSAGE = "Invalid date range.";

const SUCESS_SAVED_MESSAGE = "Information saved successfully";
const ERROR_SAVED_MESSAGE = "Information could not be saved successfully";


//Expresiones regulaes utiles

const VALIDATED_ZIP = /^\d{5}$|^\d{5}-\d{4}$/;

const VALIDATED_DECIMAL_NUMBERs = /^[0-9]+(\.[0-9]+)?$/;

function formToJson(form) {
    let inputs = $(form).find('input, select, textarea');


    inputs = $(inputs).serializeArray()


    let newJson = {}
    let simples = new RegExp("\'", "g");
    let dobles = new RegExp("\"", "g");
    let val = "";

    for (let key in inputs) {
        if (inputs[key] !== undefined) {
            // newJson[inputs[key].name] = inputs[key].value

            val = inputs[key].value.replace(simples, "");
            newJson[inputs[key].name] = val.replace(dobles, "");
        }
    }

    return newJson
}

function getTema() {
    //return "bootstrap";
    return "light";
}


function basicTable(idTable, data, columns, datafields) {


    var source = {
        localdata: data,
        datafields: datafields,
        datatype: "json"
    };

    var dataAdapter = new $.jqx.dataAdapter(source);

    $("#" + idTable).jqxGrid({
        width: '100%',
        source: dataAdapter,
        theme: getTema(),
        showfilterrow: true,
        filterable: true,
        pageable: true,
        columns: columns,
        sortable: true
    });
}


function show_yes(titulo, mensaje) {
    swal(titulo, mensaje, "success");
}

function show_no(titulo, mensaje) {
    swal(titulo, mensaje, "error");
}

function show_warning(titulo, mensaje) {
    swal(titulo, mensaje, "warning");
}

function show_message(title = "Information", mesagge ) {
    swal(title, mesagge);
}

function show_confirm(titulo, mensaje, accion, params = "") {
    swal({
        title: titulo,
        text: mensaje,
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#DD6B55",
        confirmButtonText: "Si, continuar!",
        cancelButtonText: "Cancelar",
        closeOnConfirm: true,
        closeOnCancel: true
    }, function(isConfirm) {
        if (isConfirm) {
            if (params == "")
                accion();
            else{
                accion(params);
            }
        }else{

        }
    });

    return swal;
}

function show_loading_msg(titulo="Please Wait...",time=2000) {

    swal({
        title:titulo,
        text: "",
        imageUrl: "./assets/ramfis/giphy.gif",
        showConfirmButton: false,
        showLoaderOnConfirm: true,
        timer: time
    });

    return swal;
}

function ConvertirATiempo(minutes) {
    var sign = minutes < 0 ? "-" : "";
    var min = Math.floor(Math.abs(minutes));
    var sec = Math.floor((Math.abs(minutes) * 60) % 60);
    return sign + (min < 10 ? "0" : "") + min + ":" + (sec < 10 ? "0" : "") + sec;
}


function solo_numero(campo) {
    $("#" + campo).keydown(function(e) {
        // Allow: backspace, delete, tab, escape, enter and .
        if ($.inArray(e.keyCode, [46, 8, 9, 27, 13, 110, 190]) !== -1 ||
            // Allow: Ctrl+A
            (e.keyCode == 65 && e.ctrlKey === true) ||
            // Allow: Ctrl+C
            (e.keyCode == 67 && e.ctrlKey === true) ||
            // Allow: Ctrl+X
            (e.keyCode == 88 && e.ctrlKey === true) ||
            // Allow: home, end, left, right
            (e.keyCode >= 35 && e.keyCode <= 39)) {
            // let it happen, don't do anything
            return;
        }
        // Ensure that it is a number and stop the keypress
        if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105)) {
            e.preventDefault();
        }
    });
}

function getUrl(uri) {
    let url = window.location.href;

    url = url.split('/')
    url = url.slice(0,5);

    url[url.length] = uri;

    return url.join('/')
    // let url = window.location.href;

    // url = url.split('/')
    // //console.log(url)
    // url[url.length - 1] = uri;

    // return url.join('/')
}

function ConvertToCurrency(number) {
    var number = number.toString(),
        dollars = number.split('.')[0],
        cents = (number.split('.')[1] || '') +'00';
    dollars = dollars.split('').reverse().join('')
        .replace(/(\d{3}(?!$))/g, '$1,')
        .split('').reverse().join('');
    return dollars + '.' + cents.slice(0, 2);
    // return '$' +dollars + '.' + cents.slice(0, 2);
}

function descargarArchivo(contenidoEnBlob, nombreArchivo) {
    var reader = new FileReader();
    reader.onload = function (event) {
        var save = document.createElement('a');
        save.href = event.target.result;
        save.target = '_blank';
        save.download = nombreArchivo || 'archivo.dat';
        var clicEvent = new MouseEvent('click', {
            'view': window,
            'bubbles': true,
            'cancelable': true
        });
        save.dispatchEvent(clicEvent);
        (window.URL || window.webkitURL).revokeObjectURL(save.href);
    };
    reader.readAsDataURL(contenidoEnBlob);
};













function show_toast_info (titulo,mensaje,tiempo = 3000) {
    $.toast({
        heading: titulo,
        text: mensaje,
        position: 'top-right',
        loaderBg: '#ff6849',
        icon: 'info',
        hideAfter: tiempo,
        stack: 6
    });
}

function show_toast_warning (titulo,mensaje){
    $.toast({
        heading: titulo,
        text: mensaje,
        position: 'top-right',
        loaderBg: '#ff6849',
        icon: 'warning',
        hideAfter: 3500,
        stack: 6
    });
}
function show_toast_success (titulo,mensaje){
    $.toast({
        heading: titulo,
        text: mensaje,
        position: 'top-right',
        loaderBg: '#ff6849',
        icon: 'success',
        hideAfter: 3500,
        stack: 6
    });
}

function show_toast_error (titulo,mensaje){
    $.toast({
        heading: titulo,
        text: mensaje,
        position: 'top-right',
        loaderBg: '#ff6849',
        icon: 'error',
        hideAfter: 3500

    });
}

