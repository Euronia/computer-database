jQuery.validator.addMethod("customDateValidator",
    function(value, element) {
        return this.optional(element) || moment(value,"DD/MM/YYYY").isValid();
}, "Please enter a valid date"
);

$(function() {
  // Initialize form validation on the addComputer form.
  // It has the name attribute "addComputer"
  $('#addComputer').validate({
	errorClass: "my-error-class",
    // Specify validation rules
    rules: {
      // The key name on the left side is the name attribute
      // of an input field. Validation rules are defined
      // on the right side
      name: {required : true, minlength: 2},
      introduced: {required : false, dateISO: true, customDateValidator: true},
      discontinued: {required : false, dateISO: true, customDateValidator: true}
    },
    // Specify validation error messages
    messages: {
      name: { required : "<li>Please enter a name</li>",
    	  	  minlength : "<li>Name must be longer than 1 character</li>"},
      introduced: { date : "<li>Please enter a valid date</li>"},
      discontinued: { date : "<li>Please enter a valid date</li>"}
    }
  });
});