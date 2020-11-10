var selected = 'Nombre';
var listaNodoAbierto = []; //Almacenar nodos abierto para cerrar conforme seleccione
var idnodobase = ''; //nodo principal del grafo
var rutaBase = 'http://localhost:8888/'; //ruta base para consultas
var idseleccionado; //nodo que da click
var grafoinicial; //primer resultado del grafo
var titulo;
var ruta;
var nodoseleccionado;
var foto="";
$(document).ready(function () {
    try {
        inicializarSelectPersona();
        //Inicializando variables
        listaNodoAbierto = [];
        idseleccionado = 0;
        grafoinicial = '';
        ruta = '';
        //Inicializar lista tipo
        $('#listatipo').select2({
            width: '100%',
            minimumResultsForSearch: -1,
        });
        //Metodo de Busqueda
        $("#listatipo").change(function () {
            selected = $('#listatipo').val();
            if (selected == "Nombre") {
                $("#descripcion").html("El tipo de búsqueda <b>Persona</b> permite visualizar los proyectos en los que ha intervenido" +
                    " un usuario. Si da un <b>click</b> sobre el proyecto conocerá los participantes. Si da <b>dobleclick</b> sobre el proyecto accederá a " +
                    "la información del mismo.");
                inicializarSelectPersona();
            } else if (selected == "Persona") {
                $("#descripcion").html("El tipo de búsqueda por <b>Redes</b> le permite visualizar las redes de personas con las que ha participado" +
                    " un usuario. La línea resaltada señala con quién ha mantenido mayor participación. ");
                inicializarSelectPersona();
            } else if (selected == "Proyecto") {
                $("#descripcion").html("El tipo de búsqueda por <b>Proyecto</b> visualiza las personas que han intervenido en un proyecto, " +
                    "al dar <b>click</b> sobre una persona se extienden los proyectos en los que ha participado. Y al dar <b>doble click</b>" +
                    " se presenta la información específica del proyecto seleccionado.");
                inicializarSelectProyecto();
            } else if (selected == "Area") {
                $("#descripcion").html("El tipo de búsqueda <b>Área </b>  le presenta todos los proyectos correspondientes al área buscada. " +
                    "Si da un <b>click</b> sobre el proyecto conocerá los participantes. Si da <b>click</b> sobre el proyecto accederá a " +
                    "la información del mismo. ");
                inicializarSelectArea();
            } else if (selected == "TipoProyecto") {
                $("#descripcion").html("El tipo de búsqueda <b>Tipo de proyecto </b> le presenta todos los proyectos correspondientes al tipo buscado." +
                    " Si da un <b>click</b> sobre el proyecto conocerá los participantes. Si da <b>click</b> sobre el proyecto accederá a " +
                    "la información del mismo.");
                inicializarSelectTipo();
            }
            titulo = $('#listatipo').select2('data')[0]['text'];
            if (titulo == "Proyecto" || titulo == "Área" || titulo == "Tipo de proyecto") {
                $('#titulobusqueda').text(titulo);
            } else {
                titulo = "Persona";
                $('#titulobusqueda').text(titulo);
            }
            $('#listaBusqueda').empty();
        });
        //Metodo de busqueda al seleccioanr un elemento de la lista
        $("#listaBusqueda").change(function () {
            if ($('#listaBusqueda').val() != null) {
                idnodobase = $('#listaBusqueda').val();
                if (selected == 'Proyecto') {
                    var rutaProject = rutaBase + "project/" + idnodobase;
                    $.get(rutaProject,
                        function (res) {
                            actualizarJSONHTML(res, res);
                            armarGrafo();
                            grafoinicial = res;
                        });
                } else if (selected == 'Nombre') {
                    var rutaPerson = rutaBase + "person/" + idnodobase;
                    $.get(rutaPerson,
                        function (res) {
                            actualizarJSONHTML(res, res);
                            armarGrafo();
                            grafoinicial = res;
                        });
                } else if (selected == 'Persona') {
                    var rutaPerson = rutaBase + "personperson/" + idnodobase;
                    $.get(rutaPerson,
                        function (res) {
                            actualizarJSONHTML(res, res);
                            armarGrafo();
                            grafoinicial = res;
                        });
                } else if (selected == 'Area') {
                    var rutaPerson = rutaBase + "projectArea/" + idnodobase;
                    $.get(rutaPerson,
                        function (res) {
                            actualizarJSONHTML(res, res);
                            armarGrafo();
                            grafoinicial = res;
                        });
                } else if (selected == 'TipoProyecto') {
                    var rutaPerson = rutaBase + "projectTipo/" + idnodobase;
                    $.get(rutaPerson,
                        function (res) {
                            actualizarJSONHTML(res, res);
                            armarGrafo();
                            grafoinicial = res;
                        });
                }
                var heigth = $(window).height();
                $('#mynetwork').height(heigth);
                $('#mynetwork').css('border', '1px solid lightgray');
                var heigth1 = $(window).height();
                $('#myleyenda').height(heigth1);
                $('#myleyenda').css('border', '1px solid lightgray');
                $('#card').css('display', 'block');
            }
        });
    } catch (e) {

    }
});

function inicializarSelectTipo() {
    var rutaProject = rutaBase + "listaBusquedaTipoProyecto";
    $('#listaBusqueda').select2({
        width: '100%',
        minimumInputLength: 0,
        allowClear: true,
        placeholder: "Escriba o seleccione el nombre del tipo de proyecto ...",
    });
    $.get(rutaProject,
        function (res) {
            var data = JSON.parse(res);
            for (var i = 0; i < data.length; i++) {
                var newOption = new Option(data[i].nombre, data[i].id, false, false);
                $('#listaBusqueda').append(newOption);
            }
            $('#listaBusqueda').val(null).trigger('change');
        });
}

function inicializarSelectArea() {
    var rutaProject = rutaBase + "listaBusquedaArea";
    $('#listaBusqueda').select2({
        width: '100%',
        minimumInputLength: 0,
        allowClear: true,
        placeholder: "Escriba o seleccione el nombre del área ...",
    });
    $.get(rutaProject,
        function (res) {
            var data = JSON.parse(res);
            for (var i = 0; i < data.length; i++) {
                var newOption = new Option(data[i].nombre, data[i].id, false, false);
                $('#listaBusqueda').append(newOption);
            }
            $('#listaBusqueda').val(null).trigger('change');
        });
}

function inicializarSelectProyecto() {
    $('#listaBusqueda').select2({
        width: '100%',
        minimumInputLength: 3,
        language: {
            inputTooShort: function () {
                return 'Porfavor, ingrese 3 o más caracteres';
            }
        },
        allowClear: true,
        placeholder: "Escriba el nombre del proyecto ...",
        ajax: {
            url: function (params) {
                var busqueda = params.term.toUpperCase();
                busqueda = busqueda.replace('Á', 'A');
                busqueda = busqueda.replace('É', 'E');
                busqueda = busqueda.replace('Í', 'I');
                busqueda = busqueda.replace('Ó', 'O');
                busqueda = busqueda.replace('Ú', 'U');
                ruta = rutaBase + "listaBusquedaProyecto/" + busqueda;
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
}

function inicializarSelectPersona() {
    $('#listaBusqueda').select2({
        width: '100%',
        minimumInputLength: 3,
        language: {
            inputTooShort: function () {
                return 'Porfavor, ingrese 3 o más caracteres';
            }
        },
        allowClear: true,
        placeholder: "Escriba el nombre de la persona ...",
        ajax: {
            url: function (params) {
                var busqueda = params.term.toUpperCase();
                busqueda = busqueda.replace('Á', 'A');
                busqueda = busqueda.replace('É', 'E');
                busqueda = busqueda.replace('Í', 'I');
                busqueda = busqueda.replace('Ó', 'O');
                busqueda = busqueda.replace('Ú', 'U');
                ruta = rutaBase + "listaBusquedaPersona/" + busqueda;
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
}

function actualizarJSONHTML(stringJSON1, stringJSON) {
    $("#json1").empty(); //JSON 1 PARA GRAFICAR, NO TIENE NODOS NI EDGES REPETIDOS
    $("#json1").append(stringJSON1);
    $("#json").empty(); // JSON PARA AGRANDAR Y REDUCIR GRAFO
    $("#json").append(stringJSON);
}

function eliminarNodosRepetidos(arrayNodos) {
    var hash = {};
    var nodossinRepetir = arrayNodos.nodes.filter(function (current) {
        var exists = !hash[current.id];
        hash[current.id] = true;
        return exists;
    });
    return nodossinRepetir;
}

function eliminarEdgesRepetidos(arrayEdges) {
    var edgessinRepetir = [];
    for (var j = 0; j < arrayEdges.edges.length; j++) {
        var contador = 0;
        if (j == 0) {
            edgessinRepetir.push(arrayEdges.edges[j]);
        }
        for (var k = 0; k < edgessinRepetir.length; k++) {
            if (JSON.stringify(arrayEdges.edges[j]) != JSON.stringify(edgessinRepetir[k])) {
                contador++;
                if (contador == edgessinRepetir.length) {
                    edgessinRepetir.push(arrayEdges.edges[j]);
                }
            }
        }
    }
    return edgessinRepetir;
}

function agrandarGrafo(idProject) {
    var rutaProject = rutaBase + "projectID/" + idProject;
    if (selected == "Proyecto") {
        rutaProject = rutaBase + "person/" + idProject;
    }
    var dataRepetidos = JSON.parse(document.getElementById('json').innerHTML);
    $.get(rutaProject,
        function (res) {
            var data = JSON.parse(res);
            if (data.edges.length > 1) {
                dataRepetidos = {
                    nodes: dataRepetidos.nodes.concat(data.nodes),
                    edges: dataRepetidos.edges.concat(data.edges)
                };
                var datapresentar = {
                    nodes: eliminarNodosRepetidos(dataRepetidos),
                    edges: eliminarEdgesRepetidos(dataRepetidos)
                };
                actualizarJSONHTML(JSON.stringify(datapresentar), JSON.stringify(dataRepetidos));
                armarGrafo();
            } else {
                alert("Proyecto seleccionado no contiene participantes");
            }
        });
}

function EliminarNodos(data, dataRepetidos) {
    var nodosEliminados = [];
    var nodos = [];
    for (var i = 0; i < dataRepetidos.nodes.length; i++) {
        var acumulador = 0;
        for (var j = 0; j < data.nodes.length; j++) {
            if (JSON.stringify(dataRepetidos.nodes[i]) != JSON.stringify(data.nodes[j])) {
                acumulador++;
                if (acumulador == data.nodes.length) {
                    nodos.push(dataRepetidos.nodes[i]);
                }
            } else {
                if (nodosEliminados.length == 0) {
                    nodosEliminados.push(dataRepetidos.nodes[i]);
                    break;
                }
                var acumulador2 = 0;
                for (var k = 0; k < nodosEliminados.length; k++) {
                    if (JSON.stringify(dataRepetidos.nodes[i]) != JSON.stringify(nodosEliminados[k])) {
                        acumulador2++;
                    }
                }
                if (acumulador2 == nodosEliminados.length) {
                    nodosEliminados.push(dataRepetidos.nodes[i]);
                } else {
                    nodos.push(dataRepetidos.nodes[i]);
                }
            }
        }
    }
    return nodos;
}

function EliminarEdges(data, dataRepetidos) {
    var edgesEliminados = [];
    var edges = [];
    for (var i = 0; i < dataRepetidos.edges.length; i++) {
        var acumulador = 0;
        for (var j = 0; j < data.edges.length; j++) {
            if (JSON.stringify(dataRepetidos.edges[i]) != JSON.stringify(data.edges[j])) {
                acumulador++;
                if (acumulador == data.edges.length) {
                    edges.push(dataRepetidos.edges[i]);
                }
            } else {
                if (edgesEliminados.length == 0) {
                    edgesEliminados.push(dataRepetidos.edges[i]);
                    break;
                }
                var acumulador2 = 0;
                for (var k = 0; k < edgesEliminados.length; k++) {
                    if (JSON.stringify(dataRepetidos.edges[i]) != JSON.stringify(edgesEliminados[k])) {
                        acumulador2++;
                    }
                }
                if (acumulador2 == edgesEliminados.length) {
                    edgesEliminados.push(dataRepetidos.edges[i]);
                } else {
                    edges.push(dataRepetidos.edges[i]);
                }
            }
        }
    }
    return edges;
}

function reducirGrafo(idNodo) {
    var ruta = rutaBase + "projectID/" + idNodo;
    if (selected == "Proyecto") {
        ruta = rutaBase + "person/" + idNodo;
    }
    var dataRepetidos = JSON.parse(document.getElementById('json').innerHTML);
    $.get(ruta,
        function (res) {
            var data = JSON.parse(res);
            if (data.edges.length > 1) {
                var datares = {
                    nodes: EliminarNodos(data, dataRepetidos),
                    edges: EliminarEdges(data, dataRepetidos)
                };
                var datapresentar = {
                    nodes: eliminarNodosRepetidos(datares),
                    edges: eliminarEdgesRepetidos(datares)
                };
                actualizarJSONHTML(JSON.stringify(datapresentar), JSON.stringify(datares));
                armarGrafo();
            } else {
                console.log('No contiene participantes');
            }
        });
}

function armarGrafo() {
    var physics = {
        forceAtlas2Based: {
            gravitationalConstant: -80,
        },
        solver: 'forceAtlas2Based',
        stabilization: {
            enabled: true,
            iterations: 1000,
            updateInterval: 25
        }
    };
    var options = {
        physics: physics,
        interaction: {
            hover: true
        },
        groups: {
            participante: {
                color: {
                    border: "#4D4D4D",
                    background: "#FFFFFF",
                }
            },
            area: {
                shape: "circularImage",
                image: "../static/img/area.png",
                color: {
                    border: "#4D4D4D",
                    background: "#FFFFFF",
                }
            },
            tipo: {
                shape: "circularImage",
                image: "../static/img/area.png",
                color: {
                    border: "#4D4D4D",
                    background: "#FFFFFF",
                }
            },
            director: {
                borderWidth: 4,
                color: {
                    border: "#4D4D4D",
                    background: "#FFFFFF",
                }
            },
            investigacion: {
                shape: "circularImage",
                image: "../static/img/file1.png",
                color: {
                    border: "#FFFFFF",
                    background: "#FFFFFF",
                }
            },
            buscado: {
                color: {
                    border: "#4D4D4D",
                    background: "#FFFFFF",
                }
            },
            investigacion_docente: {
                shape: "circularImage",
                image: "../static/img/file2.png",
                color: {
                    border: "#FFFFFF",
                    background: "#FFFFFF",
                }
            },
            extension: {
                shape: "circularImage",
                image: "../static/img/file3.png",
                color: {
                    border: "#FFFFFF",
                    background: "#FFFFFF",
                }
            },
            consultoria: {
                shape: "circularImage",
                image: "../static/img/file4.png",
                color: {
                    border: "#FFFFFF",
                    background: "#FFFFFF",
                }
            },
            implementacion: {
                shape: "circularImage",
                image: "../static/img/file5.png",
                color: {
                    border: "#FFFFFF",
                    background: "#FFFFFF",
                }
            },
            innovacion: {
                shape: "circularImage",
                image: "../static/img/file6.png",
                color: {
                    border: "#FFFFFF",
                    background: "#FFFFFF",
                }
            },
            propuesta: {
                shape: "circularImage",
                image: "../static/img/file7.png",
                color: {
                    border: "#FFFFFF",
                    background: "#FFFFFF",
                }
            },
            vinculacion: {
                shape: "circularImage",
                image: "../static/img/file8.png",
                color: {
                    border: "#FFFFFF",
                    background: "#FFFFFF",
                }
            },
            total: {
                shape: "circularImage",
                image: "../static/img/suma.png",
                color: {
                    border: "#FFFFFF",
                    background: "#FFFFFF",
                }
            }
        }
    };
    var data = JSON.parse(document.getElementById('json1').innerHTML);
    for (var i = 0; i < data.nodes.length; i++) {
        if (selected == "Area" || selected == "TipoProyecto") {
            nodoseleccionado = data.nodes[0].label;
        } else {
            if (idnodobase == data.nodes[i].id) {
                nodoseleccionado = data.nodes[i].label;
                foto = data.nodes[i].image;
            }
        }
    }
    var container = document.getElementById('mynetwork');
    var leyenda = document.getElementById('myleyenda');
    var x = -leyenda.clientWidth / 4 + 50;
    var y = -leyenda.clientHeight / 2;
    var totalExtension = 0;
    var totalInvestigacion = 0;
    var totalInvestigacionDocente = 0;
    var totalConsultoria = 0;
    var totalImplementacion = 0;
    var totalInnovacion = 0;
    var totalPropuesta = 0;
    var totalVinculacion = 0;
    var totalDirector = 0;
    var totalParticipante = 0;
    var acumulador = 1;
    var arrayLegend = [];
    var step = 80;
    if (data.nodes.length > 200) {
        options.physics = {
            stabilization: false,
            barnesHut: {
                gravitationalConstant: -2000,
                springConstant: 0.001,
            },
        };
    }
    for (var i = 0; i < data.nodes.length; i++) {
        delete data.nodes[i].x;
        delete data.nodes[i].y;
    }
    var data = {
        nodes: data.nodes,
        edges: data.edges
    };
    var dataleyenda = [];
    for (var j = 0; j < data.nodes.length; j++) {
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
            case 'vinculacion':
                totalVinculacion++;
                break;
            case 'director':
                totalDirector++;
                break;
            case 'participante':
                totalParticipante++;
                break;
        }
    }
    for (var i = 0; i < data.nodes.length; i++) {
        var yacumulador = y + acumulador + step;
        if (i != 0) {
            yacumulador = y + acumulador * step;
        }
        switch (data.nodes[i].group) {
            case 'buscado':
                if (arrayLegend.indexOf(1011) == -1) {
                    if (selected == "Nombre") {
                        dataleyenda.push({
                            id: 1011,
                            x: x,
                            y: yacumulador,
                            label: nodoseleccionado,
                            shape: "circularImage",
                            image: data.nodes[i].image,
                            color: {
                                border: "#4D4D4D",
                                background: "#FFFFFF",
                            },
                            value: 1,
                            fixed: true,
                            physics: false,
                        });
                        arrayLegend.push(1011);
                        acumulador++;
                    }else if (selected == "Persona") {
                        dataleyenda.push({
                            id: 1011,
                            x: x,
                            y: yacumulador,
                            label: nodoseleccionado,
                            shape: "circularImage",
                            image: foto,
                            color: {
                                border: "#4D4D4D",
                                background: "#FFFFFF",
                            },
                            value: 1,
                            fixed: true,
                            physics: false,
                        });
                        arrayLegend.push(1011);
                        acumulador++;
                    }
                }
                break;
            case 'investigacion':
                if (arrayLegend.indexOf(1001) == -1) {
                    dataleyenda.push({
                        id: 1001,
                        x: x,
                        y: yacumulador,
                        label: "Investigación (" + totalInvestigacion + ")",
                        group: "investigacion",
                        value: 1,
                        fixed: true,
                        physics: false,
                    });
                    arrayLegend.push(1001);
                    acumulador++;
                }
                break;
            case 'area':
                if (arrayLegend.indexOf(1012) == -1) {
                    dataleyenda.push({
                        id: 1012,
                        x: x,
                        y: yacumulador,
                        label: nodoseleccionado,
                        group: "area",
                        value: 1,
                        fixed: true,
                        physics: false,
                    });
                    arrayLegend.push(1011);
                    acumulador++;
                }
                break;
            case 'tipo':
                if (arrayLegend.indexOf(1013) == -1) {
                    dataleyenda.push({
                        id: 1013,
                        x: x,
                        y: yacumulador,
                        label: nodoseleccionado,
                        group: "tipo",
                        value: 1,
                        fixed: true,
                        physics: false,
                    });
                    arrayLegend.push(1011);
                    acumulador++;
                }
                break;
            case 'extension':
                if (arrayLegend.indexOf(1002) == -1) {
                    dataleyenda.push({
                        id: 1002,
                        x: x,
                        y: yacumulador,
                        label: "Extensión (" + totalExtension + ")",
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
                if (arrayLegend.indexOf(1003) == -1) {
                    dataleyenda.push({
                        id: 1003,
                        x: x,
                        y: yacumulador,
                        label: "Innovación Docente (" + totalInvestigacionDocente + ")",
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
                if (arrayLegend.indexOf(1004) == -1) {
                    dataleyenda.push({
                        id: 1004,
                        x: x,
                        y: yacumulador,
                        label: "Consultoría (" + totalConsultoria + ")",
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
                if (arrayLegend.indexOf(1005) == -1) {
                    dataleyenda.push({
                        id: 1005,
                        x: x,
                        y: yacumulador,
                        label: "Implementación (" + totalImplementacion + ")",
                        group: "implementacion",
                        value: 1,
                        fixed: true,
                        physics: false,
                    });
                    arrayLegend.push(1005);
                    acumulador++;
                }
                break;
            case 'vinculacion':
                if (arrayLegend.indexOf(1014) == -1) {
                    dataleyenda.push({
                        id: 1014,
                        x: x,
                        y: yacumulador,
                        label: "Vinculación (" + totalVinculacion + ")",
                        group: "vinculacion",
                        value: 1,
                        fixed: true,
                        physics: false,
                    });
                    arrayLegend.push(1014);
                    acumulador++;
                }
                break;
            case 'innovacion':
                if (arrayLegend.indexOf(1006) == -1) {
                    dataleyenda.push({
                        id: 1006,
                        x: x,
                        y: yacumulador,
                        label: "Innovación (" + totalInnovacion + ")",
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
                if (arrayLegend.indexOf(1007) == -1) {
                    dataleyenda.push({
                        id: 1007,
                        x: x,
                        y: yacumulador,
                        label: "Propuesta Enviada (" + totalPropuesta + ")",
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
                if (arrayLegend.indexOf(1008) == -1) {
                    if (selected == "Proyecto") {
                        dataleyenda.push({
                            id: 1008,
                            x: x,
                            y: yacumulador,
                            label: "Participante(s) (" + totalParticipante + ")",
                            shape: "circularImage",
                            image: "n",
                            color: {
                                border: "#4D4D4D",
                                background: "#FFFFFF",
                            },
                            value: 1,
                            fixed: true,
                            physics: false,
                        });
                        arrayLegend.push(1008);
                        acumulador++;
                    } else if (selected == "Persona") {
                        dataleyenda.push({
                            id: 1008,
                            x: x,
                            y: yacumulador,
                            label: "Colaborador(es) (" + totalParticipante + ")",
                            shape: "circularImage",
                            image: "n",
                            color: {
                                border: "#4D4D4D",
                                background: "#FFFFFF",
                            },
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
                if (arrayLegend.indexOf(1009) == -1) {
                    dataleyenda.push({
                        id: 1009,
                        x: x,
                        y: y + acumulador * step,
                        label: "Director (" + totalDirector + ")",
                        shape: "circularImage",
                        image: "n",
                        borderWidth: 4,
                        color: {
                            border: "#4D4D4D",
                            background: "#FFFFFF",
                        },
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
    totalProyectos = totalExtension + totalPropuesta + totalInnovacion + totalImplementacion + totalConsultoria +
        totalInvestigacionDocente + totalInvestigacion + totalVinculacion;
    if (selected == "Nombre" || selected == "Area") {
        dataleyenda.push({
            id: 1010,
            x: x,
            y: y + acumulador * step,
            label: "Total proyectos (" + totalProyectos + ")",
            group: "total",
            value: 1,
            fixed: true,
            physics: false,
        });
    }
    var leyendaData = {
        nodes: dataleyenda
    }
    var network = new vis.Network(container, data, options);
    var networkleyenda = new vis.Network(leyenda, leyendaData, options);
    var doubleClickTime = 0;
    var threshold = 200;
    network.on("click", function (params) {
        var t0 = new Date();
        var identificador = this.getNodeAt(params.pointer.DOM);
        idseleccionado = this.getNodeAt(params.pointer.DOM);
        if (t0 - doubleClickTime > threshold) {
            setTimeout(function () {
                if (t0 - doubleClickTime > threshold) {
                    var arrayEdges = network.getConnectedNodes(identificador, 'from');
                    if (selected == "Proyecto") {
                        arrayEdges = network.getConnectedNodes(identificador, 'to');
                    }
                    var idNodo = identificador;
                    if (idNodo != idnodobase) {
                        if (arrayEdges != undefined && arrayEdges != null && arrayEdges != '') {
                            if (listaNodoAbierto.indexOf(idNodo) == -1) {
                                agrandarGrafo(idNodo);
                                listaNodoAbierto.push(idNodo);
                            } else {
                                reducirGrafo(idNodo);
                                var index = listaNodoAbierto.indexOf(idNodo);
                                listaNodoAbierto.splice(index, 1);
                            }
                        }
                    } else {
                        actualizarJSONHTML(grafoinicial, grafoinicial);
                        armarGrafo();
                    }
                }
            }, threshold);
        }
    });
    network.on('doubleClick', function (params) {
        doubleClickTime = new Date();
        var id = this.getNodeAt(params.pointer.DOM);
        if(id.length>5){
            window.open(rutaBase + 'usuario/'+id, '_blank');
        }else{
            window.open(rutaBase + 'proyecto/' + id, '_blank');
        }
    });
    network.on("afterDrawing", function (ctx) {
        try {
            var nodeId = idseleccionado;
            var nodePosition = network.getPositions([nodeId]);
            ctx.strokeStyle = '#000000';
            ctx.lineWidth = 2;
            ctx.fillStyle = "rgba(0,0,0,0)";
            ctx.circle(nodePosition[nodeId].x, nodePosition[nodeId].y, 28);
            ctx.fill();
            ctx.stroke();
        } catch (e) {

        }
    });
}

