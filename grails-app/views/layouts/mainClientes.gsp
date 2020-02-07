<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Cliente</title>

    <!-- Pixel Admin's stylesheets -->
    %{--<asset:stylesheet  href="assets/stylesheets/bootstrap.min.css"/>--}%
    %{--<asset:stylesheet href="assets/stylesheets/pixel-admin.min.css" rel="stylesheet" type="text/css"/>--}%
    %{--<asset:stylesheet href="assets/stylesheets/widgets.min.css" rel="stylesheet" type="text/css"/>--}%
    %{--<asset:stylesheet href="assets/stylesheets/rtl.min.css" rel="stylesheet" type="text/css"/>--}%
    %{--<asset:stylesheet href="assets/stylesheets/themes.min.css" rel="stylesheet" type="text/css"/>--}%
    %{--<asset:stylesheet href="assets/stylesheets/pages.min.css" rel="stylesheet" type="text/css"/>--}%

    <!-- Pixel Admin's stylesheets -->
    <asset:stylesheet href="assets/stylesheets/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <asset:stylesheet href="assets/stylesheets/pixel-admin.min.css" rel="stylesheet" type="text/css"/>
    <asset:stylesheet href="assets/stylesheets/widgets.min.css" rel="stylesheet" type="text/css"/>
    <asset:stylesheet href="assets/stylesheets/rtl.min.css" rel="stylesheet" type="text/css"/>
    <asset:stylesheet href="assets/stylesheets/themes.min.css" rel="stylesheet" type="text/css"/>
    <asset:stylesheet href="assets/stylesheets/pages.min.css" rel="stylesheet" type="text/css"/>

    %{--<asset:stylesheet  href="assets/vendor/bootstrap/css/bootstrap.min.css"/>--}%
    %{--<asset:javascript  src="assets/vendor/jquery/jquery-3.2.1.min.js"/>--}%
    <asset:javascript src="assets/javascripts/jquery-3.4.1.min.js"/>
    <asset:javascript src="assets/popper/dist/popper.min.js"/>
    %{--<asset:javascript  src="assets/vendor/bootstrap/js/bootstrap.min.js"/>--}%
    %{--<asset:javascript  src="assets/vendor/bootstrap/js/bootstrap.min.js"/>--}%
    <asset:javascript src="assets/javascripts/bootstrap.min.js"/>
    <asset:javascript src="assets/javascripts/pixel-admin.min.js"/>

    <asset:stylesheet src="jqwidgets/jqwidgets/styles/jqx.base.css"/>
    <asset:stylesheet src="jqwidgets/jqwidgets/styles/jqx.material.css"/>

    %{--<asset:javascript src="jqwidgets/jqwidgets/jqxcore.js"/>--}%
    %{--<asset:javascript src="jqwidgets/jqwidgets/jqxbuttons.js"/>--}%
    %{--<asset:javascript src="jqwidgets/jqwidgets/jqxfileupload.js"/>--}%
    <asset:javascript src="utilidades.js"/>

    <!--alerts CSS -->
    <asset:stylesheet src="assets/vendor/sweetalert/sweetalert.css"/>
</head>

<body class="theme-frost main-menu-animated main-navbar-fixed main-menu-fixed page-search">
<script>var init = [];</script>

<div id="main-navbar-collapse" class="collapse navbar-collapse main-navbar-collapse navbar-inverse">
    <div>
        <ul class="nav navbar-nav">
            <li>
                <a href="./clientejq"><asset:image src="Logo_pequeno.png" class="img-responsive" alt=""
                                                   style="height: 25px;top: 0px"/></a>
            </li>
            <li>
                <a href="./ordentrabajo">Cargar Orden Trabajo</a>
            </li>

            <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">Reportes</a>
                <ul class="dropdown-menu">
                    <li><a href="./facturasclientes">Facturas</a></li>
                    <li class="divider"></li>
                    <li><a href="./ordenesclientes">Ordenes</a></li>
                </ul>
            </li>

            <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">Configuración</a>
                <ul class="dropdown-menu">
                    <li><a href="./cambiarpass">Cambiar contraseña</a></li>
                    <li class="divider"></li>
                    <li><a href="./logoutclientes">Cerrar Sesión</a></li>
                </ul>
            </li>
        </ul> <!-- / .navbar-nav -->

        <div class="right clearfix">
            <ul class="nav navbar-nav pull-right right-navbar-nav">

            </ul> <!-- / .navbar-nav -->
        </div> <!-- / .right -->
    </div>
</div>

<br><br><br>

<div class="container">
    <g:layoutBody/>
</div>

<asset:javascript src="assets/javascripts/pixel-admin.min.js"/>
<!-- Sweet-Alert  -->
<asset:javascript src="assets/vendor/sweetalert/sweetalert.min.js"/>
<asset:javascript src="assets/vendor/sweetalert/jquery.sweet-alert.custom.js"/>
<asset:javascript src="jqwidgets/jqwidgets/jqxcore.js"/>
<asset:javascript src="jqwidgets/jqwidgets/jqxdata.js"/>
<asset:javascript src="jqwidgets/jqwidgets/jqxbuttons.js"/>
<asset:javascript src="jqwidgets/jqwidgets/jqxscrollbar.js"/>
<asset:javascript src="jqwidgets/jqwidgets/jqxmenu.js"/>
<asset:javascript src="jqwidgets/jqwidgets/jqxcheckbox.js"/>
<asset:javascript src="jqwidgets/jqwidgets/jqxlistbox.js"/>
<asset:javascript src="jqwidgets/jqwidgets/jqxdropdownlist.js"/>
<asset:javascript src="jqwidgets/jqwidgets/jqxgrid.js"/>
<asset:javascript src="jqwidgets/jqwidgets/jqxgrid.sort.js"/>
<asset:javascript src="jqwidgets/jqwidgets/jqxgrid.grouping.js"/>
<asset:javascript src="jqwidgets/jqwidgets/jqxgrid.columnsresize.js"/>
<asset:javascript src="jqwidgets/jqwidgets/jqxgrid.columnsreorder.js"/>
<asset:javascript src="jqwidgets/jqwidgets/jqxgrid.pager.js"/>
<asset:javascript src="jqwidgets/jqwidgets/jqxgrid.selection.js"/>
<asset:javascript src="jqwidgets/jqwidgets/jqxgrid.edit.js"/>
<asset:javascript src="jqwidgets/jqwidgets/jqxgrid.filter.js"/>
<asset:javascript src="jqwidgets/jqwidgets/jqxdatatable.js"/>
<asset:javascript src="jqwidgets/jqwidgets/jqxtreegrid.js"/>
<asset:javascript src="jqwidgets/jqwidgets/jqxdatetimeinput.js"/>
<asset:javascript src="jqwidgets/jqwidgets/jqxcalendar.js"/>
<asset:javascript src="jqwidgets/jqwidgets/jqxloader.js"/>
<asset:javascript src="jqwidgets/jqwidgets/jqxeditor.js"/>
<asset:javascript src="jqwidgets/jqwidgets/jqxdropdownbutton.js"/>
<asset:javascript src="jqwidgets/jqwidgets/jqxfileupload.js"/>
<asset:javascript src="jqwidgets/jqwidgets/jqxdatetimeinput.js"/>

<asset:javascript src="jqwidgets/jqwidgets/jqxcolorpicker.js"/>
<asset:javascript src="jqwidgets/jqwidgets/jqxwindow.js"/>
<asset:javascript src="jqwidgets/jqwidgets/jqxeditor.js"/>




%{--<script type="text/javascript" src="../../jqwidgets/jqxcolorpicker.js"></script>--}%
%{--<script type="text/javascript" src="../../jqwidgets/jqxwindow.js"></script>--}%
%{--<script type="text/javascript" src="../../jqwidgets/jqxeditor.js"></script>--}%

</body>
<script>
    init.push(function () {
        // Javascript code here
    });
    window.PixelAdmin.start(init);
</script>
</html>