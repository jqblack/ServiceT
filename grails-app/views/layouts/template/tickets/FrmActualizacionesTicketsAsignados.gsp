<!doctype html>
<html lang="en">
<head>
    <meta name="layout" content="main">
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Comentar Cerrar Tickets</title>
</head>

<body>

<asset:image id="url_imagen" value="avatar.png" src="avatar.png" style="display: none"/>

%{--<input id="" value="avatar.png" />--}%
%{--<g:img id="url_imagen" src="assets/images/pixel-admin/avatar.png" style="display: none"/>--}%

%{--<link rel="stylesheet" href="${resource(dir: 'js/bootstrap/assets/stylesheets/', file: 'pages.min.css')}" type="text/css">--}%
<asset:stylesheet src="assets/stylesheets/pages.min.css"/>


<div class="panel">

    <div class="panel-body">
        <div class="row">

            <div class="col-md-12">



                <!-- 5. $SEARCH_RESULTS_PAGE =======================================================================
                   Search results page
                -->

                <div class="page-header">
                    <h1><i class="fa fa-search page-header-icon"></i>&nbsp;&nbsp;Resultados</h1>
                </div> <!-- / .page-header -->

                <div class="row">
                    <div class="col-md-12 col-sm-12 rango_fechas">
                        <form method="post" action="actualizar_tickets" >

                            %{--<input type="text" name="name" id="titulo" class="form-control">--}%

                            <div class="row">

                                <div class="col-md-4">
                                    <div class="form-group no-margin-hr">
                                        <label class="control-label">Rango</label>
                                        <div class="input-daterange input-group" id="bs-datepicker-range">
                                            <input type="text" class="form-control" id="fecha_desde" name="fecha_desde" placeholder="Start date" value="${params.fecha_desde==null?funciones.formatDate(new java.util.Date(),'MM/dd/yyyy'):params.fecha_desde}">
                                            <span class="input-group-addon">to</span>
                                            <input type="text" class=" form-control" id="fecha_hasta" name="fecha_hasta" placeholder="End date" value="${params.fecha_hasta==null?funciones.formatDate(new java.util.Date(),'MM/dd/yyyy'):params.fecha_hasta}">
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
                            <a href="#search-tabs-all" data-toggle="tab">Tickets Asignados <span class="label label-primary">${lista_tickets.size()}</span></a>
                        </li>
                        %{--<li>--}%
                        %{--<a href="#search-tabs-users" data-toggle="tab">Users <span class="label label-success">5</span></a>--}%
                        %{--</li>--}%
                        %{--<li>--}%
                        %{--<a href="#search-tabs-messages" data-toggle="tab">Messages <span class="label label-danger">9</span></a>--}%
                        %{--</li>--}%
                    </ul> <!-- / .nav -->
                </div>
                <!-- / Tabs -->

                <!-- Panel -->
                <div class="panel search-panel">

                    <!-- Search form -->
                    %{--<form action="" class="search-form bg-primary">--}%
                    %{--<div class="input-group input-group-lg">--}%
                    %{--<span class="input-group-addon no-background"><i class="fa fa-search"></i></span>--}%
                    %{--<input type="text" name="s" class="form-control" placeholder="Type your search here...">--}%
                    %{--<span class="input-group-btn">--}%
                    %{--<button class="btn" type="submit">Search</button>--}%
                    %{--</span>--}%
                    %{--</div> <!-- / .input-group -->--}%
                    %{--</form>--}%
                    <!-- / Search form -->

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
                                    %{--<div class="table-header">--}%
                                    %{--<div class="table-caption">--}%
                                    %{--Primary Table--}%
                                    %{--</div>--}%
                                    %{--</div>--}%
                                    <table class="table table-bordered">
                                        <thead>
                                        <tr>
                                            <th >#</th>
                                            <th style="text-align: center">Proyecto</th>
                                            <th style="text-align: center">Fecha</th>
                                            <th style="text-align: center">Fecha Vencimiento</th>
                                            <th style="text-align: center">Fecha Modificacion</th>
                                            <th style="text-align: center">Tipo</th>
                                            <th style="text-align: center">Proridad</th>
                                            <th style="text-align: center">Comentario</th>
                                            <th style="text-align: center">Ver</th>
                                            <th style="text-align: center">Cerrar</th>

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
                                            <td style="text-align: center!important;" >
                                                <span class="floatR">
                                                    <button class="btn btn-primary" name="${map.get("f_idrecord").toString()}" type="button" data-toggle="modal" id="agregar_comentario${map.get("f_idrecord").toString().encodeAsBase64().replace("=",'')}" onclick="agregar_comentari('agregar_comentario${map.get("f_idrecord").toString().encodeAsBase64().replace("=",'')}')" data-target="#modal_comentario"><span class="fa fa-plus"></span></button>
                                                </span>
                                            </td>
                                            <td style="text-align: center!important;" >
                                                <span class="floatR">

                                                    <button class="btn btn-primary" name="${map.get("f_idrecord").toString()}" type="button" data-toggle="modal" id="ver_comentarios${map.get("f_idrecord").toString().encodeAsBase64().replace("=",'')}" onclick="get_comentario('ver_comentarios${map.get("f_idrecord").toString().encodeAsBase64().replace("=",'')}')"  data-target="#modal_ver_comentarios"><span class="fa fa-eye"></span></button>
                                                </span>
                                            </td>
                                            <td style="text-align: center!important;" >
                                                <span class="floatR">
                                                    <button class="btn btn-primary" name="${map.get("f_idrecord").toString()}" type="button" data-toggle="modal" id="cerrar_ticket${map.get("f_idrecord").toString().encodeAsBase64().replace("=",'')}" onclick="get_id_cerrar_ticket('cerrar_ticket${map.get("f_idrecord").toString().encodeAsBase64().replace("=",'')}')"  data-target="#modal_cerrar"><span class="fa  fa-unlock-alt"></span></button>
                                                </span>
                                            </td>

                                        </tr>
                                        </tbody>
                                    </table>

                                </div>
                                <a class="search-title">${map.get("f_titulo").toString()}</a>
                                %{--<a href="#" class="search-url">http://example.com/some/page</a>--}%
                                <div class="search-content">
                                    ${raw(map.get("f_descipcion").toString())}
                                    %{--<%--}%
                                    %{--if(map.get("f_descipcion").toString().size()>400)--}%
                                    %{--{--}%
                                    %{--%>--}%
                                    %{--${raw(map.get("f_descipcion").toString().substring(0,400))+"..."}--}%
                                    %{--<%--}%
                                    %{--}--}%
                                    %{--else {--}%
                                    %{--%>--}%
                                    %{--${raw(map.get("f_descipcion").toString())}--}%
                                    %{--<%--}%
                                    %{--}--}%
                                    %{--%>--}%
                                    %{--<br/>--}%
                                    <h4 class="pull-right"><b style="color: red">Asigando a :</b> ${map.get("f_tecnico").toString().toUpperCase()} </h4>
                                    <br/>
                                    %{--<button class="btn btn-success" data-toggle="modal" data-target="#modal-blurred-bg">Modal with blurred background</button>--}%
                                    %{--<a href="${getRequest().getContextPath()}/reasignar_tickets?idt=${map.get("f_idrecord").toString()}" class="link_asignar"   name="${map.get("f_idrecord").toString()}" style="cursor:pointer;"> Continnuar y reasignar <span class="meta-nav">→</span></a>--}%
                                </div> <!-- / .search-content -->
                            %{--<div class="search-tags">--}%
                            %{--<span class="search-tags-text">Tags:</span>--}%
                            %{--<a href="#" class="label label-success">Lorem</a>--}%
                            %{--<a href="#" class="label label-success">Ipsum</a>--}%
                            %{--<a href="#" class="label label-success">Dolor</a>--}%
                            %{--</div> <!-- / .search-tags -->--}%
                            </li>

                            <%
                                }
                            %>




                        </ul>

                    </div>
                    <!-- / Search results -->

                    <!-- Panel Footer -->
                    <div class="panel-footer">
                        %{--<ul class="pagination">--}%
                        %{--<li class="disabled"><a href="#">«</a></li>--}%
                        %{--<li class="active"><a href="#">1 <span class="sr-only">(current)</span></a></li>--}%
                        %{--<li><a href="#">2</a></li>--}%
                        %{--<li><a href="#">3</a></li>--}%
                        %{--<li><a href="#">4</a></li>--}%
                        %{--<li><a href="#">5</a></li>--}%
                        %{--<li><a href="#">»</a></li>--}%
                        %{--</ul>--}%
                    </div> <!-- / .panel-footer -->

                </div>
                <!-- / Panel -->

                <div id="modal_comentario" class="modal fade modal-blur in" tabindex="-1" role="dialog"  aria-hidden="false">
                    <div class="modal-dialog modal-md">
                        <div class="modal-content">
                            <div class="modal-header">
                                %{--<button type="button" class="close" data-dismiss="modal">×</button>--}%
                                <h4 class="modal-title">AÑADIR COMENTARIO</h4>
                            </div>
                            %{--body--}%
                            <div class="modal-body">

                                <div class="row">
                                    <div class="col-md-6 col-sm-12">
                                        <div class="form-group">
                                            <input type="text" style="display: none;" name="" id="id_tickets_actualizar" class="form-control">
                                            <label class="">Horas :</label>
                                            <input style="height: 36px" type="text" name="" id="horas" class="form-control">
                                        </div>
                                    </div>

                                </div>



                                <div class="row" id="div_nota" >
                                    <div class="col-md-12" >
                                        <div class=" form-group">
                                            <label >Comentario:</label>
                                            <textarea class="form-control" id="nota" rows="5"></textarea>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-12">

                                        <div class="form-group">
                                            <label class="">Archivo :</label>

                                            <div  class="form-control" id="FileUpload"></div>

                                        </div>

                                    </div>
                                </div>

                                <div class="row">
                                    <div class="col-md-12" style="text-align: center">
                                        <span >
                                            %{--<button data-type="warning" data-text="<strong>Warning!</strong> Best check yo self, you're not looking too good." id="pabel" class="btn btn-small btn-primary auto-close-alert">Auto closing alert</button>--}%
                                            <button class="btn btn-success" id="procesar_modal_comentario" type="button" >Procesar</button>
                                            <button class="btn btn-danger" type="button" id="atras_modal_comentario" data-dismiss="modal">Atras</button>
                                        </span>
                                    </div>
                                </div>


                            </div>
                            %{--body--}%
                        </div> <!-- / .modal-content -->
                    </div> <!-- / .modal-dialog -->
                </div>


                <div id="modal_cerrar" class="modal fade modal-blur in" tabindex="-1" role="dialog"  aria-hidden="false">
                    <div class="modal-dialog modal-md">
                        <div class="modal-content">
                            <div class="modal-header">
                                %{--<button type="button" class="close" data-dismiss="modal">×</button>--}%
                                <h4 class="modal-title">CERRAR TICKETS</h4>
                            </div>
                            %{--body--}%
                            <div class="modal-body">

                                <div class="row">
                                    <div class="col-md-6 col-sm-12">
                                        <div class="form-group">
                                            <label class="">Horas :</label>
                                            <input style="height: 36px" type="text" name="" id="horas_cerrar" class="form-control">
                                        </div>
                                    </div>

                                </div>
                                <div class="row" id="div_nota_cerrar" >
                                    <div class="col-md-12" >
                                        <div class=" form-group">
                                            <input type="text" style="display: none;" name="" id="id_tickets_cerrar" class="form-control">
                                            <label >Comentario:</label>
                                            <textarea class="form-control" id="nota_cerrar" rows="5"></textarea>
                                        </div>
                                    </div>
                                </div>


                                <div class="row">
                                    <div class="col-md-12" style="text-align: center">
                                        <span >
                                            %{--<button data-type="warning" data-text="<strong>Warning!</strong> Best check yo self, you're not looking too good." id="pabel" class="btn btn-small btn-primary auto-close-alert">Auto closing alert</button>--}%
                                            <button class="btn btn-success" id="procesar_modal_cerrar" type="button" >Procesar</button>
                                            <button class="btn btn-danger" type="button" id="atras_modal_cerrar" data-dismiss="modal">Atras</button>
                                        </span>
                                    </div>
                                </div>


                            </div>
                            %{--body--}%
                        </div> <!-- / .modal-content -->
                    </div> <!-- / .modal-dialog -->
                </div>




                <div id="modal_ver_comentarios" class="modal fade modal-blur in" tabindex="-1" role="dialog"  aria-hidden="false">
                    <div class="modal-dialog modal-lg">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal">×</button>
                                <h4 class="modal-title">COMENTARIO</h4>
                            </div>
                            %{--body--}%
                            <div class="modal-body">


                                %{--<div class="panel">--}%

                                %{--<div class="panel-body">--}%
                                <div class="row">

                                    <div class="col-md-12">



                                        <!-- 5. $SEARCH_RESULTS_PAGE =======================================================================
                   Search results page
                -->

                                        %{--<div class="page-header">--}%
                                        %{--<h1><i class="fa fa-search page-header-icon"></i>&nbsp;&nbsp;Resultado </h1>--}%
                                        %{--</div> <!-- / .page-header -->--}%

                                        <div class="search-text">
                                            %{--<strong>$lista_tickets.size()}</strong> Tickets encontrados <!--span class="text-primary">Lorem ipsum</span-->--}%
                                        </div> <!-- / .search-text -->

                                    <!-- Tabs -->
                                        <div class="search-tabs">
                                            <ul class="nav nav-tabs">
                                                <li class="active">
                                                    <a href="#search-tabs-all" data-toggle="tab" ><span id="titulo_principal" ></span></a>
                                                </li>
                                                %{--<li>--}%
                                                %{--<a href="#search-tabs-users" data-toggle="tab">Users <span class="label label-success">5</span></a>--}%
                                                %{--</li>--}%
                                                %{--<li>--}%
                                                %{--<a href="#search-tabs-messages" data-toggle="tab">Messages <span class="label label-danger">9</span></a>--}%
                                                %{--</li>--}%
                                            </ul> <!-- / .nav -->
                                        </div>
                                        <!-- / Tabs -->

                                        <!-- Panel -->
                                        <div class="panel search-panel">


                                            <!-- Search results -->
                                            <div class="panel-body tab-content">


                                                <!-- Classic search -->
                                                %{--<ul class="search-classic tab-pane fade in active" id="search-tabs-all">--}%
                                                <ul class="search-classic tab-pane fade in active" id="lista_cometario">


                                                    %{--<li style="text-align:center;background: #EAEEFB;font-size: 20px;font-weight: bold">--}%
                                                    %{--<p>Actualizaciones del Ticket</p>--}%
                                                    %{--</li>--}%
                                                    %{--<%--}%
                                                    %{--List lista_update = ticketService.ListarTicketsUpdate(params.idt.toString().toLong());--}%

                                                    %{--for (Map<String,java.lang.Object> map_update : lista_update)--}%
                                                    %{--{--}%
                                                    %{--%>--}%
                                                    %{--<li class="" style="list-style: none;">--}%
                                                    %{--<img src="${resource(dir: "/images", file: "avatar.png")}" width="50" height="50" class="img-circle">--}%
                                                    %{--<span class="tableFont"> <strong>User :</strong> </span>--}%
                                                    %{--<span class="tableFont2" > ${map_update.get("f_hecho_por")}</span>--}%
                                                    %{--<h6 class="fk"><strong>Porteado :</strong><span> ${map_update.get("f_fecha")}</span></h6>--}%
                                                    %{--<div class="alert alert-info " style="width: 95%;">--}%
                                                    %{--${raw(map_update.get("f_update"))}--}%
                                                    %{--</div>--}%
                                                    %{--</li>--}%

                                                    %{--<%--}%

                                                    %{--}--}%

                                                    %{--%>--}%



                                                </ul>

                                            </div>
                                            <!-- / Search results -->

                                            <!-- Panel Footer -->
                                            <div class="panel-footer">
                                                %{--<ul class="pagination">--}%
                                                %{--<li class="disabled"><a href="#">«</a></li>--}%
                                                %{--<li class="active"><a href="#">1 <span class="sr-only">(current)</span></a></li>--}%
                                                %{--<li><a href="#">2</a></li>--}%
                                                %{--<li><a href="#">3</a></li>--}%
                                                %{--<li><a href="#">4</a></li>--}%
                                                %{--<li><a href="#">5</a></li>--}%
                                                %{--<li><a href="#">»</a></li>--}%
                                                %{--</ul>--}%
                                            </div> <!-- / .panel-footer -->

                                        </div>
                                        <!-- / Panel -->


                                    </div>

                                </div>

                                <div class="row">
                                    <div class="col-md-12">
                                        <button class="btn btn-danger" type="button" id="" data-dismiss="modal">Atras</button>
                                    </div>
                                </div>
                                %{--</div>--}%
                                %{--</div>--}%
                            </div>
                            %{--body--}%
                        </div> <!-- / .modal-content -->
                    </div> <!-- / .modal-dialog -->
                </div>

            </div>

        </div>

    </div>

</div>


<asset:javascript src="js_proyecto/funciones.js"/>
<asset:javascript src="js_proyecto/tickets/actualizar_tickets.js"/>
<g:javascript>
$(document).ready(function () {

ejecutar();
})
</g:javascript>
</body>
</html>