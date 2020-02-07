/**
 * Created by Pabel on 20/2/2017.
 */


function ejecutar_contrasena() {

    $("#idioma_combo").change(function () {


        var x = $("#url_cambio_contrasena").val();
        console.log(x);
        $.ajax({
            type: "POST",
            url:  "./cambiar_idioma",
            data: {idioma: $("#idioma_combo").val()},
            success: function (data) {

                window.location = window.location;

            }
        });
      
    });

    $("#procesar_modal_cambio_contrasena").click(function () {

        //codigo que todo paso bien
        
        if ($("#contrasena_anterior").val() == '') {

            if ($("#idioma").val()=='es')
            {
                alert("Digite su anterior contraseña");
            }
            else {
                alert("Enter your old password")
            }

            return;
        } else {
            if($("#nueva_contrasena").val()=='')
            {
                if ($("#idioma").val()=='es')
                {
                     alert("Digite la nueva contraseña");
                }
                else {
                    alert("Enter the new password")
                }

                return;
            }else {
                if($("#repetir_contrasena").val()=='')
                {
                    if ($("#idioma").val()=='es')
                    {
                        alert("Digite de nuevo la nueva contraseña");
                    }
                    else {
                        alert("Re-enter the new password")
                    }

                    return;
                }else {
                    if($("#repetir_contrasena").val()==$("#nueva_contrasena").val())
                    {
                       // //codigo que todo paso bien
                         var x = $("#url_cambio_contrasena").val();


                        var datos = {
                            vieja: $("#contrasena_anterior").val(),
                            nueva: $("#nueva_contrasena").val()
                        };

                        $.ajax({
                            type: "POST",
                            url: "./cambiar_contrasena",
                            data: datos,
                            success: function (data) {

                                if (data=='-1')
                                {
                                    if ($("#idioma").val()=='es')
                                    {
                                        alert('Contraseña anterior no coincide con la original');
                                    }
                                    else {
                                        alert("Older password does not match the original")
                                    }

                                    return;
                                }else {
                                    if ($("#idioma").val()=='es')
                                    {
                                        alert("Datos Guardados Correctamente");
                                    }
                                    else {
                                        alert("Data Saving Correctly")
                                    }

                                    $("#atras_cambio_contrasena").click();
                                }

                            }
                        });

                    }else {

                        if ($("#idioma").val()=='es')
                        {
                            alert("Las contraseña no coinciden");
                        }
                        else {
                            alert("Password does not match")
                        }

                        return;

                    }

                }
            }
        }
    });
    $("#atras_cambio_contrasena").click(function () {
        $("#contrasena_anterior").val('');
        $("#nueva_contrasena").val('');
        $("#repetir_contrasena").val('');
    });
}
