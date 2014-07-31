//Global Variables
var maintenanceTypes = [ "General" , "Cleaning", "Painting", "A/C", "Electric" , "Plumbing"];

// Add Item Store Interactions
$(document).on('pageinit','#home',function(){
    // Add Select Label
    $("<label></label>")
        .appendTo('#typeOfWork')
        .prop('for', 'worktype')
        .text('What type of work is it?');

    // Add Select Option
    $("<select></select>")
        .appendTo('#typeOfWork')
        .attr("id","worktype")
        .attr("name","worktype")
        .attr("data-native-menu","false");
    for(var i=0,m=maintenanceTypes.length; i<m ; i++){
        $('<option></option>')
            .appendTo('#worktype')
            .attr("value", maintenanceTypes[i])
            .text(maintenanceTypes[i])
    }

    // Refresh Style
    $("#additemform").trigger("create");

    // Validate the Data & Store & Check Against Data
    $('#additemform').validate({
        invalidHandler: function(form, validator) {},
        submitHandler: function() {
            // Store Data
            var data = $('#additemform').serializeArray(),
                d = new Date(),
                keyGen = d.getTime(),
                userInput = {};
            userInput.name = data[0].value ;
            userInput.location = data[1].value;
            userInput.type = data[2].value;
            userInput.date = data[3].value;

            var json = JSON.stringify(userInput);
            $('#additemform').trigger("reset");
            Android.saveData(json);

        }
    });
});