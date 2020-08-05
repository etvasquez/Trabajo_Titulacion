var selected;
$(document).ready(function(){
    $("select.form-control").change(function(){
        selected = $(this).children("option:selected").val();
        if(selected!=null){
            document.getElementById("con").style.display = 'block';
            document.getElementById("con1").style.display = 'block';
            $("#formGroupExampleInput").empty();
        }else{
            document.getElementById("con").style.display = 'none';
            document.getElementById("con1").style.display = 'block';
            $("#formGroupExampleInput").empty();
        }
    });
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
}

function armarGrafoPersona() {
    var options = {
        edges: {
            color: "#666666",
        },
        interaction: { hover: true, html:true },
        groups: {
            director: {
                shape: "circularImage",
                image: "../img/user.png",
                borderWidth: 2,
                size: 20,
                color: {
                    border: "#099DF8",
                    background: "#FFFFFF",
                }
            },
            projects:{
                shape: "circularImage",
                image: "../img/file.png",
                borderWidth: 2,
                size: 20,
                color: {
                    border: "#666666",
                    background: "#FFFFFF",
                }
            }
        }
    };
    var data = JSON.parse(document.getElementById('json').innerHTML)
    var container = document.getElementById('mynetwork');
    var x = -mynetwork.clientWidth / 2 + 100;
    var y = -mynetwork.clientHeight / 2 + 100;
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
    data.nodes.push({
        id: 1001,
        x: x,
        y: y + step,
        label: "Proyecto",
        group: "projects",
        value: 1,
        fixed: true,
        physics: false,
    });
    var network = new vis.Network(container, data, options);
    network.on("click", function(params) {
        alert(
            "ID: " + this.getNodeAt(params.pointer.DOM)
        );
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
