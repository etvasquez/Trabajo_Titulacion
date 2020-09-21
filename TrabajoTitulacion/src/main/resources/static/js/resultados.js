var tipo="";
var rutaBase='http://localhost:8888/';
$(document).ready(function() {
    var regex = /&lt;br&gt;/gi
    var arryAreas = [];
    tipo = JSON.parse(document.getElementById('json').innerHTML);
    persona = JSON.parse(document.getElementById('jsonpersona').innerHTML);
    $.each(persona, function(key,value) {
        if(arryAreas.indexOf(value.area)==-1){
            arryAreas.push(value.area);
        }
        if(value.rol == 'Dirección'|| value.rol == 'Codirección' || value.rol=='Director (E)'){
            $("#coordinador").append("<span><img id='img' src='../img/user.png' alt='coordinador'>"+value.nombre+" "+value.apellido+"</span><br>");
        }else{
            $("#participante").append("<span><img id='img' src='/img/participante.png'alt='participante'>"+value.nombre+" "+value.apellido+"</span><br>");
        }
    });
    var contador = 0;
    for(var i=0;i<arryAreas.length;i++){
        if(contador==arryAreas.length-1){
            $("#areas").append("<span style='background: #4235cd;border-radius: 20px;margin-left: 15px;" +
                "padding: 10px 10px 10px 10px;color: #fff;font-weight: bolder;font-size: small'>"+arryAreas[i]+"</span>");
        }else{
            $("#areas").append("<span style='background: #4235cd;border-radius: 20px;margin-left: 15px;" +
                "padding: 10px 10px 10px 10px;color: #fff;font-weight: bolder;font-size: small'>"+arryAreas[i]+"</span><br><br>");
            contador++;
        }
    }
    var descripcion = tipo.descripcion.replace(regex, '\n');
    console.log(Object.keys(persona));
    $("#titulo").append(tipo.titulo);
    $("#tipo_proyecto").append(tipo.tipo);
    if (tipo.tipo == 'Investigación') {
        $("#tipo_proyecto").css('background', '#cd2b2b');
    }
    if(tipo.tipo=='Innovación Docente' || tipo.tipo=='Innovación docente'){
        $("#tipo_proyecto").css('background','#ddd12d');
    }
    if(tipo.tipo=='Extensión'){
        $("#tipo_proyecto").css('background','#2bcd31');
    }
    if(tipo.tipo=='Consultoría'){
        $("#tipo_proyecto").css('background','#2bcdb1');
    }
    if(tipo.tipo=='Implementación'){
        $("#tipo_proyecto").css('background','#2b64cd');
    }
    if(tipo.tipo=='Innovación'){
        $("#tipo_proyecto").css('background','#a12bcd');
    }
    if(tipo.tipo=='Propuesta Enviada'){
        $("#tipo_proyecto").css('background','#cd672b');
    }
    if(tipo.tipo=='Vinculación'){
        $("#tipo_proyecto").css('background','#f122d0');
    }
    $("#inicio").append(tipo.fechainicio);
    $("#fin").append(tipo.fechafin);
    if(tipo.descripcion=='SIN ASIGNAR'){
        $("#descripcion").append("");
    }else{
        $("#descripcion").append(descripcion);
    }
    if(tipo.objetivo=='SIN ASIGNAR'){
        $("#objetivo").append("");
    }else{
        $("#objetivo").append(tipo.objetivo);
    }
    $("#reprogramado").append(tipo.reprogramado.toUpperCase());
    $("#avance").append(tipo.avance);
    $("#estado").append(tipo.estado.toUpperCase());
    if(tipo.programa=='SIN ASIGNAR'){
        $("#programa").append("");
    }else{
        $("#programa").append(tipo.programa);
    }
//get name
    $( "#InputEmail1" ).blur(function() {
        var str = $("#InputEmail1").val();
        var ruta = rutaBase + "getNameByEmail/"+str;
        $.get(ruta,
            function (res) {
                $("#InputName").val(res);
            });
    });
    $( "#InputEmail11" ).blur(function() {
        var str = $("#InputEmail11").val();
        var ruta = rutaBase + "getNameByEmail/"+str;
        $.get(ruta,
            function (res) {
                $("#InputName1").val(res);
            });
    });
});

function inicializarFormulario(str){
    $("#idCom1").val(str);
}
