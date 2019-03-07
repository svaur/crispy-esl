function getTasksTemplate() {
    return "<h2 class='flow-text'>Планировщик задач</h2>"+
        "<div class=\"divider\"></div>" +
        "<div id=\"addTaskWindow\">"+
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
        "                    <input id='mon' type=\"checkbox\"/>" +
        "                    <span>Понедельник</span>" +
        "                  </label>" +
        "                </p>" +
        "                <p>" +
        "                  <label>" +
        "                    <input id='tue' type=\"checkbox\"/>" +
        "                    <span>Вторник</span>" +
        "                  </label>" +
        "                </p>" +
        "                <p>" +
        "                  <label>" +
        "                    <input id='wed' type=\"checkbox\" />" +
        "                    <span>Среда</span>" +
        "                  </label>" +
        "                </p>" +
        "                <p>" +
        "                  <label>" +
        "                    <input id='thu' type=\"checkbox\" />" +
        "                    <span>Четверг</span>" +
        "                  </label>" +
        "                </p>" +
        "                <p>" +
        "                  <label>" +
        "                    <input id='fri' type=\"checkbox\" />" +
        "                    <span>Пятница</span>" +
        "                  </label>" +
        "                </p>" +
        "                <p>" +
        "                  <label>" +
        "                    <input id='sat' type=\"checkbox\" />" +
        "                    <span>Суббота</span>" +
        "                  </label>" +
        "                </p>" +
        "                <p>" +
        "                  <label>" +
        "                    <input id='sun' type=\"checkbox\" />" +
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
        "           <a class=\"waves-effect waves-light btn-small\" onclick=\"hideDiv('#addTaskWindow')\"><i class=\"white-text material-icons left\">cancel</i>отменить</a>"+
        "           <a class=\"waves-effect waves-light btn-small\" onclick='saveTaskData()'><i class=\"white-text material-icons left\">check</i>сохранить</a>"+
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
                "<th>Затронутые товары</th>" +
                "<th>Частота</th>" +
                "<th>Статус</th>" +
                // "<th>taskResults</th>" +
                "<th>Действие</th>" +
                "</tr>" +
                "</thead>")
            .append("<tbody id=\"taskTBody\"></tbody>");
        for (var i = 0; i < tableData.length; i++) {
            $('#taskTBody').append("<tr>" +
                "<td>" + tableData[i].taskName + "</td>" +
                "<td>" + tableData[i].updatedItemParams + "</td>" +
                "<td>" + tableData[i].frequency + "</td>" +
                "<td>" + tableData[i].status + "</td>" +
                // "<td>" + tableData[i].taskResults + "</td>" +
                "<td> " +
                    "<a class=\"blue-grey darken-1 waves-effect waves-light\" onclick='updateImageTask(" + tableData[i].id + ")'>" +
                    "<i class=\"white-text material-icons small\">update</i>" +
                    "</a>" +
                "</td>" +
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
        getAllTaskData(0)
    });
    $('#search').on('input', function() {
        getAllTaskData(0)
    });
    $('.dropdown-trigger').dropdown();
    $('#previousBtn').click(function () {
        if (pageNum > 0) {
            pageNum = pageNum - 1;
            document.getElementById("firstBtn").innerHTML = pageNum + 1;
            getAllTaskData(pageNum);
        }
    });
    // $('#secondBtn').click(function () {
    //     pageNum = $('#secondBtn').innerText;
    //     buttonsUpdate();
    //     getAllItemsData(pageNum);
    // });
    // $('#thirdBtn').click(function () {
    //     pageNum = $('#thirdBtn').innerText.;
    //     buttonsUpdate();
    //     getAllItemsData(pageNum);
    // });
    // $('#fourthBtn').click(function () {
    //     pageNum = $('#fourthBtn').innerText;
    //     buttonsUpdate();
    //     getAllItemsData(pageNum);
    // });
    $('#nextBtn').click(function () {
        pageNum = pageNum + 1;
        document.getElementById("firstBtn").innerHTML = pageNum + 1;
        getAllTaskData(pageNum);
    });
    // function buttonsUpdate() {
    //     $('#firstBtn').innerText = pageNum;
    //     $('#secondBtn').innerText = pageNum + 1;
    //     $('#thirdBtn').innerText = pageNum + 2;
    //     $('#fourthBtn').innerText = pageNum + 3;
    // }
}
function getAllTaskData(pageNum) {
    var headers = {"size": $('#taskTableCounter').val(), "pageNum": pageNum, "searchValue": $('#search').val()};
    displayTaskData("/api/getTaskTableData", headers);
}
function hideDiv(elem) {
    $(elem).hide();
}
function saveTaskData() {
    alert("нет обработчика");
}

function updateImageTask(taskId) {
    $.getJSON("/api/updateEslGroup", {taskId:taskId}, function (data) {
        Console.log(data);
    }).error(function(jqXHR) {
        alert(jqXHR.responseText);
    });
}