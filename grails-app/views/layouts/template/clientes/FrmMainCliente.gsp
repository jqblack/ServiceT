<!doctype html>
<html lang="en">
<head>

    <meta name="layout" content="mainClientes">
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Clientes</title>
</head>

<body>

<div class="row">

    <div class="col-sm-12 col-xs-12 col-md-6 col-lg-6 col-xl-6">
        <div class="stat-panel">
            <!-- Danger background, vertically centered text -->
            <div class="stat-cell bg-info valign-middle">
                <!-- Stat panel bg icon -->
                <i class="fa fa-pencil bg-icon"></i>
                <!-- Extra large text -->
                <span class="text-xlg"><span class="text-lg text-slim"></span><strong>${dash.total}</strong></span><br>
                <!-- Big text -->
                <span class="text-bg">Cantidad de Ordenes Realizadas</span><br>

            </div> <!-- /.stat-cell -->
        </div> <!-- /.stat-panel -->

    </div>

    <div class="col-sm-12 col-xs-12 col-md-6 col-lg-6 col-xl-6">
        <div class="stat-panel">
            <!-- Danger background, vertically centered text -->
            <div class="stat-cell bg-success valign-middle">
                <!-- Stat panel bg icon -->
                <i class="fa fa-eye bg-icon"></i>
                <!-- Extra large text -->
                <span class="text-xlg"><span class="text-lg text-slim"></span><strong>${dash.proceso}</strong></span><br>
                <!-- Big text -->
                <span class="text-bg">Cantidad de Ordenes en Proceso</span><br>

            </div> <!-- /.stat-cell -->
        </div> <!-- /.stat-panel -->
    </div>

    <div class="col-sm-12 col-xs-12 col-md-6 col-lg-6 col-xl-6">
        <div class="stat-panel">
            <!-- Danger background, vertically centered text -->
            <div class="stat-cell bg-warning valign-middle">
                <!-- Stat panel bg icon -->
                <i class="fa fa-archive bg-icon"></i>
                <!-- Extra large text -->
                <span class="text-xlg"><span class="text-lg text-slim"></span><strong>${dash.pendientes}</strong></span><br>
                <!-- Big text -->
                <span class="text-bg">Cantidad de Ordenes Pendientes</span><br>

            </div> <!-- /.stat-cell -->
        </div> <!-- /.stat-panel -->

    </div>

    <div class="col-sm-12 col-xs-12 col-md-6 col-lg-6 col-xl-6">
        <div class="stat-panel">
            <!-- Danger background, vertically centered text -->
            <div class="stat-cell bg-danger valign-middle">
                <!-- Stat panel bg icon -->
                <i class="fa fa-warning bg-icon"></i>
                <!-- Extra large text -->
                <span class="text-xlg"><span class="text-lg text-slim"></span><strong>${dash.canceladas}</strong></span><br>
                <!-- Big text -->
                <span class="text-bg">Cantidad de Ordenes Canceladas</span><br>

            </div> <!-- /.stat-cell -->
        </div> <!-- /.stat-panel -->
    </div>


</div>


</body>
</html>