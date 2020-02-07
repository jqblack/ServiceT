<!doctype html>
<html lang="en">
<head>

    <meta name="layout" content="main">
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Ver Tracks</title>
</head>

<body>


<div class="page-header">

    <div class="row">
        <!-- Page header, center on small screens -->
        <h1 class="col-xs-12 col-sm-4 text-center text-left-sm"><i class="fa fa-dashboard page-header-icon"></i>&nbsp;&nbsp;Vista De Tracks</h1>

        <div class="col-xs-12 col-sm-8">
            <div class="row">
                <hr class="visible-xs no-grid-gutter-h">
                <!-- "Create project" button, width=auto on desktops -->
                %{--<div class="pull-right col-xs-12 col-sm-auto"><a  class="btn btn-primary btn-labeled" id="nueva_tareas"  data-toggle="modal" data-target="#modal_tareas" style="width: 100%;"><span class="btn-label icon fa fa-plus"></span>Nueva Tarea</a></div>--}%
                %{--<div class="pull-right col-xs-12 col-sm-auto"><a  class="btn btn-primary btn-labeled" id="nueva_track"  style="width: 100%;"><span class="btn-label icon fa fa-plus"></span>Nuevo Track</a></div>--}%
                <a    class="btn btn-primary btn-labeled" id="mostrar_modal"  data-toggle="modal" data-target="#modal_detalle" style="width: 100%;display: none"><span class="btn-label icon fa fa-plus"></span>Nuevo Track</a>

                <!-- Margin -->
                <div class="visible-xs clearfix form-group-margin"></div>


            </div>
        </div>

    </div>
</div> <!-- / .page-header -->
<div class="panel">
    %{--<div class="panel-heading">--}%
    %{--<span class="panel-title">Morris.js Graph</span>--}%
    %{--</div>--}%
    <div class="panel-body">

        <br class="row">
        %{--<a   class="btn btn-primary btn-labeled" id="mostrar_modal"  data-toggle="modal" data-target="#modal_detalle" style="width: 100%;display: none"><span class="btn-label icon fa fa-plus"></span>Nuevo Track</a>--}%

        <div class="col-md-6  col-md-12" >

            <div class="form-group has-feedback">
                <label>Tarea</label>
                <div id="combo">
                    <div style="border-color: transparent;" id="tabla_tareas">
                    </div>
                </div>
            </div>


        </div>
        <div class="col-md-6  col-md-0" >
        </div>
        <br></br>
        <div class="col-md-12">
            <div id="tabla_track">
            </div>
        </div>

        <div class="col-md-12">
            <div id="modal_detalle" class="modal fade" tabindex="-1" role="dialog">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                        <div class="modal-header">
                            %{--<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>--}%
                            <h4 class="modal-title">Detalle</h4>
                        </div>
                        <div class="row">
                            <div class="col-md-2 col-sm-1 col-xs-1 col-lg-1 col-xl-1 "></div>
                            <div class="col-md-8 col-sm-10 col-xs-10 col-lg-10 col-xl-10 ">
                                <div class="box box-primary">
                                    <div class="box-header with-border">
                                    </div><!-- /.box-header -->
                                    <div class="box-body">
                                        <div class="row">

                                            <div class="col-md-12">
                                                <p id="texto"></p>
                                            </div>

                                            %{--</form>--}%
                                        </div><!-- /.box -->
                                    </div>


                                    <div class="box-footer">
                                        <div class="form-group has-feedback">
                                            <button type="" id="atras"  class="btn btn-primary" data-dismiss="modal" aria-hidden="true">  <span class="fa fa-arrow-left"></span>Atras</button>
                                        </div>

                                        <br>
                                    </div>
                                    <div class="col-md-1 col-sm-1 col-xs-1 col-lg-1 col-xl-1 "></div>
                                </div>

                            </div> <!-- / .modal-content -->
                        </div> <!-- / .modal-dialog -->
                    </div> <!-- / .modal -->
                <!-- / Large modal -->
                </div>
            </div>
        </div>

    </div>
</div>

<asset:javascript src="js_proyecto/proyectos/ver_tracks.js"/>
<script type="text/javascript" xmlns="http://www.w3.org/1999/html">

    $(document).ready(function () {
        ejecutar();
    });

</script>
</body>
</html>