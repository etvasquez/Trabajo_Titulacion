var tipo = "";
var rutaBase = 'http://localhost:8888/';
$(document).ready(function () {
    try {
        var regex = /&lt;br&gt;/gi
        var arryAreas = [];
        tipo = JSON.parse(document.getElementById('json').innerHTML);
        persona = JSON.parse(document.getElementById('jsonpersona').innerHTML);
        $.each(persona, function (key, value) {
            if (arryAreas.indexOf(value.area) == -1) {
                arryAreas.push(value.area);
            }
            if (value.rol == 'Dirección' || value.rol == 'Codirección' || value.rol == 'Director (E)') {
                $("#coordinador").append("<span><img id='img' src='../static/img/user.png' alt='coordinador'><a style='list-style: none' href='/usuario/"+value.identificacion+"'>" + value.nombre + " " + value.apellido + "</a></span><br>");
            } else {
                $("#participante").append("<span><img id='img' src='../static/img/participante.png'alt='participante'><a style='list-style: none' href='/usuario/"+value.identificacion+"'>" + value.nombre + " " + value.apellido + "</a></span><br>");
            }
        });
        var contador = 0;
        for (var i = 0; i < arryAreas.length; i++) {
            if (arryAreas[i] == 'SIN ASIGNAR' || arryAreas[i].substring(0, 4) == "http") {
            } else {
                if (contador == arryAreas.length - 1) {
                    $("#areas").append("<span style='background: #4235cd;border-radius: 20px;margin-left: 15px;" +
                        "padding: 10px 10px 10px 10px;color: #fff;font-weight: bolder;font-size: small'>" + arryAreas[i] + "</span>");
                } else {
                    $("#areas").append("<span style='background: #4235cd;border-radius: 20px;margin-left: 15px;" +
                        "padding: 10px 10px 10px 10px;color: #fff;font-weight: bolder;font-size: small'>" + arryAreas[i] + "</span><br><br>");
                    contador++;
                }
            }
        }
        var descripcion = tipo.descripcion.replace(regex, '\n');
        console.log(Object.keys(persona));
        $("#titulo").append(tipo.titulo);
        $("#tipo_proyecto").append(tipo.tipo);
        if (tipo.tipo == 'Investigación') {
            $("#tipo_proyecto").css('background', '#cd2b2b');
        }
        if (tipo.tipo == 'Innovación Docente' || tipo.tipo == 'Innovación docente') {
            $("#tipo_proyecto").css('background', '#ddd12d');
        }
        if (tipo.tipo == 'Extensión') {
            $("#tipo_proyecto").css('background', '#2bcd31');
        }
        if (tipo.tipo == 'Consultoría') {
            $("#tipo_proyecto").css('background', '#2bcdb1');
        }
        if (tipo.tipo == 'Implementación') {
            $("#tipo_proyecto").css('background', '#2b64cd');
        }
        if (tipo.tipo == 'Innovación') {
            $("#tipo_proyecto").css('background', '#a12bcd');
        }
        if (tipo.tipo == 'Propuesta Enviada') {
            $("#tipo_proyecto").css('background', '#cd672b');
        }
        if (tipo.tipo == 'Vinculación') {
            $("#tipo_proyecto").css('background', '#f122d0');
        }
        $("#inicio").append(tipo.fechainicio);
        $("#fin").append(tipo.fechafin);
        if (tipo.descripcion == 'SIN ASIGNAR') {
            $("#descripcion").append("");
        } else {
            $("#descripcion").append(descripcion);
        }
        if (tipo.objetivo == 'SIN ASIGNAR') {
            $("#objetivo").append("");
        } else {
            $("#objetivo").append(tipo.objetivo);
        }
        $("#reprogramado").append(tipo.reprogramado.toUpperCase());
        $("#avance").append(tipo.avance);
        $("#estado").append(tipo.estado.toUpperCase());
        if (tipo.programa == 'SIN ASIGNAR') {
            $("#programa").append("");
        } else {
            $("#programa").append(tipo.programa);
        }
//get name
        $("#InputEmail1").blur(function () {
            var str = $("#InputEmail1").val();
            if(str!=null && str!=""){
            var ruta = rutaBase + "getNameByEmail/" + str;
            $.get(ruta,
                function (res) {
                    $("#InputName").val(res);
                });
            }
        });
        $("#InputEmail11").blur(function () {
            var str = $("#InputEmail11").val();
            if(str!=null && str!="") {
                var ruta = rutaBase + "getNameByEmail/" + str;
                $.get(ruta,
                    function (res) {
                        $("#InputName1").val(res);
                    });
            }
        });
    } catch (e) {
    }
});

function inicializarFormulario(str) {
    $("#idCom1").val(str);
}
function generarUID() {
    $("#fileName").val(generateUUID());
    var variable = $("#idCom").val();
    $("#id").val(variable);
    $("#fileName").val(generateUUID());
}
function generateUUID() {
    var d = new Date().getTime();
    var uuid = 'xxxxxxxxxxxx4xxxyxxxxxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
        var r = (d + Math.random() * 16) % 16 | 0;
        d = Math.floor(d / 16);
        return (c == 'x' ? r : (r & 0x3 | 0x8)).toString(16);
    });
    return uuid;
}




