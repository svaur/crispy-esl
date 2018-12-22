function getWelcomeTemplate() {
    return "<h2 class='flow-text'>Главная</h2>" +
        "<div class=\"divider\"></div>"+
        "<div class=\"row\">"+
        "   <div class=\"col s6 m4 l3\">" +
        "       <div id=\"welcomeContainer\" class=\"z-depth-1 mainContainer blue-grey darken-1 white-text\">" +
        "           <div class='row'><i class=\"col s3 white-text material-icons small\">style</i><h6 class='col s8'>В базе</h6></div>" +
        "           <div align=\"right\">25 ценников</div>" +
        "       </div>" +
        "   </div>" +
        "   <div class=\"col s6 m4 l3\">" +
        "       <div id=\"welcomeContainer\" class=\"z-depth-1 mainContainer blue-grey darken-1 white-text\">" +
        "           <div class='row'><i class=\"col s3 white-text material-icons small\">content_copy</i><h6 class='col s8'>В базе</h6></div>" +
        "           <div align=\"right\">100 товаров</div>" +
        "       </div>" +
        "   </div>" +
        "   <div class=\"col s6 m4 l3\">" +
        "       <div id=\"welcomeContainer\" class=\"z-depth-1 mainContainer blue-grey darken-1 white-text\">" +
                "<div class='row'><i class=\"col s3 white-text material-icons small\">refresh</i><h6 class='col s8'>10 товаров</h6></div>" +
        "           <div align=\"right\">добавлено</div>" +
        "       </div>" +
        "   </div>" +
        "   <div class=\"col s6 m4 l3\">" +
        "       <div id=\"welcomeContainer\" class=\"z-depth-1 mainContainer blue-grey darken-1 white-text\">" +
        "       <div class='row'><i class=\"col s3 white-text material-icons small\">swap_horiz</i><h6 class='col s8'>10 привязок</h6></div>" +
        "           <div align=\"right\">добавлено</div>" +
        "       </div>" +
        "   </div>"+
        "</div>"+
        "<div class=\"row\">"+
        "   <div class=\"col s6 m4 l3\">" +
        "       <div id=\"welcomeContainer\" class=\"z-depth-1 mainContainer yellow darken-3 white-text\">" +
        "           <div class='row'><i class=\"col s3 white-text material-icons small\">warning</i><h6 class='col s8'>Точки доступа</h6></div>" +
        "           <div align=\"right\">активно 1/2</div>" +
        "       </div>" +
        "   </div>" +
        "   <div class=\"col s6 m4 l3\">" +
        "       <div id=\"welcomeContainer\" class=\"z-depth-1 mainContainer red white-text\">" +
        "           <div class='row'><i class=\"col s3 white-text material-icons small\">error</i><h6 class='col s8'>Ошибка обновления</h6></div>" +
        "           <div align=\"right\">1 товар</div>" +
        "       </div>" +
        "   </div>" +
        "   <div class=\"col s6 m4 l3\">" +
        "       <div id=\"welcomeContainer\" class=\"z-depth-1 mainContainer blue-grey darken-1 white-text\">" +
        "           <div class='row'><i class=\"col s3 white-text material-icons small\">timer</i><h6 class='col s8'>Следующее обновление</h6></div>" +
        "           <div align=\"right\">сегодня, 10pm</div>" +
        "       </div>" +
        "   </div>" +
        "   <div class=\"col s6 m4 l3\">" +
        "       <div id=\"welcomeContainer\" class=\"z-depth-1 mainContainer red white-text\">" +
                "<div class='row'><i class=\"col s3 white-text material-icons small\">error</i><h6 class='col s8'>Ошибка синхронизации</h6></div>" +
        "           <div align=\"right\">не удалось обновить товары</div>" +
        "       </div>" +
        "</div>"+
        "<div class=\"row\">" +
        "   <div class=\"col s4\"><canvas id=\"myChart1\"></canvas></div>"+
        "   <div class=\"col s4\"><canvas id=\"myChart2\"></canvas></div>"+
        "   <div class=\"col s4\"><canvas id=\"myChart3\"></canvas></div>"+
        "</div>"
}
function testChart(chartName, chartSignature, indata) {
    var ctx = document.getElementById(chartName);
    new Chart(ctx, {
        type: 'line',
        data: {
            labels: ["17 nov","18 nov","19 nov","20 nov"],
                datasets: [{
                data: indata,
                label: " ",
                borderColor: "#546e7a",
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