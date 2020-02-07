<!doctype html>
<html lang="en">
<head>
    <meta name="layout" content="mainClientes">
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Orden Clientes</title>


</head>

<body>

<div class="row">
    <div class="col-12">
        <form id="frm">

            <div class="form-group">
                <label>Titulo</label>
                <input type="text" class="form-control" id="ttitulo" name="ttitulo" >
            </div>

            <div class="mb-3">
                <textarea id="texto" name="texto" class="textarea" placeholder="Place some text here"
                          style="width: 100%; height: 200px; font-size: 14px; line-height: 18px; border: 1px solid #dddddd; padding: 10px;"></textarea>
            </div>

        </form>
    </div>
    <div class="col-12">
        <label>Seleccione sus archivos</label>
        <div id="fileupload"></div>
    </div>
    <br>
    <div class="col-12">
        <div id="tablaArchivos"></div>
    </div>
    <br>
    <div class="pull-right">
        <button class="btn btn-info" id="salvar" name="cancelar" onclick="salvarDatos()">Generar</button>
        <button class="btn btn-outline-danger" id="cancelar" name="cancelar">Cancelar</button>
    </div>
</div>




<asset:javascript src="js_proyecto/ordencliente/ordencliente.js"/>

<g:javascript>

     $(document).ready(function () {
         ejecutar();
         $('#texto').jqxEditor({
         });
     });

</g:javascript>


</body>
</html>