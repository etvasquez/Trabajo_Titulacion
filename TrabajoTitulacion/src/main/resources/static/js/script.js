var selected = 'Nombre';
var listaNodoAbierto = [];
var tipo = '';
$(document).ready(function(){
    $('.js-example-basic-single').select2({
        width:'100%',
        minimumInputLength:3,
        allowClear:true,
        placeholder:"Seleccione una opción ...",
        ajax: {
            url: function (params){
                var busqueda = params.term.toUpperCase();
                busqueda = busqueda.replace('Á', 'A');
                busqueda = busqueda.replace('É', 'E');
                busqueda = busqueda.replace('Í', 'I');
                busqueda = busqueda.replace('Ó', 'O');
                busqueda = busqueda.replace('Ú', 'U');
                if(selected=="Nombre"){
                    ruta = "http://localhost:8888/listaBusquedaPersona/"+ busqueda;
                }else{
                    ruta = "http://localhost:8888/listaBusquedaProyecto/"+ busqueda;
                }
                return ruta;
            },
            dataType: "json",
            type: "GET",
            processResults: function (data) {
                return {
                    results: $.map(data, function (item) {
                       return {
                            text: item.nombre,
                            id: item.id
                        }
                    })
                };
            }
        }
    });
    $("select.form-control").change(function(){
        selected = $(this).children("option:selected").val();
        $('#listaBusqueda').empty();
        listaNodoAbierto = [];
    });
    $("select.js-example-basic-single").change(function(){
        tipo = $('#listaBusqueda').val();
        var urlBase = "http://localhost:8888/";
        if(selected=='Proyecto'){
            var rutaProject = urlBase + "project/"+tipo;
            $.get(rutaProject,
                function (res) {
                    $("#json1").empty();
                    $("#json1").append(res);
                    $("#json").empty();
                    $("#json").append(res);
                    armarGrafo();
                    //armarGrafoProyecto();
                });
        }else if(selected=='Nombre'){
            var rutaPerson = urlBase + "person/"+tipo;
            $.get(rutaPerson,
                function (res) {
                    $("#json1").empty();
                    $("#json1").append(res);
                    $("#json").empty();
                    $("#json").append(res);
                    armarGrafo();
                });
        }
        var heigth=$(window).height();
        $('#mynetwork').height(heigth);
        $('#mynetwork').css('border','1px solid lightgray');
        var heigth1=$(window).height();
        $('#myleyenda').height(heigth1);
        $('#myleyenda').css('border','1px solid lightgray');
    });
});

function eliminarNodosRepetidos(arrayNodos) {
    var hash = {};
    var nodossinRepetir = arrayNodos.nodes.filter(function(current) {
        var exists = !hash[current.id];
        hash[current.id] = true;
        return exists;
    });
    return nodossinRepetir;
}
function eliminarEdgesRepetidos(arrayEdges) {
    var edgessinRepetir = [];
    for(var j = 0; j < arrayEdges.edges.length; j ++){
        var contador = 0;
        if(j==0){
            edgessinRepetir.push(arrayEdges.edges[j]);
        }
        for(var k = 0 ; k<edgessinRepetir.length;k++){
            if(JSON.stringify(arrayEdges.edges[j])!=JSON.stringify(edgessinRepetir[k])){
                contador++;
                if(contador==edgessinRepetir.length){
                    edgessinRepetir.push(arrayEdges.edges[j]);
                }
            }
        }
    }
    return edgessinRepetir;
}
function agrandarGrafo(idProject) {
    var urlBase = "http://localhost:8888/";
    var rutaProject = urlBase + "projectID/"+idProject;
    var dataRepetidos = JSON.parse(document.getElementById('json').innerHTML);
    $.get(rutaProject,
        function (res) {
            var data = JSON.parse(res);
            console.log(Object.keys(res));
            dataRepetidos = {
                nodes : dataRepetidos.nodes.concat(data.nodes),
                edges : dataRepetidos.edges.concat(data.edges)
            }
            $("#json").empty();
            $("#json").append(JSON.stringify(dataRepetidos));
            var datapresentar = {
                nodes: eliminarNodosRepetidos(dataRepetidos),
                edges: eliminarEdgesRepetidos(dataRepetidos)
            };
            console.log(JSON.stringify(datapresentar))
            $("#json1").empty();
            $("#json1").append(JSON.stringify(datapresentar));
            armarGrafo();
        });
}
function EliminarNodos(data, dataRepetidos) {
    var nodosEliminados =[];
    var nodos = [];
    for (var i = 0; i < dataRepetidos.nodes.length; i ++){
        var acumulador = 0;
        for(var j = 0; j < data.nodes.length; j ++){
            if(JSON.stringify(dataRepetidos.nodes[i])!=JSON.stringify(data.nodes[j])){
                acumulador++;
                if(acumulador==data.nodes.length){
                    nodos.push(dataRepetidos.nodes[i]);
                }
            }else{
                if(nodosEliminados.length==0){
                    nodosEliminados.push(dataRepetidos.nodes[i]);
                    break;
                }
                var acumulador2 = 0;
                for(var k = 0; k<nodosEliminados.length; k++){
                    if(JSON.stringify(dataRepetidos.nodes[i])!=JSON.stringify(nodosEliminados[k])){
                        acumulador2++;
                    }
                }
                if(acumulador2==nodosEliminados.length){
                    nodosEliminados.push(dataRepetidos.nodes[i]);
                }else {
                    nodos.push(dataRepetidos.nodes[i]);
                }
            }
        }
    }
    return nodos;
}
function EliminarEdges(data, dataRepetidos) {
    var edgesEliminados =[];
    var edges = [];
    for (var i = 0; i < dataRepetidos.edges.length; i ++){
        var acumulador = 0;
        for(var j = 0; j < data.edges.length; j ++){
            if(JSON.stringify(dataRepetidos.edges[i])!=JSON.stringify(data.edges[j])){
                acumulador++;
                if(acumulador==data.edges.length){
                    edges.push(dataRepetidos.edges[i]);
                }
            }else{
                if(edgesEliminados.length==0){
                    edgesEliminados.push(dataRepetidos.edges[i]);
                    break;
                }
                var acumulador2 = 0;
                for(var k = 0; k<edgesEliminados.length; k++){
                    if(JSON.stringify(dataRepetidos.edges[i])!=JSON.stringify(edgesEliminados[k])){
                        acumulador2++;
                    }
                }
                if(acumulador2==edgesEliminados.length){
                    edgesEliminados.push(dataRepetidos.edges[i]);
                }else {
                    edges.push(dataRepetidos.edges[i]);
                }
            }
        }
    }
    return edges;
}
var respuesta = "";
function getidPerson(idperson) {
    var urlBase = "http://localhost:8888/";
    var rutaProject = urlBase + "personID/"+idperson;
    $.get(rutaProject,
        function (res) {
            respuesta = res;
        });
    return respuesta;
}
function reducirGrafo(idProject) {
    var urlBase = "http://localhost:8888/";
    var rutaProject = urlBase + "projectID/"+idProject;
    var dataRepetidos = JSON.parse(document.getElementById('json').innerHTML);
    $.get(rutaProject,
        function (res) {
            var data = JSON.parse(res);
            var datares = {
                nodes: EliminarNodos(data, dataRepetidos),
                edges: EliminarEdges(data, dataRepetidos)
            }
            $("#json").empty();
            $("#json").append(JSON.stringify(datares));
            var datapresentar = {
                nodes: eliminarNodosRepetidos(datares),
                edges: eliminarEdgesRepetidos(datares)
            };
            console.log(JSON.stringify(datares))
            console.log(JSON.stringify(datapresentar))
            $("#json1").empty();
            $("#json1").append(JSON.stringify(datapresentar));
            armarGrafo();
        });
}
function armarGrafo() {
    var options = {
        interaction: { hover: true, html:true },
        groups: {
            participante: {
                shape: "circularImage",
                image: "../img/participante.png",
                color: {
                    border: "lightgray",
                    background: "#FFFFFF",
                }
            },
            director: {
                shape: "circularImage",
                image: "../img/user.png",
                borderWidth: 4,
                color: {
                    border: "lightgray",
                    background: "#FFFFFF",
                }
            },
            investigacion:{
                shape: "circularImage",
                image: "../img/file1.png",
                color: {
                    border: "#FFFFFF",
                    background: "#FFFFFF",
                }
            },
            investigacion_docente:{
                shape: "circularImage",
                image: "../img/file2.png",
                color: {
                    border: "#FFFFFF",
                    background: "#FFFFFF",
                }
            },
            extension:{
                shape: "circularImage",
                image: "../img/file3.png",
                color: {
                    border: "#FFFFFF",
                    background: "#FFFFFF",
                }
            },
            consultoria:{
                shape: "circularImage",
                image: "../img/file4.png",
                color: {
                    border: "#FFFFFF",
                    background: "#FFFFFF",
                }
            },
            implementacion:{
                shape: "circularImage",
                image: "../img/file5.png",
                color: {
                    border: "#FFFFFF",
                    background: "#FFFFFF",
                }
            },
            innovacion:{
                shape: "circularImage",
                image: "../img/file6.png",
                color: {
                    border: "#FFFFFF",
                    background: "#FFFFFF",
                }
            },
            propuesta:{
                shape: "circularImage",
                image: "../img/file7.png",
                color: {
                    border: "#FFFFFF",
                    background: "#FFFFFF",
                }
            },
            total:{
                shape: "circularImage",
                image: "../img/suma.png",
                color: {
                    border: "#FFFFFF",
                    background: "#FFFFFF",
                }
            }
        }
    };
    var data = JSON.parse(document.getElementById('json1').innerHTML);
    console.log(JSON.stringify(data));
    var container = document.getElementById('mynetwork');
    var leyenda = document.getElementById('myleyenda');
    var x = -leyenda.clientWidth/4 + 50;
    var y = -leyenda.clientHeight/2 + 50;
    var totalExtension = 0;
    var totalInvestigacion = 0;
    var totalInvestigacionDocente = 0;
    var totalConsultoria = 0;
    var totalImplementacion = 0;
    var totalInnovacion = 0;
    var totalPropuesta = 0;
    var totalDirector = 0;
    var totalParticipante = 0;
    var acumulador=1;
    var arrayLegend = [];
    var step = 80;
    var data = {
        nodes: data.nodes,
        edges: data.edges
    };
    var dataleyenda =  [];
    for(var j = 0; j < data.nodes.length; j ++){
        switch (data.nodes[j].group) {
            case 'investigacion':
                totalInvestigacion++;
                break;
            case 'extension':
                totalExtension++;
                break;
            case 'investigacion_docente':
                totalInvestigacionDocente++;
                break;
            case 'consultoria':
                totalConsultoria++;
                break;
            case 'implementacion':
                totalImplementacion++;
                break;
            case 'innovacion':
                totalInnovacion++;
                break;
            case 'propuesta':
                totalPropuesta++;
                break;
            case 'director':
                totalDirector++;
                break;
            case 'participante':
                totalParticipante++;
                break;
        }
    }
    var svg =
        '<svg xmlns="http://www.w3.org/2000/svg" width="390" height="65">' +
        '<rect x="0" y="0" width="100%" height="100%" fill="#7890A7" stroke-width="20" stroke="#ffffff" ></rect>' +
        '<foreignObject x="15" y="10" width="100%" height="100%">' +
        '<div xmlns="http://www.w3.org/1999/xhtml" style="font-size:40px">' +
        " <em>I</em> am" +
        '<span style="color:white; text-shadow:0 0 20px #000000;">' +
        " HTML in SVG!</span>" +
        "</div>" +
        "</foreignObject>" +
        "</svg>";
    var url = "data:image/svg+xml;charset=utf-8," + encodeURIComponent(svg);
    //data.nodes.push({ id: 1, label: "Hola\nBla", image: url, shape: "image" });
    for (var i = 0; i < data.nodes.length; i ++){
        var yacumulador = y + acumulador+step;
        if(i!=0){
            yacumulador = y + acumulador*step;
        }
        switch (data.nodes[i].group) {
            case 'investigacion':
                if(arrayLegend.indexOf(1001)==-1){
                    dataleyenda.push({
                        id: 1001,
                        x: x,
                        y: yacumulador,
                        label: "Investigación (" +totalInvestigacion+")",
                        group: "investigacion",
                        value: 1,
                        fixed: true,
                        physics: false,
                    });
                    arrayLegend.push(1001);
                    acumulador++;
                }
                break;
            case 'extension':
                if(arrayLegend.indexOf(1002)==-1){
                    dataleyenda.push({
                        id: 1002,
                        x: x,
                        y: yacumulador,
                        label: "Extensión ("+totalExtension+")",
                        group: "extension",
                        value: 1,
                        fixed: true,
                        physics: false,
                    });
                    arrayLegend.push(1002);
                    acumulador++;
                }
                break;
            case 'investigacion_docente':
                if(arrayLegend.indexOf(1003)==-1){
                    dataleyenda.push({
                        id: 1003,
                        x: x,
                        y: yacumulador,
                        label: "Investigación Docente ("+totalInvestigacionDocente+")",
                        group: "investigacion_docente",
                        value: 1,
                        fixed: true,
                        physics: false,
                    });
                    arrayLegend.push(1003);
                    acumulador++;
                }
                break;
            case 'consultoria':
                if(arrayLegend.indexOf(1004)==-1){
                    dataleyenda.push({
                        id: 1004,
                        x: x,
                        y: yacumulador,
                        label: "Consultoría ("+totalConsultoria+")",
                        group: "consultoria",
                        value: 1,
                        fixed: true,
                        physics: false,
                    });
                    arrayLegend.push(1004);
                    acumulador++;
                }
                break;
            case 'implementacion':
                if(arrayLegend.indexOf(1005)==-1){
                    dataleyenda.push({
                        id: 1005,
                        x: x,
                        y: yacumulador,
                        label: "Implementación ("+totalImplementacion+")",
                        group: "implementacion",
                        value: 1,
                        fixed: true,
                        physics: false,
                    });
                    arrayLegend.push(1005);
                    acumulador++;
                }
                break;
            case 'innovacion':
                if(arrayLegend.indexOf(1006)==-1){
                    dataleyenda.push({
                        id: 1006,
                        x: x,
                        y: yacumulador,
                        label: "Innovación ("+totalInnovacion+")",
                        group: "innovacion",
                        value: 1,
                        fixed: true,
                        physics: false,
                    });
                    arrayLegend.push(1006);
                    acumulador++;
                }
                break;
            case 'propuesta':
                if(arrayLegend.indexOf(1007)==-1){
                    dataleyenda.push({
                        id: 1007,
                        x: x,
                        y: yacumulador,
                        label: "Propuesta Enviada ("+totalPropuesta+")",
                        group: "propuesta",
                        value: 1,
                        fixed: true,
                        physics: false,
                    });
                    arrayLegend.push(1007);
                    acumulador++;
                }
                break;
            case 'participante':
                if(arrayLegend.indexOf(1008)==-1){
                    if(selected=="Nombre"){
                        dataleyenda.push({
                            id: 1008,
                            x: x,
                            y: yacumulador,
                            label: "Participante",
                            group: "participante",
                            value: 1,
                            fixed: true,
                            physics: false,
                        });
                        arrayLegend.push(1008);
                        acumulador++;
                    }else{
                        dataleyenda.push({
                            id: 1008,
                            x: x,
                            y: yacumulador,
                            label: "Participante ("+totalParticipante+")",
                            group: "participante",
                            value: 1,
                            fixed: true,
                            physics: false,
                        });
                        arrayLegend.push(1008);
                        acumulador++;
                    }

                }
                break;
            case 'director':
                if(arrayLegend.indexOf(1009)==-1){
                    dataleyenda.push({
                        id: 1009,
                        x: x,
                        y: y + acumulador * step,
                        label: "Director ("+totalDirector+")",
                        group: "director",
                        value: 1,
                        fixed: true,
                        physics: false,
                    });
                    arrayLegend.push(1009);
                    acumulador++;
                }
                break;
        }
    }
    totalProyectos = totalExtension+totalPropuesta+totalInnovacion+totalImplementacion+totalConsultoria+
        totalInvestigacionDocente+totalInvestigacion;
    if(selected=="Nombre"){
        dataleyenda.push({
            id: 1010,
            x: x,
            y: y + acumulador * step,
            label: "Total proyectos ("+totalProyectos+")",
            group: "total",
            value: 1,
            fixed: true,
            physics: false,
        });
    }
    var leyendaData = {
        nodes:dataleyenda
    }
    var network = new vis.Network(container, data, options);
    var networkleyenda = new vis.Network(leyenda, leyendaData, options);
    network.on("click", function(params) {
        var idNodo = this.getNodeAt(params.pointer.DOM);
        var idperson = getidPerson(tipo);
        if(idNodo!=idperson){
            if(listaNodoAbierto.indexOf(idNodo)==-1){
                agrandarGrafo(idNodo);
                listaNodoAbierto.push(idNodo);
            }else{
                reducirGrafo(idNodo);
                var index = listaNodoAbierto.indexOf(idNodo);
                listaNodoAbierto.splice(index, 1);
            }
        }
    });
}

