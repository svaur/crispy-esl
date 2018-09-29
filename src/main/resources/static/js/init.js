$(document).ready(function(){
    // $('#dropdown1').click(function(){
        $.getJSON('/api/getTableData', {}, function(data){
            console.log(data)
            var tableData = $.parseJSON(JSON.stringify(data));
            $('#tableStructure').html('')
                .append("<table class=\"bordered striped centered highlight responsive-table\">")
                .append("<thead><tr><th>colum1</th><th>colum2</th><th>colum3</th><th>colum4</th><th>colum5</th><th>colum6</th><th>colum7</th><th>colum8</th><th>colum9</th></tr></thead>")
                .append("<tbody>")
                .append("<tr><td>" + tableData[0].key1 + "</td><td>" + tableData[0].key2 + "</td><td>" + tableData[0].key3 + "</td><td>" + tableData[0].key4 + "</td><td>" + tableData[0].key5 + "</td><td>" + tableData[0].key6 + "</td><td>" + tableData[0].key7 + "</td><td>" + tableData[0].key8 + "</td>" +
                    "<td><a class=\"waves-effect waves-light btn-small\">Button</a></td></tr>")
                .append("<tr><td>" + tableData[1].key1 + "</td><td>" + tableData[1].key2 + "</td><td>" + tableData[1].key3 + "</td><td>" + tableData[1].key4 + "</td><td>" + tableData[1].key5 + "</td><td>" + tableData[1].key6 + "</td><td>" + tableData[1].key7 + "</td><td>" + tableData[1].key8 + "</td>" +
                    "<td><a class=\"waves-effect waves-light btn-small\">Button</a></td></tr>")
                .append("<tr><td>" + tableData[2].key1 + "</td><td>" + tableData[2].key2 + "</td><td>" + tableData[2].key3 + "</td><td>" + tableData[2].key4 + "</td><td>" + tableData[2].key5 + "</td><td>" + tableData[2].key6 + "</td><td>" + tableData[2].key7 + "</td><td>" + tableData[2].key8 + "</td>" +
                    "<td><a class=\"waves-effect waves-light btn-small\">Button</a></td></tr>")
                .append("<tr><td>" + tableData[3].key1 + "</td><td>" + tableData[3].key2 + "</td><td>" + tableData[3].key3 + "</td><td>" + tableData[3].key4 + "</td><td>" + tableData[3].key5 + "</td><td>" + tableData[3].key6 + "</td><td>" + tableData[3].key7 + "</td><td>" + tableData[3].key8 + "</td>" +
                    "<td><a class=\"waves-effect waves-light btn-small\">Button</a></td></tr>")
                .append("</tr></tbody>")
                .append("</table>")
        });
    // })
});