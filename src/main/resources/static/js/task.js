function getTasksTemplate() {
    return "<span class='flow-text'>Планировщик задач</span>"
        + getTableTemplate('task')
}
function displayTaskData(url, headers) {
    var display = document.getElementById("mainProgress");
    display.style.visibility='visible';
    $.getJSON(url, headers, function (data) {
        var tableData = $.parseJSON(JSON.stringify(data));
        $('#taskTable').html('')
            .append("<thead>" +
                "<tr>" +
                "<th>Имя</th>" +
                "<th>Тип</th>" +
                "<th>Частота</th>" +
                "<th>Последнее обновление</th>" +
                "<th>Запланированное обновление</th>" +
                "<th>Действие</th>" +
                "</tr>" +
                "</thead>")
            .append("<tbody id=\"taskTBody\"></tbody>");
        for (var i = 0; i < tableData.length; i++) {
            $('#taskTBody').append("<tr>" +
                "<td>" + tableData[i].taskName + "</td>" +
                "<td>" + tableData[i].taskType + "</td>" +
                "<td>" + tableData[i].frequency + "</td>" +
                "<td>" + tableData[i].lastUpdate + "</td>" +
                "<td>" + tableData[i].nextShedule + "</td>" +
                "<td> </td>" +
                "</tr>");
        }
        $('.dropdown-trigger').dropdown();
        display.style.visibility='hidden'
    }).error(function(jqXHR) {
        alert(jqXHR.responseText);
        display.style.visibility='hidden'
    });
}
function taskActivateActions() {
    $('#taskTableCounter').formSelect().on('change', function () {
        var headers = {"size": $('#taskTableCounter').val()};
        displayEslData("/api/getTaskTableData", headers);
    });
    $('#search').on('input', function() {
        var headers = {"size": $('#taskTableCounter').val(), "searchValue": $('#search').val()};
        displayTaskData("/api/searchTaskData", headers);
    });
    $('.dropdown-trigger').dropdown();
}