function getServersStatusTemplate() {
    return "<h2 class='flow-text'>Статус точек доступа</h2>" +
        "<div class=\"divider\"/>"+
        getTableTemplate('serversStatus')
}
function displayServersStatusData(url, headers) {
    var display = document.getElementById("mainProgress");
    display.style.visibility='visible';
    $('#serversStatusTable').html('')
        .append("<thead>" +
            "<tr>" +
            "<th>ip</th>" +
            "<th>port</th>" +
            "<th>статус</th>" +
            "<th>приложение</th>" +
            "<th>RAM</th>" +
            "<th>HDD</th>" +
            "<th>CPU</th>" +
            "</tr>" +
            "</thead>")
        .append("<tbody id=\"serversStatusTBody\"></tbody>");
    $.getJSON("/api/getLocalUtilizationData",  function (data) {
        var mapData = $.parseJSON(JSON.stringify(data));
        display.style.visibility='hidden';
        $('#serversStatusTBody').append("<tr>" +
            "<td>" + mapData.ip + "</td>" +
            "<td>" + mapData.port + "</td>" +
            "<td>" + mapData.status + "</td>" +
            "<td>" + mapData.app + "</td>" +
            "<td>" + mapData.ram + "</td>" +
            "<td>" + mapData.hdd + "</td>" +
            "<td>" + mapData.cpu + "</td>" +
            "<td>" +
            "</li>" +
            "</ul>" +
            "</td>" +
            "</tr>");
    }).error(function(jqXHR) {
        //alert(jqXHR.responseText);
        display.style.visibility='hidden'
    });
    $.getJSON(url, headers, function (data) {
        var serversStatusData = $.parseJSON(JSON.stringify(data));
        for (var i = 0; i < serversStatusData.length; i++) {
            $('#serversStatusTBody').append("<tr>" +
                "<td>" + serversStatusData[i].ip + "</td>" +
                "<td>" + serversStatusData[i].port + "</td>" +
                "<td>" + serversStatusData[i].status + "</td>" +
                "<td>" + serversStatusData[i].app + "</td>" +
                "<td>" + serversStatusData[i].ram + "</td>" +
                "<td>" + serversStatusData[i].hdd + "</td>" +
                "<td>" + serversStatusData[i].cpu + "</td>" +
                "<td>" +
                "</li>" +
                "</ul>" +
                "</td>" +
                "</tr>");
        }
        display.style.visibility='hidden'
    }).error(function(jqXHR) {
        //alert(jqXHR.responseText);
        display.style.visibility='hidden'
    });
}
//
// function getLocalUtilizationData() {
//     var display = document.getElementById("mainProgress");
//     display.style.visibility='visible';
//     $.getJSON("/api/getLocalUtilizationData",  function (data) {
//         var mapData = $.parseJSON(JSON.stringify(data));
//         display.style.visibility='hidden';
//         return mapData.ram + "<br>" + mapData.hdd + "<br>" + mapData.cpu;
//     }).error(function(jqXHR) {
//         alert(jqXHR.responseText);
//         display.style.visibility='hidden'
//     });
// }