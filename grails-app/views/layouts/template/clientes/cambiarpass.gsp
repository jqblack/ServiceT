<!doctype html>
<html lang="en">
<head>
    <meta name="layout" content="mainClientes">
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Cambiar Contraseña</title>
</head>

<body>

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


<asset:javascript src="js_proyecto/clientes/cambiarpass.js"/>
</body>
</html>