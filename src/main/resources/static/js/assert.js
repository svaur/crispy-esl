function getAssertTemplate() {
    return "<span class='flow-text'>Привязка ценников</span>" +
        "<div class=\"divider\"></div>"+
        "<ul id=\"tabs-swipe-demo\" class=\"tabs tabs-fixed-width grey lighten-5\">" +
        "   <li class=\"tab col s3\"><a class=\"active\" href=\"#test-swipe-1\">Привязка</a></li>" +
        "   <li class=\"tab col s3\"><a href=\"#test-swipe-2\">Конфигурация</a></li>" +
        "</ul>" +
        "<div id=\"test-swipe-1\" class=\"col s12\">" +
            "<select>" +
                "<option value=\"add\">Привязка ценника</option>" +
                "<option value=\"delete\">Отвязать ценник</option>" +
            "</select>" +
        "    <form>" +
        "       <div class=\"row\">" +
        "       <div class=\"col s12 input-field\">" +
        "           <input id=\"eslInput\" type=\"text\" class=\"validate\">" +
        "           <label for=\"eslInput\">Ценник</label>" +
        "       </div>" +
        "       <div class=\"col s12 input-field\">" +
        "          <select id=\"templateInput\" class=\"validate\">" +
        "              <option value=\"default\">по умолчанию</option>" +
        "          </select>" +
        "           <label for=\"templateInput\">Шаблон</label>" +
        "       </div>" +
        "       <div class=\"col s12 input-field\">" +
        "           <input id=\"itemInput\" type=\"tel\" class=\"validate\">" +
        "           <label for=\"itemInput\">Товар</label>" +
        "       </div>" +
        "       </div>"+
        "    </form>" +
        "<a class=\"waves-effect waves-light btn-small\" onclick='cancelBtn()'><i class=\"material-icons left\">cancel</i>отменить</a>"+
        "<a class=\"waves-effect waves-light btn-small\" onclick='saveData()'><i class=\"material-icons left\">check</i>сохранить</a>"+
        "</div>" +
        "<div id=\"test-swipe-2\" class=\"col s12\">не готово</div>"
}

function saveData() {
    if (document.getElementById("eslInput").value===""){
        alert("введите данные для сохранения");
    }else {
        var dataToSave = {
            "esl": document.getElementById("eslInput").value,
            "template": document.getElementById("templateInput").value,
            "item": document.getElementById("itemInput").value
        };
        $.getJSON("/api/assignEsl", dataToSave, function (data) {
            alert(data);
        });
    }
}
function cancelBtn() {
    document.getElementById("eslInput").value="";
    document.getElementById("itemInput").value="";
}