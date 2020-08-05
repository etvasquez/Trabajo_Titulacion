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
