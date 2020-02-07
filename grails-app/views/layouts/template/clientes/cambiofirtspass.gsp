<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Cambiar contraseña</title>

    <asset:stylesheet  href="assets/vendor/bootstrap/css/bootstrap.min.css"/>

    <!--alerts CSS -->
    <asset:stylesheet src="assets/vendor/sweetalert/sweetalert.css"/>
</head>

<body>

<div class="container mt-6">

    <br><br><br>
    <h4 class="text-info">Cambiar contraseña!</h4>

    <form id="frm" method="post">
        <div class="form-group">
            <label>Contraseña Nueva</label>
            <input type="password" class="form-control" id="pass1" name="pass1" placeholder="Contraseña Nueva">
        </div>
        <div class="form-group">
            <label>Confirmar Contraseña</label>
            <input type="password" class="form-control" id="pass2" name="pass2" placeholder="Confirmar Contraseña">
        </div>

        <div class="float-right">
            <button type="submit" class="btn btn-info">Cambiar</button>
            <button type="button" onclick="Limpiarfrm()" class="btn btn-outline-danger">Cancelar</button>
        </div>
    </form>

</div>


<asset:javascript  src="assets/vendor/jquery/jquery-3.2.1.min.js"/>
<asset:javascript  src="assets/vendor/bootstrap/js/bootstrap.min.js"/>

<!-- Sweet-Alert  -->
<asset:javascript src="assets/vendor/sweetalert/sweetalert.min.js"/>
<asset:javascript src="assets/vendor/sweetalert/jquery.sweet-alert.custom.js"/>

<asset:javascript src="utilidades.js"/>
<asset:javascript src="js_proyecto/clientes/cambiarfirtspass.js"/>
</body>
</html>