function getWelcomeTemplate() {
    return "<span class='flow-text'>Добро пожаловать</span>" +
        "<div class=\"divider\"></div>"+
        "<div class=\"row\">"+
        "   <div class=\"col s3\">" +
        "       <div id=\"welcomeContainer\" class=\"row z-depth-2 blue-grey lighten-2\">" +
        "           <div class=\"col s4\"><i class=\"material-icons medium\">style</i></div>" +
        "           <div class=\"col s3\"><p>25</p><p>ESL</p></div>" +
        "           <div class=\"col s3\"><p>100</p><p>ITEM</p></div>" +
        "       </div>" +
        "   </div>" +
        "   <div class=\"col s3\">" +
        "       <div id=\"welcomeContainer\" class=\"row z-depth-2 blue-grey lighten-2\">" +
        "           <div class=\"col s4\"><i class=\"material-icons medium\">wifi</i></div>" +
        "           <div class=\"col s8\"><p>10 ESLs in</p><p>transmissions</p></div>" +
        "       </div>" +
        "   </div>" +
        "   <div class=\"col s3\">" +
        "       <div id=\"welcomeContainer\" class=\"row z-depth-2 orange lighten-2\">" +
        "           <div class=\"col s4\"><i class=\"material-icons medium\">content_copy</i></div>" +
        "           <div class=\"col s8\"><p>1 items</p><p>today</p></div>" +
        "       </div>" +
        "   </div>" +
        "   <div class=\"col s3\">" +
        "       <div id=\"welcomeContainer\" class=\"row z-depth-2 orange lighten-2\">" +
        "           <div class=\"col s4\"><i class=\"material-icons medium\">swap_horiz</i></div>" +
        "           <div class=\"col s8\"><p>1 Associations</p><p>today</p></div>" +
        "       </div>" +
        "</div>"+
        "<div class=\"row\">" +
        "   <div class=\"col s4\"><canvas id=\"myChart1\" class=\"col s4\"></canvas></div>"+
        "   <div class=\"col s4\"><canvas id=\"myChart2\" class=\"col s4\"></canvas></div>"+
        "   <div class=\"col s4\"><canvas id=\"myChart3\" class=\"col s4\"></canvas></div>"+
        "</div>"
}
function testChart(chartName) {
    var ctx = document.getElementById(chartName);
    new Chart(ctx, {
        type: 'line',
        data: {
            labels: [1500,1600,1700,1750,1800,1850,1900,1950,1999,2050],
            datasets: [{
                data: [86,114,106,106,107,111,133,221,783,2478],
                label: "Africa",
                borderColor: "#3e95cd",
                fill: false
            }, {
                data: [282,350,411,502,635,809,947,1402,3700,5267],
                label: "Asia",
                borderColor: "#8e5ea2",
                fill: false
            }, {
                data: [168,170,178,190,203,276,408,547,675,734],
                label: "Europe",
                borderColor: "#3cba9f",
                fill: false
            }, {
                data: [40,20,10,16,24,38,74,167,508,784],
                label: "Latin America",
                borderColor: "#e8c3b9",
                fill: false
            }, {
                data: [6,3,2,2,7,26,82,172,312,433],
                label: "North America",
                borderColor: "#c45850",
                fill: false
            }
            ]
        },
        options: {
            title: {
                display: true,
                text: 'World population per region (in millions)'
            }
        }
    });
}