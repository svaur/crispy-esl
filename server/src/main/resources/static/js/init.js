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
});

function displayWorkSpace(menu, url) {
    setActive();
    switch(menu) {
        case "#welcomeWorkSpace":
            $('#workSpace').html('')
                .append(getWelcomeTemplate());
            testChart("myChart1", 'Статистика обновления ценников');
            testChart("myChart2", 'Статистика обновления товаров');
            testChart("myChart3", 'Статистика привязок');
            break;
        case "#eslsWorkSpace":
            $('#workSpace').html('')
                .append(getEslsTemplate());
            eslActivateActions();
            displayEslData(url);
            break;
        case "#itemsWorkSpace":
            $('#workSpace').html('')
                .append(getItemsTemplate());
            itemActivateActions();
            displayItemData(url);
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
            displayTaskData(url);
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
            break;
        default:
            alert("пока не готово");
    }
    $('select').formSelect();//Для отображения селектов
}

function setActive(nameClassToActive) {
    // $('#welcomeWorkSpace').removeClass("active");
    // $('#eslsWorkSpace').removeClass("active");
    // $('#itemsWorkSpace').removeClass("active");
    // $('#associateWorkSpace').removeClass("active");
    // $('#taskWorkSpace').removeClass("active");
    $(nameClassToActive).addClass("active");
}
function showImage(code) {
    var w = window.open();
    $.getJSON("/api/getImage", {eslCode:code}, function (data) {
        $(w.document.body).html(
            "<style>.shadow {" +
            "box-shadow: 0 0 10px rgba(0,0,0,0.5);" +
            "padding: 0px;}</style>" +
            "<img class=\"shadow\" id='esl-image' src='" + data + "'>");
    }).error(function(jqXHR) {
        alert(jqXHR.responseText);
    });
}