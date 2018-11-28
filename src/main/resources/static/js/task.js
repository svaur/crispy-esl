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
function showAddTaskWindow() {
    return "<div id=\"addTaskWindow\" class=\"z-depth-2\">"+
        "   <div class=\"row\">"+
        "       <div id=\"test-swipe-1\" class=\"col s5\">" +
        "       <select id=\"selectAssertEsl\" class=\"validate\">" +
        "           <option value=\"add\">Привязка ценника</option>" +
        "           <option value=\"delete\">Отвязать ценник</option>" +
        "       </select>" +
        "       <form>" +
        "          <div class=\"row\">" +
        "          <div class=\"col s12 input-field\">" +
        "              <input id=\"eslInput\" type=\"text\" class=\"validate\">" +
        "              <label for=\"eslInput\">Ценник</label>" +
        "          </div>" +
        "          <div class=\"col s12 input-field\">" +
        "             <select id=\"templateInput\" class=\"validate\">" +
        "                 <option value=\"default\">по умолчанию</option>" +
        "             </select>" +
        "              <label for=\"templateInput\">Шаблон</label>" +
        "          </div>" +
        "          <div class=\"col s12 input-field\">" +
        "              <input id=\"itemInput\" type=\"tel\" class=\"validate\">" +
        "              <label for=\"itemInput\">Товар</label>" +
        "          </div>" +
        "          </div>"+
        "       </form>" +
        "       <a class=\"waves-effect waves-light btn-small\" onclick='cancelBtn()'><i class=\"material-icons left\">cancel</i>отменить</a>"+
        "       <a class=\"waves-effect waves-light btn-small\" onclick='saveData()'><i class=\"material-icons left\">check</i>сохранить</a>"+
        "   </div>"
        "</div>"
}