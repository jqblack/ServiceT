<!doctype html>
<html lang="en">
<head>

    <meta name="layout" content="main">
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Reasignar Tickets</title>
</head>

<body>


<%
    Map<String,java.lang.Object> map;

    List ls = ticketService.GetTiketsById(params.idt.toString().toLong());


    if (ls.size()<=0)
    {
%>
<div class="page-500">
    <div class="header">
    </div> <!-- / .header -->
    <div class="error-code">Error</div>
    <div class="error-text">
        <span class="oops">OOPS!</span><br>
        <span class="hr"></span>
        <br>
        ESTE TICKET NO A SIDO ENCONTRADO EN NUESTRA PLATAFORMA
    </div> <!-- / .error-text -->
</div>

<%
    }else
    {
        map = ls.first();

//        if((map.get("f_idquienasigno").toString().equals('null') || map.get("f_idquienasigno").is(null) || map.get("f_idquienasigno").toString().equals("0") || map.get("f_idquienasigno").toString().equals("")) && (map.get("f_idtecnicoasignado").toString().equals("null") || map.get("f_idtecnicoasignado").is(null) || map.get("f_idtecnicoasignado").toString().equals("0")|| map.get("f_idtecnicoasignado").toString().equals("")) )

        //System.out.println map.get("f_cancelado").toString().toBoolean();
        if(map.get("f_status").toString() as Long == 3)
        {

%>
<div class="panel">
    <div class="panel-body">
        <div class="row">
            <div class="col-md-12">
                <!-- 5. $SEARCH_RESULTS_PAGE
                         =======================================================================
                           Search results page -->
                <div class="page-header">
                    <h1><i class="fa fa-search page-header-icon"></i>&nbsp;&nbsp;Resultado </h1>
                </div> <!-- / .page-header -->
                <ul class="search-classic tab-pane fade in active" >
                    <li style="text-align:center;background: #EAEEFB;font-size: 20px;font-weight: bold">
                        <p>Este Ticket fue cancelado</p>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div>

<%

    }
    else  if(map.get("f_status").toString() as Long  == 2)
    {

%>


%{--<g:javascript>--}%

%{--set_error("${getRequest().getContextPath()}/error");--}%
%{--</g:javascript>--}%

%{--<meta http-equiv="refresh" content="0; url=${getRequest().getContextPath()}/error" />--}%
<input style="display: none;" type=""  id="id_tickets" class="form-control" value="${params.idt}" required>

<div class="panel">

    <div class="panel-body">
        <div class="row">

            <div class="col-md-12">



                <!-- 5. $SEARCH_RESULTS_PAGE =======================================================================
                   Search results page
                -->

                <div class="page-header">
                    <h1><i class="fa fa-search page-header-icon"></i>&nbsp;&nbsp;Resultado </h1>
                </div> <!-- / .page-header -->

                <div class="search-text">
                    %{--<strong>$lista_tickets.size()}</strong> Tickets encontrados <!--span class="text-primary">Lorem ipsum</span-->--}%
                </div> <!-- / .search-text -->

            <!-- Tabs -->
                <div class="search-tabs">
                    <ul class="nav nav-tabs">
                        <li class="active">
                            <a href="#search-tabs-all" data-toggle="tab">${map.get("f_titulo")} <!--span class="label label-primary">21</span--></a>
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



                            <li>
                                <h4>Registrado Por : ${map.get("f_hecho_por").toString().toUpperCase()} </h4>
                                <div class="table-info">
                                    %{--<div class="table-header">--}%
                                    %{--<div class="table-caption">--}%
                                    %{--Primary Table--}%
                                    %{--</div>--}%
                                    %{--</div>--}%
                                    <table class="table table-bordered">
                                        <thead>
                                        <tr>
                                            <th>#</th>
                                            <th style="text-align: center">Proyecto</th>
                                            <th style="text-align: center">Fecha</th>
                                            <th style="text-align: center">Fecha Vencimiento</th>
                                            <th style="text-align: center">Fecha Modificacion</th>
                                            <th style="text-align: center">Proridad</th>
                                            <th style="text-align: center"></th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <tr>
                                            <td>${map.get("f_idrecord").toString().padLeft(6,"0")}</td>
                                            <td style="text-align: center">${map.get("f_nombre").toString()}</td>
                                            <td style="text-align: center">${map.get("f_fecha").toString()}</td>
                                            <td style="text-align: center">${map.get("f_fecha_vencimiento").toString()}</td>
                                            <td style="text-align: center">${map.get("f_fecha_modificacion").toString()}</td>
                                            <td style="text-align: center!important;" > <div class="label ${map.get("f_color").toString()} ticket-label">${map.get("f_tipo_tk").toString().equals("1")?'E-':'P-'}${map.get("f_prioridad").toString()}</div> </td>
                                            <td style="text-align: center">
                                                <span class="floatR">
                                                    <button class="btn btn-success" id="boton_reasignar" type="button" data-toggle="modal" data-target="#modal_asignar">Reasignar</button>
                                                    <button class="btn btn-danger" type="button" data-toggle="modal" data-target="#modal_cancelar">Cancelar</button>
                                                    %{--<a href="answer.php?method=c6171c5e21892e5fb66b0b318b1cc982:110:de+Finibus"><button class="btn btn-danger" type="button">Cancelar</button></a>--}%
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

                                </div> <!-- / .search-content -->

                            </li>
                            <li style="text-align:center;background: #EAEEFB;font-size: 20px;font-weight: bold">
                                <p>Actualizaciones del Ticket</p>
                            </li>
                            <%
                                    List lista_update = ticketService.ListarTicketsUpdate(params.idt.toString().toLong());

                                    for (Map<String,java.lang.Object> map_update : lista_update)
                                    {
                            %>
                            <li class="" style="list-style: none;">
                                <img src="${resource(dir: "/images", file: "avatar.png")}" width="50" height="50" class="img-circle">
                                <span class="tableFont"> <strong>User :</strong> </span>
                                <span class="tableFont2" > ${map_update.get("f_hecho_por")}</span>

                                <%
                                        if (!map_update.get("f_picture").is(null))
                                        {
                                %>
                                <form action="descargarUpdate" method="post" target="_blank">
                                    <input hidden type="text" name="idrecord" value="${map_update.get("f_idrecord")}">
                                    &nbsp;&nbsp;<button type="submit" class="btn-primary btn btn-sm" ><span class="fa fa-arrow-circle-down"></span>&nbsp;Descargar archivo</button>
                                </form>
                                <%

                                        }
                                %>
                                <h6 class="fk"><strong>Porteado :</strong><span> ${map_update.get("f_fecha")}</span></h6>
                                <div class="alert alert-info " style="width: 95%;">
                                    ${raw(map_update.get("f_update"))}
                                </div>
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

                <div id="modal_asignar" rel="namespace:animal" class="modal modalasignar fade modal-blur in" tabindex="-1" role="dialog"  aria-hidden="false">
                    <div class="modal-dialog modal-md">
                        <div class="modal-content">
                            <div class="modal-header">
                                %{--<button type="button" class="close" data-dismiss="modal">×</button>--}%
                                <h4 class="modal-title">REASIGNACION</h4>
                            </div>
                            %{--body--}%
                            <div  class="modal-body">
                                <div class="row">
                                    <div class="col-md-12 col-sm-12">
                                        <div class="form-group">
                                            <label>Tecnico :</label>
                                            <select id="tecnico" class="form-control form-group-margin">
                                                <option value="0">--SELECCIONE--</option>
                                                <g:each in="${list_empleados}">
                                                    <option value="${it.getId()}">${it.fDescripcion}</option>
                                                </g:each>
                                            </select>
                                        </div>
                                    </div>

                                    <div class="col-md-6 col-sm-12">
                                        <div class="form-group has-feedback">
                                            <label>Fecha de vencimiento</label>
                                            <div class="input-group date" id="fecha">
                                                %{--<input type="text" id="fecha_de_caducidad" class="form-control"><span class="input-group-addon"><i class="fa fa-calendar"></i></span>--}%
                                                <input type="datetime" id="fecha_de_caducidad" >
                                            </div>
                                        </div>
                                    </div>


                                    <div class="col-md-6 col-sm-12">
                                        <div class=" form-group">
                                            <input style="display: none;" type=""  id="idprioridad_original" class="form-control" value="${map.get("f_idprioridad")}" required>
                                            <label >Prioridad:</label>
                                            <select id="prioridad" class="form-control form-group-margin">
                                                <option value="0">--SELECCIONE--</option>
                                                %{--<option value="1">Error</option>--}%
                                                <optgroup label="Error">

                                                    <%

                                                            for (Map<String,java.lang.Object> map_prioridades : lista_prioridades){

                                                                if(map_prioridades.get("f_tipo_tk").toString().equals("1"))
                                                                {



                                                    %>
                                                    <option  value="${map_prioridades.get("f_idrecord")}">E - ${map_prioridades.get("f_descripcion")} </option>
                                                    %{--<option <% if (map.get("f_prioridad").toString().equals("1")){%>style="background-color: red;color: white" <%}else if (map.get("f_prioridad").toString().equals("2")){%>style="background-color: orangered;color: white"<%}else if (map.get("f_prioridad").toString().equals("3")){%>style="background-color: #00a0da;color: white"<%}else if (map.get("f_prioridad").toString().equals("4")){%>style="background-color: green;color: white"<%}%> value="${map.get("f_idrecord")}">E - ${map.get("f_descripcion")} </option>--}%
                                                    <%
                                                                }
                                                            }
                                                    %>

                                                </optgroup>
                                                <optgroup label="Peticion">

                                                    <%

                                                            for (Map<String,java.lang.Object> map_prioridades : lista_prioridades){

                                                                if(map_prioridades.get("f_tipo_tk").toString().equals("2"))
                                                                {



                                                    %>
                                                    <option  value="${map_prioridades.get("f_idrecord")}">P - ${map_prioridades.get("f_descripcion")} </option>
                                                    %{--<option  <% if (map.get("f_prioridad").toString().equals("1")){%>style="background-color: red;color: white" <%}else if (map.get("f_prioridad").toString().equals("2")){%>style="background-color: orangered;color: white"<%}else if (map.get("f_prioridad").toString().equals("3")){%>style="background-color: #00a0da;color: white"<%}else if (map.get("f_prioridad").toString().equals("4")){%>style="background-color: green;color: white"<%}%> value="${map.get("f_idrecord")}">P - ${map.get("f_descripcion")} </option>--}%
                                                    <%
                                                                }
                                                            }
                                                    %>

                                                </optgroup>

                                            </select>

                                        </div>
                                    </div>
                                </div>

                                <div class="row" id="div_nota" style="display: none">
                                    <div class="col-md-12" >
                                        <div class=" form-group">
                                            <label class="col-sm-4 control-label">Comentario:</label>
                                            <textarea class="form-control" id="nota" rows="5"></textarea>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-12" style="text-align: center">
                                        <span >
                                            %{--<button data-type="warning" data-text="<strong>Warning!</strong> Best check yo self, you're not looking too good." id="pabel" class="btn btn-small btn-primary auto-close-alert">Auto closing alert</button>--}%
                                            <button class="btn btn-success" id="procesar_modal_asignacion" type="button" >Procesar</button>
                                            <button class="btn btn-danger" type="button" id="atras_modal_asignacion" data-dismiss="modal">Atras</button>
                                        </span>
                                    </div>
                                </div>

                            </div>
                            %{--body--}%
                        </div> <!-- / .modal-content -->
                    </div> <!-- / .modal-dialog -->


                    <div id="uidemo-modals-alerts-success" class="modal  modal-alert modal-success fade">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <i class="fa fa-check-circle"></i>
                                </div>
                                <div id="titulo_notifificacion_si" class="modal-title">Alerta</div>
                                <div id="body_notifificacion_si" class="modal-body">Some alert text</div>
                                <div class="modal-footer">
                                    <button id="ok_notifificacion_si" type="button" class="btn btn-success" data-dismiss="modal">OK</button>
                                </div>
                            </div> <!-- / .modal-content -->
                        </div> <!-- / .modal-dialog -->
                    </div> <!-- / .modal -->
                <!-- / Success -->

                <!-- Danger -->
                    <div id="uidemo-modals-alerts-danger" class="modal modal-alert modal-danger fade">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <i class="fa fa-times-circle"></i>
                                </div>
                                <div id="titulo_notifificacion_no" class="modal-title">Alerta</div>
                                <div id="body_notifificacion_no" class="modal-body">Some alert text</div>
                                <div class="modal-footer">
                                    <button id="ok_notifificacion_no" type="button" class="btn btn-danger" data-dismiss="modal"  aria-hidden="true">OK</button>
                                </div>
                            </div> <!-- / .modal-content -->
                        </div> <!-- / .modal-dialog -->
                    </div> <!-- / .modal -->
                <!-- / Danger -->
                    <button  id="notificacion_si" style="display:none"  class="btn btn-success" data-toggle="modal" data-target="#uidemo-modals-alerts-success">Success</button>&nbsp;&nbsp;&nbsp;
                    <button id="notificacion_no" style="display:none" class="btn btn-danger" data-toggle="modal" data-target="#uidemo-modals-alerts-danger">Danger</button>&nbsp;&nbsp;&nbsp;

                </div>


                <div id="modal_cancelar" rel="namespace:animal" class="modal modalasignar fade modal-blur in" tabindex="-1" role="dialog"  aria-hidden="false">
                    <div class="modal-dialog modal-md">
                        <div class="modal-content">
                            <div class="modal-header">
                                %{--<button type="button" class="close" data-dismiss="modal">×</button>--}%
                                <h4 class="modal-title">CANCELACION</h4>
                            </div>
                            %{--body--}%
                            <div  class="modal-body">

                                <div class="row" id="div_nota_cancelar" >
                                    <div class="col-md-12" >
                                        <div class=" form-group">
                                            <label class="col-sm-4 control-label">Comentario:</label>
                                            <textarea class="form-control" id="nota_cancelacion" rows="5"></textarea>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-12" style="text-align: center">
                                        <span >
                                            %{--<button data-type="warning" data-text="<strong>Warning!</strong> Best check yo self, you're not looking too good." id="pabel" class="btn btn-small btn-primary auto-close-alert">Auto closing alert</button>--}%
                                            <button class="btn btn-success" id="procesar_modal_cancelacion" type="button" >Procesar</button>
                                            <button class="btn btn-danger" type="button" id="atras_modal_cancelacion" data-dismiss="modal">Atras</button>
                                        </span>
                                    </div>
                                </div>







                            </div>
                            %{--body--}%
                        </div> <!-- / .modal-content -->
                    </div> <!-- / .modal-dialog -->



                </div>




            </div>

        </div>

    </div>

</div>




<%

        }
    }
%>


<asset:javascript src="js_proyecto/funciones.js"/>
<asset:javascript src="js_proyecto/tickets/reasignar_tickets.js"/>

<g:javascript>
    $(document).ready(function () {
        ejecutar();
    });

    init.push(function () {
        $("#fecha_de_caducidad").jqxDateTimeInput({ width: '247px', height: '30px' });
    });

    set_tecnico(${map.get("f_idtecnicoasignado")});
    set_prioridad(${map.get("f_idprioridad")});



</g:javascript>

<script type="text/javascript">
    init.push(function () {
        $('.add-tooltip').tooltip();
    });
    //window.PixelAdmin.start(init);
</script>
</body>
</html>