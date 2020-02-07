<!doctype html>
<html lang="en">
<head>

    <meta name="layout" content="main">
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Tickets sin Asignar</title>
</head>

<body>

%{--<link rel="stylesheet" href="${resource(dir: 'js/bootstrap/assets/stylesheets/', file: 'pages.min.css')}" type="text/css">--}%
<asset:stylesheet src="assets/stylesheets/pages.min.css"/>



<div class="panel">

    <div class="panel-body">
        <div class="row">

            <div class="col-md-12">


                <div class="page-header">
                    <h1><i class="fa fa-search page-header-icon"></i>&nbsp;&nbsp;Resultados</h1>
                </div> <!-- / .page-header -->


                <div class="row">
                    <div class="col-md-12 col-sm-12 rango_fechas">
                        <form method="post" action="tickets_sin_asignar" >

                            %{--<input type="text" name="name" id="titulo" class="form-control">--}%

                            <div class="row">

                                <div class="col-md-4">
                                    <div class="form-group no-margin-hr">
                                        <label class="control-label">Rango</label>
                                        <div class="input-daterange input-group" id="bs-datepicker-range">
                                            <input type="text" class="form-control" id="fecha_desde" name="fecha_desde" placeholder="Start date" value="${params.fecha_desde==null?funciones.formatDate(new java.util.Date(),"MM/dd/yyyy"):params.fecha_desde}">
                                            <span class="input-group-addon">to</span>
                                            <input type="text" class=" form-control" id="fecha_hasta" name="fecha_hasta" placeholder="End date" value="${params.fecha_hasta==null?funciones.formatDate(new java.util.Date(),"MM/dd/yyyy"):params.fecha_hasta}">
                                        </div>
                                    </div>
                                </div>

                                <div class="col-md-1">
                                    <div class="form-group no-margin-hr">
                                        <label class="control-label">Numero</label>
                                        <input type="text" name="numero_ticket" id="numero_ticket" value="${params.numero_ticket.toString().replace('null','')}" class="form-control">
                                    </div>
                                </div>

                                <div class="col-md-3">
                                    <div class="form-group no-margin-hr">
                                        <label class="control-label">Titulo</label>
                                        <input type="text" name="titulo" id="titulo" value="${params.titulo.toString().replace('null','')}" class="form-control">
                                    </div>
                                </div>
                                <div class="col-md-1">
                                    <div class="form-group no-margin-hr">
                                        <label class="control-label">&nbsp;&nbsp;&nbsp;</label><br/>
                                        <span class="floatR">
                                            <input type="submit" class="btn btn-success" type="button" value="Actualizar" />

                                        </span>
                                    </div>

                                </div>
                            </div>

                        </form>
                    </div>
                </div>


                <div class="search-text">
                    <strong>${lista_tickets.size()}</strong> Tickets encontrados <!--span class="text-primary">Lorem ipsum</span-->
                </div> <!-- / .search-text -->

            <!-- Tabs -->
                <div class="search-tabs">
                    <ul class="nav nav-tabs">
                        <li class="active">
                            <a href="#search-tabs-all" data-toggle="tab">Tickets no Asignados <span class="label label-primary">${lista_tickets.size()}</span></a>
                        </li>

                    </ul> <!-- / .nav -->
                </div>
                <!-- / Tabs -->

                <!-- Panel -->
                <div class="panel search-panel">


                    <!-- Search results -->
                    <div class="panel-body tab-content">

                        <!-- Classic search -->
                        <ul class="search-classic tab-pane fade in active" id="search-tabs-all">


                            <%
                                for (Map<String,java.lang.Object> map : lista_tickets){

                            %>
                            <li>

                                <div class="row">
                                    <div class="col-md-6">
                                        <h4><b>Registrado Por :</b> ${map.get("f_hecho_por").toString().toUpperCase()} </h4>
                                    </div>
                                    <div class="col-md-6">
                                        <h4 class="pull-right"><b>Cliente :</b> ${map.get("f_cliente").toString().toUpperCase()} </h4>
                                    </div>
                                </div>

                                <div class="table-info">

                                    <table class="table table-bordered table-sm">
                                        <thead>
                                        <tr>
                                            <th >#</th>
                                            <th style="text-align: center">Proyecto</th>
                                            <th style="text-align: center">Fecha</th>
                                            <th style="text-align: center">Fecha Vencimiento</th>
                                            <th style="text-align: center">Fecha Modificacion</th>
                                            <th style="text-align: center">Tipo</th>
                                            <th style="text-align: center">Proridad</th>

                                        </tr>
                                        </thead>
                                        <tbody>
                                        <tr>
                                            <td >${map.get("f_idrecord").toString().padLeft(6,"0")}</td>
                                            <td style="text-align: center">${map.get("f_nombre").toString()}</td>
                                            <td style="text-align: center">${map.get("f_fecha").toString()}</td>
                                            <td style="text-align: center">${map.get("f_fecha_vencimiento").toString()}</td>
                                            <td style="text-align: center">${map.get("f_fecha_modificacion").toString()}</td>
                                            <td style="text-align: center">${map.get("f_tipo_espanol").toString()}</td>
                                            <td style="text-align: center!important;" > <div class="label ${map.get("f_color").toString()} ticket-label">${map.get("f_tipo_tk").toString().equals("1")?'Error-':'Peticion-'}${map.get("f_prioridad").toString()}</div> </td>

                                        </tr>
                                        </tbody>
                                    </table>

                                </div>
                                <a class="search-title">${map.get("f_titulo").toString()}</a>
                                %{--<a href="#" class="search-url">http://example.com/some/page</a>--}%
                                <div class="search-content">

                                    <%
                                            if(map.get("f_descipcion").toString().size()>400)
                                            {
                                    %>
                                    ${raw(map.get("f_descipcion").toString().substring(0,400))+"..."}
                                    <%
                                        }
                                        else {
                                    %>
                                    ${raw(map.get("f_descipcion").toString())}
                                    <%
                                            }
                                    %>
                                    <br/>
                                    %{--<button class="btn btn-success" data-toggle="modal" data-target="#modal-blurred-bg">Modal with blurred background</button>--}%
                                    <a href="asignar_tickets?idt=${map.get("f_idrecord").toString()}" class="link_asignar"   name="${map.get("f_idrecord").toString()}" style="cursor:pointer;"> Continnuar y asignar <span class="meta-nav">→</span></a>
                                </div> <!-- / .search-content -->

                            </li>

                            <%
                                }
                            %>

                        </ul>

                    </div>
                    <!-- / Search results -->

                    <!-- Panel Footer -->
                    <div class="panel-footer">

                    </div> <!-- / .panel-footer -->

                </div>
                <!-- / Panel -->

                <div id="modal_asignar" class="modal fade modal-blur in" tabindex="-1" role="dialog"  aria-hidden="false">
                    <div class="modal-dialog modal-md">
                        <div class="modal-content">
                            <div class="modal-header">
                                %{--<button type="button" class="close" data-dismiss="modal">×</button>--}%
                                <h4 class="modal-title">ASIGNACION</h4>
                            </div>
                            %{--body--}%
                            <div class="modal-body">

                            </div>
                            %{--body--}%
                        </div> <!-- / .modal-content -->
                    </div> <!-- / .modal-dialog -->
                </div>

            </div>

        </div>

    </div>

</div>


<asset:javascript src="js_proyecto/tickets/tickets_sin_asignar.js"/>
<g:javascript>
    $(document).ready(function () {
        ejecutar();
    })
</g:javascript>
</body>
</html>