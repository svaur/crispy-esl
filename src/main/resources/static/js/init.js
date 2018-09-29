$(document).ready(function(){
    $('.sidenav').sidenav();
    $('.dropdown-trigger').dropdown();
    downloadTable('#menu1', '/api/getTableData');
    $('#menu1').click(function(){
        downloadTable('#menu1', '/api/getTableData');
    });
    $('#menu2').click(function(){
        setActive('#menu2');
        downloadTable('#menu2', '/api/getAnotherTableData');
    });
    $('#menu3').click(function(){
        setActive('#menu3');
        $.getJSON('/api/getRandStr', {}, function(data){
            $('#workSpace').html('')
                .append("<h5>"+data+"</h5>")

        });
    });
    $('#menu4').click(function(){
        setActive('#menu4');
            $('#workSpace').html('')
                .append("<h5>sdfsdfsdfsdfsdfsdf</h5>")
    })
});
function downloadTable(menu, url) {
    setActive();
    $.getJSON(url, {}, function(data){
        var tableData = $.parseJSON(JSON.stringify(data));
        $('#workSpace').html('')
            .append("<table class=\"bordered striped centered highlight responsive-table\">")
            .append("<thead><tr><th>colum1</th><th>colum2</th><th>colum3</th><th>colum4</th><th>colum5</th><th>colum6</th><th>colum7</th><th>colum8</th><th>colum9</th></tr></thead>")
            .append("<tbody>");
        for ( var i=0; i<tableData.length; i++) {
            $('#workSpace').append("<tr><td>" + tableData[i].key1 + "</td><td>" + tableData[i].key2 + "</td><td>" + tableData[i].key3 + "</td><td>" + tableData[i].key4 + "</td><td>" + tableData[i].key5 + "</td><td>" + tableData[i].key6 + "</td><td>" + tableData[i].key7 + "</td><td>" + tableData[i].key8 + "</td>" +
                "<td><a class=\"waves-effect waves-light btn-small blue-grey lighten-3\">Button</a></td></tr>");
        }
        $('#workSpace')
            .append("</tbody>")
            .append("</table>")
    });
}
function setActive(nameClassToActive) {
    $('#menu1').removeClass("active");
    $('#menu2').removeClass("active");
    $('#menu3').removeClass("active");
    $('#menu4').removeClass("active");
    $(nameClassToActive).addClass("active");
}