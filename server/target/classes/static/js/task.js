function getTasksTemplate() {
    return "<span class='flow-text'>Планировщик задач</span>"+
        "<div class=\"divider\"></div>" +
        "<div id=\"addTaskWindow\" class=\"z-depth-2\">"+
        "   <div class=\"row\">"+
        "       <div id=\"test-swipe-1\" class=\"col s8\">" +
        "           <form>" +
        // "              <select id=\"repeatTaskType\" class=\"validate\">" +
        // "                  <option value=\"singleRun\">однократное срабатывание</option>" +
        // "                  <option value=\"repeatRun\">цикличное срабатывание</option>" +
        // "              </select>" +
        "              <div class=\"switch\">" +
        "                 <label>" +
        "                   однократное срабатывание" +
        "                   <input id=\"runSwitch\" type=\"checkbox\">" +
        "                   <span class=\"lever\"></span>" +
        "                   цикличное срабатывание" +
        "                 </label>" +
        "               </div>"+
        "               <div id=\"daysCheckboxes\">" +
        "                <p>" +
        "                  <label>" +
        "                    <input id='mon' type=\"checkbox\" class=\"filled-in\"/>" +
        "                    <span>Понедельник</span>" +
        "                  </label>" +
        "                </p>" +
        "                <p>" +
        "                  <label>" +
        "                    <input id='tue' type=\"checkbox\" class=\"filled-in\"/>" +
        "                    <span>Вторник</span>" +
        "                  </label>" +
        "                </p>" +
        "                <p>" +
        "                  <label>" +
        "                    <input id='wed' type=\"checkbox\" class=\"filled-in\"/>" +
        "                    <span>Среда</span>" +
        "                  </label>" +
        "                </p>" +
        "                <p>" +
        "                  <label>" +
        "                    <input id='thu' type=\"checkbox\" class=\"filled-in\"/>" +
        "                    <span>Четверг</span>" +
        "                  </label>" +
        "                </p>" +
        "                <p>" +
        "                  <label>" +
        "                    <input id='fri' type=\"checkbox\" class=\"filled-in\"/>" +
        "                    <span>Пятница</span>" +
        "                  </label>" +
        "                </p>" +
        "                <p>" +
        "                  <label>" +
        "                    <input id='sat' type=\"checkbox\" class=\"filled-in\"/>" +
        "                    <span>Суббота</span>" +
        "                  </label>" +
        "                </p>" +
        "                <p>" +
        "                  <label>" +
        "                    <input id='sun' type=\"checkbox\" class=\"filled-in\"/>" +
        "                    <span>Воскресенье</span>" +
        "                  </label>" +
        "                </p>" +
        "              </div>"+
        "              <select id=\"selectTaskType\" class=\"validate\">" +
        "                  <option value=\"update\">обновление</option>" +
        "                  <option value=\"smartUpdate\">умное обновление</option>" +
        "              </select>" +
        "              <div class=\"row\">" +
        "              <div class=\"col s12 input-field\">" +
        "                  <input id=\"taskName\" type=\"text\" class=\"validate\">" +
        "                  <label for=\"taskName\">Имя задачи</label>" +
        "              </div>" +
        "              <div class=\"col s12 input-field\">" +
        "                  <input id=\"dateStart\" type=\"text\" class=\"datepicker\">" +
        "                  <label for=\"dateStart\">Дата первого срабатывания</label>" +
        "              </div>" +
        "              <div class=\"col s12 input-field\">" +
        "                  <input id=\"timeStart\" type=\"text\" class=\"timepicker\">" +
        "                  <label for=\"timeStart\">время срабатывания</label>" +
        "              </div>" +
        "           </form>" +
        "           <a class=\"waves-effect waves-light btn-small\" onclick=\"hideDiv('#addTaskWindow')\"><i class=\"material-icons left\">cancel</i>отменить</a>"+
        "           <a class=\"waves-effect waves-light btn-small\" onclick='saveTaskData()'><i class=\"material-icons left\">check</i>сохранить</a>"+
        "           </div>"+
        "       </div>"+
        "   </div>"+
        "</div>"+
        getTableTemplate('task')
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
function hideDiv(elem) {
    $(elem).hide();
}
function saveTaskData() {
    alert("нет обработчика");
}