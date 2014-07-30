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
            userInput.jName = ["Job Name:" , data[0].value ];
            userInput.location = ["Location:" , data[1].value ];
            userInput.worktype = ["Work Type:" , data[2].value ];
            userInput.priority = ["Priority:" , data[3].value ];
            userInput.people   = ["Workers Sent:" , data[4].value ];
            userInput.finishby = ["Finish By:" , data[5].value ];
            userInput.notes    = ["Notes:" , data[6].value ];
            localStorage.setItem(keyGen , JSON.stringify(userInput));
            location.reload()
        }
    });
});