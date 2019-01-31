function getAccessPointTemplate() {
    return "<h2 class='flow-text'>Статус точек доступа</h2>" +
        "<div class=\"divider\"/>"+
        getTableTemplate('accessPoint')
}
function displayAccessPointData(url, headers) {
    var display = document.getElementById("mainProgress");
    display.style.visibility='visible';
    $.getJSON(url, headers, function (data) {
        var accessPointData = $.parseJSON(JSON.stringify(data));
        $('#accessPointTable').html('')
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
            .append("<tbody id=\"accessPointTBody\"></tbody>");
        for (var i = 0; i < accessPointData.length; i++) {
            $('#accessPointTBody').append("<tr>" +
                "<td>" + accessPointData[i].ip + "</td>" +
                "<td>" + accessPointData[i].port + "</td>" +
                "<td>" + accessPointData[i].status + "</td>" +
                "<td>" + accessPointData[i].app + "</td>" +
                "<td>" + accessPointData[i].ram + "</td>" +
                "<td>" + accessPointData[i].hdd + "</td>" +
                "<td>" + accessPointData[i].cpu + "</td>" +
                "<td>" +
                "</li>" +
                "</ul>" +
                "</td>" +
                "</tr>");
        }
        display.style.visibility='hidden'
    }).error(function(jqXHR) {
        alert(jqXHR.responseText);
        display.style.visibility='hidden'
    });
}