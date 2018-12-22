function getWelcomeTemplate() {
    return "<h2 class='flow-text'>Добро пожаловать</h2>" +
        "<div class=\"divider\"></div>"+
        "<div class=\"row\">"+
        "   <div class=\"col s6 m4 l3\">" +
        "       <div id=\"welcomeContainer\" class=\"z-depth-1 mainContainer blue-grey lighten-2\">" +
        "           <div><i class=\"material-icons\">style</i> 25 ценников</div>" +
        "           <div><i class=\"material-icons\">content_copy</i> 100 товаров</div>" +
        "       </div>" +
        "   </div>" +
        "   <div class=\"col s6 m4 l3\">" +
        "       <div id=\"welcomeContainer\" class=\"z-depth-1 mainContainer blue-grey lighten-2\">" +
        "           <div><i class=\"material-icons\">wifi</i> 10 ценников</div>" +
        "           <div>обновляются</div>" +
        "       </div>" +
        "   </div>" +
        "   <div class=\"col s6 m4 l3\">" +
        "       <div id=\"welcomeContainer\" class=\"z-depth-1 mainContainer orange lighten-2\">" +
                "<div><i class=\"material-icons\">content_copy</i>10 товаров</div>" +
        "           <div>добавлено</div>" +
        "       </div>" +
        "   </div>" +
        "   <div class=\"col s6 m4 l3\">" +
        "       <div id=\"welcomeContainer\" class=\"z-depth-1 mainContainer orange lighten-2\">" +
        "       <div><i class=\"material-icons\">swap_horiz</i>10 привязок</div>" +
        "           <div>добавлено</div>" +
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