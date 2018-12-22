function getWelcomeTemplate() {
    return "<h2 class='flow-text'>Добро пожаловать</h2>" +
        "<div class=\"divider\"></div>"+
        "<div class=\"row\">"+
        "   <div class=\"col s6 m4 l3\">" +
        "       <div id=\"welcomeContainer\" class=\"row blue-grey lighten-2\">" +
        "           <div class=\"col s4\"><i class=\"material-icons medium\">style</i></div>" +
        "           <div class=\"col s3\"><p>25</p><p>ESL</p></div>" +
        "           <div class=\"col s3\"><p>100</p><p>ITEM</p></div>" +
        "       </div>" +
        "   </div>" +
        "   <div class=\"col s6 m4 l3\">" +
        "       <div id=\"welcomeContainer\" class=\"row blue-grey lighten-2\">" +
        "           <div class=\"col s4\"><i class=\"material-icons medium\">wifi</i></div>" +
        "           <div class=\"col s8\"><p>10 ESLs in</p><p>transmissions</p></div>" +
        "       </div>" +
        "   </div>" +
        "   <div class=\"col s6 m4 l3\">" +
        "       <div id=\"welcomeContainer\" class=\"row orange lighten-2\">" +
        "           <div class=\"col s4\"><i class=\"material-icons medium\">content_copy</i></div>" +
        "           <div class=\"col s8\"><p>1 items</p><p>today</p></div>" +
        "       </div>" +
        "   </div>" +
        "   <div class=\"col s6 m4 l3\">" +
        "       <div id=\"welcomeContainer\" class=\"row orange lighten-2\">" +
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
function testChart(chartName, chartSignature) {
    var ctx = document.getElementById(chartName);
    new Chart(ctx, {
        type: 'line',
        data: {
            labels: ["17 nov","18 nov","19 nov","20 nov"],
                datasets: [{
                data: [0, 0, 0, 0],
                // label: " ",
                borderColor: "#3e95cd",
                fill: false
            }
            ]
        },
        options: {
            title: {
                display: true,
                text: chartSignature
            }
        }
    });
}