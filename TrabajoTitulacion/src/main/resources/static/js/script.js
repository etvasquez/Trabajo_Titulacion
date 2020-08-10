var selected = 'Nombre';
var mostrar = false;
$(document).ready(function(){
    $("select.form-control").change(function(){
        selected = $(this).children("option:selected").val();
        mostrar = false;
    });
});
var input = document.getElementById("formGroupExampleInput");
input.addEventListener("keyup", function(event) {
    if (event.keyCode === 13) {
        event.preventDefault();
        alert("hola")
        document.getElementById("myBtn").onclick();
    }
});
function cargarGrafo() {
    var tipo = $('#formGroupExampleInput').val();
    var urlBase = "http://localhost:8888/";
    if(selected=='Proyecto'){
        var rutaProject = urlBase + "project/"+tipo;
        $.get(rutaProject,
            function (res) {
                console.log(Object.keys(res));
                $("#json").empty();
                $("#json").append(res);
                armarGrafoProyecto();
            });
    }else if(selected=='Nombre'){
        var rutaPerson = urlBase + "person/"+tipo;
        $.get(rutaPerson,
            function (res) {
                console.log(Object.keys(res));
                $("#json").empty();
                $("#json").append(res);
                armarGrafoPersona();
            });
    }
    var heigth=$(window).height();
    $('#mynetwork').height(heigth);
    $('#mynetwork').css('border','1px solid lightgray');
}

function cargarGrafoID(idProject) {
    var urlBase = "http://localhost:8888/";
    var rutaProject = urlBase + "projectID/"+idProject;
    var data = JSON.parse(document.getElementById('json').innerHTML);
    $.get(rutaProject,
        function (res) {
            var data1 = JSON.parse(res);
            console.log(Object.keys(res));
            var node1 = data.nodes.concat(data1.nodes);
            var hash = {};
            node1 = node1.filter(function(current) {
                var exists = !hash[current.id];
                hash[current.id] = true;
                return exists;
            });
            var edges1 = data1.edges;
            var edgesunico =[];
            for (var i = 0; i < edges1.length; i ++){
                var count = 0;
                for(var j = 0; j < data.edges.length; j ++){
                    if(JSON.stringify(edges1[i])!=JSON.stringify(data.edges[j])){
                        count++;
                        if(count==data.edges.length){
                            console.log("Edges: "+JSON.stringify(edges1[i]));
                            edgesunico.push(edges1[i]);
                        }

                    }
                }

            }
            data = {
                nodes: node1,
                edges: data.edges.concat(edgesunico)
            };
            console.log(JSON.stringify(data))
            $("#json").empty();
            $("#json").append(JSON.stringify(data));
            armarGrafoPersona();
        });
}

function reducirGrafo(idProject) {
    var urlBase = "http://localhost:8888/";
    var rutaProject = urlBase + "projectID/"+idProject;
    var data = JSON.parse(document.getElementById('json').innerHTML);
    $.get(rutaProject,
        function (res) {
            var data1 = JSON.parse(res);
            var nodos =[];
            var edges =[];
            console.log(Object.keys(res));
            var nodepequenio = data1.nodes;
            var edgespequenio = data1.edges;
            for (var i = 0; i < data.nodes.length; i ++){
                var acumulador = 0;
                for(var j = 0; j < nodepequenio.length; j ++){
                    if((JSON.stringify(data.nodes[i])==JSON.stringify(nodepequenio[j]))){
                        nodos.push(data.nodes[i]);
                        break;
                    }
                    if(JSON.stringify(data.nodes[i])!=JSON.stringify(nodepequenio[j])){
                        acumulador++;
                        if(acumulador==nodepequenio.length){
                            nodos.push(data.nodes[i]);
                        }
                    }
                }
            }
            for (var i = 0; i < data.edges.length; i ++){
                var acumulador = 0;
                for(var j = 0; j < edgespequenio.length; j ++){
                    if((JSON.stringify(data.edges[i])==JSON.stringify(edgespequenio[j]))){
                        edges.push(data.edges[i]);
                        break;
                    }
                    if(JSON.stringify(data.edges[i])!=JSON.stringify(edgespequenio[j])){
                        acumulador++;
                        if(acumulador==edgespequenio.length){
                            edges.push(data.edges[i]);
                        }
                    }
                }
            }
            data = {
                nodes: nodos,
                edges: edges
            };
            console.log(JSON.stringify(data))
            $("#json").empty();
            $("#json").append(JSON.stringify(data));
            armarGrafoPersona();
        });
}

function armarGrafoPersona() {
    var options = {
        interaction: { hover: true, html:true },
        groups: {
            director: {
                shape: "circularImage",
                image: "../img/user.jpeg",
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
        },
        edges: {
            color: "#666666",
        }
    };
    var data = JSON.parse(document.getElementById('json').innerHTML)
    var container = document.getElementById('mynetwork');
    var x = -mynetwork.clientWidth / 2 + 10;
    var y = -mynetwork.clientHeight / 2 + 10;
    var totalExtension = 0;
    var totalInvestigacion = 0;
    var totalInvestigacionDocente = 0;
    var totalConsultoria = 0;
    var totalImplementacion = 0;
    var totalInnovacion = 0;
    var totalPropuesta = 0;
    var acumulador=1;
    var arrayLegend = [];
    var step = 80;
    var data = {
        nodes: data.nodes,
        edges: data.edges
    };
    data.nodes.push({
        id: 1000,
        x: x,
        y: y,
        label: "Director",
        group: "director",
        value: 1,
        fixed: true,
        physics: false,
    });
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
        }
    }
    for (var i = 0; i < data.nodes.length; i ++){
        switch (data.nodes[i].group) {
            case 'investigacion':
                if(arrayLegend.indexOf(1001)==-1){
                    data.nodes.push({
                        id: 1001,
                        x: x,
                        y: y + acumulador+step,
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
                    data.nodes.push({
                        id: 1002,
                        x: x,
                        y: y + acumulador * step,
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
                    data.nodes.push({
                        id: 1003,
                        x: x,
                        y: y + acumulador * step,
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
                    data.nodes.push({
                        id: 1004,
                        x: x,
                        y: y + acumulador * step,
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
                    data.nodes.push({
                        id: 1005,
                        x: x,
                        y: y + acumulador * step,
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
                    data.nodes.push({
                        id: 1006,
                        x: x,
                        y: y + acumulador * step,
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
                    data.nodes.push({
                        id: 1007,
                        x: x,
                        y: y + acumulador * step,
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
        }
    }
    totalProyectos = totalExtension+totalPropuesta+totalInnovacion+totalImplementacion+totalConsultoria+totalInvestigacionDocente+totalInvestigacion;
    data.nodes.push({
        id: 1008,
        x: x,
        y: y + acumulador * step,
        label: "Total proyectos ("+totalProyectos+")",
        group: "total",
        value: 1,
        fixed: true,
        physics: false,
    });
    var network = new vis.Network(container, data, options);
    network.on("click", function(params) {
        console.log("bandera"+mostrar);
        if(!mostrar) {
            console.log("si llega mostrar");
            cargarGrafoID(String(this.getNodeAt(params.pointer.DOM)));
            mostrar=true;
        }else{
            console.log("si llega reducir");
            reducirGrafo(String(this.getNodeAt(params.pointer.DOM)));
            mostrar=false;
        }
    });
}

function armarGrafoProyecto() {
    var options = {
        interaction: { hover: true, html:true },
        groups: {
            participante: {
                shape: "icon",
                icon: {
                    face: "'Font Awesome 5 Free'",
                    weight: "bold", // Font Awesome 5 doesn't work properly unless bold.
                    code: "\uf007",
                    size: 50,
                    color: "#40AACD"
                }
            },
            director: {
                shape: "icon",
                icon: {
                    face: "'Font Awesome 5 Free'",
                    weight: "bold", // Font Awesome 5 doesn't work properly unless bold.
                    code: "\uf007",
                    size: 50,
                    color: "#5EE312"
                }
            },
            projects:{
                shape: "icon",
                icon: {
                    face: "'Font Awesome 5 Free'",
                    weight: "bold", // Font Awesome 5 doesn't work properly unless bold.
                    code: "\uf15c",
                    size: 50,
                    color: "#f0a30a"
                }
            }
        }
    };
    var data = JSON.parse(document.getElementById('json').innerHTML)
    var container = document.getElementById('mynetwork');
    var data = {
        nodes: data.nodes,
        edges: data.edges
    };
    var network = new vis.Network(container, data, options);
    network.on("click", function(params) {
        alert(
            "ID: " + this.getNodeAt(params.pointer.DOM)
        );
    });
}
