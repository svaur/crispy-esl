$(document).ready(function () {
    $('.sidenav').sidenav();
    $('.dropdown-trigger').dropdown();
    displayWorkSpace('#welcomeWorkSpace', '');
    setActive('#welcomeWorkSpace');
    $('#welcomeWorkSpace').click(function () {
        setActive('#welcomeWorkSpace');
        displayWorkSpace('#welcomeWorkSpace', '');
    });
    $('#eslsWorkSpace').click(function () {
        setActive('#eslsWorkSpace');
        displayWorkSpace('#eslsWorkSpace', '/api/getEslTableData');
    });
    $('#itemsWorkSpace').click(function () {
        setActive('#itemsWorkSpace');
        displayWorkSpace('#itemsWorkSpace', '/api/getItemTableData');
    });
    $('#associateWorkSpace').click(function () {
        setActive('#associateWorkSpace');
        displayWorkSpace('#associateWorkSpace', ' ');
    });
    $('#taskWorkSpace').click(function () {
        setActive('#taskWorkSpace');
        displayWorkSpace('#taskWorkSpace', '/api/getTaskTableData');
    });
    $('#logWorkSpace').click(function () {
        setActive('#logWorkSpace');
        displayWorkSpace('#logWorkSpace', ' ');
    });
    $('#reportWorkSpace').click(function () {
        setActive('#reportWorkSpace');
        displayWorkSpace('#reportWorkSpace', ' ');
    });
});
var pageNum=0;
function displayWorkSpace(menu, url) {
    pageNum=0;
    setActive();
    switch(menu) {
        case "#welcomeWorkSpace":
            $('#workSpace').html('')
                .append(getWelcomeTemplate());
            testChart("myChart1", 'Статистика обновления ценников', [0, 2, 5, 3]);
            testChart("myChart2", 'Статистика обновления товаров', [0, 2, 2, 0]);
            testChart("myChart3", 'Статистика привязок',[0, 2, 50, 3]);
            break;
        case "#eslsWorkSpace":
            $('#workSpace').html('')
                .append(getEslsTemplate());
            eslActivateActions();
            var headers = {"size": $('#eslTableCounter').val(), "pageNum": 0, "searchValue": ""};
            displayEslData(url, headers);
            activatePaginator();
            break;
        case "#itemsWorkSpace":
            $('#workSpace').html('')
                .append(getItemsTemplate());
            itemActivateActions();
            var headers = {"size": $('#itemTableCounter').val(), "pageNum": 0, "searchValue": ""};
            displayItemData(url, headers);
            activatePaginator();
            break;
        case "#associateWorkSpace":
            $('#workSpace').html('')
                .append(getAssociateTemplate());
            $('.tabs').tabs();
            break;
        case "#taskWorkSpace":
            $('#workSpace').html('')
                .append(getTasksTemplate());
            taskActivateActions();
            var headers = {"size": $('#taskTableCounter').val(), "pageNum": 0, "searchValue": ""};
            displayTaskData(url, headers);
            $('#addTaskWindow').hide();
            $('#addTask').click(function () {
                $('#addTaskWindow').show();
            });
            $('.datepicker').datepicker();
            $('.timepicker').timepicker();
            $('#daysCheckboxes').hide();
            $('#runSwitch').click(function () {
                if($('#runSwitch').prop('checked')){
                    $('#daysCheckboxes').show();
                    console.log("sddsd")
                }else{
                    $('#daysCheckboxes').hide();
                    console.log("1211111")
                }
            });
            activatePaginator();
            break;
        case "#logWorkSpace":
            $('#workSpace').html('')
                .append(getLogTemplate());
            break;
        case "#reportWorkSpace":
            $('#workSpace').html('')
                .append(getReportTemplate());
            break;
        default:
            alert("пока не готово");
    }
    $('select').formSelect();//Для отображения селектов
}

function setActive(nameClassToActive) {
    $('#welcomeWorkSpace').removeClass("active");
    $('#eslsWorkSpace').removeClass("active");
    $('#itemsWorkSpace').removeClass("active");
    $('#associateWorkSpace').removeClass("active");
    $('#taskWorkSpace').removeClass("active");
    $('#logWorkSpace').removeClass("active");
    $('#reportWorkSpace').removeClass("active");
    $(nameClassToActive).addClass("active");
}
function showImage(code) {
    var w = window.open();
    $.getJSON("/api/getImage", {eslCode:code}, function (data) {
        if(data.contains("ERROR")){
            alert("не удалось загрузить изображение. Попробуйте запросить обновление статуса");
        }else {
            $(w.document.body).html(
                "<style>.shadow {" +
                "box-shadow: 0 0 10px rgba(0,0,0,0.5);" +
                "padding: 0px;}</style>" +
                "<img class=\"shadow\" id='esl-image' src='" + data + "'>");
        }
    }).error(function(jqXHR) {
        alert(jqXHR.responseText);
    });
}