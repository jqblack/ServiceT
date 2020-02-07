
$("#frm").submit(function (e) {
    e.preventDefault();
    var params = {};
    params = formToJson(this);
    if (validar(params) != true) {
        return;
    }
    cambiarfirtspass(params);
});

function validar(params) {

    if (params.pass1 == ""){
        show_no("Error","Ingrese la contraseña nueva");
        return false;
    }else if (params.pass2 == ""){
        show_no("Error","Confirme su contraseña nueva");
        return false;
    }
    else if (params.pass1 != params.pass2) {
        show_no("Error","Las contraseñas no coinciden");
        return false;
    }
    else if (params.pass1 == params.pass2) {
        return true;
    }
}

function cambiarfirtspass(params) {
    $.ajax({
        url: window.location + "/cambiarfirstpass",
        data: params,
        type: 'GET',
        success: function (response) {
            if (response) {
                window.location.reload();
            }
            ///window.location.href = response.redirecturl
        }
    });

}

function Limpiarfrm() {
    $("#frm").trigger('reset');
}
