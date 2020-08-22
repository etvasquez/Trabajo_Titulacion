var selected = 'Nombre';
var listaNodoAbierto = []; //Almacenar nodos abierto para cerrar conforme seleccione
var idnodobase = ''; //nodo principal del grafo
var rutaBase='http://localhost:8888/'; //ruta base para consultas
var idseleccionado; //nodo que da click
var grafoinicial; //primer resultado del grafo
$(document).ready(function(){
    //Inicializacion de lista para busqueda
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
                    ruta = rutaBase+"listaBusquedaPersona/"+ busqueda;
                }else{
                    ruta = rutaBase+"listaBusquedaProyecto/"+ busqueda;
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
    //Metodo de Busqueda
    $("select.form-control").change(function(){
        selected = $(this).children("option:selected").val();
        $('#listaBusqueda').empty();
        listaNodoAbierto = [];
        idseleccionado = 0;
        grafoinicial = '';
    });
    //Metodo de busqueda al seleccioanr un elemento de la lista
    $("select.js-example-basic-single").change(function(){
        idnodobase = $('#listaBusqueda').val();
        idseleccionado = idnodobase;
        if(selected=='Proyecto'){
            var rutaProject = rutaBase + "project/"+idnodobase;
            $.get(rutaProject,
                function (res) {
                    actualizarJSONHTML(res,res);
                    armarGrafo();
                    grafoinicial = res;
                });
        }else if(selected=='Nombre'){
            var rutaPerson = rutaBase + "person/"+idnodobase;
            $.get(rutaPerson,
                function (res) {
                    actualizarJSONHTML(res,res);
                    armarGrafo();
                    grafoinicial = res;
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
function actualizarJSONHTML(stringJSON1, stringJSON) {
    $("#json1").empty(); //JSON 1 PARA GRAFICAR, NO TIENE NODOS NI EDGES REPETIDOS
    $("#json1").append(stringJSON1);
    $("#json").empty(); // JSON PARA AGRANDAR Y REDUCIR GRAFO
    $("#json").append(stringJSON);
}
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
    var rutaProject = rutaBase + "projectID/"+idProject;
    var dataRepetidos = JSON.parse(document.getElementById('json').innerHTML);
    $.get(rutaProject,
        function (res) {
            var data = JSON.parse(res);
            if(data.edges.length>1){
                dataRepetidos = {
                    nodes : dataRepetidos.nodes.concat(data.nodes),
                    edges : dataRepetidos.edges.concat(data.edges)
                };
                var datapresentar = {
                    nodes: eliminarNodosRepetidos(dataRepetidos),
                    edges: eliminarEdgesRepetidos(dataRepetidos)
                };
                actualizarJSONHTML(JSON.stringify(datapresentar),JSON.stringify(dataRepetidos));
                armarGrafo();
            }else{
                console.log("No contiene participantes");
            }
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
function reducirGrafo(idProject) {
    var rutaProject = rutaBase + "projectID/"+idProject;
    var dataRepetidos = JSON.parse(document.getElementById('json').innerHTML);
    $.get(rutaProject,
        function (res) {
            var data = JSON.parse(res);
            if(data.edges.length>1){
                var datares = {
                    nodes: EliminarNodos(data, dataRepetidos),
                    edges: EliminarEdges(data, dataRepetidos)
                };
                var datapresentar = {
                    nodes: eliminarNodosRepetidos(datares),
                    edges: eliminarEdgesRepetidos(datares)
                };
                actualizarJSONHTML(JSON.stringify(datapresentar),JSON.stringify(datares));
                armarGrafo();
            }else{
                console.log('No contiene participantes');
            }
        });
}
function armarGrafo() {
    var options = {
        interaction: { hover: true},
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
            buscado:{
                shape: "circularImage",
                image: "../img/usersearch.png",
                color: {
                    border: "lightgray",
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
    for (var i = 0; i < data.nodes.length; i ++){
        var yacumulador = y + acumulador+step;
        if(i!=0){
            yacumulador = y + acumulador*step;
        }
        switch (data.nodes[i].group) {
            case 'buscado':
                if(arrayLegend.indexOf(1011)==-1){
                    dataleyenda.push({
                        id: 1011,
                        x: x,
                        y: yacumulador,
                        label: "Persona Buscada",
                        group: "buscado",
                        value: 1,
                        fixed: true,
                        physics: false,
                    });
                    arrayLegend.push(1011);
                    acumulador++;
                }
                break;
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
    var doubleClickTime = 0;
    var threshold = 200;
    network.on("click", function(params) {
        var t0 = new Date();
        var identificador = this.getNodeAt(params.pointer.DOM);
        idseleccionado = this.getNodeAt(params.pointer.DOM);
        if (t0 - doubleClickTime > threshold) {
            setTimeout(function () {
                if (t0 - doubleClickTime > threshold) {
                    var arrayEdges = network.getConnectedNodes(identificador,'from');
                    var idNodo = identificador;
                    if(idNodo!=idnodobase){
                        if(arrayEdges!=undefined && arrayEdges!=null && arrayEdges!='') {
                            if (listaNodoAbierto.indexOf(idNodo) == -1) {
                                agrandarGrafo(idNodo);
                                listaNodoAbierto.push(idNodo);
                            } else {
                                reducirGrafo(idNodo);
                                var index = listaNodoAbierto.indexOf(idNodo);
                                listaNodoAbierto.splice(index, 1);
                            }
                        }
                    }else{
                        actualizarJSONHTML(grafoinicial,grafoinicial);
                        armarGrafo();
                    }
                }
            },threshold);
        }
    });
    network.on('doubleClick', function(params) {
        doubleClickTime = new Date();
        var id = this.getNodeAt(params.pointer.DOM);
        window.location.href="/resultados/"+id;
    });
    network.on("afterDrawing", function (ctx) {
        try{
            var nodeId = idseleccionado;
            var nodePosition = network.getPositions([nodeId]);
            ctx.strokeStyle = '#000000';
            ctx.lineWidth = 2;
            ctx.fillStyle = "rgba(0,0,0,0)";
            ctx.circle(nodePosition[nodeId].x, nodePosition[nodeId].y,28);
            ctx.fill();
            ctx.stroke();
        }catch (e) {

        }
    });
}

